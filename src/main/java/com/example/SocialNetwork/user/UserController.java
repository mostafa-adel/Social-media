package com.example.SocialNetwork.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired UserService userService;


    // POST /users - Create a new user
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);

        if (createdUser == null) {
            // User creation failed (e.g., user already exists)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The user request was invalid");
        }

        // Return the created user with status 201 (Created)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // POST /users/{userId}/friends/{friendId} - Add a friend
    @PostMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<?> addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        boolean success = userService.addFriend(userId, friendId);

        if (!success) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The request was invalid");
        }

        return ResponseEntity.ok("Successfully added the friendship relation");
    }

    // POST /users/{userId}/followers/{followerId
    @PostMapping("/{userId}/followers/{followerId}")
    public ResponseEntity<?> addFollower(@PathVariable Long userId, @PathVariable Long followerId) {
        boolean success = userService.addFollower(userId, followerId);

        if (!success) {
            return ResponseEntity.badRequest().body("The request was invalid");
        }

        return ResponseEntity.ok("Successfully added the follower relation");
    }

}

