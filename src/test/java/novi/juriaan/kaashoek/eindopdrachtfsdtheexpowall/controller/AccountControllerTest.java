package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.AccountDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Account;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.AccountRepository;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.RoleRepository;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.UserRepository;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.security.JwtService;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.service.AccountService;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.boot.convert.ApplicationConversionService.configure;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AccountController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AccountService accountService;

    @MockBean
    UserDetailsService userDetailsService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    Account account1;
    Account account2;
    Account account3;
    Account accountCreate;
    Account accountChange;
    AccountDTO accountDTO1;
    AccountDTO accountDTO2;
    AccountDTO accountDTO3;

    AccountDTO accountCreateDTO;

    AccountDTO accountChangeDTO;

    @BeforeEach
    public void setUp() {

        account1 = new Account(1L, "User1", "TestU1", LocalDate.of(1990, 01,01));
        account2 = new Account(2L, "User2", "TestU2", LocalDate.of(1990, 01,01));
        account3 = new Account(3L, "User3", "TestU3", LocalDate.of(1990, 01,01));

        accountCreate = new Account(1L, "User1", "TestU1", LocalDate.of(1990, 01,01));

        accountChange = new Account(1L, "User5", "TestU3", LocalDate.of(1990, 01,01));

        accountDTO1 = AccountDTO.fromAccount(account1);
        accountDTO2 = AccountDTO.fromAccount(account2);
        accountDTO3 = AccountDTO.fromAccount(account3);

        accountCreateDTO = AccountDTO.fromAccount(accountCreate);
        accountChangeDTO = AccountDTO.fromAccount(accountChange);
    }

    @Test
    void shouldGetAccounts() throws Exception{
        when(accountService.getAccounts()).thenReturn(List.of(accountDTO1, accountDTO2, accountDTO3));

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].firstName").value("User1"))
                .andExpect(jsonPath("$[0].lastName").value("TestU1"))
                .andExpect(jsonPath("$[0].DOB").value("1990-01-01"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].firstName").value("User2"))
                .andExpect(jsonPath("$[1].lastName").value("TestU2"))
                .andExpect(jsonPath("$[1].DOB").value("1990-01-01"))
                .andExpect(jsonPath("$[2].id").value(3L))
                .andExpect(jsonPath("$[2].firstName").value("User3"))
                .andExpect(jsonPath("$[2].lastName").value("TestU3"))
                .andExpect(jsonPath("$[2].DOB").value("1990-01-01"));
    }

    @Test
    void shouldGetAccount() throws Exception{
        when(accountService.getAccount(1L)).thenReturn(accountDTO1);

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("firstName").value("User1"))
                .andExpect(jsonPath("lastName").value("TestU1"))
                .andExpect(jsonPath("DOB").value("1990-01-01"));
    }

    @Test
    void shouldCreateAccount() throws Exception{
        when(accountService.createAccount(accountCreateDTO)).thenReturn(accountCreateDTO);

        mockMvc.perform(post("/accounts").with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(accountCreateDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldUpdateAccount() throws Exception{
        given(accountService.changeAccount(1L, accountChangeDTO)).willReturn(accountChangeDTO);

        mockMvc.perform(put("/accounts/1").with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(accountChangeDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteAccount() throws Exception{
    }

    public static String asJsonString(final Object obj) {
        try {
                return new ObjectMapper().writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
        }
    }
}