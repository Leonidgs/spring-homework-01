package org.example;

import org.example.service.QuestionService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("spring-context.xml");
        QuestionService questionService = context.getBean("questionService", QuestionService.class);
        questionService.executeTest();
        context.close();
    }
}