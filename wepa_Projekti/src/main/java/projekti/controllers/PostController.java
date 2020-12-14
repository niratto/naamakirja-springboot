package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.services.PostService;

@Controller
public class PostController {
    @Autowired
    PostService postService;
    
    @PostMapping(value = "/post/{userId]}")
    public String addActorToMovie(@PathVariable long userId, @RequestParam Long postId) {
        postService.addPostToUser(userId, postId);
        return "redirect:/mainpage";
    }

}
