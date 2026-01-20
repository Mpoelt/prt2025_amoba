package org.example.service;

import org.example.domain.Board;
import org.springframework.stereotype.Service;

@Service
public class LoadGameDecider {
    private final ConsoleService consoleService;
    private final GameLoadService gameLoadService;

    public LoadGameDecider(final ConsoleService consoleService, final GameLoadService gameLoadService) {
        this.consoleService = consoleService;
        this.gameLoadService = gameLoadService;
    }

    @SuppressWarnings("PMD.OnlyOneReturn")
    public Board loadBoard() {
        final String load =  consoleService.readStringFromConsole("Load Game? (yes/no): ");
        if ("yes".equalsIgnoreCase(load)) {
            return gameLoadService.loadBoardFromFile("mentes.txt");
        } else {
            final int size = consoleService.readIntFromConsole("Add meg a pálya méretét: ");
            return new Board(size);
        }
    }
}
