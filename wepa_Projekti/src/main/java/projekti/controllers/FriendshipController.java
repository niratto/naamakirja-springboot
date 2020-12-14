package projekti.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import projekti.entities.Friendship;
import projekti.entities.UserAccount;
import projekti.repositories.FriendshipRepository;
import projekti.repositories.UserAccountRepository;

@Controller
public class FriendshipController {

    @Autowired
    FriendshipRepository fsRepository;

    @Autowired
    UserAccountRepository uaRepository;

    @GetMapping("/friendspage")
    public String list(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserAccount ua = uaRepository.findByUsername(auth.getName());

        List<Friendship> receivedAndAccepted = fsRepository.findAllByUserIdAndFriends(ua.getId(), true);
        List<Friendship> sentAndAccepted = fsRepository.findAllByFriendRequestIdAndFriends(ua.getId(), true);

        List<Friendship> allFriendships = Stream.concat(receivedAndAccepted.stream(), sentAndAccepted.stream())
                .collect(Collectors.toList());

        List<UserAccount> friendsList = new ArrayList<>();
        receivedAndAccepted.forEach((f) -> {
            System.out.println("DEBUG: " + f.getFriendRequestId() + " ja " + uaRepository.getOne(f.getUserId()));
            friendsList.add(uaRepository.getOne(f.getFriendRequestId()));
        });

        sentAndAccepted.forEach((f) -> {
            System.out.println("DEBUG: " + f.getUserId() + " ja " + uaRepository.getOne(f.getUserId()));
            friendsList.add(uaRepository.getOne(f.getUserId()));
        });

        System.out.println("FRIENDSLIST: " + friendsList);

        model.addAttribute("name", uaRepository.findByUsername(auth.getName()));
        model.addAttribute("friends", friendsList);

        return "friendspage";

        /*
               List<Friendship> receivedAndAccepted =  fsRepository.findAllByUserIdAndFriends(id,true);
       List<Friendship> sentAndAccepted =  fsRepository.findAllByFriendRequestIdAndFriends(id,true);
       
       List<Friendship> allFriendships = Stream.concat(receivedAndAccepted.stream(), sentAndAccepted.stream())
                             .collect(Collectors.toList());
       
       return allFriendships;
         */
    }

    @GetMapping("/friendrequests/sent")
    public String geSentFriendrequests(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserAccount ua = uaRepository.findByUsername(auth.getName());

        List<Friendship> sentAndAccepted = fsRepository.findAllByFriendRequestIdAndFriends(ua.getId(), false);

        List<UserAccount> sentRequestsList = new ArrayList<>();

        sentAndAccepted.forEach((f) -> {
            sentRequestsList.add(uaRepository.getOne(f.getUserId()));
        });

        model.addAttribute("name", uaRepository.findByUsername(auth.getName()));
        model.addAttribute("requests", sentRequestsList);

        return "sentRequests";
    }

    @GetMapping("/friendrequests/received")
    public String getReceivedFriendrequests(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserAccount ua = uaRepository.findByUsername(auth.getName());

        List<Friendship> receivedAndAccepted = fsRepository.findAllByUserIdAndFriends(ua.getId(), false);

        List<UserAccount> receivedRequestsList = new ArrayList<>();

        receivedAndAccepted.forEach((f) -> {
            receivedRequestsList.add(uaRepository.getOne(f.getFriendRequestId()));
        });

        model.addAttribute("name", uaRepository.findByUsername(auth.getName()));
        model.addAttribute("requests", receivedRequestsList);

        return "receivedRequests";
    }

}
