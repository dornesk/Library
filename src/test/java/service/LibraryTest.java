package service;

import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    private Library library;

    @BeforeEach
    void setUp() {
        library = new Library();
    }

    @Test
    @DisplayName("Корректное добавление книги")
    void addBook_ShouldAddBookToList() {
        Book book = new Book("Book", "Author", "genre", 2020);
        library.addBook(book);
        assertEquals(1, library.listBooks().size());
    }

    @Test
    @DisplayName("Добавление книги с пустым названием")
    void addBook_shouldThrowExceptionBlankTitle() {
        Book book = new Book("", "a", "t", 2);
        assertThrows(IllegalArgumentException.class, () -> library.addBook(book));
   }

    @Test
    @DisplayName("Успешное удаление книги")
    void removeBook_shouldReturnTrue() {
        library.addBook(new Book("b", "a", "g", 2000));
        assertTrue(library.removeBook("b"));
    }

    @Test
    @DisplayName("Не найдена книга для удаления")
    void removeBook_shouldReturnFalse() {
        assertFalse(library.removeBook("b"));
    }

    @Test
    @DisplayName("Чувствительность к регистру при поиске")
    void searchByTitle_shouldBeCaseInsensitive() {
        library.addBook(new Book("book", "a", "g", 2000));
        List<Book> result = library.searchByTitle("BOOK");
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Проверка защитного копирования")
    void listBooks_shouldReturnCopyNotOriginal() {
        library.addBook(new Book("book", "a", "g", 2000));
        List<Book> books = library.listBooks();
        books.clear();
        assertEquals(1, library.listBooks().size(), "Оригинальный список не должен измениться");
    }

    @Test
    @DisplayName("Проверка на дубликаты")
    void addBook_shouldThrowNewDuplicate() {
        Book book = new Book("book", "a", "g", 2000);
        library.addBook(book);
        assertThrows(IllegalArgumentException.class, () -> library.addBook(book));
    }
}