/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.entities.Friendship;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    Friendship findByUserId(long userId);
    Friendship findByFriendRequestId(long friendRequestId);
    Friendship findByUserIdAndFriendRequestId(long userId, long friendRequestId);
    List <Friendship> findAllByUserIdAndFriends(long userId, boolean friends);
    List <Friendship> findAllByFriendRequestIdAndFriends(long friendRequestId, boolean friends);
        
}
