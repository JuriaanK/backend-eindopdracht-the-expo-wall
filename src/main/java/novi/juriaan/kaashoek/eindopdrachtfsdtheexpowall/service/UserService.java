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


        List<Role> userRoles = new ArrayList<>();
        Optional<Role> or = roleRepos.findById("ADMIN");
        userRoles.add(or.get());
        newAdmin.setRoles(userRoles);

        userRepos.save(newAdmin);
        return newAdmin.getUsername();
    }


    public void updateUser(String username, UserDTO newUser){
        if (!userRepos.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepos.findById(username).get();
        User changedUser = UserDTO.toUser(newUser);

        if (!user.getUsername().equals(changedUser.getUsername())){
            user.setUsername(changedUser.getUsername());
        }
        if (!user.getEmail().equals(changedUser.getEmail())){
            user.setEmail(changedUser.getEmail());
        }
        if(!user.getPassword().equals(changedUser.getPassword())){
            user.setPassword(changedUser.getPassword());
        }
        if (!user.getUserBio().equals(changedUser.getUserBio())){
            user.setUserBio(changedUser.getUserBio());
        }
    }

    public void deleteUser(String username){
        userRepos.deleteById(username);
    }








}
