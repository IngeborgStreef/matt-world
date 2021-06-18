package nl.mattworld.book;

import java.util.List;
import java.util.Optional;

public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> retrieveBooks() {
        return repository.findAll();
    }

    public Optional<Book> findBookById(String id) {
        return repository.findOneById(id);
    }

    public Book createBook(Book testBook) {
        return repository.saveBook(testBook);
    }

    public List<Book> retrieveBookByMinimumLevel(int level) {
        return repository.findByLevelGte(level);
    }
}
