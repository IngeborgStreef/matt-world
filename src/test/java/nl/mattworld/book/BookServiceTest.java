package nl.mattworld.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository repositoryMock;

    private BookService service;

    @BeforeEach
    public void setup() {
        service= new BookService(repositoryMock);
    }

    @Test
    public void canListBooks() {
        when(repositoryMock.findAll()).thenReturn(List.of(new Book("1", 1), new Book("2", 2)));
        List<Book> books = service.retrieveBooks();
        assertEquals(2, books.size());
    }

    @Test
    public void canFindBookById() {
        String id = "1";
        when(repositoryMock.findOneById(id)).thenReturn(Optional.of(new Book(id, 1)));
        Optional<Book> bookOptional = service.findBookById(id);
        assertTrue(bookOptional.isPresent());
        assertEquals(id, bookOptional.get().getId());
    }

    @Test
    public void canCreateBook() {
        String expectedId = "3";
        Book testBook = new Book("3", 3);
        when(repositoryMock.saveBook(testBook)).thenAnswer(inv -> {
            Book book = inv.getArgument(0);
            book.setId(expectedId);
            return book;
        });
        Book created = service.createBook(testBook);
        assertEquals("3", created.getId());
    }

    @Test
    public void canFilterBooksByLevel() {
        int expectedLevel = 4;
        when(repositoryMock.findByLevelGte(expectedLevel)).thenReturn(List.of(new Book("4", 4)));
        List<Book> booksFilteredByLevel = service.retrieveBookByMinimumLevel(expectedLevel);
        assertEquals(1, booksFilteredByLevel.size());
    }

//    @Test
//    public void canUpdateBookLevel() {
//    }

//    @Test
//    public void shouldFailOnUpdateNonExistingBook() {
//        fail();
//    }

    @Test
    public void parentCanBuyBook() {

    }
}