package com.example.SocialNetwork.user;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
//import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String fullName;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "User_Friends",
            joinColumns = { @JoinColumn(name = "userId") },
            inverseJoinColumns = { @JoinColumn(name = "friendId") }
    )
    private Set<User> friends = new HashSet<>();

    @OneToMany(mappedBy = "follower")
    private Set<Follower> followers = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "user_following",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_user_id")
    )
    private Set<User> following = new HashSet<>();

    public User(String fullName)
    {
        this.fullName=fullName;
    }

    // Helper methods for adding/removing friends and followers
    public void addFriend(User user) {
        friends.add(user);
        user.friends.add(this);
    }

    public void removeFriend(User user) {
        friends.remove(user);
        user.friends.remove(this);
    }

    public void addFollower(Follower follower) {
        followers.add(follower);
    }

    public void removeFollower(Follower follower) {
        followers.remove(follower);
    }


    public void followUser(User user) {
        following.add(user);
    }

    public void unfollowUser(User user) {
        following.remove(user);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", friends=" + friends.stream().map(User::getUserId).collect(Collectors.toSet()) +
                ", followers=" + followers.stream().map(Follower::getId).collect(Collectors.toSet()) +
                ", following=" + following.stream().map(User::getUserId).collect(Collectors.toSet()) +
                '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }
}

