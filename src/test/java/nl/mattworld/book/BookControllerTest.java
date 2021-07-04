package nl.mattworld.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser(username = "matt", roles = "ADMIN")
    @Test
    public void createBook() throws Exception {
        BookDto book = new BookDto(null, 1, "Egypt", "http://matt-world.nl/images/egypt.jpg", "Egypt summary");
        when(bookService.createBook(book.toEntity())).thenReturn(book.toEntity());
        this.mockMvc.perform(post("/api/books")
                .content(objectMapper.writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.imageUrl").value(book.getImageUrl()))
                .andExpect(jsonPath("$.summary").value(book.getSummary()));
    }

    @WithMockUser(username = "matt", roles = "ADMIN")
    @Test
    public void createBookBadRequestMissingTitle() throws Exception {
        BookDto book = new BookDto(null, 1, null, "http://matt-world.nl/images/egypt.jpg", "Egypt summary");
        when(bookService.createBook(book.toEntity())).thenReturn(book.toEntity());
        this.mockMvc.perform(post("/api/books")
                .content(objectMapper.writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "matt", roles = "ADMIN")
    @Test
    public void getAllBooks() throws Exception {
        when(bookService.retrieveBooks()).thenReturn(List.of(new Book(), new Book()));
        this.mockMvc.perform(get("/api/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @WithMockUser(username = "matt", roles = "ADMIN")
    @Test
    public void getBookById() throws Exception {
        Book book = new Book();
        book.setId("1");
        when(bookService.findBookById("1")).thenReturn(Optional.of(book));
        this.mockMvc.perform(get("/api/books/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @WithMockUser(username = "matt", roles = "ADMIN")
    @Test
    public void updateBookById() throws Exception {
        BookDto book = new BookDto(null, 1, "Egypt", "http://matt-world.nl/images/egypt.jpg", "Egypt summary");

        this.mockMvc.perform(put("/api/books/1")
                .content(objectMapper.writeValueAsString( book))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(bookService).updateBook("1", book.toEntity());
    }

}