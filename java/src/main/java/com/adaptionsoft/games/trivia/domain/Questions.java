package com.adaptionsoft.games.trivia.domain;

import java.util.HashMap;
import java.util.LinkedList;

public class Questions {

    HashMap<String, LinkedList<String>> questionsBySubject;

    public Questions() {
        questionsBySubject = new HashMap<>();
        for (int index = 0; index < Game.SUBJECTS.length; index++) {
            String subject = Game.SUBJECTS[index];
            LinkedList<String> questionsList = createQuestionsListFor(subject);
            questionsBySubject.put(subject, questionsList);
        }
    }

    private LinkedList<String> createQuestionsListFor(String subject) {
        LinkedList<String> questionsList = new LinkedList<>();
        for (int questionNumber = 0; questionNumber < 50; questionNumber++) {
            String question = createQuestion(questionNumber, subject);
            questionsList.addLast(question);
        }
        return questionsList;
    }

    private String createQuestion(int questionNumber, String category) {
        return String.format("%s Question %s", category, questionNumber);
    }

    String getQuestionAndRemoveFromList(String category) {
        LinkedList<String> questionsList = questionsBySubject.get(category);
        return questionsList.removeFirst();
    }
}