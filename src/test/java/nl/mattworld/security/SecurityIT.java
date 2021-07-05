package nl.mattworld.security;

import nl.mattworld.book.Book;
import nl.mattworld.book.BookDto;
import nl.mattworld.user.User;
import nl.mattworld.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityIT {

    private final String userEmail = "user@matt-world.nl";
    private final String adminEmail = "admin@matt-world.nl";
    private final String password = "password";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setup() {
        if (userService.findByEmail(userEmail).isEmpty()) {
            User user = new User();
            user.setEmail(userEmail);
            user.setPassword(password);
            user.setRole(User.Role.USER);
            userService.createUser(user);
        }
        if (userService.findByEmail(adminEmail).isEmpty()) {
            User user = new User();
            user.setEmail(adminEmail);
            user.setPassword(password);
            user.setRole(User.Role.ADMIN);
            userService.createUser(user);
        }
    }

    @Test
    public void canLogin() {
        String jwt = login(userEmail);
        assertNotNull(jwt);
    }

    @Test
    public void canAccessAsUser() {
        String jwt = login(userEmail);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);
        assertTrue(restTemplate.exchange("/api/books", HttpMethod.GET, new HttpEntity<>(headers), Book[].class).getStatusCode().is2xxSuccessful());
        BookDto book = new BookDto(null, 1, "Egypt", "http://matt-world.nl/images/egypt.jpg", "Egypt summary");
        assertEquals(403, restTemplate.exchange("/api/books", HttpMethod.POST, new HttpEntity<>(book, headers), Book.class).getStatusCodeValue());
    }

    @Test
    public void canAccessAsAdmin() {
        String jwt = login(adminEmail);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);
        assertTrue(restTemplate.exchange("/api/books", HttpMethod.GET, new HttpEntity<>(headers), Book[].class).getStatusCode().is2xxSuccessful());
        BookDto book = new BookDto(null, 1, "Egypt", "http://matt-world.nl/images/egypt.jpg", "Egypt summary");
        assertTrue(restTemplate.exchange("/api/books", HttpMethod.POST, new HttpEntity<>(book, headers), Book.class).getStatusCode().is2xxSuccessful());
    }

    @Test
    public void canNotAccess() {
        login(userEmail);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("fake");
        assertTrue(restTemplate.exchange("/api/books", HttpMethod.GET, new HttpEntity<>(headers), Book[].class).getStatusCode().is4xxClientError());
    }

    private String login(String email) {
        LoginController.AuthenticationRequest request = new LoginController.AuthenticationRequest(email, password);
        LoginController.AuthenticationResponse response = restTemplate.postForObject("/login", request, LoginController.AuthenticationResponse.class);
        return response.getJwt();
    }
}