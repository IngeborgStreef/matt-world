package nl.mattworld.user.avatar;

import lombok.RequiredArgsConstructor;
import nl.mattworld.exceptions.NotFoundException;
import nl.mattworld.user.User;
import nl.mattworld.user.UserService;
import nl.mattworld.user.child.Child;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class AvatarController {

    private final UserService userService;
    private final AvatarService avatarService;

    @PostMapping("/api/users/{userId}/avatar")
    public String uploadUserAvatar(@RequestParam("file") MultipartFile file, @PathVariable String userId) {
        User user = userService.findById(userId).orElseThrow(() -> new NotFoundException("User not found by id: " + userId));

        String url = avatarService.save(userId, file);
        user.setAvatarUrl(url);
        userService.updateUser(userId, user);
        return "Upload successful";
    }

    @PostMapping("/api/users/{userId}/children/{childId}/avatar")
    public String uploadChildAvatar(@RequestParam("file") MultipartFile file, @PathVariable String childId) {
        Child child = userService.findChildById(childId).orElseThrow(() -> new NotFoundException("Child not found by id: " + childId));
        String url = avatarService.save(childId, file);
        child.setAvatarUrl(url);
        userService.updateChild(childId, child);
        return "Upload successful";
    }
}
