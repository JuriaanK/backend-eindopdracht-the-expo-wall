package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.controller;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.UserDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.exceptions.BadRequestException;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Role;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.User;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.RoleRepository;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.UserRepository;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.service.UserService;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService, UserRepository userRepos, RoleRepository roleRepos, PasswordEncoder encoder) {
        this.userService = userService;
    }

    @GetMapping(value ="")
    public ResponseEntity<List<UserDTO>>getUsers(){
        List<UserDTO> userDTOs = userService.getUsers();

        return ResponseEntity.ok().body(userDTOs);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("username") String username){
        UserDTO askedUser = userService.getUser(username);

        return ResponseEntity.ok().body(askedUser);
    }

    @PostMapping(value = "")
    public String createUser(@RequestBody UserDTO userDTO){
        String newUser = userService.createUser(userDTO);

        return "Created " + newUser;
    }

    @PostMapping(value = "/admin")
    public String createAdmin(@RequestBody UserDTO userDTO){
        String newAdmin = userService.createAdmin(userDTO);
        return "Created " + newAdmin;
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("username") String username, @RequestBody UserDTO userDTO){
        userService.updateUser(username, userDTO);

        return ResponseEntity.ok().body(userDTO);
    }

    @PutMapping(value = "/passwordchange/{username}")
    public ResponseEntity<String> updatePassword(@PathVariable("username") String username, @RequestBody String password){
        userService.updatePassword(username, password);

        return ResponseEntity.ok().body(password);
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username){
        userService.deleteUser(username);

        return ResponseEntity.noContent().build();
    }
}
