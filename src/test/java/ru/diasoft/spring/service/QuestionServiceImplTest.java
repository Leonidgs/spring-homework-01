package ru.diasoft.spring.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.diasoft.spring.dao.QuestionDao;
import ru.diasoft.spring.domain.Question;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionServiceImplTest {

    private static final Question Q1 = new Question("What is 2+2?", List.of("4", "3", "5"), 0);
    private static final Question Q2 = new Question("What is 3+3?", List.of("6", "5", "7"), 0);

    private String runAndCaptureOutput(QuestionDao dao, IOService io, int passingScore) {
        new QuestionServiceImpl(dao, io, passingScore).executeTest();
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(io, atLeastOnce()).println(captor.capture());
        return captor.getAllValues().stream().collect(Collectors.joining("\n"));
    }

    @Test
    void executeTest_studentPassesWhenScoreReachesPassingScore() {
        QuestionDao mockDao = mock(QuestionDao.class);
        IOService mockIO = mock(IOService.class);
        when(mockDao.findAll()).thenReturn(List.of(Q1, Q2));
        when(mockIO.readLine()).thenReturn("John", "Doe", "1", "1");

        String output = runAndCaptureOutput(mockDao, mockIO, 2);

        assertTrue(output.contains("PASSED"), "Expected PASSED when all answers are correct");
    }

    @Test
    void executeTest_studentFailsWhenScoreBelowPassingScore() {
        QuestionDao mockDao = mock(QuestionDao.class);
        IOService mockIO = mock(IOService.class);
        when(mockDao.findAll()).thenReturn(List.of(Q1, Q2));
        when(mockIO.readLine()).thenReturn("Jane", "Doe", "2", "2");

        String output = runAndCaptureOutput(mockDao, mockIO, 1);

        assertTrue(output.contains("FAILED"), "Expected FAILED when all answers are wrong");
    }

    @Test
    void executeTest_outputContainsStudentFullName() {
        QuestionDao mockDao = mock(QuestionDao.class);
        IOService mockIO = mock(IOService.class);
        when(mockDao.findAll()).thenReturn(List.of(Q1));
        when(mockIO.readLine()).thenReturn("Alice", "Smith", "1");

        String output = runAndCaptureOutput(mockDao, mockIO, 1);

        assertTrue(output.contains("Alice Smith"), "Output should contain student's full name");
    }

    @Test
    void executeTest_nonNumericInputCountsAsWrongAnswer() {
        QuestionDao mockDao = mock(QuestionDao.class);
        IOService mockIO = mock(IOService.class);
        when(mockDao.findAll()).thenReturn(List.of(Q1));
        when(mockIO.readLine()).thenReturn("Bob", "Jones", "abc");

        String output = runAndCaptureOutput(mockDao, mockIO, 1);

        assertTrue(output.contains("Incorrect"), "Non-numeric input should be treated as wrong answer");
        assertTrue(output.contains("FAILED"), "Student should fail with a non-numeric answer");
    }

    @Test
    void executeTest_wrongAnswerFeedbackShowsCorrectAnswerText() {
        QuestionDao mockDao = mock(QuestionDao.class);
        IOService mockIO = mock(IOService.class);
        when(mockDao.findAll()).thenReturn(List.of(Q1));
        when(mockIO.readLine()).thenReturn("Tom", "Brown", "2");

        String output = runAndCaptureOutput(mockDao, mockIO, 1);

        assertTrue(output.contains("The correct answer was: 1. 4"), "Feedback should show the correct answer number and text");
    }
}
