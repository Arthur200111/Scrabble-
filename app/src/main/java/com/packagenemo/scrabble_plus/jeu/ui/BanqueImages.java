package com.packagenemo.scrabble_plus.jeu.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Génère des images pour l'affichage du jeu de Scrabble.
 */
public class BanqueImages {

    // Représente la luminosité des cases en surbrillance. Entre -255 et 255
    private final int BRIGHTNESS_HIGHLIGHT = 40;
    private final int LIMITE_IMAGES_CHARGEES = 500;
    private final String FICHIER_PROPERTIES = "CheminsImages.properties";

    // Chemins des images utilisées
    private String mDossierImages;
    private String mCheminImagesLettres;
    private String mCheminImagesPlateau;
    private String mCheminImagesMain;

    // Map qui stocke toutes les variables du fichier properties
    private Map<String, String[]> mMapConfig;

    // Map qui stoque toutes les images déjà chargées
    private final Map<String, Bitmap> mImagesChargees;

    // Contexte dans lequel afficher les images
    private final Context mContext;

    private static final Logger logger = Logger.getLogger(String.valueOf(BanqueImages.class));

    /**
     * Initialise l'instance, charge le chemin des images
     * @param context : contexte d'affichage
     */
    public BanqueImages(Context context){
        mContext = context;

        mImagesChargees = new HashMap<>();

        chargerCheminsImages();
    }

    /**
     * Charge le chemin des images utilisés dans l'affichage
     */
    public void chargerCheminsImages(){
        Properties properties = new Properties();
        try {
            AssetManager assetManager = mContext.getAssets();
            InputStream inputStream = assetManager.open(FICHIER_PROPERTIES);
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
     * Converti un string en image.
     * Le string est codifié en utilisant des normes établies à l'avance
     *
     * @param stringCase : 4 caractères séparés par des virgules "*,*,*,*" : type,caractère,points,hightlight
     * @param dslWidth : Largeur de l'image générée
     * @param dstHeight : Hauteur de l'image générée
     * @return : Un Bitmap aux dimensions précisés
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

    /**
     * La classe stocke toutes les images qu'elle crée.
     * Si une même image est demandée deux fois, on la copie plutôt que de la charger deux fois.
     * @param stringCase : 4 caractères séparés par des virgules "*,*,*,*" : type,caractère,points,hightlight
     * @param dslWidth : Largeur de l'image générée
     * @param dstHeight : Hauteur de l'image générée
     * @return : Un Bitmap aux dimensions précisés
     */
    private Bitmap chercheDansImagesChargees(String stringCase, int dslWidth, int dstHeight){
        Bitmap imageChargee = mImagesChargees.get(stringCase);
        if (imageChargee == null){
            return null;
        }
        return imageChargee.copy(imageChargee.getConfig(), imageChargee.isMutable());
    }

    /**
     * Enregistre une image chargée
     * @param stringCase : 4 caractères séparés par des virgules "*,*,*,*" : type,caractère,points,hightlight
     * @param imageCase : Bitmap de l'image
     */
    private void enregistrerImage(String stringCase, Bitmap imageCase){
        mImagesChargees.put(stringCase, imageCase);

        // On fixe une limite au nombre d'images enregistrées. permet d'éviter les fuites de mémoire
         if (mImagesChargees.size() > LIMITE_IMAGES_CHARGEES){
             logger.warning("Attention, le nombre max d'images chargées a été atteint");
         }
    }

    /**
     * Charge une "case vide" du plateau. C'est à dire une case de type 0 qui ne contient pas de
     * lettre, à destination du plateau
     * @param arrayString : array de 4 caractères représentant le type,caractère,points,hightlight
     * @param bitmapListe : Liste des bitmaps à empiler pour obtenir l'image finale
     * @param dslWidth : Largeur de l'image générée
     * @param dstHeight : Hauteur de l'image générée
     */
    public void caseVidePlateau(
            String[] arrayString, List<Bitmap> bitmapListe, int dslWidth, int dstHeight){
        Bitmap bitmap;
         String nomImage;

         for (String element : Objects.requireNonNull(mMapConfig.get("plateau_" + arrayString[1]))) {
             nomImage = mCheminImagesPlateau + element;
             bitmap = chargeBitmap(nomImage, dslWidth, dstHeight);
             bitmapListe.add(bitmap);
         }
    }

    /**
     * Charge une "case vide" de la main. C'est à dire une case de type 1 qui ne contient pas de
     * lettre, à destination de la main
     * @param arrayString : array de 4 caractères représentant le type,caractère,points,hightlight
     * @param bitmapListe : Liste des bitmaps à empiler pour obtenir l'image finale
     * @param dslWidth : Largeur de l'image générée
     * @param dstHeight : Hauteur de l'image générée
     */
    public void caseVideMain(
            String[] arrayString, List<Bitmap> bitmapListe, int dslWidth, int dstHeight){

        String nomImage;
        // Background
        nomImage = mCheminImagesMain + "background";
        bitmapListe.add(chargeBitmap(nomImage, dslWidth, dstHeight));
    }

    /**
     * Charge une case de type lettre. C'est à dire une case de type 2 qui représente une lettre
     * @param arrayString : array de 4 caractères représentant le type,caractère,points,hightlight
     * @param bitmapListe : Liste des bitmaps à empiler pour obtenir l'image finale
     * @param dslWidth : Largeur de l'image générée
     * @param dstHeight : Hauteur de l'image générée
     */
    public void caseLettre(
            String[] arrayString, List<Bitmap> bitmapListe, int dslWidth, int dstHeight){

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



    /**
     * Combiner les images Bitmap en un seul Bitmap de taille unique
     * @param bitmap : liste des bitmaps qui composeront l'images
     * @return : un Bitmap qui sera l'empilement des images de la liste
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
     * Permet d'ajuste le contraste et la luminosité d'un bitmap
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

    /**
     * Charge un bitmap dont le nom est spécifié
     * @param nomImage : nom de l'image que l'on veut charger
     * @param dslWidth : Largeur de l'image générée
     * @param dstHeight : Hauteur de l'image générée
     * @return : Limage spécifiée récupérée en Bitmap
     */
    private Bitmap chargeBitmap(String nomImage, int dslWidth, int dstHeight){

        @SuppressLint("DiscouragedApi") int resID = mContext.getResources().getIdentifier(
                nomImage , mDossierImages, mContext.getPackageName());
        Bitmap bitmap = BitmapFactory.decodeResource(
                mContext.getResources(), resID);

        return Bitmap.createScaledBitmap(bitmap, dslWidth, dstHeight,false);
    }
}
