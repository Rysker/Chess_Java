package MoveStrategy;

import Board.Board;
import DataTypes.PieceColor;
import DataTypes.Tuple;
import Pieces.Piece;

import java.util.ArrayList;

public class PawnMoveset extends MoveStrategy
{
    @Override
    public ArrayList<Tuple<Integer, Integer>> firstMoveCheck(int origin_row, int origin_col, Board board)
    {
        ArrayList<Tuple<Integer, Integer>> moves = new ArrayList<>();
        PieceColor color = board.getPieceFromCoords(origin_row, origin_col).getColor();

        if (color.equals(PieceColor.WHITE))
        {
            if(checkIfFreeBlock(board, origin_row, origin_col, 1))
            {
                moves.add(new Tuple<>(origin_row - 1, origin_col));
                if (origin_row == 6 && checkIfFreeBlock(board, origin_row, origin_col, 2))
                {
                    moves.add(new Tuple<>(origin_row - 2, origin_col));
                }
            }

        }
        else
        {
            if (checkIfFreeBlock(board, origin_row, origin_col, -1))
            {
                moves.add(new Tuple<>(origin_row + 1, origin_col));
                if (origin_row == 1 && checkIfFreeBlock(board, origin_row, origin_col, -2))
                {
                    moves.add(new Tuple<>(origin_row + 2, origin_col));
                }
            }
        }

        return moves;
    }

    private boolean checkIfFreeBlock(Board board, int row, int col, int distance)
    {
        if(!checkValidRange(row - distance, col))
            return false;
        Piece destination = board.getPieceFromCoords(row - distance, col);
        return (destination == null);
    }

    private boolean checkIfEnemy(Board board, PieceColor color, int row, int col)
    {
        if(!checkValidRange(row, col))
            return false;
        return board.getPieceFromCoords(row, col).getColor() != color;
    }
}
