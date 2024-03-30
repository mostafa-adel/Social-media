package com.example.SocialNetwork;

import com.example.SocialNetwork.post.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

import com.example.SocialNetwork.post.Post;
import com.example.SocialNetwork.user.User;
import com.example.SocialNetwork.user.UserRepository;
import com.example.SocialNetwork.post.PostRepository;

import java.util.Optional;

class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    private Post post;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        post = new Post();
        post.setPostId(1L);
        user = new User();
        user.setUserId(1L);
    }

    @Test
    void testLikePost() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        assertTrue(postService.likePost(1L, 1L));
        assertTrue(post.getLikedByUsers().contains(user));
        assertEquals(1, post.getLikes());
    }

    @Test
    void testUnlikePost() {
        post.addLike(user);
        post.increaseLikes();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        assertTrue(postService.unlikePost(1L, 1L));
        assertFalse(post.getLikedByUsers().contains(user));
        assertEquals(0, post.getLikes());
    }
}
