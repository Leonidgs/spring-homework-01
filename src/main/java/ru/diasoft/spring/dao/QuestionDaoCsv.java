package ru.diasoft.spring.dao;

import ru.diasoft.spring.domain.Question;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoCsv implements QuestionDao {

    private final String csvResourceName;

    public QuestionDaoCsv(String csvResourceName) {
        this.csvResourceName = csvResourceName;
    }

    @Override
    public List<Question> findAll() {
        List<Question> questions = new ArrayList<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(csvResourceName);
        if (inputStream == null) {
            throw new RuntimeException("CSV resource not found: " + csvResourceName);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",");
                String text = parts[0].trim();
                List<String> answers = new ArrayList<>();
                for (int i = 1; i < parts.length; i++) {
                    answers.add(parts[i].trim());
                }
                questions.add(new Question(text, answers));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read CSV resource: " + csvResourceName, e);
        }
        return questions;
    }
}
