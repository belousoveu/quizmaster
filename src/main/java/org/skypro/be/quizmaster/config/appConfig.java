package org.skypro.be.quizmaster.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class appConfig {

    private final Random randomBean = new Random();

    @Bean(name = "randomBean")
    public Random getRandomBean() {
        return randomBean;
    }
}
