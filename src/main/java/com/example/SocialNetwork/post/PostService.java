package com.example.SocialNetwork.post;

import com.example.SocialNetwork.user.User;
import com.example.SocialNetwork.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository; // Assuming you have this

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post createPost(Post post, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        post.setUser(user);
        return postRepository.save(post);
    }

    // Other methods...
    @Transactional
    public boolean likePost(Long postId, Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Post> postOpt = postRepository.findById(postId);

        if (userOpt.isPresent() && postOpt.isPresent()) {
            Post post = postOpt.get();
            User user = userOpt.get();

            if (!post.isLikedByUser(user)) {
                post.addLike(user);
                post.increaseLikes();
                postRepository.save(post);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean unlikePost(Long postId, Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Post> postOpt = postRepository.findById(postId);

        if (userOpt.isPresent() && postOpt.isPresent()) {
            Post post = postOpt.get();
            User user = userOpt.get();

            if (post.isLikedByUser(user)) {
                post.removeLike(user);
                post.decreaseLikes();
                postRepository.save(post);
                return true;
            }
        }
        return false;
    }

}

