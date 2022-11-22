package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "username_id")
    private String username;
    @Column
    private String email;
    @Column
    private String Password;
    @Column
    private String userBio;

    @OneToOne
    private Account account;
    @Column
    private Long accountID;




    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;

    public User(String username, String email, String password, String userBio, Account account, Long accountID) {
        this.username = username;
        this.email = email;
        Password = password;
        this.userBio = userBio;
        this.account = account;
        this.accountID = accountID;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public User(String username, String email, String password, String userBio, Account account, Long accountID, Collection<Role> roles) {
        this.username = username;
        this.email = email;
        Password = password;
        this.userBio = userBio;
        this.account = account;
        this.accountID = accountID;
        this.roles = roles;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Long getAccountID() {
        return accountID;
    }

    public void setAccountID(Long accountID) {
        this.accountID = accountID;
    }


}
