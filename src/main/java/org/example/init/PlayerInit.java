package org.example.init;

import org.example.domain.HumanPlayer;
import org.example.domain.Player;
import org.example.domain.Symbol;
import org.example.service.ConsoleService;
import org.springframework.stereotype.Service;

@Service
public class PlayerInit {
private final ConsoleService consoleService;

    public PlayerInit(final ConsoleService consoleService) {
        this.consoleService = consoleService;
    }

public Player createHumanPlayer() {
       final  String name = consoleService.readStringFromConsole("Add meg a játékos nevét: ");
        return new HumanPlayer(Symbol.X, name);
}
}
