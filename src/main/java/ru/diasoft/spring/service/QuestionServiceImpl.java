package ru.diasoft.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.diasoft.spring.dao.QuestionDao;
import ru.diasoft.spring.domain.Question;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;
    private final IOService ioService;
    private final int passingScore;

    public QuestionServiceImpl(QuestionDao dao, IOService ioService, @Value("${passing.score}") int passingScore) {
        this.dao = dao;
        this.ioService = ioService;
        this.passingScore = passingScore;
    }

    @Override
    public void executeTest() {
        String firstName = ioService.readLineWithPrompt("Enter your first name:");
        String lastName = ioService.readLineWithPrompt("Enter your last name:");

        ioService.printLine("");
        ioService.printLine("Welcome, " + firstName + " " + lastName + "!");
        ioService.printLine("Please answer the following questions by entering the number of your choice.");
        ioService.printLine("");

        List<Question> questions = dao.findAll();
        int correctCount = 0;

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            ioService.printLine("Question " + (i + 1) + ": " + question.getText());
            List<String> answers = question.getAnswers();
            for (int j = 0; j < answers.size(); j++) {
                ioService.printLine("  " + (j + 1) + ". " + answers.get(j));
            }

            int userAnswer = readUserAnswer();

            if (userAnswer == question.getCorrectAnswerIndex()) {
                correctCount++;
                ioService.printLine("Correct!");
            } else {
                ioService.printLine("Incorrect. The correct answer was: " +
                        (question.getCorrectAnswerIndex() + 1) + ". " +
                        answers.get(question.getCorrectAnswerIndex()));
            }
            ioService.printLine("");
        }

        ioService.printLine("Test completed!");
        ioService.printLine(firstName + " " + lastName + ", you answered " + correctCount + " out of " +
                questions.size() + " questions correctly.");

        if (correctCount >= passingScore) {
            ioService.printLine("Congratulations! You PASSED the test!");
        } else {
            ioService.printLine("Sorry, you FAILED the test. You needed at least " + passingScore +
                    " correct answers to pass.");
        }
    }

    private int readUserAnswer() {
        String input = ioService.readLineWithPrompt("Your answer:");
        try {
            return Integer.parseInt(input.trim()) - 1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
