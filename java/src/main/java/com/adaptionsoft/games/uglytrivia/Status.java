package com.adaptionsoft.games.uglytrivia;

public class Status {

    private Console console;

    public Status(Console console) {
        this.console = console;
    }

    public void informAboutNotToGetOutOFPenaltyBox(String currentPlayerName) {
        console.print(currentPlayerName + " is not getting out of the penalty box");
    }

    public void informAboutTheRoll(int roll) {
        console.print("They have rolled a " + roll);
    }

    public void informAboutTheCurrentPlayer(String currentPlayerName) {
        console.print(currentPlayerName + " is the current player");
    }

    public void informAboutNewLocation(String currentPlayerName, int currentPlayerPlace) {
        console.print(currentPlayerName
                + "'s new location is "
                + currentPlayerPlace);
    }

    public void informAboutCategory(String currentCategory) {
        console.print("The category is " + currentCategory);
    }

    public void informAboutQuestion(Object question) {
        console.print(question.toString());
    }

    public void informAboutUserGettingOutOfPenaltyBox(String currentPlayerName) {
        console.print(currentPlayerName + " is getting out of the penalty box");
    }

    public void informAboutAddedPlayer(String playerName) {
        console.print(playerName + " was added");
    }

    public void informAboutNumberOfPlayers(int size) {
        console.print("They are player number " + size);
    }

    public void informAboutCorrectAnswer() {
        console.print("Answer was correct!!!!");
    }

    public void informAboutGoldCoins(String currentPlayerName, int currentPlayerGoldCoins) {
        console.print(currentPlayerName
                + " now has "
                + currentPlayerGoldCoins
                + " Gold Coins.");
    }

    public void informAboutWrongAnswer() {
        console.print("Question was incorrectly answered");
    }

    public void informAboutUserGettingInPenaltyBox(String currentPlayerName) {
        console.print(currentPlayerName + " was sent to the penalty box");
    }
}
