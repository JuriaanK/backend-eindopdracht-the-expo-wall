package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
