package ru.diasoft.spring.service;

import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;

@Service
public class ConsoleIOService implements IOService {

    private final Scanner scanner;
    private final PrintStream output;

    public ConsoleIOService() {
        this.scanner = new Scanner(System.in);
        this.output = System.out;
    }

    @Override
    public void printLine(String line) {
        output.println(line);
    }

    @Override
    public void printFormattedLine(String format, Object... args) {
        output.printf(format + "%n", args);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public String readLineWithPrompt(String prompt) {
        printLine(prompt);
        return readLine();
    }
}
