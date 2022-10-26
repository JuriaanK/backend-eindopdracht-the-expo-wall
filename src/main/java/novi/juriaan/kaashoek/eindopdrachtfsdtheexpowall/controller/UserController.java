package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.controller;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.UserDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.exceptions.BadRequestException;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Role;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.User;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.RoleRepository;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.UserRepository;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepos;
    private final RoleRepository roleRepos;
    private final PasswordEncoder encoder;

    public UserController(UserService userService, UserRepository userRepos, RoleRepository roleRepos, PasswordEncoder encoder) {
        this.userService = userService;
        this.userRepos = userRepos;
        this.roleRepos = roleRepos;
        this.encoder = encoder;
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
        User newUser = new User();
        newUser.setUsername(userDTO.username);
        newUser.setPassword(encoder.encode(userDTO.password));

        List<Role> userRoles = new ArrayList<>();
        for (String rolename : userDTO.roles) {
            Optional<Role> or = roleRepos.findById(rolename);

            userRoles.add(or.get());
        }
        newUser.setRoles(userRoles);

        userRepos.save(newUser);

        return "Done";
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("username") String username, @RequestBody UserDTO userDTO){
        userService.updateUser(username, userDTO);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username){
        userService.deleteUser(username);

        return ResponseEntity.noContent().build();
    }


}
