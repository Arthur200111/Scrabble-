package com.packagenemo.scrabble_plus.lobby;

/**
 * Cette classe représente un les données d'un élément du menu déroulant
 */
public class PlayerData {
    private String playerName;
    private int playerImgId;

    /**
     * Constructeur de base d'une donnée
     * @param playerName nom du joueur
     * @param playerImgId id de l'image du joueur
     */
    public PlayerData(String playerName, int playerImgId) {
        this.playerName = playerName;
        this.playerImgId = playerImgId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerImgId() {
        return playerImgId;
    }

    public void setPlayerImgId(int playerImgId) {
        this.playerImgId = playerImgId;
    }
}
