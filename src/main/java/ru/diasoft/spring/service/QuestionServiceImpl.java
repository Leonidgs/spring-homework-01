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

    public QuestionServiceImpl(QuestionDao dao, IOService ioService,
                               @Value("${passing.score}") int passingScore) {
        this.dao = dao;
        this.ioService = ioService;
        this.passingScore = passingScore;
    }

    @Override
    public void executeTest() {
        String firstName = askName("first");
        String lastName = askName("last");

        ioService.println("\nWelcome, " + firstName + " " + lastName + "!");
        ioService.println("Please answer the following questions by entering the number of your choice.\n");

        List<Question> questions = dao.findAll();
        int correctCount = 0;

        for (int i = 0; i < questions.size(); i++) {
            if (askQuestion(i + 1, questions.get(i))) {
                correctCount++;
            }
        }

        printResult(firstName, lastName, questions.size(), correctCount);
    }

    private String askName(String which) {
        ioService.println("Enter your " + which + " name:");
        return ioService.readLine().trim();
    }

    private void printQuestion(int number, Question question) {
        ioService.println("Question " + number + ": " + question.getText());
        List<String> answers = question.getAnswers();
        for (int j = 0; j < answers.size(); j++) {
            ioService.println("  " + (j + 1) + ". " + answers.get(j));
        }
    }

    private boolean askQuestion(int number, Question question) {
        printQuestion(number, question);
        ioService.print("Your answer: ");

        int userAnswer;
        try {
            userAnswer = Integer.parseInt(ioService.readLine().trim()) - 1;
        } catch (NumberFormatException e) {
            userAnswer = -1;
        }

        if (userAnswer == question.getCorrectAnswerIndex()) {
            ioService.println("Correct!\n");
            return true;
        } else {
            ioService.println("Incorrect. The correct answer was: " +
                    (question.getCorrectAnswerIndex() + 1) + ". " +
                    question.getAnswers().get(question.getCorrectAnswerIndex()) + "\n");
            return false;
        }
    }

    private void printResult(String firstName, String lastName, int total, int correct) {
        ioService.println("Test completed!");
        ioService.println(firstName + " " + lastName + ", you answered " + correct +
                " out of " + total + " questions correctly.");

        if (correct >= passingScore) {
            ioService.println("Congratulations! You PASSED the test!");
        } else {
            ioService.println("Sorry, you FAILED the test. You needed at least " +
                    passingScore + " correct answers to pass.");
        }
    }
}
