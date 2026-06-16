package ru.diasoft.spring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.spring.dao.QuestionDao;
import ru.diasoft.spring.domain.Question;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private QuestionDao questionDao;

    @Mock
    private IOService ioService;

    @Test
    void executeTest_shouldAskNameAndShowWelcome() {
        when(questionDao.findAll()).thenReturn(List.of(
                new Question("Q1?", List.of("A1", "A2"), 0)
        ));
        when(ioService.readLineWithPrompt("Enter your first name:")).thenReturn("John");
        when(ioService.readLineWithPrompt("Enter your last name:")).thenReturn("Doe");
        when(ioService.readLineWithPrompt("Your answer:")).thenReturn("1");

        QuestionServiceImpl service = new QuestionServiceImpl(questionDao, ioService, 1);
        service.executeTest();

        verify(ioService).readLineWithPrompt("Enter your first name:");
        verify(ioService).readLineWithPrompt("Enter your last name:");
        verify(ioService).printLine(contains("Welcome"));
    }

    @Test
    void executeTest_shouldAskAllQuestions() {
        when(questionDao.findAll()).thenReturn(List.of(
                new Question("Q1?", List.of("A1", "A2"), 0),
                new Question("Q2?", List.of("B1", "B2"), 1)
        ));
        when(ioService.readLineWithPrompt("Enter your first name:")).thenReturn("John");
        when(ioService.readLineWithPrompt("Enter your last name:")).thenReturn("Doe");
        when(ioService.readLineWithPrompt("Your answer:")).thenReturn("1");

        QuestionServiceImpl service = new QuestionServiceImpl(questionDao, ioService, 1);
        service.executeTest();

        verify(questionDao, times(1)).findAll();
        verify(ioService, times(2)).readLineWithPrompt("Your answer:");
    }

    @Test
    void executeTest_shouldShowCorrectWhenAnswerIsRight() {
        when(questionDao.findAll()).thenReturn(List.of(
                new Question("Q1?", List.of("Correct", "Wrong"), 0)
        ));
        when(ioService.readLineWithPrompt("Enter your first name:")).thenReturn("John");
        when(ioService.readLineWithPrompt("Enter your last name:")).thenReturn("Doe");
        when(ioService.readLineWithPrompt("Your answer:")).thenReturn("1");

        QuestionServiceImpl service = new QuestionServiceImpl(questionDao, ioService, 1);
        service.executeTest();

        verify(ioService).printLine("Correct!");
    }

    @Test
    void executeTest_shouldShowIncorrectWhenAnswerIsWrong() {
        when(questionDao.findAll()).thenReturn(List.of(
                new Question("Q1?", List.of("Correct", "Wrong"), 0)
        ));
        when(ioService.readLineWithPrompt("Enter your first name:")).thenReturn("John");
        when(ioService.readLineWithPrompt("Enter your last name:")).thenReturn("Doe");
        when(ioService.readLineWithPrompt("Your answer:")).thenReturn("2");

        QuestionServiceImpl service = new QuestionServiceImpl(questionDao, ioService, 1);
        service.executeTest();

        verify(ioService).printLine(contains("Incorrect"));
    }

    @Test
    void executeTest_shouldShowPassWhenScoreMeetsPassingScore() {
        when(questionDao.findAll()).thenReturn(List.of(
                new Question("Q1?", List.of("A1", "A2"), 0)
        ));
        when(ioService.readLineWithPrompt("Enter your first name:")).thenReturn("John");
        when(ioService.readLineWithPrompt("Enter your last name:")).thenReturn("Doe");
        when(ioService.readLineWithPrompt("Your answer:")).thenReturn("1");

        QuestionServiceImpl service = new QuestionServiceImpl(questionDao, ioService, 1);
        service.executeTest();

        verify(ioService).printLine(contains("PASSED"));
    }

    @Test
    void executeTest_shouldShowFailWhenScoreBelowPassingScore() {
        when(questionDao.findAll()).thenReturn(List.of(
                new Question("Q1?", List.of("A1", "A2"), 0)
        ));
        when(ioService.readLineWithPrompt("Enter your first name:")).thenReturn("John");
        when(ioService.readLineWithPrompt("Enter your last name:")).thenReturn("Doe");
        when(ioService.readLineWithPrompt("Your answer:")).thenReturn("2");

        QuestionServiceImpl service = new QuestionServiceImpl(questionDao, ioService, 2);
        service.executeTest();

        verify(ioService).printLine(contains("FAILED"));
    }

    @Test
    void executeTest_shouldHandleInvalidAnswer() {
        when(questionDao.findAll()).thenReturn(List.of(
                new Question("Q1?", List.of("A1", "A2"), 0)
        ));
        when(ioService.readLineWithPrompt("Enter your first name:")).thenReturn("John");
        when(ioService.readLineWithPrompt("Enter your last name:")).thenReturn("Doe");
        when(ioService.readLineWithPrompt("Your answer:")).thenReturn("invalid");

        QuestionServiceImpl service = new QuestionServiceImpl(questionDao, ioService, 1);
        service.executeTest();

        verify(ioService).printLine(contains("Incorrect"));
    }
}
