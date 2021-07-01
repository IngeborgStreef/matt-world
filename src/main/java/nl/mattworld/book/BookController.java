package nl.mattworld.book;

import nl.mattworld.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/books")
    public List<BookDto> getAllBooks() {
        return bookService.retrieveBooks().stream().map(BookDto::fromEntity).collect(Collectors.toList());
    }

    @PostMapping("/api/books")
    public BookDto createBook(@RequestBody @Valid BookDto book) {
        return BookDto.fromEntity(bookService.createBook(book.toEntity()));
    }

    @GetMapping("/api/books/{id}")
    public BookDto getBook(@PathVariable String id) {
        return bookService.findBookById(id).map(BookDto::fromEntity).orElseThrow(()->new NotFoundException("Book not found by ID: " + id));
    }

    @PutMapping("/api/books/{id}")
    public void updateBook(@PathVariable String id, @RequestBody @Valid BookDto update) {
        bookService.updateBook(id, update.toEntity());
    }

}
