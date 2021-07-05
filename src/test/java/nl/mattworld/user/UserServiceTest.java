package nl.mattworld.user;

import nl.mattworld.exceptions.NotFoundException;
import nl.mattworld.user.child.Child;
import nl.mattworld.user.child.ChildRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;
    private UserService userService;
    @Mock
    private ChildRepository childRepositoryMock;
    @Mock
    private PasswordEncoder passwordEncoderMock;

    @BeforeEach
    public void setup() {
        userService = new UserService(userRepositoryMock, childRepositoryMock, passwordEncoderMock);
    }

    @Test
    public void canCreateUser() {
        String expectedId = "1";
        User testUser = new User();
        when(userRepositoryMock.save(testUser)).thenAnswer(inv -> {
            User user = inv.getArgument(0);
            user.setId(expectedId);
            return user;
        });
        User created = userService.createUser(testUser);
        assertEquals("1", created.getId());
    }
    @Test
    public void canCreateChild() {
        String expectedId = "1";
        Child testChild = new Child();
        User user = new User();
        user.setId("1");
        user.setRole(User.Role.USER);
        when(childRepositoryMock.save(testChild)).thenAnswer(inv -> {
           Child child = inv.getArgument(0);
           child.setId(expectedId);
           return child;
        });
        when(userRepositoryMock.findById(user.getId())).thenReturn(Optional.of(user));
        Child created = userService.createChild(user.getId(), testChild);
        assertEquals("1", created.getId());
    }

    @Test
    public void canFindUserById() {
        String id = "1";
        User user = new User();
        user.setId(id);
        when(userRepositoryMock.findById(id)).thenReturn(Optional.of(user));
        Optional<User> userOptional = userService.findById(id);
        assertTrue(userOptional.isPresent());
        assertEquals(id, userOptional.get().getId());
    }
    @Test
    public void canFindChildById() {
        String id = "1";
        Child child = new Child();
        child.setId(id);
        when(childRepositoryMock.findById(id)).thenReturn(Optional.of(child));
        Optional<Child> childOptional = userService.findChildById(id);
        assertTrue(childOptional.isPresent());
        assertEquals(id, childOptional.get().getId());
    }

    @Test
    public void canListUsers() {
        when(userRepositoryMock.findAll()).thenReturn(List.of(new User(), new User()));
        List<User> users = userService.retrieveAll();
        assertEquals(2, users.size());
    }
    @Test
    public void canListChildrenFromParent() {
        User user = new User();
        user.setId("1");
        user.setRole(User.Role.USER);
        when(childRepositoryMock.findAllByParentId(user.getId())).thenReturn(List.of(new Child(), new Child()));
        List<Child> children = userService.retrieveAllChildrenFromParent(user.getId());
        assertEquals(2, children.size());
    }

    @Test
    public void canUpdateUser() {
        User user = new User();
        user.setId("1");
        user.setRole(User.Role.USER);
        assertThrows(NotFoundException.class, () -> userService.updateUser(user.getId(), user));
        verify(userRepositoryMock, never()).save(user);
    }
    @Test
    public void canUpdateChild() {
        Child child = new Child();
        child.setId("1");
        when(childRepositoryMock.existsById(child.getId())).thenReturn(false);
        assertThrows(NotFoundException.class, () -> userService.updateChild(child.getId(), child));
        verify(childRepositoryMock, never()).save(child);
    }

    @Test
    public void canDeleteUser() {
        User user = new User();
        user.setId("1");
        userService.deleteUser(user.getId());
        verify(userRepositoryMock).deleteById(user.getId());
    }
    @Test
    public void canDeleteChild() {
        Child child = new Child();
        child.setId("1");
        userService.deleteChild(child.getId());
        verify(childRepositoryMock).deleteById(child.getId());
    }

    @Test
    public void canFindByEmail() {
        User user = new User();
        user.setName("Matt");
        when(userRepositoryMock.findByEmail(any())).thenReturn(Optional.of(user));
        Optional<User> found = userService.findByEmail("matt@matt-world.nl");
        assertTrue(found.isPresent());
        assertEquals("Matt", found.get().getName());
    }

}
