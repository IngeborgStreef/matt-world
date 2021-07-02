package nl.mattworld.user;

import nl.mattworld.user.child.Child;
import nl.mattworld.user.child.ChildRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceIT {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void deleteAll() {
        childRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void canCreateUser() {
        User user = new User();
        User created = userService.createUser(user);
        User found = userRepository.findById(created.getId()).orElseThrow();
        assertEquals(created, found);
    }
    @Test
    public void canCreateChild() {
        Child child = new Child();
        Child created = userService.createChild(child);
        Child found = childRepository.findById(created.getId()).orElseThrow();
        assertEquals(created, found);
    }

    @Test
    public void canFindUserById() {
        User user = new User();
        user.setId("1");
        User created = userRepository.save(user);
        Optional<User> found = userService.findById(created.getId());
        assertTrue(found.isPresent());
        assertEquals(created, found.get());
    }
    @Test
    public void canFindChildById() {
        Child child = new Child();
        child.setId("1");
        Child created = childRepository.save(child);
        Optional<Child> found = userService.findChildById(created.getId());
        assertTrue(found.isPresent());
        assertEquals(created, found.get());
    }

    @Test
    public void canListUsers() {
        User one = new User();
        userRepository.save(one);
        User two = new User();
        userRepository.save(two);
        List<User> users = userService.retrieveAll();
        assertEquals(2, users.size());
    }
    @Test
    public void canListChildrenFromParent() {
        User user = new User();
        user.setRole(User.Role.USER);
        user = userRepository.save(user);
        Child one = new Child();
        one.setParent(user);
        childRepository.save(one);
        Child two = new Child();
        two.setParent(user);
        childRepository.save(two);
        List<Child> children = userService.retrieveAllChildrenFromParent(user.getId());
        assertEquals(2, children.size());
    }

    @Test
    public void canUpdateUser() {
        User user = userService.createUser(new User());
        user.setName("piet");
        User created = userRepository.save(user);
        User update = new User();
        update.setId(created.getId());
        update.setName("henk");
        userService.updateUser(created.getId(), update);
        User result = userRepository.findById(created.getId()).orElseThrow();
        assertEquals("henk", result.getName());
    }
    @Test
    public void canUpdateChild() {
        User parent = userService.createUser(new User());
        Child child = new Child();
        child.setName("piet");
        child.setParent(parent);
        Child created = childRepository.save(child);
        Child update = new Child();
        update.setId(created.getId());
        update.setParent(child.getParent());
        update.setName("henk");
        userService.updateChild(created.getId(),update);
        Child result = childRepository.findById(created.getId()).orElseThrow();
        assertEquals("henk",result.getName());
    }

    @Test
    public void canDeleteUser() {
        User user = userRepository.save(new User());
        userService.deleteUser(user.getId());
        assertFalse(userRepository.existsById(user.getId()));
    }

    @Test
    public void canDeleteChild() {
        Child child = childRepository.save(new Child());
        userService.deleteChild(child.getId());
        assertFalse(childRepository.existsById(child.getId()));
    }

}