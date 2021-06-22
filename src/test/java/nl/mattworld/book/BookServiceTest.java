package nl.mattworld.book;

import nl.mattworld.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        when(repositoryMock.findAll()).thenReturn(List.of(new Book(), new Book()));
        List<Book> books = service.retrieveBooks();
        assertEquals(2, books.size());
    }

    @Test
    public void canFindBookById() {
        String id = "1";
        Book book = new Book();
        book.setId(id);
        when(repositoryMock.findOneById(id)).thenReturn(Optional.of(book));
        Optional<Book> bookOptional = service.findBookById(id);
        assertTrue(bookOptional.isPresent());
        assertEquals(id, bookOptional.get().getId());
    }

    @Test
    public void canCreateBook() {
        String expectedId = "3";
        Book testBook = new Book();
        when(repositoryMock.save(testBook)).thenAnswer(inv -> {
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
        Book book = new Book();
        book.setLevel(expectedLevel);
        when(repositoryMock.findByLevelIsGreaterThanEqual(expectedLevel)).thenReturn(List.of(book));
        List<Book> booksFilteredByLevel = service.retrieveBooksByMinimumLevel(expectedLevel);
        assertEquals(1, booksFilteredByLevel.size());
    }

    @Test
    public void canUpdateBookLevel() {
        Book book = new Book();
        book.setLevel(1);
        book.setId("1");
        when(repositoryMock.existsById(book.getId())).thenReturn(true);
        service.updateBook(book.getId(), book);
        verify(repositoryMock).save(book);
    }

    @Test
    public void shouldFailOnUpdateNonExistingBook() {
        Book book = new Book();
        book.setId("1");
        when(repositoryMock.existsById(book.getId())).thenReturn(false);
        assertThrows(NotFoundException.class, () -> service.updateBook(book.getId(), book));
        verify(repositoryMock, never()).save(book);
    }

    @Test
    public void shouldNotOverwriteId(){
        String id = "1";
        Book book = new Book();
        book.setId("NOT_1");
        when(repositoryMock.existsById(id)).thenReturn(true);
        service.updateBook(id,book);
        book.setId(id);
        verify(repositoryMock).save(book);
    }

    @Test
    public void parentCanBuyBook() {
        fail();
    }
}