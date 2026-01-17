package org.example.service;

import org.example.domain.Board;
import org.springframework.stereotype.Service;

@Service
public class LoadGameDecider {
    private final ConsoleService consoleService;
    private final GameLoadService gameLoadService;

    public LoadGameDecider(ConsoleService consoleService, GameLoadService gameLoadService) {
        this.consoleService = consoleService;
        this.gameLoadService = gameLoadService;
    }


    public Board loadBoard(){
        String load =  consoleService.readStringFromConsole("Load Game? (yes/no): ");
        if ("yes".equalsIgnoreCase(load)){
            return gameLoadService.loadBoardFromFile("mentes.txt");
        } else {
            int size = consoleService.readIntFromConsole("Add meg a pálya méretét: ");
            return new Board(size);
        }
    }
}
