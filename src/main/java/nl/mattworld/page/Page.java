package nl.mattworld.page;

import lombok.Data;
import nl.mattworld.book.Book;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table (name = "PAGES")
public class Page {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @ManyToOne
    private Book book;
    private String title;
    private String text;
    private int number;
    private String imageUrl;

}