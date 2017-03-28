package com.adaptionsoft.games.uglytrivia;

public class Game {

    public static final int MAX_GOLD_COINS = 6;
    private Status status;
    private Questions questions;
    private Players players;

    public Game() {
        this(new Players(), new Status(new Console()), new Questions());
    }

    public Game(Players players, Status status, Questions questions) {
        this.players = players;
        this.status = status;
        this.questions = questions;
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    public boolean add(String playerName) {
        players.add(playerName);

        status.informAboutAddedPlayer(playerName);
        status.informAboutNumberOfPlayers(players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        status.informAboutCurrentPlayerAndRoll(players.currentPlayerName(), roll);

        if (!players.currentPlayerIsInPenaltyBox()) {
            move(roll);
            return;
        }

        if (shouldGetOutOfPenaltyBox(roll)) {
            players.setGettingOutOfPenaltyBox(true);
            status.informAboutUserGettingOutOfPenaltyBox(players.currentPlayerName());
            move(roll);
            return;
        }

        status.informAboutNotToGetOutOFPenaltyBox(players.currentPlayerName());
        players.setGettingOutOfPenaltyBox(false);
    }

    private boolean shouldGetOutOfPenaltyBox(int roll) {
        return roll % 2 != 0;
    }

    private void move(int roll) {
        players.moveCurrentPlayer(roll);

        String currentCategory = questions.currentCategory(players.currentPlayerPlace());
        Object question = questions.getQuestionAndRemoveFromList(currentCategory);

        status.informAboutLocationCategoryAndQuestion(players.currentPlayerName(), players.currentPlayerPlace(), currentCategory, question.toString());
    }

    public boolean wasCorrectlyAnswered() {
        if (players.currentPlayerIsInPenaltyBox() && !players.isGettingOutOfPenaltyBox()) {
            players.nextPlayer();
            return true;
        }

        status.informAboutCorrectAnswer();
        players.increaseGoldCoins();
        status.informAboutGoldCoins(players.currentPlayerName(), players.currentPlayerGoldCoins());

        boolean notWinner = hasNotMaxGoldCoins();
        players.nextPlayer();

        return notWinner;
    }

    public boolean wrongAnswer() {
        status.informAboutWrongAnswer();
        status.informAboutUserGettingInPenaltyBox(players.currentPlayerName());
        players.movePlayerToPenaltyBox();

        players.nextPlayer();
        return true;
    }

    private boolean hasNotMaxGoldCoins() {
        return (players.currentPlayerGoldCoins() < MAX_GOLD_COINS);
    }
}
