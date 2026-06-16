package ru.diasoft.spring.service;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleIOServiceTest {

    @Test
    void readLine_shouldReturnInputFromScanner() {
        String input = "test input\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ConsoleIOService ioService = new ConsoleIOService();

        String result = ioService.readLine();

        assertEquals("test input", result);
    }

    @Test
    void readLineWithPrompt_shouldPrintPromptAndReturnInput() {
        String input = "user answer\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        ConsoleIOService ioService = new ConsoleIOService();
        String result = ioService.readLineWithPrompt("Enter value:");

        assertEquals("user answer", result);
        assertTrue(outputStream.toString().contains("Enter value:"));
    }

    @Test
    void printLine_shouldOutputLine() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        ConsoleIOService ioService = new ConsoleIOService();

        ioService.printLine("Hello World");

        assertTrue(outputStream.toString().contains("Hello World"));
    }

    @Test
    void printFormattedLine_shouldOutputFormattedLine() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        ConsoleIOService ioService = new ConsoleIOService();

        ioService.printFormattedLine("Hello %s, you scored %d", "John", 5);

        String output = outputStream.toString();
        assertTrue(output.contains("Hello John, you scored 5"));
    }
}
