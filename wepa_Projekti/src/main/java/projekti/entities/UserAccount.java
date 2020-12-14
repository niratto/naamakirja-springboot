package projekti.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount extends AbstractPersistable<Long> {

    @Column(unique = true)
    @Size(min = 4, max = 50)
    private String username;

    @Size(min = 4, max = 50)
    private String visibleName;

    @NotEmpty
    private String password;
    
    @OneToMany
    private List<Post> posts = new ArrayList<>();
}
