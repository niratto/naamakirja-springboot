package projekti.repositories;

import java.util.List;
import projekti.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByUsername(String username);
    List<UserAccount> findAllById(long id);
}
