package com.example.SocialNetwork.post;

import com.example.SocialNetwork.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

//import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Visibility visibility;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private Integer likes = 0;

    @ManyToMany
    @JoinTable(
            name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likedByUsers = new HashSet<>();

    // Methods to increase or decrease likes
    public void increaseLikes() {
        likes++;
    }

    public void decreaseLikes() {
        if (likes > 0) {
            likes--;
        }
    }

    // Method to add a like  ...........
    public void addLike(User user) {
        this.likedByUsers.add(user);
    }

    // Method to remove a like
    public void removeLike(User user) {
        this.likedByUsers.remove(user);
    }

    // Method to check if a user has liked this post
    public boolean isLikedByUser(User user) {
        return this.likedByUsers.contains(user);
    }

    // Visibility enum
    public enum Visibility {
        PUBLIC, PRIVATE
    }
}
