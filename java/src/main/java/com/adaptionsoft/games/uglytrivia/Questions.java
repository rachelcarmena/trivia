package com.adaptionsoft.games.uglytrivia;

import java.util.LinkedList;
import java.util.List;

public class Questions {
    public static final String POP = "Pop";
    public static final String SCIENCE = "Science";
    public static final String SPORTS = "Sports";
    public static final String ROCK = "Rock";
    public static final String QUESTION_TEXT = " Question ";

    LinkedList<String> popQuestions = new LinkedList<>();
    LinkedList<String> scienceQuestions = new LinkedList<>();
    LinkedList<String> sportsQuestions = new LinkedList<>();
    LinkedList<String> rockQuestions = new LinkedList<>();

    public Questions() {
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast(createQuestion(i, POP));
            scienceQuestions.addLast((createQuestion(i, SCIENCE)));
            sportsQuestions.addLast((createQuestion(i, SPORTS)));
            rockQuestions.addLast(createQuestion(i, ROCK));
        }
    }

    private String createQuestion(int questionNumber, String category) {
        return category + QUESTION_TEXT + questionNumber;
    }

    String getQuestionAndRemoveFromList(String category) {
        switch (category) {
            case POP:
                return popQuestions.removeFirst();
            case SCIENCE:
                return scienceQuestions.removeFirst();
            case SPORTS:
                return sportsQuestions.removeFirst();
            case ROCK:
                return rockQuestions.removeFirst();
        }
        return "";
    }

    String currentCategory(int place) {
        switch (place) {
            case 0:
            case 4:
            case 8:
                return POP;
            case 1:
            case 5:
            case 9:
                return SCIENCE;
            case 2:
            case 6:
            case 10:
                return SPORTS;
        }
        return ROCK;
    }
}