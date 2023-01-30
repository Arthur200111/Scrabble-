package com.packagenemo.scrabble_plus.jeu.ui;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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

    private String mDossierImages;
    private String mCheminImagesLettres;
    private String mCheminImagesPlateau;
    private String mCheminImagesMain;

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

         // TODO : Mettre en place la bibliothèque des images déjà chargées

         String[] arrayString = stringCaseToArray(stringCase);

         // liste des bitmaps qui devront s'assembler pour représenter la case
         List<Bitmap> listBitmap = new ArrayList<>();

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

         // TODO : Traiter le HighLight

         return combinerBitmaps(listBitmap);
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

    public String[] stringCaseToArray(String stringCase){
        stringCase = (stringCase == null || stringCase.length() == 0)
                ? null
                : (stringCase.substring(0, stringCase.length() - 1));

        return stringCase.split(",");
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

    private Bitmap chargeBitmap(String nomImage, int dslWidth, int dstHeight){

        int resID = mContext.getResources().getIdentifier(
                nomImage , mDossierImages, mContext.getPackageName());
        Bitmap bitmap = BitmapFactory.decodeResource(
                mContext.getResources(), resID);

        return Bitmap.createScaledBitmap(bitmap, dslWidth, dstHeight,false);
    }
}
