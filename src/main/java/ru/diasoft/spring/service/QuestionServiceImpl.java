package ru.diasoft.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.diasoft.spring.dao.QuestionDao;
import ru.diasoft.spring.domain.Question;

import java.util.List;
import java.util.Scanner;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;
    private final int passingScore;

    public QuestionServiceImpl(QuestionDao dao, @Value("${passing.score}") int passingScore) {
        this.dao = dao;
        this.passingScore = passingScore;
    }

    @Override
    public void executeTest() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your first name:");
        String firstName = scanner.nextLine().trim();

        System.out.println("Enter your last name:");
        String lastName = scanner.nextLine().trim();

        System.out.println("\nWelcome, " + firstName + " " + lastName + "!");
        System.out.println("Please answer the following questions by entering the number of your choice.\n");

        List<Question> questions = dao.findAll();
        int correctCount = 0;

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            System.out.println("Question " + (i + 1) + ": " + question.getText());
            List<String> answers = question.getAnswers();
            for (int j = 0; j < answers.size(); j++) {
                System.out.println("  " + (j + 1) + ". " + answers.get(j));
            }

            System.out.print("Your answer: ");
            int userAnswer;
            try {
                userAnswer = Integer.parseInt(scanner.nextLine().trim()) - 1;
            } catch (NumberFormatException e) {
                userAnswer = -1;
            }

            if (userAnswer == question.getCorrectAnswerIndex()) {
                correctCount++;
                System.out.println("Correct!\n");
            } else {
                System.out.println("Incorrect. The correct answer was: " +
                        (question.getCorrectAnswerIndex() + 1) + ". " +
                        answers.get(question.getCorrectAnswerIndex()) + "\n");
            }
        }

        System.out.println("Test completed!");
        System.out.println(firstName + " " + lastName + ", you answered " + correctCount + " out of " +
                questions.size() + " questions correctly.");

        if (correctCount >= passingScore) {
            System.out.println("Congratulations! You PASSED the test!");
        } else {
            System.out.println("Sorry, you FAILED the test. You needed at least " + passingScore +
                    " correct answers to pass.");
        }
    }
}
