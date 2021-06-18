package nl.mattworld.book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    //SELECT * FROM books
    List<Book> findAll();
    //SELECT * FROM books WHERE id=id LIMIT 1
    Optional<Book> findOneById(String id);
    //SELECT * FROM books WHERE level >= level
    List<Book> findByLevelGte(int level);

    //if exists: updates book
    //if non exist: returns book including DB id
    Book saveBook(Book book);
}
