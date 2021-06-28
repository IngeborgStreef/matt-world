package nl.mattworld.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PageService {
    private final PageRepository repository;

    @Autowired
    public PageService(PageRepository repository) {
        this.repository = repository;
    }

    //todo: check if this function is still needed
    public List<Page> retrievePages() {
        return repository.findAll();
    }

    public List<Page> listPagesPerBook(String bookId) {
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

}
