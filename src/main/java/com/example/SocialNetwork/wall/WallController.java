package com.example.SocialNetwork.wall;

import com.example.SocialNetwork.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/walls")
public class WallController {

    private final WallService wallService;

    @Autowired
    public WallController(WallService wallService) {
        this.wallService = wallService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Post>> getUserWall(@PathVariable Long userId) {
        try {
            List<Post> posts = wallService.getUserWall(userId);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
