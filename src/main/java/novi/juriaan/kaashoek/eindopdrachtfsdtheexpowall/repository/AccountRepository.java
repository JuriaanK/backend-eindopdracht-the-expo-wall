package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository;


import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
