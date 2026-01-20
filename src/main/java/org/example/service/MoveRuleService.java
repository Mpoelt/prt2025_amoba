package org.example.service;

import org.example.domain.Board;
import org.example.domain.Symbol;
import org.springframework.stereotype.Service;

@SuppressWarnings({"PMD.AtLeastOneConstructor", "PMD.OnlyOneReturn", "PMD.LongVariable"})
@Service
public class MoveRuleService {

    public boolean hasAnySymbol(final Board board, final Symbol symbol) {
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.get(row, col) == symbol) {
                    return true;
                }
            }
        }
        return false;
    }


   public boolean isAdjecentToSymbol(final Board board, final int row, final int col, final Symbol symbol) {
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {

                //ugyan az a mező, ezt kihagyjuk
                if (rowOffset == 0 && colOffset == 0) {
                    continue;
                }

                final int neighborRow = row + rowOffset;
                final int neighborColumn = col + colOffset;

                final boolean rowIsInsideBoard = neighborRow >= 0 && neighborRow < board.getSize();
                final boolean columnIsInsideBoard = neighborColumn >= 0 && neighborColumn < board.getSize();

                if (rowIsInsideBoard && columnIsInsideBoard) {
                    if (board.get(neighborRow, neighborColumn) == symbol) {
                        return true;
                    }
                }

            }
        }
        return false;

   }

}
