package com.packagenemo.scrabble_plus.lobby;

public class PlayerData {
    private String playerName;
    private int playerImgId;

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
