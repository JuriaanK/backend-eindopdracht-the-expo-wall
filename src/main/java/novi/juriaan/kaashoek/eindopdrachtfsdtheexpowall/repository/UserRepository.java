package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
}
