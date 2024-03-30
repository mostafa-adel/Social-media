package com.example.SocialNetwork.wall;

import com.example.SocialNetwork.post.Post;
import com.example.SocialNetwork.post.PostRepository;
import com.example.SocialNetwork.user.Follower;
import com.example.SocialNetwork.user.User;
import com.example.SocialNetwork.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WallService {

@Autowired UserRepository userRepository;
@Autowired PostRepository postRepository;
    @Transactional
    public List<Post> getUserWall(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Post> posts = new ArrayList<>();
        Set<User> friends = user.getFriends();
        Set<User> followedUsers =user.getFollowing();

        // Add user's own posts
        posts.addAll(postRepository.findByUser(user));

        // Add friends' posts
        for (User friend : friends) {
            posts.addAll(postRepository.findByUser(friend));
        }
        System.out.println("===============");
        System.out.println(user.getFollowing());
        System.out.println("===============");
        // Add followed users' public posts
        for (User followedUser : followedUsers) {
            posts.addAll(postRepository.findByUserAndVisibility(followedUser, Post.Visibility.PUBLIC));
        }


        // Sort posts by timestamp descending
        return posts.stream()
                .sorted((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()))
                .collect(Collectors.toList());
    }
}
