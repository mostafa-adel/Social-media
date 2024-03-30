package com.example.SocialNetwork.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository){
        return args -> {
            User mostafa= new User(
                    "Mostafa Adel"
            );

            User amr = new User(

                    "Amr Diab"
            );

            User tamer = new User(

                    "Tamer Hosny"
            );

            repository.saveAll(
                    List.of(mostafa,amr,tamer)
            );
        };
    }
}
