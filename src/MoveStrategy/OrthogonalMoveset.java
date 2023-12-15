package MoveStrategy;

import Board.Board;
import DataTypes.Tuple;
import Board.*;
import DataTypes.PieceColor;

import java.util.ArrayList;

public class OrthogonalMoveset extends MoveStrategy
{
    @Override
    public ArrayList<Tuple<Integer, Integer>> firstMoveCheck(int origin_row, int origin_col, Board board)
    {

        ArrayList tmp;
        tmp = generateEntry(origin_row, origin_col, board);
        return tmp;
    }

    private ArrayList<Tuple<Integer, Integer>> generateEntry(int origin_row, int origin_col, Board board)
    {
        ArrayList<Tuple<Integer, Integer>> moves = new ArrayList<>();
        PieceColor color = board.getPieceFromCoords(origin_row, origin_col).getColor();

        // N
        for (int i = 1; i < 8; i++)
        {
            Tuple<Integer, Integer> tmp = new Tuple<>(origin_row - i, origin_col);
            if (checkValidRange(tmp.getFirst(), tmp.getSecond()))
            {
                Block block = board.getBlockFromCoords(origin_row - i, origin_col);
                if (block.getPiece() != null)
                {
                    if (!block.getPiece().getColor().equals(color))
                    {
                        moves.add(new Tuple<>(origin_row - i, origin_col));
                    }
                    break;
                }
                else
                {
                    moves.add(new Tuple<>(origin_row - i, origin_col));
                }
            }
        }

        // E
        for (int i = 1; i < 8; i++)
        {
            Tuple<Integer, Integer> tmp = new Tuple<>(origin_row, origin_col + i);
            if (checkValidRange(tmp.getFirst(), tmp.getSecond()))
            {
                Block block = board.getBlockFromCoords(origin_row, origin_col + i);
                if (block.getPiece() != null)
                {
                    if (!block.getPiece().getColor().equals(color))
                    {
                        moves.add(new Tuple<>(origin_row, origin_col + i));
                    }
                    break;
                }
                else
                {
                    moves.add(new Tuple<>(origin_row, origin_col + i));
                }
            }
        }

        // S
        for (int i = 1; i < 8; i++)
        {
            Tuple<Integer, Integer> tmp = new Tuple<>(origin_row + i, origin_col);
            if (checkValidRange(tmp.getFirst(), tmp.getSecond()))
            {
                Block block = board.getBlockFromCoords(origin_row + i, origin_col);
                if (block.getPiece() != null)
                {
                    if (!block.getPiece().getColor().equals(color))
                    {
                        moves.add(new Tuple<>(origin_row + i, origin_col));
                    }
                    break;
                }
                else
                {
                    moves.add(new Tuple<>(origin_row + i, origin_col));
                }
            }
        }

        // W
        for (int i = 1; i < 8; i++)
        {
            Tuple<Integer, Integer> tmp = new Tuple<>(origin_row, origin_col - i);
            if (checkValidRange(tmp.getFirst(), tmp.getSecond()))
            {
                Block block = board.getBlockFromCoords(origin_row, origin_col - i);
                if (block.getPiece() != null)
                {
                    if (!block.getPiece().getColor().equals(color))
                    {
                        moves.add(new Tuple<>(origin_row, origin_col - i));
                    }
                    break;
                }
                else
                {
                    moves.add(new Tuple<>(origin_row, origin_col - i));
                }
            }
        }

        return moves;
    }
}
