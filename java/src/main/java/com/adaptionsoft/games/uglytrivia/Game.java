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
        console.informAboutTheRole(roll);

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
        console.informAboutCategory(currentCategory());
        askQuestion();
    }

    private void askQuestion() {
        if (currentCategory() == "Pop")
            console.informAboutQuestion(questions.popQuestions.removeFirst());
        if (currentCategory() == "Science")
            console.informAboutQuestion(questions.scienceQuestions.removeFirst());
        if (currentCategory() == "Sports")
            console.informAboutQuestion(questions.sportsQuestions.removeFirst());
        if (currentCategory() == "Rock")
            console.informAboutQuestion(questions.rockQuestions.removeFirst());
    }

    private String currentCategory() {
        if (players.currentPlayerPlace() == 0) return "Pop";
        if (players.currentPlayerPlace() == 4) return "Pop";
        if (players.currentPlayerPlace() == 8) return "Pop";
        if (players.currentPlayerPlace() == 1) return "Science";
        if (players.currentPlayerPlace() == 5) return "Science";
        if (players.currentPlayerPlace() == 9) return "Science";
        if (players.currentPlayerPlace() == 2) return "Sports";
        if (players.currentPlayerPlace() == 6) return "Sports";
        if (players.currentPlayerPlace() == 10) return "Sports";
        return "Rock";
    }

    public boolean wasCorrectlyAnswered() {
        if (players.currentPlayerIsInPenaltyBox()) {
            if (players.isGettingOutOfPenaltyBox()) {
                System.out.println("Answer was correct!!!!");
                players.increaseGoldCoins();
                System.out.println(players.currentPlayerName()
                        + " now has "
                        + players.currentPlayerGoldCoins()
                        + " Gold Coins.");

                boolean winner = didPlayerWin();
                players.nextPlayer();

                return winner;
            } else {
                players.nextPlayer();
                return true;
            }
        } else {
            System.out.println("Answer was corrent!!!!");
            players.increaseGoldCoins();
            System.out.println(players.currentPlayerName()
                    + " now has "
                    + players.currentPlayerGoldCoins()
                    + " Gold Coins.");

            boolean winner = didPlayerWin();
            players.nextPlayer();

            return winner;
        }
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.currentPlayerName() + " was sent to the penalty box");
        players.movePlayerToPenaltyBox();

        players.nextPlayer();
        return true;
    }

    private boolean didPlayerWin() {
        return !(players.currentPlayerGoldCoins() == 6);
    }
}
