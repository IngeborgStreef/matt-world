package nl.mattworld.user.avatar;

import nl.mattworld.security.JwtService;
import nl.mattworld.user.User;
import nl.mattworld.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AvatarIT {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Test
    public void canUploadAvatar() throws IOException {

        User user = createUser();
        String token = jwtService.generateToken(user);
        ResponseEntity<String> response = uploadForUser(user.getId(), token);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Upload successful", response.getBody());
        User found = userRepository.findById(user.getId()).orElseThrow();
        assertNotNull(found.getAvatarUrl());
    }

    @Test
    public void canUploadAndDownloadAvatar() throws IOException {
        User user = createUser();
        String token = jwtService.generateToken(user);
        uploadForUser(user.getId(), token);
        User found = userRepository.findById(user.getId()).orElseThrow();
        System.out.println(found.getAvatarUrl());

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        String image = restTemplate.exchange(found.getAvatarUrl(), HttpMethod.GET,new HttpEntity<>(headers),String.class).getBody();
        assertTrue(image.contains("PNG"));
    }


    private ResponseEntity<String> uploadForUser(String userId, String token) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(token);

        File file = new ClassPathResource("matt.png").getFile();
        assertTrue(file.exists());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(file));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        return restTemplate.postForEntity("/api/users/" + userId + "/avatar", requestEntity, String.class);
    }

    private User createUser() {
        User toSave = new User();
        toSave.setEmail("matt@matt-world.nl");
        toSave.setRole(User.Role.USER);
        return userRepository.save(toSave);
    }
}
