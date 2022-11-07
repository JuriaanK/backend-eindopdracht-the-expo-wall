package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Account;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDTO {
    public String username;
    public String email;
    public String password;
    public String userBio;
    @JsonIgnore
    public String[] roles;
    @JsonIgnore
    public AccountDTO account;

    public Long accountID;

    public static UserDTO fromUser(User user){
        UserDTO dto = new UserDTO();

        dto.username = user.getUsername();
        dto.email = user.getEmail();
        dto.password = user.getPassword();
        dto.userBio = user.getUserBio();
        if(dto.account != null){
        dto.account = AccountDTO.fromAccount(user.getAccount());}
        dto.accountID = user.getAccountID();

        return dto;
    }

    public static User toUser(UserDTO userDTO){
        User user = new User();

        user.setUsername(userDTO.username);
        user.setEmail(userDTO.email);
        user.setPassword(userDTO.password);
        user.setUserBio(userDTO.userBio);
        if (user.getAccount() != null){
        user.setAccount(AccountDTO.toAccount(userDTO.account));}
        user.setAccountID(userDTO.accountID);

        return user;
    }
}
