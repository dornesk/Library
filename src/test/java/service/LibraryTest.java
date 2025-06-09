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

    @Test
    @DisplayName("Поиск по нескольким параметрам должен возвращать корректную книгу")
    void searchBooks_shouldReturnBookMatchingAllCriteria() {
        Book book1 = new Book("book1", "a1", "g", 2000);
        Book book2 = new Book("book2", "a2", "g", 2000);
        library.addBook(book1);
        library.addBook(book2);

        List<Book> result = library.searchBooks("book1", "a1", "g", 2000);

        assertEquals(1, result.size());
        assertTrue(result.contains(book1));
    }

    @Test
    @DisplayName("Поиск только по автору")
    void searchBooks_shouldReturnBookMatchingAuthor() {
        Book book1 = new Book("book1", "a1", "g", 2000);
        Book book2 = new Book("book2", "a2", "g", 2000);
        library.addBook(book1);
        library.addBook(book2);

        List<Book> result = library.searchBooks(null, "a1", null,null);

        assertEquals(1, result.size());
        assertTrue(result.contains(book1));
    }

    @Test
    @DisplayName("Поиск с null во всех полях - возвращает все книги")
    void searchBooks_withAllNulls_shouldReturnAllBooks() {
        Book book1 = new Book("book1", "a1", "g", 2000);
        Book book2 = new Book("book2", "a2", "g", 2000);
        library.addBook(book1);
        library.addBook(book2);

        List<Book> result = library.searchBooks(null, null, null,null);

        assertEquals(2, result.size());
        assertTrue(result.contains(book1));
        assertTrue(result.contains(book1));
    }

    @Test
    @DisplayName("Поиск по частичному совпадению в названии")
    void searchBooks_shouldMatchPartial() {
        Book book1 = new Book("Harry Potter", "jr", "g", 2000);
        library.addBook(book1);

        List<Book> result = library.searchBooks("potter", null, null, null);

        assertEquals(1, result.size());
        assertTrue(result.contains(book1));
    }

    @Test
    @DisplayName("Поиск по автору среди большого количества книг")
    void searchBooks_shouldHandleLargeDataset() {
        for (int i = 0; i < 1000; i++) {
            library.addBook(new Book("book" + i, "auth" + i, "genre", 1900 + i % 20));
        }

        Book target = new Book("book", "target author", "genre", 2000);
        library.addBook(target);

        List<Book> result = library.searchBooks(null, "target author", null, null);

        assertEquals(1, result.size());
        assertTrue(result.contains(target));
    }
}