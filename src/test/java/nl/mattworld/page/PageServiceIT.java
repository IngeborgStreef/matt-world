package nl.mattworld.page;

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
    private PageRepository repository;

    @Autowired
    private PageService service;

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
        Page page = new Page();
        page.setBookId("1");
        page.setNumber(2);
        Page created = repository.save(page);
        Optional<Page> found = service.findPageByBookIdAndNumber(created.getBookId(), created.getNumber());
        assertTrue(found.isPresent());
        assertEquals(created, found.get());
    }



//    @Test
//    public void canFindPageByBookIdAndNumber() {
//        String bookId = "1";
//        int number = 1;
//        Page page = new Page();
//        page.setBookId(bookId);
//        page.setNumber(number);
//        when(repositoryMock.findOneByBookIdAndNumber(bookId, number)).thenReturn(Optional.of(page));
//        Optional<Page> pageOptional = service.findPageByBookIdAndNumber(bookId, number);
//        assertTrue(pageOptional.isPresent());
//        assertEquals(bookId, pageOptional.get().getBookId());
//        assertEquals(number, pageOptional.get().getNumber());
//    }
//
//    @Test
//    public void canCreatePage() {
//        String expectedId = "3";
//        Page testPage = new Page();
//        when(repositoryMock.save(testPage)).thenAnswer(inv -> {
//            Page page = inv.getArgument(0);
//            page.setId(expectedId);
//            return page;
//        });
//        Page created = service.createPage(testPage);
//        assertEquals("3", created.getId());
//    }
//
//}

}
