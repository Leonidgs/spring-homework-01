package ru.diasoft.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.diasoft.spring.config.AppConfig;
import ru.diasoft.spring.service.QuestionService;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        QuestionService questionService = context.getBean(QuestionService.class);
        questionService.executeTest();
        context.close();
    }
}
