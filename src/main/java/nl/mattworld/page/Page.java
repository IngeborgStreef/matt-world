package nl.mattworld.page;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table (name = "PAGES")
public class Page {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String bookId;
    private String title;
    private String text;
    private int number;
    private String imageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Page{" +
                "id='" + id + '\'' +
                ", bookId='" + bookId + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", number=" + number +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return number == page.number && Objects.equals(id, page.id) && Objects.equals(bookId, page.bookId) && Objects.equals(title, page.title) && Objects.equals(text, page.text) && Objects.equals(imageUrl, page.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId, title, text, number, imageUrl);
    }
}