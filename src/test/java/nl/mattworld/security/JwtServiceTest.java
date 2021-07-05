package nl.mattworld.security;

import nl.mattworld.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JwtServiceTest {

    private JwtService jwtService = new JwtService("secret");

    @Test
    public void canGenerateToken() {
        User user = new User();
        user.setPassword("password");
        user.setEmail("matt@matt-world.nl");
        user.setRole(User.Role.USER);
        String token = jwtService.generateToken(user);
        System.out.println(token);
        assertEquals(3, token.split("\\.").length);
    }

    @Test
    public void canValidateToken() {
        User user = new User();
        user.setPassword("password");
        user.setEmail("matt@matt-world.nl");
        user.setRole(User.Role.USER);
        String token = jwtService.generateToken(user);
        assertTrue(jwtService.validateToken(token, user));
    }


    @Test
    public void canExtractUsername() {
        User user = new User();
        user.setPassword("password");
        user.setEmail("matt@matt-world.nl");
        user.setRole(User.Role.USER);
        String token = jwtService.generateToken(user);
        assertEquals(user.getEmail(), jwtService.extractUsername(token));
    }
}