package com.example.SocialNetwork.post;


import com.example.SocialNetwork.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    Collection<? extends Post> findByUser(User user);

    List<Post> findByUserAndVisibility(User user, Post.Visibility visibility);
}
