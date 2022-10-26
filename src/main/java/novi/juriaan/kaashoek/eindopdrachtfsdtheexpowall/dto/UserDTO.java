package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Authority;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.User;

import java.util.Set;

public class UserDTO {

    public String username;
    public String email;
    public String password;
    public String userBio;
    public String[] roles;

    public static UserDTO fromUser(User user){
        UserDTO dto = new UserDTO();

        dto.username = user.getUsername();
        dto.email = user.getEmail();
        dto.password = user.getPassword();
        dto.userBio = user.getUserBio();

        return dto;
    }

    public static User toUser(UserDTO userDTO){
        User user = new User();

        user.setUsername(userDTO.username);
        user.setEmail(userDTO.email);
        user.setPassword(userDTO.password);
        user.setUserBio(userDTO.userBio);

        return user;
    }
}
