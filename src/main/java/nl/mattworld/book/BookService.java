package nl.mattworld.book;

import nl.mattworld.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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
        return repository.save(testBook);
    }

    public List<Book> retrieveBooksByMinimumLevel(int level) {
        return repository.findByLevelIsGreaterThanEqual(level);
    }

    public void updateBook(String id, Book update) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Unable to update. Book not found by ID: " + id);
        }
        update.setId(id);
        repository.save(update);
    }
}
