import service.Library;
import ui.ConsoleUI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        ConsoleUI ui = new ConsoleUI(library, scanner);
        ui.run();
    }
}