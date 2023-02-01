package com.packagenemo.scrabble_plus.jeu.ui;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import com.packagenemo.scrabble_plus.R;
import com.packagenemo.scrabble_plus.jeu.model.Lettre;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Cette classe sert aux affichages graphiques du jeu
 * Permet de convertir certaines classes en image
 */
public class BanqueImages {

    // Représente la luminosité des cases en surbrillance. Entre -255 et 255
    private int BRIGHTNESS_HIGHLIGHT = 40;
    private int LIMITE_IMAGES_CHARGEES = 500;

    private String mDossierImages;
    private String mCheminImagesLettres;
    private String mCheminImagesPlateau;
    private String mCheminImagesMain;

    // Map qui stoque toutes les images déjà chargées
    private Map<String, Bitmap> mImagesChargees;

    // Map qui stocke toutes les variables du chemin de config
    private Map<String, String[]> mMapConfig;
    private Context mContext;

    private static Logger logger = Logger.getLogger(String.valueOf(BanqueImages.class));

    /**
     * Initialise l'instance avec le chemin contenant les images
     * @param context
     */
    public BanqueImages(Context context){

        mContext = context;

        mImagesChargees = new HashMap<>();

        chargerCheminsImages();
    }

    /**
     * Converti une instance de case en image
     *
     * @param stringCase
     * @param dslWidth
     * @param dstHeight
     * @return
     */
     public Bitmap convertCaseToBitmap(String stringCase, int dslWidth, int dstHeight){

         Bitmap imageCase;

         // On commence par chercher si une occurence de l'image qui doit être créée n'a pas été déjà chargée
         imageCase = chercheDansImagesChargees(stringCase, dslWidth, dstHeight);
         if (imageCase != null){
             return imageCase;
         }
         // Sinon, on va devoir en créer une nouvelle.

         // liste des bitmaps qui devront s'assembler pour représenter la case
         List<Bitmap> listBitmap = new ArrayList<>();

         String[] arrayString = stringCase.split(",");

         // La première lettre de notre String nous indique quel est le type de la case traitee
         switch (arrayString[0]){
             case "0":
                 caseVidePlateau(arrayString, listBitmap, dslWidth, dstHeight);
                 break;
             case "1":
                 caseVideMain(arrayString, listBitmap, dslWidth, dstHeight);
                 break;
             case "2":
                 caseLettre(arrayString, listBitmap, dslWidth, dstHeight);
                 break;
             default:
                 logger.warning("String " + stringCase + " image non traite car inconnue");
                 break;
         }

         // On combine toutes les images qui composent le bitmap pour n'en avoir plus qu'une
         imageCase = combinerBitmaps(listBitmap);

         // Le 4 ème caractère nous indique si la case doit être en surbrillance
         if (arrayString[3].equals("1")){
             imageCase = changeBitmapContrastBrightness(imageCase, 1,
                     BRIGHTNESS_HIGHLIGHT);
         }

         // On enregiste l'image dans nos images chargées pour ne pas avoir à répéter le processus
         // lorsque l'on aura à charger exactement la même image
         enregistrerImage(stringCase, imageCase);

         return imageCase;
    }

    private Bitmap chercheDansImagesChargees(String stringCase, int dslWidth, int dstHeight){
        Bitmap imageChargee = mImagesChargees.get(stringCase);
        if (imageChargee == null){
            return null;
        }
        return imageChargee.copy(imageChargee.getConfig(), imageChargee.isMutable());
    }

    private void enregistrerImage(String stringCase, Bitmap imageCase){
        mImagesChargees.put(stringCase, imageCase);
         if (mImagesChargees.size() > LIMITE_IMAGES_CHARGEES){
             logger.warning("Attention, le nombre max d'images chargées a été atteint");
         }
    }


    public void caseVidePlateau(String[] arrayString, List<Bitmap> bitmapListe, int dslWidth, int dstHeight){
        Bitmap bitmap;
         String nomImage;

         for (String element : mMapConfig.get("plateau_" + arrayString[1])) {
             nomImage = mCheminImagesPlateau + element;
             bitmap = chargeBitmap(nomImage, dslWidth, dstHeight);
             bitmapListe.add(bitmap);
         }
    }

    public void caseVideMain(String[] arrayString, List<Bitmap> bitmapListe, int dslWidth, int dstHeight){

        String nomImage;
        // Background
        nomImage = mCheminImagesMain + "background";
        bitmapListe.add(chargeBitmap(nomImage, dslWidth, dstHeight));
    }

    public void caseLettre(String[] arrayString, List<Bitmap> bitmapListe, int dslWidth, int dstHeight){

        String nomImage;
        // Background
        nomImage = mCheminImagesLettres + "background";
        bitmapListe.add(chargeBitmap(nomImage, dslWidth, dstHeight));

        // Lettre
        nomImage = mCheminImagesLettres + arrayString[1].toLowerCase();
        bitmapListe.add(chargeBitmap(nomImage, dslWidth, dstHeight));

        // Points des lettres
        nomImage = mCheminImagesLettres + Integer.valueOf(arrayString[2]);
        bitmapListe.add(chargeBitmap(nomImage, dslWidth, dstHeight));
    }

    public void chargerCheminsImages(){
        Properties properties = new Properties();
        try {
            AssetManager assetManager = mContext.getAssets();
            InputStream inputStream = assetManager.open("CheminsImages.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mDossierImages = properties.getProperty("dossierImages");

        mCheminImagesLettres = properties.getProperty("cheminLettres");
        mCheminImagesPlateau = properties.getProperty("cheminPlateau");
        mCheminImagesMain = properties.getProperty("cheminMain");

        // On charge tous les empilements de backgrounds pour le plateau
        mMapConfig = new HashMap<>();
        String cle;
        String stringTemp;
        for (int i = 0 ; i <= 5 ; i++){
            cle = "plateau_" + i;

            stringTemp = properties.getProperty(cle).split("#")[1];
            mMapConfig.put(cle ,stringTemp.split(","));
        }
    }

    /**
     * Combiner les images Bitmap en un seul Bitmap
     * @param bitmap
     * @return
     */
    private Bitmap combinerBitmaps(List<Bitmap> bitmap) {

        if (bitmap.size() <= 1){
            return bitmap.get(0);
        }

        int w = bitmap.get(0).getWidth();
        int h = bitmap.get(0).getHeight();

        Bitmap temp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(temp);
        for (int i = 0; i < bitmap.size(); i++) {
            canvas.drawBitmap(bitmap.get(i), 0f, 0f, null);
        }
        return temp;
    }

    /**
     *
     * @param bmp input bitmap
     * @param contrast 0..10 1 is default
     * @param brightness -255..255 0 is default
     * @return new bitmap
     */
    private Bitmap changeBitmapContrastBrightness(Bitmap bmp, float contrast, float brightness)
    {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        contrast, 0, 0, 0, brightness,
                        0, contrast, 0, 0, brightness,
                        0, 0, contrast, 0, brightness,
                        0, 0, 0, 1, 0
                });

        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return ret;
    }

    private Bitmap chargeBitmap(String nomImage, int dslWidth, int dstHeight){

        int resID = mContext.getResources().getIdentifier(
                nomImage , mDossierImages, mContext.getPackageName());
        Bitmap bitmap = BitmapFactory.decodeResource(
                mContext.getResources(), resID);

        return Bitmap.createScaledBitmap(bitmap, dslWidth, dstHeight,false);
    }
}
