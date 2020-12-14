package projekti.controllers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import projekti.entities.Post;
import projekti.entities.UserAccount;
import projekti.repositories.PostRepository;
import projekti.repositories.UserAccountRepository;
import projekti.services.PostService;

@RestController
public class PostRestController {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserAccountRepository uaRepository;

    @GetMapping(value = "/rest/posts")
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @GetMapping(value = "/rest/posts/{id}")
    public List<Post> getPosts(@PathVariable long id) {
        return uaRepository.getOne(id).getPosts();
    }

    //@RequestMapping(value = "/rest/post", method = RequestMethod.POST)
    //public void addPost(@RequestBody Post body) {
    //    String postMsg = body.getPostContent();
    //    postService.add(postMsg);
    //}
    //@PostMapping(value = "/rest/post/{userId}")
    @RequestMapping(value = "/rest/post/{userId}", method = RequestMethod.POST)
    public String addPostForUser(@RequestBody Post body, @PathVariable long userId) {
        String returnMessage = "";
        String postMsg = body.getPostContent();
        long postId = postService.add(postMsg);
        boolean ret = postService.addPostToUser(userId, postId);

        UserAccount ua = uaRepository.getOne(userId);
        
        if (ret) {
            returnMessage += "{\n\t\"msg\": \"Post for " + ua.getUsername() + " was created (" + new Date() + ")\",\n";
            returnMessage += "\t\"postMsg\": \"" + postMsg + "\"\n}";
            returnMessage += "\t\"httpStatus\": \"" + HttpStatus.CREATED_201 + "\"\n}";
        } else {
            returnMessage += "{\n\t\"msg\": \"Post for " + ua.getUsername() + " was NOT created (" + new Date() + ")\",\n";
            returnMessage += "\t\"postMsg\": \"" + postMsg + "\"\n}";
            returnMessage += "\t\"httpStatus\": \"" + HttpStatus.BAD_REQUEST_400 + "\"\n}";
        }
        
        return returnMessage;
    }
}
