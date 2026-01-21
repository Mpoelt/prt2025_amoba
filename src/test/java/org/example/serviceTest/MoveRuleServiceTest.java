package org.example.serviceTest;

import org.example.domain.Board;
import org.example.domain.Symbol;
import org.example.service.MoveRuleService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoveRuleServiceTest {

    private MoveRuleService moveRuleService;
    private Board board;

    @Before
    public void setUp() {
        moveRuleService = new MoveRuleService();
        board = new Board(3);
    }

    // --------------------------------------------------
    // hasAnySymbol
    // --------------------------------------------------

    @Test
    public void hasAnySymbol_whenBoardContainsSymbol_shouldReturnTrue() {
        // GIVEN
        board.set(1, 1, Symbol.X);

        // WHEN
        boolean result = moveRuleService.hasAnySymbol(board, Symbol.X);

        // THEN
        assertTrue(result);
    }

    @Test
    public void hasAnySymbol_whenBoardDoesNotContainSymbol_shouldReturnFalse() {
        // GIVEN – üres tábla

        // WHEN
        boolean result = moveRuleService.hasAnySymbol(board, Symbol.O);

        // THEN
        assertFalse(result);
    }

    // --------------------------------------------------
    // isAdjecentToSymbol
    // --------------------------------------------------

    @Test
    public void isAdjecentToSymbol_whenSymbolIsAdjacent_shouldReturnTrue() {
        // GIVEN
        board.set(1, 1, Symbol.X);

        // WHEN – (0,1) szomszédos (felül)
        boolean result = moveRuleService.isAdjecentToSymbol(board, 0, 1, Symbol.X);

        // THEN
        assertTrue(result);
    }

    @Test
    public void isAdjecentToSymbol_whenSymbolIsDiagonal_shouldReturnTrue() {
        // GIVEN
        board.set(1, 1, Symbol.O);

        // WHEN – diagonálisan szomszédos
        boolean result = moveRuleService.isAdjecentToSymbol(board, 0, 0, Symbol.O);

        // THEN
        assertTrue(result);
    }

    @Test
    public void isAdjecentToSymbol_whenNoAdjacentSymbol_shouldReturnFalse() {
        // GIVEN
        board.set(2, 2, Symbol.X);

        // WHEN – nincs mellette X
        boolean result = moveRuleService.isAdjecentToSymbol(board, 0, 0, Symbol.X);

        // THEN
        assertFalse(result);
    }

    @Test
    public void isAdjecentToSymbol_shouldNotCheckOutOfBounds() {
        // GIVEN
        board.set(0, 0, Symbol.X);

        // WHEN – sarok, határon kívüli mezők lennének
        boolean result = moveRuleService.isAdjecentToSymbol(board, 2, 2, Symbol.X);

        // THEN
        assertFalse(result);
    }
}
