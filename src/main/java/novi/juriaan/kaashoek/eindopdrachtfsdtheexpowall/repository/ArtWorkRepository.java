package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Account;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.ArtWork;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;


@Transactional
public interface ArtWorkRepository extends JpaRepository<ArtWork, Long> {
    List<ArtWork> findByAccount(Account account);
}
