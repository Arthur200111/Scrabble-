package com.packagenemo.scrabble_plus.jeu.manager;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.packagenemo.scrabble_plus.jeu.callback.StringInterface;
import com.packagenemo.scrabble_plus.jeu.model.Joueur;
import com.packagenemo.scrabble_plus.jeu.model.Parametres;
import com.packagenemo.scrabble_plus.jeu.model.Pioche;
import com.packagenemo.scrabble_plus.jeu.callback.PartieInterface;
import com.packagenemo.scrabble_plus.jeu.repository.PartieRepository;

public class    PartieManager {

    private static volatile PartieManager instance;
    private PartieRepository partieRepository;

    private PartieManager() {
        partieRepository = PartieRepository.getInstance();
    }

    public static PartieManager getInstance() {
        PartieManager result = instance;
        if (result != null) {
            return result;
        }
        synchronized(PartieManager.class) {
            if (instance == null) {
                instance = new PartieManager();
            }
            return instance;
        }
    }

    public CollectionReference getJoueurs(String numero_partie){
        return partieRepository.getJoueursCollection(numero_partie);
    }
    public CollectionReference getPioche(String numero_partie){
        return partieRepository.getPiocheCollection(numero_partie);
    }
    public CollectionReference getCoups(String numero_partie){
        return partieRepository.getAllCoups(numero_partie);
    }
    public CollectionReference getParametres(String numero_partie){
        return partieRepository.getParametres(numero_partie);
    }


    public Task<DocumentSnapshot> getPartieInfo(String numero_partie){
        return partieRepository.getPartieInfo(numero_partie);
    }

    public Task<QuerySnapshot> getPartieFromCode(String code){
        return partieRepository.getPartieFromCode(code);
    }

    public void createPartie(String nom_partie, String code, String plateau, Pioche pioche, Parametres parametres){
        partieRepository.createPartie(nom_partie,code,plateau,pioche, parametres);
    }

    public void isPartieExisting(String idPartie, StringInterface pi){
        this.partieRepository.isPartieExisting(idPartie, pi);
    }

    /*public void joinPartie(String code_partie, Joueur joueur){
        partieRepository.joinPartie(code_partie, joueur);
    }*/


    public boolean joinPartie(String idPartie){
        return partieRepository.joinPartie(idPartie);
    }

    public void findPlayersInPartie(String idPartie, PartieInterface pi){
        this.partieRepository.findPlayersInPartie(
                idPartie,
                pi
        );
    }


    public CollectionReference getJoueursCollection(String numero_partie){
        return partieRepository.getJoueursCollection(numero_partie);
    }

    public String getPiocheInfo(String numero){
        return partieRepository.getPiocheInfo(numero);
    }

    public DocumentSnapshot getLastCoupInfo(String numero,int nombre){
        return partieRepository.getLastCoupInfo(numero,nombre);
    }


    public void getPartieFromUser(PartieInterface cb){
        this.partieRepository.getPartieFromUser(cb);
    }

    public void getPlateauFromPartie(String str, StringInterface si){
        this.partieRepository.getPlateauFromPartie(str, si);
    }


    public CollectionReference getCollectionReference(String idPartie){
        return this.partieRepository.getCollectionReference(idPartie);
    }
    /*
    public HashMap partieInfo = new HashMap<>();


    public HashMap getPartieInfo(){
        readData(new PartieManager.CallBack() {
            @Override
            public void onCallBack(HashMap data) {
                Log.d("debug", "dans callback final");
                Log.d("debug",data.toString());
            }
        });
        return partieInfo;
    }

    public void readData(CallBack callBack){
        Task<DocumentSnapshot> task = partieRepository.getPartieInfo("jeux000001");
        task.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                Log.d("debug Partie manager",doc.getData().toString() );

                partieInfo.putAll(doc.getData());
                callBack.onCallBack(partieInfo);
            }
        });
        Log.d("debug Partie manager","end function" );
    }

    public interface CallBack {
        void onCallBack(HashMap data);
    }

     */
}
