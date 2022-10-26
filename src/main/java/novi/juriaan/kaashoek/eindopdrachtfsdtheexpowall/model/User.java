package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model;



import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;
    @Column
    private String email;
    @Column
    private String Password;
    @Column
    private String userBio;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }


    public User(String username, String email, String password, String userBio) {
        this.username = username;
        this.email = email;
        Password = password;
        this.userBio = userBio;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

}
