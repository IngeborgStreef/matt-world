package nl.mattworld.user;

import lombok.Value;

@Value
public class UserDto {

    String id;
    String name;
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
        return new UserDto(entity.getId(), entity.getName(), entity.getEmail(), entity.getPassword(), entity.getAvatarUrl(), entity.getRole());
    }
}
