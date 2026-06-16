package ru.diasoft.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.diasoft.spring.service.QuestionService;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("spring-context.xml");
        QuestionService questionService = context.getBean("questionService", QuestionService.class);
        questionService.executeTest();
        context.close();
    }
}
