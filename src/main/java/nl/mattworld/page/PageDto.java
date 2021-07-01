package nl.mattworld.page;

import lombok.Value;
import nl.mattworld.book.Book;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Value
public class PageDto {
    String id;
    @NotBlank
    String bookId;
    @NotBlank
    String title;
    @NotBlank
    String text;
    @Positive
    int number;
    @NotBlank
    String imageUrl;

    public static PageDto fromEntity(Page page) {
        String bookId = null;
        if (page.getBook() != null)
            bookId = page.getBook().getId();
        return new PageDto(page.getId(), bookId, page.getTitle(), page.getText(), page.getNumber(), page.getImageUrl());
    }

    public Page toEntity() {
        Book book = new Book();
        book.setId(bookId);
        Page page = new Page();
        page.setId(id);
        page.setBook(book);
        page.setTitle(title);
        page.setText(text);
        page.setNumber(number);
        page.setImageUrl(imageUrl);
        return page;
    }

}