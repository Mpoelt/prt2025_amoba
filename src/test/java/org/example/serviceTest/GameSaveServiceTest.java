package org.example.serviceTest;

import org.example.domain.Board;
import org.example.domain.Symbol;
import org.example.service.GameSaveService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GameSaveServiceTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    // Létrehozunk egy példányt a service-ből
    private final GameSaveService gameSaveService = new GameSaveService();

    @Test
    public void saveBoardToFile_shouldWriteCorrectFile() throws IOException {
        Board board = new Board(3);
        board.set(0, 0, Symbol.X);
        board.set(1, 1, Symbol.O);

        // Használjunk ideiglenes fájlt a temp mappából
        File tempFile = tempFolder.newFile("test_board.txt");

        // Mentés
        gameSaveService.saveBoardToFile(board, tempFile.getAbsolutePath());

        List<String> lines = Files.readAllLines(tempFile.toPath());

        // Ellenőrizzük a fejlécet
        assertEquals("   A B C ", lines.get(1)); // három szóköz az elején + szóköz a sor végén

        // Ellenőrizzük az első sort
        assertEquals(" 0 X . . ", lines.get(2));

        // Ellenőrizzük a második sort
        assertEquals(" 1 . O . ", lines.get(3));

        // Ellenőrizzük a harmadik sort
        assertEquals(" 2 . . . ", lines.get(4));
    }

    @Test
    public void saveBoardToFile_emptyBoard_shouldWriteDots() throws IOException {
        Board board = new Board(2);

        File tempFile = tempFolder.newFile("emptyBoard.txt");

        // Mentés
        gameSaveService.saveBoardToFile(board, tempFile.getAbsolutePath());

        // Fájl ellenőrzése
        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
            assertEquals("SIZE 2", reader.readLine().trim());
            assertEquals("   A B ", reader.readLine()); // három szóköz az elején + szóköz a végén
            assertEquals(" 0 . . ", reader.readLine());
            assertEquals(" 1 . . ", reader.readLine());
        }
    }
}
