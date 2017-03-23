package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;

public class Players {
    private ArrayList players = new ArrayList();
    private int[] places = new int[6];
    private int[] purses  = new int[6];
    private boolean[] inPenaltyBox  = new boolean[6];
    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;

    public ArrayList getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList players) {
        this.players = players;
    }

    public int[] getPlaces() {
        return places;
    }

    public void setPlaces(int[] places) {
        this.places = places;
    }

    public int[] getPurses() {
        return purses;
    }

    public void setPurses(int[] purses) {
        this.purses = purses;
    }

    public boolean[] getInPenaltyBox() {
        return inPenaltyBox;
    }

    public void setInPenaltyBox(boolean[] inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean isGettingOutOfPenaltyBox() {
        return isGettingOutOfPenaltyBox;
    }

    public void setGettingOutOfPenaltyBox(boolean gettingOutOfPenaltyBox) {
        isGettingOutOfPenaltyBox = gettingOutOfPenaltyBox;
    }
}
