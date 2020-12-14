package projekti.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import projekti.entities.Friendship;
import projekti.entities.UserAccount;
import projekti.repositories.FriendshipRepository;
import projekti.repositories.UserAccountRepository;

@RestController
public class FriendshipRestController {

    @Autowired
    FriendshipRepository fsRepository;

    @Autowired
    UserAccountRepository uaRepository;

    @GetMapping("/rest/friendships")
    public List<Friendship> getFriendships() {
        return fsRepository.findAll();
    }

    @GetMapping("/rest/friendships/accepted/{id}")
    public List<UserAccount> geReceivedAndAccepted(@PathVariable long id) {
       List<Friendship> receivedAndAccepted =  fsRepository.findAllByUserIdAndFriends(id,true);
       List<Friendship> sentAndAccepted =  fsRepository.findAllByFriendRequestIdAndFriends(id,true);
       
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
       return friendsList;
    }
    
    @GetMapping("/rest/friendships/requests/sent/{id}")
    public List<UserAccount> geSentFriendrequests(@PathVariable long id) {
       List<Friendship> sentAndAccepted =  fsRepository.findAllByFriendRequestIdAndFriends(id,false);
              
       List<UserAccount> friendsList = new ArrayList<>();

       sentAndAccepted.forEach((f) -> {
           friendsList.add(uaRepository.getOne(f.getUserId()));
        });

       return friendsList;
    }

    @GetMapping("/rest/friendships/requests/received/{id}")
    public List<UserAccount> getReceivedFriendrequests(@PathVariable long id) {
       List<Friendship> receivedAndAccepted =  fsRepository.findAllByUserIdAndFriends(id,false);
              
       List<UserAccount> friendsList = new ArrayList<>();

       receivedAndAccepted.forEach((f) -> {
           friendsList.add(uaRepository.getOne(f.getFriendRequestId()));
        });

       return friendsList;
    }

    @PostMapping("/rest/friendRequest/send/from/{requesterId}/to/{userId}")
    public String sendFriendRequest(@PathVariable long requesterId, @PathVariable long userId) {
        String returnMessage = "";
        Friendship friendRequestTo;
        Friendship friendRequestFrom;
        UserAccount user;
        UserAccount requester;
        try {
            user = uaRepository.getOne(userId);
            requester = uaRepository.getOne(requesterId);

            System.out.println("*** User " + user.getUsername() + " and requester " + requester.getUsername() + " fetched from the userAccountRepository... ***");

            friendRequestTo = fsRepository.findByUserIdAndFriendRequestId(userId, requesterId);
            friendRequestFrom = fsRepository.findByUserIdAndFriendRequestId(requesterId, userId);

            if (friendRequestTo == null && friendRequestFrom == null) {
                friendRequestTo = new Friendship();
                friendRequestTo.setUserId(user.getId());
                friendRequestTo.setFriendRequestId(requester.getId());
                friendRequestTo.setFriends(false);
                friendRequestTo.setTimestamp(new Date());
                fsRepository.save(friendRequestTo);

                returnMessage += "{\n\t\"msg\": \"Friend request from " + requester.getUsername() + " to " + user.getUsername() + " sent! (" + new Date() + ")\",\n";
                returnMessage += "\t\"httpStatus\": \"" + HttpStatus.OK_200 + "\"\n}";
            } else {
                returnMessage += "{\n\t\"msg\": \"Friend request among " + requester.getUsername() + " and " + user.getUsername() + " already exists! (" + new Date() + ")\",\n";
                returnMessage += "\t\"httpStatus\": \"" + HttpStatus.BAD_REQUEST_400 + "\"\n}";

            }

        } catch (Exception e) {
            returnMessage += "{\n\t\"msg\": \"Given user or requester doesn't exist in database! (" + new Date() + ")\",\n";
            returnMessage += "\t\"httpStatus\": \"" + HttpStatus.BAD_REQUEST_400 + "\"\n}";
        }

        return returnMessage;
    }

    @PostMapping("/rest/friendRequest/accept/from/{requesterId}/to/{userId}")
    public String acceptFriendRequest(@PathVariable long requesterId, @PathVariable long userId) {
        String returnMessage = "";
        Friendship friendRequestFrom;
        UserAccount user;
        UserAccount requester;
        try {
            user = uaRepository.getOne(userId);
            requester = uaRepository.getOne(requesterId);

            System.out.println("*** User " + user.getUsername() + " and requester " + requester.getUsername() + " fetched from the userAccountRepository... ***");

            friendRequestFrom = fsRepository.findByUserIdAndFriendRequestId(userId, requesterId);

            System.out.println("friendrequest is " + friendRequestFrom + " - " + requesterId + "," + userId + "..." + requester.getId() + "," + user.getId());

            if (!friendRequestFrom.isFriends()) {
                friendRequestFrom.setFriends(true);
                friendRequestFrom.setTimestamp(new Date());
                fsRepository.save(friendRequestFrom);

                returnMessage += "{\n\t\"msg\": \"Friend request from " + requester.getUsername() + " to " + user.getUsername() + " was accepted! (" + new Date() + ")\",\n";
                returnMessage += "\t\"httpStatus\": \"" + HttpStatus.OK_200 + "\"\n}";
            } else {
                returnMessage += "{\n\t\"msg\": \"Friend request among " + requester.getUsername() + " and " + user.getUsername() + " doesn't exists! (" + new Date() + ")\",\n";
                returnMessage += "\t\"httpStatus\": \"" + HttpStatus.BAD_REQUEST_400 + "\"\n}";

            }

        } catch (Exception e) {
            returnMessage += "{\n\t\"msg\": \"Given user or requester doesn't exist in database! (" + new Date() + ")\",\n";
            returnMessage += "\t\"httpStatus\": \"" + HttpStatus.BAD_REQUEST_400 + "\"\n}";
        }

        return returnMessage;
    }

}
