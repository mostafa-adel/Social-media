package com.example.SocialNetwork;

import com.example.SocialNetwork.post.PostController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.SocialNetwork.post.PostDto;
import com.example.SocialNetwork.post.PostService;
import com.example.SocialNetwork.user.UserService;

class PostControllerTest {

    @Mock
    private PostService postService;

    @Mock
    private UserService userService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLikePost() {
        when(postService.likePost(anyLong(), anyLong())).thenReturn(true);
        var response = postController.likePost(1L, 1L);

        assertEquals(OK, response.getStatusCode());
        assertEquals("Post liked successfully", response.getBody());
    }

    @Test
    void testUnlikePost() {
        when(postService.unlikePost(anyLong(), anyLong())).thenReturn(true);
        var response = postController.unlikePost(1L, 1L);

        assertEquals(OK, response.getStatusCode());
        assertEquals("Post unliked successfully", response.getBody());
    }

}
