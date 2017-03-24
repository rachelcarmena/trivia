package com.adaptionsoft.games.uglytrivia;

public class Console {

    public void informAboutNotToGetOutOFPenaltyBox(String currentPlayerName) {
        System.out.println(currentPlayerName + " is not getting out of the penalty box");
    }

    public void informAboutTheRole(int roll) {
        System.out.println("They have rolled a " + roll);
    }

    public void informAboutTheCurrentPlayer(String currentPlayerName) {
        System.out.println(currentPlayerName + " is the current player");
    }

    public void informAboutNewLocation(String currentPlayerName, int currentPlayerPlace) {
        System.out.println(currentPlayerName
                + "'s new location is "
                + currentPlayerPlace);
    }

    public void informAboutCategory(String currentCategory) {
        System.out.println("The category is " + currentCategory);
    }

    public void informAboutQuestion(Object question) {
        System.out.println(question);
    }

    public void informAboutUserGettingOutOfPenaltyBox(String currentPlayerName) {
        System.out.println(currentPlayerName + " is getting out of the penalty box");
    }

    public void informAboutAddedPlayer(String playerName) {
        System.out.println(playerName + " was added");
    }

    public void informAboutNumberOfPlayers(int size) {
        System.out.println("They are player number " + size);
    }
}
