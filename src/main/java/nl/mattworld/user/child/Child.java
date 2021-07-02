package nl.mattworld.user.child;

import lombok.Data;
import nl.mattworld.user.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table (name = "CHILDREN")
public class Child {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
    private LocalDate dateOfBirth;
    @ManyToOne(fetch = FetchType.LAZY)
    private User parent;
}