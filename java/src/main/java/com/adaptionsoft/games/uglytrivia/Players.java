package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;

public class Players {
    private ArrayList names = new ArrayList();
    private int[] places = new int[6];
    private int[] purses  = new int[6];
    private boolean[] inPenaltyBox  = new boolean[6];
    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;

    public int[] getPlaces() {
        return places;
    }

    public int[] getPurses() {
        return purses;
    }

    public boolean[] getInPenaltyBox() {
        return inPenaltyBox;
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

    int currentPlayerPlace() {
        return getPlaces()[getCurrentPlayer()];
    }

    boolean currentPlayerIsInPenaltyBox() {
        return getInPenaltyBox()[getCurrentPlayer()];
    }

    Object currentPlayerName() {
        return names.get(getCurrentPlayer());
    }

    void increaseGoldCoins() {
        getPurses()[getCurrentPlayer()]++;
    }

    int currentPlayerGoldCoins() {
        return getPurses()[getCurrentPlayer()];
    }

    void add(String playerName) {
        names.add(playerName);
        places[size()] = 0;
        purses[size()] = 0;
        inPenaltyBox[size()] = false;
    }

    int size() {
        return names.size();
    }

    void moveCurrentPlayer(int roll) {
        places[currentPlayer] += roll;
        if (this.places[this.currentPlayer] > 11){
            this.places[this.currentPlayer] -= 12;
        }
    }
}
