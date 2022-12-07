package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.service;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.UserDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Account;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Role;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.User;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.RoleRepository;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.UserRepository;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.security.MyUserDetails;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {
    @Mock
    UserRepository userRepos;

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    RoleService roleService;

    @InjectMocks
    UserService userService;

    @Mock
    SecurityConfig securityConfig;

    @Mock
    MyUserDetails myUserDetails;

    @Captor
    ArgumentCaptor<User> argumentCaptor;

    Account account1;
    Account account2;
    Account account3;

    User user1;
    User user2;
    User user3;

    User updateUser;

    Role role;

    @BeforeEach
    void setUp(){

        account1 = new Account(1L, "User1", "TestU1", LocalDate.of(1990, 01,01));
        account2 = new Account(2L, "User2", "TestU2", LocalDate.of(1990, 01,01));
        account3 = new Account(3L, "User3", "TestU3", LocalDate.of(1990, 01,01));

        user1 = new User("user1", "user1@test.nl", "W3lk0m!", "blabla", account1, 1L);
        user2 = new User("user2", "user2@test.nl", "W3lk0m!", "blabla", account2, 2L);
        user3 = new User("user3", "user3@test.nl", "W3lk0m!", "blabla", account3, 3L);

        updateUser = new User("user1", "user1@test.nl", "W3lk0m!", "Jajajaja", account1, 1L);

       Collection<Role> userRoles = new ArrayList<>();
       role = new Role();
       role.setRolename("USER");
       userRoles.add(role);
       user2.setRoles(userRoles);
    }

    @Test
    void getUsers() {
        when(userRepos.findAll()).thenReturn(List.of(user1, user2, user3));

        List<User> users = userRepos.findAll();
        List<UserDTO> userDTOS = userService.getUsers();

        assertEquals(users.get(0).getUsername(), userDTOS.get(0).username);
        assertEquals(users.get(0).getEmail(), userDTOS.get(0).email);
        assertEquals(users.get(0).getPassword(), userDTOS.get(0).password);
        assertEquals(users.get(0).getUserBio(), userDTOS.get(0).userBio);
        assertEquals(users.get(0).getAccountID(), userDTOS.get(0).accountID);
    }

    @Test
    void getUser() {
        when(userRepos.findById("user2")).thenReturn(Optional.ofNullable(user2));

        User user = userRepos.findById("user2").get();
        UserDTO userDTO = userService.getUser("user2");

        assertEquals(user.getUsername(), userDTO.username);
        assertEquals(user.getEmail(), userDTO.email);
        assertEquals(user.getPassword(), userDTO.password);
        assertEquals(user.getUserBio(), userDTO.userBio);
        assertEquals(user.getAccountID(), userDTO.accountID);
    }

    @Test
    void updateUser() {
        when(userRepos.findById("user1")).thenReturn(Optional.of(user1));

        UserDTO userDto = UserDTO.fromUser(updateUser);

        when(userRepos.save(UserDTO.toUser(userDto))).thenReturn(user1);

        userService.updateUser("user1" , userDto);

        verify(userRepos, times(1)).save(argumentCaptor.capture());

        User captured = argumentCaptor.getValue();

        assertEquals(userDto.username, captured.getUsername());
        assertEquals(userDto.email, captured.getEmail());
        assertEquals(userDto.password, captured.getPassword());
        assertEquals(userDto.userBio, captured.getUserBio());
        assertEquals(userDto.accountID, captured.getAccountID());
    }

    @Test
    void deleteUser() {
        userService.deleteUser("user1");

        verify(userRepos).deleteById("user1");
    }
}