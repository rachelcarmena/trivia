package com.adaptionsoft.games.trivia.delivery;

public class Status {

    private Console console;

    public Status(Console console) {
        this.console = console;
    }

    public void informAboutNotToGetOutOFPenaltyBox(String currentPlayerName) {
        console.print(currentPlayerName + " is not getting out of the penalty box");
    }

    public void informAboutUserGettingOutOfPenaltyBox(String currentPlayerName) {
        console.print(currentPlayerName + " is getting out of the penalty box");
    }

    public void informAboutAddedPlayer(String playerName, int size) {
        console.print(playerName + " was added");
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

    public void informAboutLocationCategoryAndQuestion(String playerName, int place, String category, String question) {
        console.print(playerName
                + "'s new location is "
                + place);
        console.print("The category is " + category);
        console.print(question);
    }

    public void informAboutCurrentPlayerAndRoll(String playerName, int roll) {
        console.print(playerName + " is the current player");
        console.print("They have rolled a " + roll);
    }
}
