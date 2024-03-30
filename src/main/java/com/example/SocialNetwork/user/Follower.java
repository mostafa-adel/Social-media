package com.example.SocialNetwork.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "followers")
public class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    @ManyToOne
    @JoinColumn(name = "followed_id", nullable = false)
    private User followed;


    @Override
    public String toString() {
        return "Follower{" +
                "id=" + id +
                ", followerId=" + (follower != null ? follower.getUserId() : null) +
                ", followedId=" + (followed != null ? followed.getUserId() : null) +
                '}';
    }
}