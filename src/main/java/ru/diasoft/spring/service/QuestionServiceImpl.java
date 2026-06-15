package ru.diasoft.spring.service;

import ru.diasoft.spring.dao.QuestionDao;
import ru.diasoft.spring.domain.Question;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;

    public QuestionServiceImpl(QuestionDao dao) {
        this.dao = dao;
    }

    @Override
    public void executeTest() {
        List<Question> questions = dao.findAll();
        for (Question question : questions) {
            System.out.println("Question: " + question.getText());
            List<String> answers = question.getAnswers();
            for (int i = 0; i < answers.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + answers.get(i));
            }
            System.out.println();
        }
    }
}
