package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.service;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.UserDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Role;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.User;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.RoleRepository;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepos;
    private final RoleRepository roleRepos;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepos, RoleRepository roleRepos, PasswordEncoder encoder) {
        this.userRepos = userRepos;
        this.roleRepos = roleRepos;
        this.encoder = encoder;
    }

    public List<UserDTO> getUsers(){
        List<UserDTO> collection = new ArrayList<>();
        List<User> list = userRepos.findAll();
        for (User user : list){
            collection.add(UserDTO.fromUser(user));
        }
        return collection;
    }

    public UserDTO getUser(String username){
        UserDTO dto = new UserDTO();
        Optional<User> user = userRepos.findById(username);
        String userRole = user.get().getRoles().toString();
        if (user.isPresent()){
            dto = UserDTO.fromUser(user.get());
        }else{
            throw new UsernameNotFoundException(username);
        }

        return dto;
    }

    public String createUser(UserDTO userDTO){
        User newUser = new User();
        newUser.setUsername(userDTO.username);
        newUser.setPassword(encoder.encode(userDTO.password));
        newUser.setEmail(userDTO.email);
        newUser.setUserBio("Don't forget your Bio");

        List<Role> userRoles = new ArrayList<>();
        Optional<Role> or = roleRepos.findById("USER");
        userRoles.add(or.get());
        newUser.setRoles(userRoles);

        userRepos.save(newUser);

        return newUser.getUsername();
    }

    public String createAdmin(UserDTO userDTO){
        User newAdmin = new User();
        newAdmin.setUsername(userDTO.username);
        newAdmin.setPassword(encoder.encode(userDTO.password));
        newAdmin.setEmail(userDTO.email);
        newAdmin.setUserBio("Don't forget your Bio");

        List<Role> userRoles = new ArrayList<>();
        Optional<Role> or = roleRepos.findById("ADMIN");
        userRoles.add(or.get());
        newAdmin.setRoles(userRoles);

        userRepos.save(newAdmin);
        return newAdmin.getUsername();
    }

    public UserDTO updateUser(String username, UserDTO newUser){
        if (!userRepos.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepos.findById(username).get();
        User changedUser = UserDTO.toUser(newUser);

        if (    !user.getEmail().equals(changedUser.getEmail())
                && changedUser.getEmail() != null
                && changedUser.getEmail() != ""){
            user.setEmail(newUser.email);
        }
        if(     !user.getPassword().equals(changedUser.getPassword())
                && changedUser.getPassword() != null
                && changedUser.getPassword() != ""){
            user.setPassword(encoder.encode(newUser.password));
        }
        if (    !user.getUserBio().equals(changedUser.getUserBio())
                && changedUser.getUserBio() != null
                && changedUser.getUserBio() != ""){
            user.setUserBio(newUser.userBio);
        }

        userRepos.save(user);
        return UserDTO.fromUser(user);
    }

    public void updatePassword(String username, String password){
        if (!userRepos.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepos.findById(username).get();
        user.setPassword(encoder.encode(password));
    }

    public void deleteUser(String username){
        userRepos.deleteById(username);
    }
}
