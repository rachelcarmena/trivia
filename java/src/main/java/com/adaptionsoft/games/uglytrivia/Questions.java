package com.adaptionsoft.games.uglytrivia;

import java.util.LinkedList;

public class Questions {
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    public Questions() {
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast("Rock Question " + i);
        }
    }

    Object getQuestionAndRemoveFromList(String category) {
        switch (category) {
            case "Pop":
                return popQuestions.removeFirst();
            case "Science":
                return scienceQuestions.removeFirst();
            case "Sports":
                return sportsQuestions.removeFirst();
            case "Rock":
                return rockQuestions.removeFirst();
        }
        return "";
    }
}
