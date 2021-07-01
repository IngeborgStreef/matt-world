package nl.mattworld.user.child;

import lombok.Value;
import nl.mattworld.user.User;

import java.time.LocalDate;

@Value
public class ChildDto {
    String id;
    String name;
    LocalDate dateOfBirth;
    String parentId;

    public Child toEntity() {
        User parent = new User();
        parent.setId(parentId);
        Child child = new Child();
        child.setId(id);
        child.setName(name);
        child.setDateOfBirth(dateOfBirth);
        child.setParent(parent);
        return child;
    }

    public static ChildDto fromEntity(Child entity) {
        String parentId = null;
        if (entity.getParent() != null)
            parentId = entity.getParent().getId();
        return new ChildDto(entity.getId(), entity.getName(), entity.getDateOfBirth(), parentId);
    }
}
