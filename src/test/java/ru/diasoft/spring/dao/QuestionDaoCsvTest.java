package ru.diasoft.spring.dao;

import org.junit.jupiter.api.Test;
import ru.diasoft.spring.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionDaoCsvTest {

    @Test
    void findAll_shouldReturnQuestionsFromCsv() {
        QuestionDaoCsv dao = new QuestionDaoCsv("test-questions.csv");

        List<Question> questions = dao.findAll();

        assertEquals(3, questions.size());

        Question first = questions.get(0);
        assertEquals("What is 2+2?", first.getText());
        assertEquals(4, first.getAnswers().size());
        assertEquals("4", first.getAnswers().get(0));
        assertEquals(0, first.getCorrectAnswerIndex());

        Question second = questions.get(1);
        assertEquals("What is the capital of France?", second.getText());
        assertEquals("Paris", second.getAnswers().get(0));
        assertEquals(0, second.getCorrectAnswerIndex());
    }

    @Test
    void findAll_shouldThrowExceptionWhenFileNotFound() {
        QuestionDaoCsv dao = new QuestionDaoCsv("non-existent-file.csv");

        RuntimeException exception = assertThrows(RuntimeException.class, dao::findAll);
        assertTrue(exception.getMessage().contains("CSV resource not found"));
    }

    @Test
    void findAll_shouldSkipEmptyLines() {
        QuestionDaoCsv dao = new QuestionDaoCsv("test-questions.csv");

        List<Question> questions = dao.findAll();

        assertFalse(questions.isEmpty());
    }
}
