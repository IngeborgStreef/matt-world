package nl.mattworld.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BookServiceIT {

    @Autowired
    private BookRepository repository;

    @Autowired
    private BookService service;

    @Test
    public void canListBooks() {
        Book one = new Book();
        one.setLevel(2);
        one.setTitle("Jagers en verzamelaars");
        one.setImageUrl("hierkomteenplaatjevanjagersenverzamelaars");
        one.setSummary("summary");
        repository.save(one);
        Book two = new Book();
        two.setLevel(3);
        two.setTitle("De gouden Eeuw");
        two.setImageUrl("plaatje");
        two.setSummary("summary");
        repository.save(two);
        List<Book> books = service.retrieveBooks();
        assertEquals(2,books.size());
    }

    @Test
    public void canFindBookById() {
        Book book = new Book();
        book.setLevel(1);
        book.setTitle("Egypt");
        book.setImageUrl("hierkomteenplaatjevanegypte");
        book.setSummary("summary");
        Book created = repository.save(book);
        Optional<Book> found = service.findBookById(created.getId());
        assertTrue(found.isPresent());
        assertEquals(created, found.get());
    }

    @Test
    public void canFilterBookByLevel() {
        Book one = new Book();
        one.setLevel(4);
        repository.save(one);
        Book two = new Book();
        two.setLevel(5);
        repository.save(two);
        List<Book> found = service.retrieveBooksByMinimumLevel(5);
        assertEquals(1, found.size());
        List<Book> foundTwo = service.retrieveBooksByMinimumLevel(4);
        assertEquals(2, foundTwo.size());
    }

    @Test
    public void canCreateBook() {
        Book book = new Book();
        Book created = service.createBook(book);
        Book found = repository.findById(created.getId()).orElseThrow();
        assertEquals(created, found);
    }

    @Test
    public void canUpdateBookLevel() {
        Book book = new Book();
        book.setLevel(1);
        Book created = repository.save(book);
        Book update = new Book();
        update.setLevel(2);
        service.updateBook(created.getId(), update);
        Book found = repository.findById(created.getId()).orElseThrow();
        assertEquals(2, found.getLevel());
    }
}
