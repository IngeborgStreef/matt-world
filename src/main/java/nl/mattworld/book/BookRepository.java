package nl.mattworld.book;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, String> {
    //SELECT * FROM books
    List<Book> findAll();
    //SELECT * FROM books WHERE id=id LIMIT 1
    Optional<Book> findOneById(String id);
    //SELECT * FROM books WHERE level >= level
    List<Book> findByLevelIsGreaterThanEqual(int level);
}
