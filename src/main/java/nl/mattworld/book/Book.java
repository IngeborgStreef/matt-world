package nl.mattworld.book;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table (name = "BOOKS")
public class Book {
    @Id
    @GeneratedValue (generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private int level;
    private String title;
    private String imageUrl;
    private String summery;

    public Book(String id, int level, String title, String imageUrl, String summery) {
        this.id = id;
        this.level = level;
        this.title = title;
        this.imageUrl = imageUrl;
        this.summery = summery;
    }

    public Book() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSummery() {
        return summery;
    }

    public void setSummery(String summery) {
        this.summery = summery;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", level=" + level +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", summery='" + summery + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return level == book.level && Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(imageUrl, book.imageUrl) && Objects.equals(summery, book.summery);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, level, title, imageUrl, summery);
    }
}