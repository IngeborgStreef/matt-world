package nl.mattworld.page;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class PageServiceTest {

    @Mock
    private PageRepository repositoryMock;

    private PageService service;

    @BeforeEach
    public void setup() {
        service= new PageService(repositoryMock);
    }

    @Test
    public void canListPagesPerBook() {
        fail();
    }

    @Test
    public void canFindPageById() {
        fail();
    }

    @Test
    public void canFindPageByBookIdAndNumber() {
        fail();
    }

    @Test
    public void canCreatePage() {
        fail();
    }

}
