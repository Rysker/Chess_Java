package MoveStrategy;

import DataTypes.Tuple;
import Board.*;
import DataTypes.PieceColor;
import Pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public abstract class MoveStrategy
{
    abstract public ArrayList<Tuple<Integer, Integer>> firstMoveCheck(int origin_row, int origin_col, Board board);

    public boolean checkValidRange(int row, int col)
    {
        return (0 <= row && row <= 7) && (0 <= col && col <= 7);
    }

    public ArrayList<Tuple<Integer, Integer>> returnValidMoves(List<Tuple<Integer, Integer>> moves)
    {
        ArrayList<Tuple<Integer, Integer>> validMoves = new ArrayList<>();
        for (Tuple<Integer, Integer> move : moves)
        {
            if (checkValidRange(move.getFirst(), move.getSecond()))
            {
                validMoves.add(move);
            }
        }
        return validMoves;
    }

    public ArrayList<Tuple<Integer, Integer>> eliminateBlocked(int row, int col, List<Tuple<Integer, Integer>> moves, Board board)
    {
        ArrayList<Tuple<Integer, Integer>> validMoves = new ArrayList<>();
        PieceColor color = board.getPieceFromCoords(row, col).getColor();
        for(Tuple<Integer, Integer> move: moves)
        {
            Piece piece = board.getPieceFromCoords(move.getFirst(), move.getSecond());
            if(piece == null || (piece.getColor() != color))
                validMoves.add(move);
        }
        return validMoves;
    }
}
