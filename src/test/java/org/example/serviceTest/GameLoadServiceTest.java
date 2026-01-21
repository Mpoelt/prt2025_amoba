package org.example.serviceTest;

import org.example.domain.Board;
import org.example.domain.Symbol;
import org.example.service.GameLoadService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class GameLoadServiceTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void loadBoardFromFile_shouldLoadCorrectBoard() throws IOException {
        // --- GIVEN: ideiglenes fájl létrehozása ---
        File tempFile = tempFolder.newFile("testboard.txt");

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("Size 3\n");         // Méret
            writer.write("Header\n");         // Fejléc
            writer.write("1 X O X\n");        // Sor 0
            writer.write("2 O X O\n");        // Sor 1
            writer.write("3 X EMPTY O\n");    // Sor 2
        }

        GameLoadService gameLoadService = new GameLoadService();

        // --- WHEN: board betöltése ---
        Board board = gameLoadService.loadBoardFromFile(tempFile.getAbsolutePath());

        // --- THEN: ellenőrzés ---
        assertEquals(3, board.getSize());

        assertEquals(Symbol.X, board.get(0,0));
        assertEquals(Symbol.O, board.get(0,1));
        assertEquals(Symbol.X, board.get(0,2));

        assertEquals(Symbol.O, board.get(1,0));
        assertEquals(Symbol.X, board.get(1,1));
        assertEquals(Symbol.O, board.get(1,2));

        assertEquals(Symbol.X, board.get(2,0));
        assertEquals(Symbol.EMPTY, board.get(2,1));
        assertEquals(Symbol.O, board.get(2,2));
    }

    @Test(expected = RuntimeException.class)
    public void loadBoardFromFile_invalidFile_shouldThrowRuntimeException() {
        GameLoadService gameLoadService = new GameLoadService();

        // Nem létező fájl -> kivételt kell dobnia
        gameLoadService.loadBoardFromFile("nemletezo.txt");
    }
}
