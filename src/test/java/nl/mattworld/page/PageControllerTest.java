package nl.mattworld.page;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.mattworld.book.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PageController.class)
public class PageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PageService pageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createPage() throws Exception {
        Page page = new Page();
        page.setTitle("Egypt");
        when(pageService.createPage(page)).thenReturn(page);
        this.mockMvc.perform(post("/api/pages")
                .content(objectMapper.writeValueAsString(page))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Egypt"));
    }

    @Test
    public void getAllPages() throws Exception {
        Book book = new Book();
        book.setId("1");
        Page pageOne = new Page();
        Page pageTwo = new Page();
        pageOne.setBookId("1");
        pageTwo.setBookId("1");

        when(pageService.listPagesPerBook("book_id")).thenReturn(List.of(pageOne, pageTwo));
        this.mockMvc.perform(get("/api/books/book_id/pages"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void getPageById() throws Exception {
        Page page = new Page();
        page.setId("1");
        page.setNumber(2);
        when(pageService.findPageById("1")).thenReturn(Optional.of(page));
        this.mockMvc.perform(get("/api/books/book_id/pages/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    public void getPageByBookIdAndNumber() throws Exception {
        Page page = new Page();
        page.setId("1");
        page.setNumber(2);
        when(pageService.findPageByBookIdAndNumber("book_id", 2)).thenReturn(Optional.of(page));
        this.mockMvc.perform(get("/api/books/book_id/page/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }
}