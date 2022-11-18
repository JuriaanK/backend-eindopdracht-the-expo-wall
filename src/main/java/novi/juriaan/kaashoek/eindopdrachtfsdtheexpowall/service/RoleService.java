package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.service;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Role;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.User;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.RoleRepository;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.UserRepository;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.Optional;
@Service
public class RoleService {

    private final RoleRepository roleRepos;
    private final UserRepository userRepos;

    public RoleService(RoleRepository roleRepos, UserRepository userRepos) {
        this.roleRepos = roleRepos;
        this.userRepos = userRepos;
    }

    public Collection<Role> GetRole(String username){

        Optional<User> user = userRepos.findById(username);
        Collection<Role> userRole = user.get().getRoles();

        return userRole;
    }
}
