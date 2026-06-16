package ru.diasoft.spring.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    @Test
    void constructor_shouldSetFieldsCorrectly() {
        List<String> answers = List.of("Paris", "London", "Berlin");
        Question question = new Question("Capital of France?", answers, 0);

        assertEquals("Capital of France?", question.getText());
        assertEquals(answers, question.getAnswers());
        assertEquals(0, question.getCorrectAnswerIndex());
    }

    @Test
    void setText_shouldUpdateText() {
        Question question = new Question("Old text?", List.of("A", "B"), 0);

        question.setText("New text?");

        assertEquals("New text?", question.getText());
    }

    @Test
    void setAnswers_shouldUpdateAnswers() {
        Question question = new Question("Question?", List.of("A", "B"), 0);
        List<String> newAnswers = List.of("C", "D", "E");

        question.setAnswers(newAnswers);

        assertEquals(newAnswers, question.getAnswers());
    }

    @Test
    void setCorrectAnswerIndex_shouldUpdateIndex() {
        Question question = new Question("Question?", List.of("A", "B", "C"), 0);

        question.setCorrectAnswerIndex(2);

        assertEquals(2, question.getCorrectAnswerIndex());
    }

    @Test
    void getAnswers_shouldReturnCorrectSize() {
        Question question = new Question("Question?", List.of("A", "B", "C", "D"), 0);

        assertEquals(4, question.getAnswers().size());
    }
}
