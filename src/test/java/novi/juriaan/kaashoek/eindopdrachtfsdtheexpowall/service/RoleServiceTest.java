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
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RoleServiceTest {
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

    User user1;

    Role role;

    @BeforeEach
    void setUp() {

        account1 = new Account(1L, "User1", "TestU1", LocalDate.of(1990, 01, 01));

        user1 = new User("user1", "user1@test.nl", "W3lk0m!", "blabla", account1, 1L);

        Collection<Role> userRoles = new ArrayList<>();
        role = new Role();
        role.setRolename("USER");
        userRoles.add(role);
        user1.setRoles(userRoles);


    }

    @Test
    void getRole() {
        when(userRepos.findById("user1")).thenReturn(Optional.ofNullable(user1));

        User user = userRepos.findById("user1").get();
        Collection<Role> roles = roleService.GetRole("user1");

        assertEquals(user.getRoles(), roles);

    }
}