package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model;


import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    private String rolename;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    public Role(String rolename) {
    }

    public Role() {

    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}

