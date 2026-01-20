package org.example;

import org.example.service.GameService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AmobaGameApp {
    public static void main(final String[] args) {

       final ApplicationContext context = SpringApplication.run(AmobaGameApp.class, args);

       final GameService gameService = context.getBean(GameService.class);
        gameService.startGame();

    }


}
