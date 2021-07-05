package nl.mattworld.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.mattworld.security.JwtService;
import nl.mattworld.user.child.Child;
import nl.mattworld.user.child.ChildDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    //needed for security to work
    @MockBean
    private UserDetailsService userDetailsServiceMock;
    @MockBean
    private JwtService jwtServiceMock;

    @WithMockUser(username = "matt", roles = "ADMIN")
    @Test
    public void canGetUser() throws Exception {
        User user = new User();
        user.setId("user_id");
        when(userService.findById("user_id")).thenReturn(Optional.of(user));
        this.mockMvc.perform(get("/api/users/user_id"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("user_id"));
    }

    @WithMockUser(username = "matt", roles = "ADMIN")
    @Test
    public void canGetUsers() throws Exception {
        User user = new User();
        user.setId("user_id");
        when(userService.retrieveAll()).thenReturn(List.of(user, user));
        this.mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @WithMockUser(username = "matt", roles = "ADMIN")
    @Test
    public void canCreateUser() throws Exception {
        UserDto userDto = new UserDto(null, "Matt", "matt@matt-world.nl", "secret", "http://matt-world.nl/images/users/matt.jpg", User.Role.USER);
        when(userService.createUser(any())).thenReturn(userDto.toEntity());
        this.mockMvc.perform(post("/api/users").content(objectMapper.writeValueAsString(userDto)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.name").value(userDto.getName()))
                .andExpect(jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(jsonPath("$.avatarUrl").value(userDto.getAvatarUrl()))
                .andExpect(jsonPath("$.role").value(userDto.getRole().toString()));
    }

    @WithMockUser(username = "matt", roles = "ADMIN")
    @Test
    public void canUpdateUser() throws Exception {
        UserDto userDto = new UserDto("user_id", "Matt", "matt@matt-world.nl", "secret", "http://matt-world.nl/images/users/matt.jpg", User.Role.USER);

        this.mockMvc.perform(put("/api/users/user_id").content(objectMapper.writeValueAsString(userDto)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(userService).updateUser("user_id", userDto.toEntity());
    }

    @WithMockUser(username = "matt", roles = "ADMIN")
    @Test
    public void canDeleteUser() throws Exception {

        this.mockMvc.perform(delete("/api/users/user_id"))
                .andDo(print())
                .andExpect(status().isOk());
        verify(userService).deleteUser("user_id");
    }


    @WithMockUser(username = "matt", roles = "ADMIN")
    @Test
    public void canGetChild() throws Exception {
        Child child = new Child();
        child.setId("child_id");
        when(userService.findChildById("child_id")).thenReturn(Optional.of(child));
        this.mockMvc.perform(get("/api/users/user_id/children/child_id"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("child_id"));
    }

    @WithMockUser(username = "matt", roles = "ADMIN")
    @Test
    public void canGetChildren() throws Exception {
        Child child = new Child();
        child.setId("child_id");
        when(userService.retrieveAllChildrenFromParent("user_id")).thenReturn(List.of(child, child));
        this.mockMvc.perform(get("/api/users/user_id/children"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @WithMockUser(username = "matt", roles = "ADMIN")
    @Test
    public void canCreatChild() throws Exception {
        ChildDto childDto = new ChildDto(null, "Matt", LocalDate.of(2020, 10, 18), "http://matt-world.nl/images/children/matt.jpg", "user_id");
        when(userService.createChild("user_id", childDto.toEntity())).thenReturn(childDto.toEntity());
        this.mockMvc.perform(post("/api/users/user_id/children").content(objectMapper.writeValueAsString(childDto)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(childDto.getName()))
                .andExpect(jsonPath("$.parentId").value(childDto.getParentId()))
                .andExpect(jsonPath("$.dateOfBirth").value(childDto.getDateOfBirth().toString()))
                .andExpect(jsonPath("$.avatarUrl").value(childDto.getAvatarUrl()));
    }

    @WithMockUser(username = "matt", roles = "ADMIN")
    @Test
    public void canUpdateChild() throws Exception {
        ChildDto childDto = new ChildDto(null, "Matt", LocalDate.of(2020, 10, 18), "http://matt-world.nl/images/children/matt.jpg", "user_id");

        this.mockMvc.perform(put("/api/users/user_id/children/child_id").content(objectMapper.writeValueAsString(childDto)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(userService).updateChild("child_id", childDto.toEntity());
    }

    @WithMockUser(username = "matt", roles = "ADMIN")
    @Test
    public void canDeleteChild() throws Exception {

        this.mockMvc.perform(delete("/api/users/user_id/children/child_id"))
                .andDo(print())
                .andExpect(status().isOk());
        verify(userService).deleteChild("child_id");
    }
}
