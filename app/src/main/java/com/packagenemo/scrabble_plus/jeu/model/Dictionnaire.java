package com.packagenemo.scrabble_plus.jeu.model;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

import android.content.res.Resources;

import com.packagenemo.scrabble_plus.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Dictionnaire {
    private ArrayList<String> listMot;
    private int longueur_dico;

    /**
     *
     */
    public Dictionnaire() {
        listMot = new ArrayList<>();
        //this.loadDico(R.raw.dictionnaire_francais);
    }


    /**
     * @return List<String>
     */
    public List<String> getListMot() {
        return this.listMot;
    }


    /**
     * @param listMot
     */
    public void setListMot(ArrayList<String> listMot) {
        this.listMot = listMot;
    }


    /**
     *
     */
    public void loadDico(int path){
        try {
            InputStream is = Resources.getSystem().openRawResource(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            longueur_dico = Integer.parseInt(br.readLine());
            for (int i=0; i<longueur_dico; i++){
                String line = br.readLine();
                listMot.add(line);
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * @param mot
     * @return boolean
     */
    public boolean verifMot(String mot){

        int i = longueur_dico;
        int j = 0;
        int k;
        String compareMot;

        while (abs(i-j)>1){
            k = (i+j)/2;
            compareMot = listMot.get(k);
            if(compareMot.compareTo(mot)==0) {
                return true;
            }
            else if (compareMot.compareTo(mot)>0){
                j = min(i,j);
            }
            else if (compareMot.compareTo(mot)<0){
                j = max(i,j);
            }
            else{
                j = min(i,j);
            }

            i = k;
        }
        return listMot.get(j).equals(mot);
    }
}
