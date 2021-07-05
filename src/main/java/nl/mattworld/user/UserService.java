package nl.mattworld.user;

import lombok.RequiredArgsConstructor;
import nl.mattworld.exceptions.NoValidUserFoundException;
import nl.mattworld.exceptions.NotFoundException;
import nl.mattworld.exceptions.RoleUpdateNotAllowedException;
import nl.mattworld.user.child.Child;
import nl.mattworld.user.child.ChildRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ChildRepository childRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Child createChild(String userId, Child child) {
        findById(userId).filter(user -> user.getRole() == User.Role.USER).orElseThrow(() -> new NoValidUserFoundException("User is not found or role is not valid"));
        return childRepository.save(child);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Optional<Child> findChildById(String id) {
        return childRepository.findById(id);
    }

    public List<User> retrieveAll() {
        return userRepository.findAll();
    }

    public List<Child> retrieveAllChildrenFromParent(String parentId) {
        return childRepository.findAllByParentId(parentId);
    }

    public void updateUser(String id, User update) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Unable to update. User not found by ID: " + id));
        if (user.getRole() != update.getRole())
            throw new RoleUpdateNotAllowedException("Role update not allowed");
        update.setId(id);
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(update);
    }

    public void updateChild(String id, Child update) {
        if (!childRepository.existsById(id)) {
            throw new NotFoundException("Unable to update. Child not found by ID: " + id);
        }
        update.setId(id);
        childRepository.save(update);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public void deleteChild(String id) {
        childRepository.deleteById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
