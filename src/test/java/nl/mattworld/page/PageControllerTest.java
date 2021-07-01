package nl.mattworld.page;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        PageDto page = new PageDto(null, "1", "Egypt", "Lorum", 2, "http://matt-world/pages/egypt");
        when(pageService.createPage(page.toEntity())).thenReturn(page.toEntity());
        this.mockMvc.perform(post("/api/pages")
                .content(objectMapper.writeValueAsString(page))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(page.getTitle()))
                .andExpect(jsonPath("$.imageUrl").value(page.getImageUrl()));
    }

    @Test
    public void getAllPages() throws Exception {
        PageDto page = new PageDto(null, "book_id", "Egypt", "Lorum", 2, "http://matt-world/pages/egypt");
        when(pageService.retrievePagesPerBook(page.getBookId())).thenReturn(List.of(page.toEntity(), page.toEntity()));
        this.mockMvc.perform(get("/api/books/book_id/pages"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
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

    @Test
    public void updatePage() throws Exception {
        PageDto page = new PageDto(null, "1", "Egypt", "This is about Egypt", 1, "http://matt-world.nl/images/egypt.jpg");
        this.mockMvc.perform(put("/api/books/book_id/pages/1")
                .content(objectMapper.writeValueAsString(page))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(pageService).updatePage("1", page.toEntity());
    }

    @Test
    public void deletePage() throws Exception {
        this.mockMvc.perform(delete("/api/books/book_id/pages/1"))
                .andDo(print())
                .andExpect(status().isOk());
        verify(pageService).deletePage("1");
    }
}