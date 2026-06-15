package org.example.dao;

import org.example.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAll();
}
