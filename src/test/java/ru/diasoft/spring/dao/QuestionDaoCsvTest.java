package ru.diasoft.spring.dao;

import org.junit.jupiter.api.Test;
import ru.diasoft.spring.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionDaoCsvTest {

    @Test
    void findAll_returnsCorrectNumberOfQuestions() {
        QuestionDaoCsv dao = new QuestionDaoCsv("questions.csv");
        List<Question> questions = dao.findAll();
        assertEquals(5, questions.size());
    }

    @Test
    void findAll_firstQuestion_hasCorrectText() {
        QuestionDaoCsv dao = new QuestionDaoCsv("questions.csv");
        Question first = dao.findAll().get(0);
        assertEquals("What is the capital of France?", first.getText());
    }

    @Test
    void findAll_firstQuestion_hasCorrectAnswers() {
        QuestionDaoCsv dao = new QuestionDaoCsv("questions.csv");
        Question first = dao.findAll().get(0);
        List<String> answers = first.getAnswers();
        assertEquals(4, answers.size());
        assertEquals("Paris", answers.get(0));
        assertEquals("London", answers.get(1));
        assertEquals("Berlin", answers.get(2));
        assertEquals("Madrid", answers.get(3));
    }

    @Test
    void findAll_allQuestionsHaveCorrectAnswerIndexZero() {
        QuestionDaoCsv dao = new QuestionDaoCsv("questions.csv");
        dao.findAll().forEach(q -> assertEquals(0, q.getCorrectAnswerIndex()));
    }

    @Test
    void findAll_skipsEmptyLines() {
        QuestionDaoCsv dao = new QuestionDaoCsv("questions-edge.csv");
        List<Question> questions = dao.findAll();
        assertEquals(2, questions.size());
        assertEquals("Question A?", questions.get(0).getText());
        assertEquals("Answer A1", questions.get(0).getAnswers().get(0));
        assertEquals("Question B?", questions.get(1).getText());
        assertEquals("Answer B1", questions.get(1).getAnswers().get(0));
    }

    @Test
    void findAll_throwsRuntimeException_whenFileNotFound() {
        QuestionDaoCsv dao = new QuestionDaoCsv("nonexistent.csv");
        assertThrows(RuntimeException.class, dao::findAll);
    }
}
