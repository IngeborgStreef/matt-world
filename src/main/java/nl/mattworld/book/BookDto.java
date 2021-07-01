package nl.mattworld.book;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Value
public class BookDto {
    String id;
    @Positive
    int level;
    @NotBlank
    String title;
    @NotBlank
    String imageUrl;
    @NotBlank
    String summary;

    public Book toEntity() {
        Book book = new Book();
        book.setId(id);
        book.setLevel(level);
        book.setTitle(title);
        book.setImageUrl(imageUrl);
        book.setSummary(summary);
        return book;
    }

    public static BookDto fromEntity(Book book) {
        return new BookDto(book.getId(), book.getLevel(), book.getTitle(), book.getImageUrl(), book.getSummary());
    }

}