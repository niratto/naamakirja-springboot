package projekti.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import projekti.entities.UserAccount;
import projekti.repositories.*;

@RestController
public class UserAccountRestController {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/rest/accounts")
    public List<UserAccount> getAccounts() {
        return userAccountRepository.findAll();
    }

    @PostMapping("/rest/registration")
    public String registerNewUser(@RequestBody UserAccount userAccount) {
        String returnMessage = "";
        UserAccount checkUA = userAccountRepository.findByUsername(userAccount.getUsername());

        if (checkUA == null) {
            UserAccount ua = new UserAccount(userAccount.getUsername(), userAccount.getVisibleName(), passwordEncoder.encode(userAccount.getPassword()),new ArrayList<>());
            ua = userAccountRepository.save(ua);
            if (ua.getId() != null) {
                returnMessage += "{\n\t\"msg\": \"User " + userAccount.getUsername() + " was created at " + new Date() + "\",\n";
                returnMessage += "\t\"httpStatus\": \"" + HttpStatus.CREATED_201 + "\"\n}";
                return returnMessage;
            } else {
                returnMessage += "{\n\t\"msg\": \"Creation for user " + userAccount.getUsername() + " failed! (" + new Date() + ")\",\n";
                returnMessage += "\t\"httpStatus\": \"" + HttpStatus.INTERNAL_SERVER_ERROR_500 + "\"\n}";
                return returnMessage;
            }
        } else {
            returnMessage += "{\n\t\"msg\": \"User " + userAccount.getUsername() + " already exists! (" + new Date() + ")\",\n";
            returnMessage += "\t\"httpStatus\": \"" + HttpStatus.BAD_REQUEST_400 + "\"\n}";
            return returnMessage;
        }
    }
}
