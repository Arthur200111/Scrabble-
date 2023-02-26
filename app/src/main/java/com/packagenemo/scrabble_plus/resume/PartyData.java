package com.packagenemo.scrabble_plus.resume;

public class PartyData {
    private String partyLabel;
    private String partyState;
    private int partyImgId;

    public PartyData(String partyLabel, String partyState, int partyImgId) {
        this.partyLabel = partyLabel;
        this.partyState = partyState;
        this.partyImgId = partyImgId;
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
