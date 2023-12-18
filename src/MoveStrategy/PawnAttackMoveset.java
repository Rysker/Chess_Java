package MoveStrategy;

import Board.Board;
import DataTypes.PieceColor;
import DataTypes.Tuple;
import Pieces.*;

import java.util.ArrayList;

public class PawnAttackMoveset extends MoveStrategy
{
    @Override
    public ArrayList<Tuple<Integer, Integer>> firstMoveCheck(int origin_row, int origin_col, Board board)
    {
        Piece piece = board.getPieceFromCoords(origin_row, origin_col);
        Tuple<Integer, Integer> coords = piece.getCoords(board);
        PieceColor color = piece.getColor();
        int x;
        if (color.equals(PieceColor.WHITE))
            x = -1;
        else
            x = 1;
        ArrayList<Tuple<Integer, Integer>> tmp = new ArrayList<>();
        if(checkValidRange(coords.getFirst() + x, coords.getSecond() + x))
        {
            Piece enemy = board.getPieceFromCoords(coords.getFirst() + x, coords.getSecond() + x);
            if (enemy != null && !enemy.getColor().equals(color))
                tmp.add(new Tuple<>(coords.getFirst() + x, coords.getSecond() + x));
        }

        if(checkValidRange(coords.getFirst() + x, coords.getSecond() - x))
        {
            Piece enemy = board.getPieceFromCoords(coords.getFirst() + x, coords.getSecond() - x);
            if (enemy != null && !enemy.getColor().equals(color))
                tmp.add(new Tuple<>(coords.getFirst() + x, coords.getSecond() - x));
        }
        return tmp;
    }
}
