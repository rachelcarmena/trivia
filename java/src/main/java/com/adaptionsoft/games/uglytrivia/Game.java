package com.adaptionsoft.games.uglytrivia;

import java.util.LinkedList;

public class Game {

    private Players players;
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    private TracerBullet tracerBullet;

    public Game() {
        this(new Players());
    }

    public Game(Players players) {
        this.players = players;
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast(createRockQuestion(i));
        }
    }

    public String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    public boolean add(String playerName) {
        players.add(playerName);

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        informAboutTheCurrentPlayer();
        informAboutTheRoll(roll);

        if (!players.currentPlayerIsInPenaltyBox()) {
            move(roll);
            return;
        }

        if (shouldGetOutOfPenaltyBox(roll)) {
            players.setGettingOutOfPenaltyBox(true);
            informAboutGettingOutOfPenaltyBox();
            move(roll);
            return;
        }

        informAboutNotToGetOutOfPenaltyBox();
        players.setGettingOutOfPenaltyBox(false);

    }

    private void informAboutGettingOutOfPenaltyBox() {
        tracerBullet.informAboutGettingOutOfPenaltyBox();
        System.out.println(players.currentPlayerName() + " is getting out of the penalty box");
    }

    private void informAboutNotToGetOutOfPenaltyBox() {
        tracerBullet.informAboutNotToGetOutOFPenaltyBox();
        System.out.println(players.currentPlayerName() + " is not getting out of the penalty box");
    }

    private void informAboutTheRoll(int roll) {
        tracerBullet.informAboutTheRole();
        System.out.println("They have rolled a " + roll);
    }

    private void informAboutTheCurrentPlayer() {
        tracerBullet.informAboutTheCurrentPlayer();
        System.out.println(players.currentPlayerName() + " is the current player");
    }

    private boolean shouldGetOutOfPenaltyBox(int roll) {
        return roll % 2 != 0;
    }

    private void move(int roll) {
        players.moveCurrentPlayer(roll);

        System.out.println(players.currentPlayerName()
                + "'s new location is "
                + players.currentPlayerPlace());
        System.out.println("The category is " + currentCategory());
        askQuestion();
    }

    private void askQuestion() {
        if (currentCategory() == "Pop")
            System.out.println(popQuestions.removeFirst());
        if (currentCategory() == "Science")
            System.out.println(scienceQuestions.removeFirst());
        if (currentCategory() == "Sports")
            System.out.println(sportsQuestions.removeFirst());
        if (currentCategory() == "Rock")
            System.out.println(rockQuestions.removeFirst());
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

    public void setTracerBullet(TracerBullet tracerBullet) {
        this.tracerBullet = tracerBullet;
    }
}
