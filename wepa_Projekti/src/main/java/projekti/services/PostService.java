package projekti.services;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projekti.entities.Post;
import projekti.repositories.PostRepository;
import projekti.repositories.UserAccountRepository;

@Service
public class PostService {

    @Autowired
    UserAccountRepository uaRepository;

    @Autowired
    PostRepository postRepository;

    @Transactional
    public long add(String postMsg) {
        Post post = new Post();
        post.setPostContent(postMsg);
        post.setTimestamp(new Date());

        System.out.println("KEIJOOO: " + post);
        
        post = postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public boolean addPostToUser(Long userId, Long postId) {
        return uaRepository.getOne(userId).getPosts().add(postRepository.getOne(postId));
    }
}
