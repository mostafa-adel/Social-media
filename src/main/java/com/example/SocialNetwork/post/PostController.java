package com.example.SocialNetwork.post;

import com.example.SocialNetwork.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/posts")
public class PostController {

@Autowired PostService postService;
@Autowired UserService userService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostDto postDto) { // Use a DTO to include userId
        try {
            Post post = convertToEntity(postDto); // Method to convert DTO to entity
            Post createdPost = postService.createPost(post, postDto.getUserId());
            return ResponseEntity.ok(createdPost);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable Long postId, @RequestParam Long userId) {
        boolean success = postService.likePost(postId, userId);
        if (success) {
            return ResponseEntity.ok().body("Post liked successfully");
        } else {
            return ResponseEntity.badRequest().body("Unable to like post");
        }
    }

    @PostMapping("/{postId}/unlike")
    public ResponseEntity<?> unlikePost(@PathVariable Long postId, @RequestParam Long userId) {
        boolean success = postService.unlikePost(postId, userId);
        if (success) {
            return ResponseEntity.ok().body("Post unliked successfully");
        } else {
            return ResponseEntity.badRequest().body("Unable to unlike post");
        }
    }


    private Post convertToEntity(PostDto postDto) {
        Post post = new Post();
        post.setUser(userService.findUser(postDto.getUserId()).get());
        post.setText(postDto.getText());
        post.setVisibility(postDto.getVisibility());
        post.setCreatedAt(LocalDateTime.now()); // Assuming that creation time at this point
        post.setLikes(0); // Initialize likes to 0
        return post;
    }
}

