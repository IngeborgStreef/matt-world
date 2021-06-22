package nl.mattworld.book;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "BOOKS")
public class Book {
    @Id
    @GeneratedValue (generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid4")
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
}