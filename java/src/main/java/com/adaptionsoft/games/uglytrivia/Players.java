package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;

public class Players {
    private ArrayList names = new ArrayList();
    private int[] places = new int[6];
    private int[] purses  = new int[6];
    private boolean[] inPenaltyBox  = new boolean[6];
    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;

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
        return places[getCurrentPlayer()];
    }

    boolean currentPlayerIsInPenaltyBox() {
        return inPenaltyBox[currentPlayer];
    }

    Object currentPlayerName() {
        return names.get(getCurrentPlayer());
    }

    void increaseGoldCoins() {
        purses[getCurrentPlayer()]++;
    }

    int currentPlayerGoldCoins() {
        return purses[getCurrentPlayer()];
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

    void movePlayerToPenaltyBox() {
        inPenaltyBox[currentPlayer] = true;
    }
}
