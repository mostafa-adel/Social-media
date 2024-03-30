package com.example.SocialNetwork;

import com.example.SocialNetwork.user.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FollowerRepository followerRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addFriendSuccess() {
        Long userId = 1L;
        Long friendId = 2L;
        User user = new User("User1");
        User friend = new User("User2");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findById(friendId)).thenReturn(Optional.of(friend));

        boolean result = userService.addFriend(userId, friendId);

        assertTrue(result);
        assertTrue(user.getFriends().contains(friend));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void addFriendFailureUserNotFound() {
        Long userId = 1L;
        Long friendId = 2L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        boolean result = userService.addFriend(userId, friendId);

        assertFalse(result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void addFollowerSuccess() {
        Long userId = 1L;
        Long followerId = 2L;
        User user = new User("User1");
        User followerUser = new User("User2");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findById(followerId)).thenReturn(Optional.of(followerUser));

        boolean result = userService.addFollower(userId, followerId);

        assertTrue(result);
        assertTrue(user.getFollowers().stream().anyMatch(f -> f.getFollower().equals(followerUser)));
        assertTrue(followerUser.getFollowing().contains(user));
        verify(followerRepository, times(1)).save(any(Follower.class));
        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).save(followerUser);
    }

    @Test
    void addFollowerFailureUserNotFound() {
        Long userId = 1L;
        Long followerId = 2L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        boolean result = userService.addFollower(userId, followerId);

        assertFalse(result);
        verify(followerRepository, never()).save(any(Follower.class));
        verify(userRepository, never()).save(any(User.class));
    }


}
