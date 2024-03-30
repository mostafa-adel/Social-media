package com.example.SocialNetwork.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
    // Additional methods, if needed
}
