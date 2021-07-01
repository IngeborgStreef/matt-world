package nl.mattworld.page;

import nl.mattworld.book.Book;
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
public class PageServiceTest {

    @Mock
    private PageRepository repositoryMock;

    private PageService service;

    @BeforeEach
    public void setup() {
        service = new PageService(repositoryMock);
    }

    @Test
    public void canListPages() {
        when(repositoryMock.findAll()).thenReturn(List.of(new Page(), new Page()));
        List<Page> pages = service.retrievePages();
        assertEquals(2, pages.size());
    }

    //todo: check if test is usefull. If so, finish (and add to IT tests). Else throw.
//    @Test
//    public void canListPagesPerBook() {
//        Page pageOne = new Page();
//        pageOne.setBookId("1");
//        Page pageTwo = new Page();
//        pageTwo.setBookId("2");
//        when(repositoryMock.findAllByBookId("id")).thenReturn(List.of(pageOne, pageTwo));
//        List<Page> bookPages = service.retrievePages();
//        assertEquals(2, bookPages.size());
//    }

    @Test
    public void canFindPageById() {
        String id = "1";
        Page page = new Page();
        page.setId(id);
        when(repositoryMock.findOneById(id)).thenReturn(Optional.of(page));
        Optional<Page> pageOptional = service.findPageById(id);
        assertTrue(pageOptional.isPresent());
        assertEquals(id, pageOptional.get().getId());
    }

    @Test
    public void canFindPageByBookIdAndNumber() {
        Book book = new Book();
        book.setId("1");
        int number = 1;
        Page page = new Page();
        page.setBook(book);
        page.setNumber(number);
        when(repositoryMock.findOneByBookIdAndNumber(book.getId(), number)).thenReturn(Optional.of(page));
        Optional<Page> pageOptional = service.findPageByBookIdAndNumber(book.getId(), number);
        assertTrue(pageOptional.isPresent());
        assertEquals(book, pageOptional.get().getBook());
        assertEquals(number, pageOptional.get().getNumber());
    }

    @Test
    public void canCreatePage() {
        String expectedId = "3";
        Page testPage = new Page();
        when(repositoryMock.save(testPage)).thenAnswer(inv -> {
            Page page = inv.getArgument(0);
            page.setId(expectedId);
            return page;
        });
        Page created = service.createPage(testPage);
        assertEquals("3", created.getId());
    }

}
