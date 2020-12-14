package projekti.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projekti.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
