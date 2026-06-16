package ru.diasoft.spring.service;

public interface IOService {
    void printLine(String line);
    void printFormattedLine(String format, Object... args);
    String readLine();
    String readLineWithPrompt(String prompt);
}
