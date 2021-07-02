package nl.mattworld.page;

import nl.mattworld.book.Book;
import nl.mattworld.book.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PageServiceIT {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PageRepository repository;

    @Autowired
    private PageService service;

    @BeforeEach
    public void deleteAll() {
        repository.deleteAll();
    }

    @Test
    public void canListPages() {
        Page one = new Page();
        repository.save(one);
        Page two = new Page();
        repository.save(two);
        List<Page> pages = service.retrievePages();
        assertEquals(2, pages.size());
    }

    @Test
    public void canFindPageById() {
        Page page = new Page();
        page.setId("1");
        Page created = repository.save(page);
        Optional<Page> found = service.findPageById(created.getId());
        assertTrue(found.isPresent());
        assertEquals(created, found.get());
    }

    @Test
    public void canFindPageByBookIdAndNumber() {
        Book book = bookRepository.save(new Book());
        Page page = new Page();
        page.setBook(book);
        page.setNumber(2);
        Page created = repository.save(page);
        Optional<Page> found = service.findPageByBookIdAndNumber(book.getId(), created.getNumber());
        assertTrue(found.isPresent());
        assertEquals(created, found.get());
    }

    @Test
    public void canCreatePage() {
        Page page = new Page();
        Page created = service.createPage(page);
        Page found = repository.findById(created.getId()).orElseThrow();
        assertEquals(created, found);
    }
}
