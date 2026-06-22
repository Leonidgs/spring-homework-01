package ru.diasoft.spring.domain;

import java.util.List;

public class Question {

    private final String text;
    private final List<String> answers;
    private int correctAnswerIndex;

    public Question(String text, List<String> answers, int correctAnswerIndex) {
        this.text = text;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getText() {
        return text;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }
}
