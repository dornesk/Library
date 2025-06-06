import service.Library;
import ui.ConsoleInputReader;
import ui.ConsoleUI;
import ui.InputReader;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        InputReader reader = new ConsoleInputReader(new Scanner(System.in));
        ConsoleUI ui = new ConsoleUI(library, reader);
        ui.run();
    }
}