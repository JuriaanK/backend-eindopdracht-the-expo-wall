package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.controller;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.AccountDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.UserDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Account;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.User;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.RoleRepository;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.UserRepository;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.security.JwtService;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.security.SecurityConfig;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.service.UserService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.*;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserService userService;

    @MockBean
    UserDetailsService userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    User user1;
    User user2;
    User user3;
    User userInput1;

    Account account1;
    Account account2;
    Account account3;

    AccountDTO accountDTO1;
    AccountDTO accountDTO2;
    AccountDTO accountDTO3;

    UserDTO userDTO1;
    UserDTO userDTO2;
    UserDTO userDTO3;

    UserDTO userInputDTO1;

    @BeforeEach
    public void setUp(){

        account1 = new Account(1L, "User1", "TestU1", LocalDate.of(1990, 01,01));
        account2 = new Account(2L, "User2", "TestU2", LocalDate.of(1990, 01,01));
        account3 = new Account(3L, "User3", "TestU3", LocalDate.of(1990, 01,01));

        user1 = new User("user1", "user1@test.nl", "W3lk0m!", "blabla", account1, 1L);
        user2 = new User("user2", "user2@test.nl", "W3lk0m!", "blabla", account2, 2L);
        user3 = new User("user3", "user3@test.nl", "W3lk0m!", "blabla", account3, 3L);

        accountDTO1 = AccountDTO.fromAccount(account1);
        accountDTO2 = AccountDTO.fromAccount(account2);
        accountDTO3 = AccountDTO.fromAccount(account3);

        userInput1 = new User("user1", "user1@test.nl", "W3lk0m!","blabla");

        userDTO1 = UserDTO.fromUser(user1);
        userDTO2 = UserDTO.fromUser(user2);
        userDTO3 = UserDTO.fromUser(user3);

        userInputDTO1 = UserDTO.fromUser(userInput1);
    }

    @Test
    @WithMockUser(username="testuser", roles="USER")
    void shouldFetchAllUsers() throws Exception {
        given(userService.getUsers()).willReturn(List.of(userDTO1, userDTO2, userDTO3));

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[0].email").value("user1@test.nl"))
                .andExpect(jsonPath("$[0].password").value("W3lk0m!"))
                .andExpect(jsonPath("$[0].userBio").value("blabla"))
                .andExpect(jsonPath("$[0].accountID").value(1L))
                .andExpect(jsonPath("$[1].username").value("user2"))
                .andExpect(jsonPath("$[1].email").value("user2@test.nl"))
                .andExpect(jsonPath("$[1].password").value("W3lk0m!"))
                .andExpect(jsonPath("$[1].userBio").value("blabla"))
                .andExpect(jsonPath("$[1].accountID").value(2L))
                .andExpect(jsonPath("$[2].username").value("user3"))
                .andExpect(jsonPath("$[2].email").value("user3@test.nl"))
                .andExpect(jsonPath("$[2].password").value("W3lk0m!"))
                .andExpect(jsonPath("$[2].userBio").value("blabla"))
                .andExpect(jsonPath("$[2].accountID").value(3L));
    }
    @Test
    @WithMockUser(username="testuser", roles="USER")
    void shouldFetchUser() throws Exception {
        given(userService.getUser(anyString())).willReturn(userDTO2);

        mockMvc.perform(get("/users/user2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value("user2"))
                .andExpect(jsonPath("email").value("user2@test.nl"))
                .andExpect(jsonPath("password").value("W3lk0m!"))
                .andExpect(jsonPath("userBio").value("blabla"))
                .andExpect(jsonPath("accountID").value(2L));
    }

    @Test
    void shouldSaveUser() throws Exception {
        given(userService.createUser(userDTO1)).willReturn(userDTO1.username);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO1)))
                .andExpect(status().isOk());

    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}