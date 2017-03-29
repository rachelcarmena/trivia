package com.adaptionsoft.games.trivia.runner.domain;

import com.adaptionsoft.games.trivia.runner.delivery.Console;
import com.adaptionsoft.games.trivia.runner.delivery.Status;

public class Game {

    public static final int PLAYER_PLACE_LIMIT = 12;
    public static final int MAX_GOLD_COINS = 6;

    public static final String POP = "Pop";
    public static final String SCIENCE = "Science";
    public static final String SPORTS = "Sports";
    public static final String ROCK = "Rock";
    public static final String[] SUBJECTS = {POP, SCIENCE, SPORTS, ROCK};

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

        status.informAboutAddedPlayer(playerName, players.size());
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

        String currentCategory = currentCategory(players.currentPlayerPlace());
        String question = questions.getQuestionAndRemoveFromList(currentCategory);

        status.informAboutLocationCategoryAndQuestion(players.currentPlayerName(), players.currentPlayerPlace(), currentCategory, question);
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

    public static String currentCategory(int place) {
        for (int index = 0; index < SUBJECTS.length; index++) {
            if ((place - index) % SUBJECTS.length == 0)
                return SUBJECTS[index];
        }
        return "";
    }
}
