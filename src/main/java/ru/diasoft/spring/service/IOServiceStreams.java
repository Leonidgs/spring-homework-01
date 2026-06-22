package ru.diasoft.spring.service;

import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceStreams implements IOService {

    private final Scanner scanner;
    private final PrintStream out;

    public IOServiceStreams() {
        this.scanner = new Scanner(System.in);
        this.out = System.out;
    }

    @Override
    public void print(String text) {
        out.print(text);
    }

    @Override
    public void println(String text) {
        out.println(text);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
