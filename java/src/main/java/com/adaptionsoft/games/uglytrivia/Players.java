package com.adaptionsoft.games.uglytrivia;

public class Players {

    private CircularList<Player> players = new CircularList<>();
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
        Node<Player> node = new Node(player);
        players.add(node);
    }

    int size() {
        return players.size();
    }

    public void moveCurrentPlayer(int roll) {
        Player currentPlayer = getCurrentPlayer();
        int currentPlayerPlace = currentPlayer.getPlace();
        currentPlayerPlace += roll;
        if (currentPlayerPlace >= Game.PLAYER_PLACE_LIMIT) {
            currentPlayerPlace -= Game.PLAYER_PLACE_LIMIT;
        }
        currentPlayer.setPlace(currentPlayerPlace);
    }

    public void movePlayerToPenaltyBox() {
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.setInPenaltyBox();
    }

    public void nextPlayer() {
        players.next();
    }

    private Player getCurrentPlayer() {
        return players.current();
    }
}
