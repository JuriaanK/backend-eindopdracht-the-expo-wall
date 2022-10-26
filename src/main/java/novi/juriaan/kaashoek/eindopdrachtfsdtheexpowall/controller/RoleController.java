package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.controller;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.RoleDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Role;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.RoleRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    private final RoleRepository repos;

    public RoleController(RoleRepository repos) {
        this.repos = repos;
    }
    @PostMapping("/roles")
    public String createRole(@RequestBody RoleDTO role) {
        Role newRole = new Role();
        newRole.setRolename(role.rolename);
        repos.save(newRole);

        return "Done";
    }
}
