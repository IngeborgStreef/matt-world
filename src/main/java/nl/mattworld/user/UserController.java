package nl.mattworld.user;

import nl.mattworld.exceptions.NotFoundException;
import nl.mattworld.user.child.ChildDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users/{userId}")
    public UserDto getUser(@PathVariable String userId) {
        return userService.findById(userId).map(UserDto::fromEntity).orElseThrow(() -> new NotFoundException("User not found by ID: " + userId));
    }

    @GetMapping("/api/users/{userId}/children/{childId}")
    public ChildDto getChild(@PathVariable String childId) {
        return userService.findChildById(childId).map(ChildDto::fromEntity).orElseThrow(() -> new NotFoundException("Child not found by ID: " + childId));
    }

    @GetMapping("/api/users")
    public List<UserDto> getAllUsers() {
        return userService.retrieveAll().stream().map(UserDto::fromEntity).collect(Collectors.toList());
    }

    @GetMapping("/api/users/{userId}/children")
    public List<ChildDto> getAllChildrenFromParent(@PathVariable String userId) {
        return userService.retrieveAllChildrenFromParent(userId).stream().map(ChildDto::fromEntity).collect(Collectors.toList());
    }

    @PutMapping("/api/users/{id}")
    public void updateUser(@PathVariable String id, @RequestBody @Valid UserDto update) {
        userService.updateUser(id, update.toEntity());
    }

    @PutMapping("/api/users/{userId}/children/{childId}")
    public void updateChild(@PathVariable String childId, @RequestBody @Valid ChildDto update) {
        userService.updateChild(childId, update.toEntity());
    }

    @DeleteMapping("/api/users/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }

    @DeleteMapping("/api/users/{userId}/children/{childId}")
    public void deleteChild(@PathVariable String childId) {
        userService.deleteChild(childId);
    }

}
