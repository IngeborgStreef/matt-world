package nl.mattworld.user;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Child {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
    private LocalDate dateOfBirth;
    @ManyToOne(fetch = FetchType.LAZY)
    private Parent parent;
}