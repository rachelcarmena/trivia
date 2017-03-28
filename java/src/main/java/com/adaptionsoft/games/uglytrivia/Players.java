package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.List;

public class Players {

    public static final int PLAYER_PLACE_LIMIT = 11;
    private List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private boolean isGettingOutOfPenaltyBox;

    public boolean isGettingOutOfPenaltyBox() {
        return isGettingOutOfPenaltyBox;
    }

    public void setGettingOutOfPenaltyBox(boolean gettingOutOfPenaltyBox) {
        isGettingOutOfPenaltyBox = gettingOutOfPenaltyBox;
    }

    public int currentPlayerPlace() {
        Player currentPlayer = getCurrentPlayer();
        return currentPlayer.getPlace();
    }

    public boolean currentPlayerIsInPenaltyBox() {
        Player currentPlayer = getCurrentPlayer();
        return currentPlayer.inPenaltyBox();
    }

    public String currentPlayerName() {
        Player currentPlayer = getCurrentPlayer();
        return currentPlayer.getName();
    }

    public int currentPlayerGoldCoins() {
        Player player = getCurrentPlayer();
        return player.getPurse();
    }

    public void increaseGoldCoins() {
        Player currentPlayer = getCurrentPlayer();
        int currentPurse = currentPlayer.getPurse();
        currentPurse++;
        currentPlayer.setPurse(currentPurse);
    }

    public void add(String playerName) {
        Player player = new Player(playerName);
        players.add(player);
    }

    int size() {
        return players.size();
    }

    public void moveCurrentPlayer(int roll) {
        Player currentPlayer = getCurrentPlayer();
        int currentPlayerPlace = currentPlayer.getPlace();
        currentPlayerPlace += roll;
        if (currentPlayerPlace > PLAYER_PLACE_LIMIT){
            currentPlayerPlace -= (PLAYER_PLACE_LIMIT + 1);
        }
        currentPlayer.setPlace(currentPlayerPlace);
    }

    public void movePlayerToPenaltyBox() {
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.setInPenaltyBox();
    }

    public void nextPlayer() {
        this.currentPlayerIndex += 1;
        if (currentPlayerIndex == size()) this.currentPlayerIndex = 0;
    }

    private Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
}
