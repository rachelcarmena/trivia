package com.adaptionsoft.games.uglytrivia;

import java.util.LinkedList;

public class Questions {

    public static final String QUESTION_TEXT = " Question ";

    LinkedList<String> popQuestions = new LinkedList<>();
    LinkedList<String> scienceQuestions = new LinkedList<>();
    LinkedList<String> sportsQuestions = new LinkedList<>();
    LinkedList<String> rockQuestions = new LinkedList<>();

    public Questions() {
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast(createQuestion(i, Game.POP));
            scienceQuestions.addLast((createQuestion(i, Game.SCIENCE)));
            sportsQuestions.addLast((createQuestion(i, Game.SPORTS)));
            rockQuestions.addLast(createQuestion(i, Game.ROCK));
        }
    }

    private String createQuestion(int questionNumber, String category) {
        return category + QUESTION_TEXT + questionNumber;
    }

    String getQuestionAndRemoveFromList(String category) {
        switch (category) {
            case Game.POP:
                return popQuestions.removeFirst();
            case Game.SCIENCE:
                return scienceQuestions.removeFirst();
            case Game.SPORTS:
                return sportsQuestions.removeFirst();
            case Game.ROCK:
                return rockQuestions.removeFirst();
        }
        return "";
    }
}