package nl.mattworld.page;

import lombok.RequiredArgsConstructor;
import nl.mattworld.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PageService {
    private final PageRepository repository;

    //todo: check if this function is still needed
    public List<Page> retrievePages() {
        return repository.findAll();
    }

    public List<Page> retrievePagesPerBook(String bookId) {
        return repository.findAllByBookId(bookId);
    }

    public Optional<Page> findPageById(String id) {
        return repository.findOneById(id);
    }

    public Optional<Page> findPageByBookIdAndNumber(String bookId, int number) {
        return repository.findOneByBookIdAndNumber(bookId, number);
    }

    public Page createPage(Page page) {
        return repository.save(page);
    }

    public void updatePage(String id, Page update) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Unable to update. Page not found by ID: " + id);
        }
        update.setId(id);
        repository.save(update);
    }

    public void deletePage(String id) {
        repository.deleteById(id);
    }

}
