package com.packagenemo.scrabble_plus.resume;

/**
 * Cette classe représente un les données d'un élément du menu déroulant
 */
public class PartyData {
    private String partyLabel;
    private String id;
    private String partyState;
    private int partyImgId;

    /**
     * Constructeur de base d'une donnée
     * @param partyLabel nom de la partie
     * @param partyState état de la partie
     * @param partyImgId id de l'image de la partie
     */
    public PartyData(String partyLabel, String partyState, int partyImgId, String id) {
        this.partyLabel = partyLabel;
        this.partyState = partyState;
        this.partyImgId = partyImgId;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartyLabel() {
        return partyLabel;
    }

    public void setPartyLabel(String partyLabel) {
        this.partyLabel = partyLabel;
    }

    public String getPartyState() {
        return partyState;
    }

    public void setPartyState(String partyState) {
        this.partyState = partyState;
    }

    public int getPartyImgId() {
        return partyImgId;
    }

    public void setPartyImgId(int partyImgId) {
        this.partyImgId = partyImgId;
    }
}
