package ui;

import java.util.Scanner;

public class ConsoleInputReader implements InputReader {
    private final Scanner scanner;

    public ConsoleInputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String readLine() {
        return scanner.nextLine().trim();
    }
}
