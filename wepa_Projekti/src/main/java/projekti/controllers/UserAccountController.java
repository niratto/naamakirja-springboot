package projekti.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import projekti.repositories.*;
import projekti.entities.*;

@Controller
public class UserAccountController {

    @Autowired
    UserAccountRepository userAccountRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @ModelAttribute
    private UserAccount getUserAccount() {
        return new UserAccount();
    }

    @GetMapping("/mainpage")
    public String list(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserAccount ua = userAccountRepository.findByUsername(auth.getName());
        
        model.addAttribute("name", userAccountRepository.findByUsername(auth.getName()));
        model.addAttribute("posts", userAccountRepository.getOne(ua.getId()).getPosts());
        return "mainpage";
    }

    @PostMapping("/mainpage")
    public String create(@Valid @ModelAttribute UserAccount userAccount, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        UserAccount ua = new UserAccount(userAccount.getUsername(), userAccount.getVisibleName(), passwordEncoder.encode(userAccount.getPassword()), new ArrayList<>());
        userAccountRepository.save(ua);

        return "redirect:/mainpage";
    }

    @GetMapping("/user/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserAccount ua = new UserAccount();
        model.addAttribute("user", ua);
        return "register";
    }
}
