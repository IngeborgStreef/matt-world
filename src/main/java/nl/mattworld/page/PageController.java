package nl.mattworld.page;

import nl.mattworld.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PageController {

    private final PageService pageService;

    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @PostMapping("/api/pages")
    public Page createPage(@RequestBody Page page) {
        return pageService.createPage(page);
    }

    @GetMapping("/api/books/{bookId}/pages")
    public List<Page> getAllPages(@PathVariable String bookId) {
        return pageService.listPagesPerBook(bookId);
    }

    @GetMapping("/api/books/{bookId}/pages/{pageId}")
    public Page getPage(@PathVariable String pageId) {
        return pageService.findPageById(pageId).orElseThrow(() ->new NotFoundException("Page not found by ID: " + pageId));
    }

    //todo: maken number --> booknumber
    @GetMapping("/api/books/{bookId}/page/{pageNumber}")
    public Page getPageByBookIdAndNumber(@PathVariable String bookId, @PathVariable int pageNumber) {
        return pageService.findPageByBookIdAndNumber(bookId, pageNumber).orElseThrow(() -> new NotFoundException("Book not found by bookid: " + bookId + "or page not found by pagenumber: " + pageNumber));
    }

}
