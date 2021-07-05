package nl.mattworld.security;

import nl.mattworld.user.User;
import nl.mattworld.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailServiceTest {

    @Mock
    private UserService userService;

    private CustomUserDetailsService service;

    @BeforeEach
    public void setUp() {
        service = new CustomUserDetailsService(userService);
    }

    @Test
    public void canFindUserDetails(){
        String email = "matt@matt-world.nl";
        User user = new User();
        user.setEmail(email);
        user.setPassword("password");
        user.setRole(User.Role.USER);
        when(userService.findByEmail(email)).thenReturn(Optional.of(user));
        UserDetails userDetails = service.loadUserByUsername("matt@matt-world.nl");
        assertEquals(email,userDetails.getUsername());
        assertEquals(user.getPassword(),userDetails.getPassword());
        assertEquals(1,userDetails.getAuthorities().size());
        assertEquals("ROLE_USER",userDetails.getAuthorities().stream().findFirst().orElseThrow().getAuthority());
    }
}