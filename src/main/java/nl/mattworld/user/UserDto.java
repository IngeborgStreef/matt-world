package nl.mattworld.user;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
public class UserDto {

    String id;
    @NotBlank
    String name;
    @Email
    String email;
    String password;
    String avatarUrl;
    User.Role role;

    public User toEntity() {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setAvatarUrl(avatarUrl);
        user.setRole(role);
        return user;
    }

    public static UserDto fromEntity(User entity) {
        return new UserDto(entity.getId(), entity.getName(), entity.getEmail(), null, entity.getAvatarUrl(), entity.getRole());
    }
}
