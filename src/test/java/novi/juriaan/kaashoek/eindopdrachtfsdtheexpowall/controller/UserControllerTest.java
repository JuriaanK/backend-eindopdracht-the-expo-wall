package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.controller;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.AccountDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.UserDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Account;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.User;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.UserRepository;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.security.JwtService;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.service.UserService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.*;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@ContextConfiguration("/applicationContext.xml")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    User user1;
    User user2;
    User user3;

    Account account1;
    Account account2;
    Account account3;

    AccountDTO accountDTO1;
    AccountDTO accountDTO2;
    AccountDTO accountDTO3;

    UserDTO userDTO1;
    UserDTO userDTO2;
    UserDTO userDTO3;



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

        userDTO1 = UserDTO.fromUser(user1);
        userDTO2 = UserDTO.fromUser(user2);
        userDTO3 = UserDTO.fromUser(user3);
    }



    @Test
    @WithMockUser(username="testuser", roles="USER")
    void shouldFetchAllUsers() throws Exception {
        given(userService.getUsers()).willReturn(List.of(userDTO1, userDTO2, userDTO3));

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk());


    }
}