package ui;

import model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.Library;

import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsoleUITest {

    @Mock
    private Library library;

    @Mock
    private InputReader inputReader;

    @InjectMocks
    private ConsoleUI consoleUI;

    @Test
    @DisplayName("Корректное добавление книги")
    void runAdd_shouldAddBook() {
        when(inputReader.readLine()).thenReturn(
                "add",
                "book",
                "author",
                "genre",
                "2000",
                "exit"
        );

        consoleUI.run();

        verify(library).addBook(argThat(book ->
                book.title().equals("book") &&
                book.author().equals("author") &&
                book.genre().equals("genre") &&
                book.year() == 2000
                ));
    }

    @Test
    @DisplayName("Корректное удаление существующей книги")
    void runRemove_shouldRemoveBook() {
        when(inputReader.readLine()).thenReturn(
                "remove",
                "title",
                "exit"
        );
        when(library.removeBook("title")).thenReturn(true);

        consoleUI.run();

        verify(library).removeBook("title");
    }

    @Test
    @DisplayName("Корректный поиск книги по названию")
    void runFind_shouldReturnBooks() {
        Book book = new Book("title", "author", "genre", 2000);
        when(inputReader.readLine()).thenReturn(
                "find",
                "title",
                "exit"
        );
        when(library.searchByTitle("title")).thenReturn(List.of(book));

        consoleUI.run();

        verify(library).searchByTitle("title");
    }

    @Test
    @DisplayName("Корректный вывод перечня книг")
    void runList_shouldListBooks() {
        Book book = new Book("title", "author", "genre", 2000);
        when(inputReader.readLine()).thenReturn(
                "list",
                "exit"
        );
        when(library.listBooks()).thenReturn(List.of(book));

        consoleUI.run();

        verify(library).listBooks();
    }

    @Test
    @DisplayName("Некорректная команда")
    void runInvalidCommand_shouldPrintInvalidChoice() {
        when(inputReader.readLine()).thenReturn(
                "unknown",
                "exit"
        );

        consoleUI.run();

        verifyNoInteractions(library);
    }


}
