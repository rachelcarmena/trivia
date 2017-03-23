package com.adaptionsoft.games.uglytrivia;

import java.util.LinkedList;

public class Game {

    private final Players players = new Players();
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    public  Game(){
    	for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
    }

	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}
	
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {
		
		
	    players.getPlayers().add(playerName);
	    players.getPlaces()[howManyPlayers()] = 0;
	    players.getPurses()[howManyPlayers()] = 0;
	    players.getInPenaltyBox()[howManyPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.getPlayers().size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.getPlayers().size();
	}

	public void roll(int roll) {
        System.out.println(players.getPlayers().get(players.getCurrentPlayer()) + " is the current player");
        System.out.println("They have rolled a " + roll);

        if(!players.getInPenaltyBox()[players.getCurrentPlayer()]) {
            move(roll);
            return;
        }

        if (shouldGetOutOfPenaltyBox(roll)) {
            players.setGettingOutOfPenaltyBox(true);
            System.out.println(players.getPlayers().get(players.getCurrentPlayer()) + " is getting out of the penalty box");
            move(roll);
            return;

        }

        System.out.println(players.getPlayers().get(players.getCurrentPlayer()) + " is not getting out of the penalty box");
        players.setGettingOutOfPenaltyBox(false);

    }

    private boolean shouldGetOutOfPenaltyBox(int roll) {
        return roll % 2 != 0;
    }

    private void move(int roll) {
        players.getPlaces()[players.getCurrentPlayer()] = players.currentPlayerPlace() + roll;
        if (players.currentPlayerPlace() > 11) players.getPlaces()[players.getCurrentPlayer()] = players.currentPlayerPlace() - 12;

        System.out.println(players.getPlayers().get(players.getCurrentPlayer())
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
		if (players.getInPenaltyBox()[players.getCurrentPlayer()]){
			if (players.isGettingOutOfPenaltyBox()) {
				System.out.println("Answer was correct!!!!");
				players.getPurses()[players.getCurrentPlayer()]++;
				System.out.println(players.getPlayers().get(players.getCurrentPlayer())
						+ " now has "
						+ players.getPurses()[players.getCurrentPlayer()]
						+ " Gold Coins.");
				
				boolean winner = didPlayerWin();
				players.setCurrentPlayer(players.getCurrentPlayer() + 1);
				if (players.getCurrentPlayer() == players.getPlayers().size()) players.setCurrentPlayer(0);
				
				return winner;
			} else {
				players.setCurrentPlayer(players.getCurrentPlayer() + 1);
				if (players.getCurrentPlayer() == players.getPlayers().size()) players.setCurrentPlayer(0);
				return true;
			}
			
			
			
		} else {
		
			System.out.println("Answer was corrent!!!!");
			players.getPurses()[players.getCurrentPlayer()]++;
			System.out.println(players.getPlayers().get(players.getCurrentPlayer())
					+ " now has "
					+ players.getPurses()[players.getCurrentPlayer()]
					+ " Gold Coins.");
			
			boolean winner = didPlayerWin();
			players.setCurrentPlayer(players.getCurrentPlayer() + 1);
			if (players.getCurrentPlayer() == players.getPlayers().size()) players.setCurrentPlayer(0);
			
			return winner;
		}
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.getPlayers().get(players.getCurrentPlayer())+ " was sent to the penalty box");
		players.getInPenaltyBox()[players.getCurrentPlayer()] = true;
		
		players.setCurrentPlayer(players.getCurrentPlayer() + 1);
		if (players.getCurrentPlayer() == players.getPlayers().size()) players.setCurrentPlayer(0);
		return true;
	}


	private boolean didPlayerWin() {
		return !(players.getPurses()[players.getCurrentPlayer()] == 6);
	}

}
