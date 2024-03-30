package com.example.SocialNetwork.post;

import com.example.SocialNetwork.user.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Setter
@Getter
public class PostDto {
    private Long userId;
    private String text;
    private Post.Visibility visibility;




}
