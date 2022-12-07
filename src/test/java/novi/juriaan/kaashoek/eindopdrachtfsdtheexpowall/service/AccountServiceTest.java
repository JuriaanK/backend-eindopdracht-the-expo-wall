package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.service;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.AccountDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.UserDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Account;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Role;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.User;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.AccountRepository;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.RoleRepository;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.UserRepository;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.security.MyUserDetails;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AccountServiceTest {
    @Mock
    AccountRepository accountRepos;

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    RoleService roleService;

    @InjectMocks
    AccountService accountService;

    @Mock
    SecurityConfig securityConfig;

    @Mock
    MyUserDetails myUserDetails;

    @Captor
    ArgumentCaptor<Account> argumentCaptor;

    Account account1;
    Account account2;
    Account account3;

    User user1;
    User user2;
    User user3;

    Account updateAccount;

    Role role;
    @BeforeEach
    void setUp() {

        account1 = new Account(1L, "User1", "TestU1", LocalDate.of(1990, 01,01));
        account2 = new Account(2L, "User2", "TestU2", LocalDate.of(1990, 01,01));
        account3 = new Account(3L, "User3", "TestU3", LocalDate.of(1990, 01,01));

        user1 = new User("user1", "user1@test.nl", "W3lk0m!", "blabla", account1, 1L);
        user2 = new User("user2", "user2@test.nl", "W3lk0m!", "blabla", account2, 2L);
        user3 = new User("user3", "user3@test.nl", "W3lk0m!", "blabla", account3, 3L);

        updateAccount =  new Account(1L, "User1", "TestCase1", LocalDate.of(1990, 01,01));
        Collection<Role> userRoles = new ArrayList<>();
        role = new Role();
        role.setRolename("USER");
        userRoles.add(role);
        user2.setRoles(userRoles);
    }

    @Test
    void getAccounts() {
        when(accountRepos.findAll()).thenReturn(List.of(account1, account2, account3));

        List<Account> accounts = accountRepos.findAll();
        List<AccountDTO> accountDTOS = accountService.getAccounts();

        assertEquals(accounts.get(0).getId(), accountDTOS.get(0).id);
        assertEquals(accounts.get(0).getFirstName(), accountDTOS.get(0).firstName);
        assertEquals(accounts.get(0).getLastName(), accountDTOS.get(0).lastName);
        assertEquals(accounts.get(0).getDOB(), accountDTOS.get(0).DOB);
    }

    @Test
    void getAccount() {
        when(accountRepos.findById(1L)).thenReturn(Optional.ofNullable(account1));

        Account account = accountRepos.findById(1L).get();
        AccountDTO accountDTO = accountService.getAccount(1L);

        assertEquals(account.getId(), accountDTO.id);
        assertEquals(account.getFirstName(), accountDTO.firstName);
        assertEquals(account.getLastName(), accountDTO.lastName);
        assertEquals(account.getDOB(), accountDTO.DOB);

    }

    @Test
    void deleteAccount() {
        accountService.deleteAccount(1L);
        verify(accountRepos).deleteById(1L);
    }

    @Test
    void changeAccount() {
        when(accountRepos.findById(1L)).thenReturn(Optional.of(account1));

        AccountDTO accountDTO = AccountDTO.fromAccount(updateAccount);

        when(accountRepos.save(AccountDTO.toAccount(accountDTO))).thenReturn(account1);

        accountService.changeAccount(1L , accountDTO);

        verify(accountRepos, times(1)).save(argumentCaptor.capture());

        Account captured = argumentCaptor.getValue();

        assertEquals(accountDTO.id, captured.getId());
        assertEquals(accountDTO.firstName, captured.getFirstName());
        assertEquals(accountDTO.lastName, captured.getLastName());
        assertEquals(accountDTO.DOB, captured.getDOB());
    }

}