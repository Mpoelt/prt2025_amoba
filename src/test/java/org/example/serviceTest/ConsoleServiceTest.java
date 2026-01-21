package org.example.serviceTest;

import org.example.service.ConsoleService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

public class ConsoleServiceTest {

    private InputStream originalIn;

    @Before
    public void setUp() {
        // Eredeti System.in elmentése
        originalIn = System.in;
    }

    @After
    public void tearDown() {
        // System.in visszaállítása, hogy más teszteket ne törjünk el
        System.setIn(originalIn);
    }

    @Test
    public void readIntFromConsole_shouldReturnInt() {
        // GIVEN
        System.setIn(new ByteArrayInputStream("42\n".getBytes()));
        ConsoleService consoleService = new ConsoleService();

        // WHEN
        int result = consoleService.readIntFromConsole("Adj meg egy számot:");

        // THEN
        assertEquals(42, result);
    }

    @Test
    public void readBooleanFromConsole_shouldReturnBoolean() {
        // GIVEN
        System.setIn(new ByteArrayInputStream("true\n".getBytes()));
        ConsoleService consoleService = new ConsoleService();

        // WHEN
        boolean result = consoleService.readBooleanFromConsole("Igen?");

        // THEN
        assertTrue(result);
    }

    @Test
    public void readStringFromConsole_shouldReturnString() {
        // GIVEN
        System.setIn(new ByteArrayInputStream("hello\n".getBytes()));
        ConsoleService consoleService = new ConsoleService();

        // WHEN
        String result = consoleService.readStringFromConsole("Adj meg egy szöveget:");

        // THEN
        assertEquals("hello", result);
    }

    @Test
    public void print_shouldNotThrow() {
        ConsoleService consoleService = new ConsoleService();

        consoleService.print("Ez egy üzenet");

        // Ha nem dob kivételt, a teszt sikeres
    }

    @Test
    public void printWithPlayerName_shouldNotThrow() {
        ConsoleService consoleService = new ConsoleService();

        consoleService.printWithPlayerName("Üzenet a játékosnak: {}", "Player1");

        // Ha nem dob kivételt, a teszt sikeres
    }
}
