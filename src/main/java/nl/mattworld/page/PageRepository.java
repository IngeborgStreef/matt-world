package nl.mattworld.page;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PageRepository extends CrudRepository<Page, String> {
    List<Page> findAll();
    Optional<Page> findOneById(String id);
    List<Page> findAllByBookId(String bookId);
    Optional<Page> findOneByBookIdAndNumber(String bookId, int number);
}

//todo: kijken welke functies al onderdeel zijn van de CrudRepository