package com.adaptionsoft.games.uglytrivia;

public class Console {
    public void informAboutGettingOutOfPenaltyBox(String currentPlayerName) {
        System.out.println(currentPlayerName + " is getting out of the penalty box");
    }

    public void informAboutNotToGetOutOFPenaltyBox(String currentPlayerName) {
        System.out.println(currentPlayerName + " is not getting out of the penalty box");
    }

    public void informAboutTheRole(int roll) {
        System.out.println("They have rolled a " + roll);
    }

    public void informAboutTheCurrentPlayer(String currentPlayerName) {
        System.out.println(currentPlayerName + " is the current player");
    }
}
