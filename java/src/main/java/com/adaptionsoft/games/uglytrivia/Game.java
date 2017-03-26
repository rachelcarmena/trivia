package com.adaptionsoft.games.uglytrivia;

public class Game {

    private Questions questions;
    private Players players;
    private Console console;

    public Game() {
        this(new Players(), new Console(), new Questions());
    }

    public Game(Players players, Console console, Questions questions) {
        this.players = players;
        this.console = console;
        this.questions = questions;
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    public boolean add(String playerName) {
        players.add(playerName);

        console.informAboutAddedPlayer(playerName);
        console.informAboutNumberOfPlayers(players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        console.informAboutTheCurrentPlayer(players.currentPlayerName());
        console.informAboutTheRoll(roll);

        if (!players.currentPlayerIsInPenaltyBox()) {
            move(roll);
            return;
        }

        if (shouldGetOutOfPenaltyBox(roll)) {
            players.setGettingOutOfPenaltyBox(true);
            console.informAboutUserGettingOutOfPenaltyBox(players.currentPlayerName());
            move(roll);
            return;
        }

        console.informAboutNotToGetOutOFPenaltyBox(players.currentPlayerName());
        players.setGettingOutOfPenaltyBox(false);
    }

    private boolean shouldGetOutOfPenaltyBox(int roll) {
        return roll % 2 != 0;
    }

    private void move(int roll) {
        players.moveCurrentPlayer(roll);
        console.informAboutNewLocation(players.currentPlayerName(), players.currentPlayerPlace());

        String currentCategory = questions.currentCategory(players.currentPlayerPlace());
        console.informAboutCategory(currentCategory);
        askQuestion(currentCategory);
    }

    private void askQuestion(String currentCategory) {
        Object question = questions.getQuestionAndRemoveFromList(currentCategory);
        console.informAboutQuestion(question);
    }

    public boolean wasCorrectlyAnswered() {
        if (players.currentPlayerIsInPenaltyBox() && !players.isGettingOutOfPenaltyBox()) {
            players.nextPlayer();
            return true;
        }

        console.informAboutCorrectAnswer();
        players.increaseGoldCoins();
        console.informAboutGoldCoins(players.currentPlayerName(), players.currentPlayerGoldCoins());

        boolean notWinner = hasNotMaxGoldCoins();
        players.nextPlayer();

        return notWinner;
    }

    public boolean wrongAnswer() {
        console.informAboutWrongAnswer();
        console.informAboutUserGettingInPenaltyBox(players.currentPlayerName());
        players.movePlayerToPenaltyBox();

        players.nextPlayer();
        return true;
    }

    private boolean hasNotMaxGoldCoins() {
        return (players.currentPlayerGoldCoins() < 6);
    }
}
