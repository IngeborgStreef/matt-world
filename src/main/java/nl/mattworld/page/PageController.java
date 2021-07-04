package nl.mattworld.page;

import nl.mattworld.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PageController {

    private final PageService pageService;

    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @PostMapping("/api/books/{bookId}/pages")
    public PageDto createPage(@RequestBody @Valid PageDto page) {
        return PageDto.fromEntity(pageService.createPage(page.toEntity()));
    }

    @GetMapping("/api/books/{bookId}/pages")
    public List<PageDto> getAllPages(@PathVariable String bookId) {
        return pageService.retrievePagesPerBook(bookId).stream().map(PageDto::fromEntity).collect(Collectors.toList());
    }

    @GetMapping("/api/books/{bookId}/pages/{pageId}")
    public PageDto getPage(@PathVariable String pageId) {
        return pageService.findPageById(pageId).map(PageDto::fromEntity).orElseThrow(() ->new NotFoundException("Page not found by ID: " + pageId));
    }

    @PutMapping("/api/books/{bookId}/pages/{pageId}")
    public void updatePage(@PathVariable String pageId, @RequestBody @Valid PageDto update) {
        pageService.updatePage(pageId, update.toEntity());
    }

    @DeleteMapping("/api/books/{bookId}/pages/{pageId}")
    public void deletePage(@PathVariable String pageId) {
        pageService.deletePage(pageId);
    }

    @GetMapping("/api/books/{bookId}/page/{pageNumber}")
    public PageDto getPageByBookIdAndNumber(@PathVariable String bookId, @PathVariable int pageNumber) {
        return pageService.findPageByBookIdAndNumber(bookId, pageNumber).map(PageDto::fromEntity).orElseThrow(() -> new NotFoundException("Book not found by bookid: " + bookId + "or page not found by pagenumber: " + pageNumber));
    }

}
