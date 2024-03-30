package com.example.SocialNetwork.user;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired UserRepository userRepository;
    @Autowired FollowerRepository followerRepository;

    public User createUser(User user) {
        if (userRepository.findByFullName(user.getFullName()).isPresent()) {
            // User with the same full name already exists
            return null;
        }
        return userRepository.save(user);
    }


    public boolean addFriend(Long userId, Long friendId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<User> friendOpt = userRepository.findById(friendId);

        if (!userOpt.isPresent() || !friendOpt.isPresent()) {
            return false; // User or friend not found
        }

        User user = userOpt.get();
        User friend = friendOpt.get();

        user.addFriend(friend);
        userRepository.save(user);

        return true;
    }

    @Transactional
    public boolean addFollower(Long userId, Long followerId) {
        if (userId.equals(followerId)) {
            return false; // A user cannot follow themselves
        }

        Optional<User> userOpt = userRepository.findById(userId);
        Optional<User> followerUserOpt = userRepository.findById(followerId);

        if (!userOpt.isPresent() || !followerUserOpt.isPresent()) {
            return false; // User or follower not found
        }

        User user = userOpt.get();
        User followerUser = followerUserOpt.get();

        // Create a new follower entity
        Follower follower = new Follower();
        follower.setFollower(followerUser);
        follower.setFollowed(user);

        // Add the follower entity to the user's followers set
        user.getFollowers().add(follower);

        // Update the following set of the follower user
        followerUser.followUser(user);

        // Persist changes to the database
        followerRepository.save(follower);
        userRepository.save(followerUser); // Only save the follower user

        return true;
    }

    public Optional<User> findUser(Long userId){
        return userRepository.findById(userId);
    }

}
