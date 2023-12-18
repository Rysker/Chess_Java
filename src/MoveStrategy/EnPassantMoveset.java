package MoveStrategy;

import Board.Board;
import DataTypes.PieceColor;
import DataTypes.Tuple;
import Pieces.Piece;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class EnPassantMoveset extends MoveStrategy
{
    @Override
    public ArrayList<Tuple<Integer, Integer>> firstMoveCheck(int origin_row, int origin_col, Board board)
    {
        ArrayList<Tuple<Integer, Integer>> moves = new ArrayList<>();
        Piece piece = board.getPieceFromCoords(origin_row, origin_col);
        PieceColor color = piece.getColor();
        //WhiteEnPassant
        if(color == PieceColor.WHITE)
        {
            if(piece.getCoords(board).getFirst() == 3)
            {
                if(checkValidRange(origin_row, origin_col - 1))
                {
                    if(board.getPieceFromCoords(origin_row, origin_col - 1) != null)
                    {
                        int difference = board.getPieceFromCoords(origin_row, origin_col - 1).getLastMoveTurn() - board.getTurn();
                        if (abs(difference) == 1)
                        {
                            moves.add(new Tuple<>(origin_row - 1, origin_col - 1));
                            return moves;
                        }
                    }
                }

                if(checkValidRange(origin_row, origin_col + 1))
                {
                    if(board.getPieceFromCoords(origin_row, origin_col + 1) != null)
                    {
                        int difference = board.getPieceFromCoords(origin_row, origin_col + 1).getLastMoveTurn() - board.getTurn();
                        if (abs(difference) == 1)
                        {
                            moves.add(new Tuple<>(origin_row - 1, origin_col + 1));
                            return moves;
                        }
                    }
                }
            }
        }
        else
        {
            if(piece.getCoords(board).getFirst() == 4)
            {
                if(checkValidRange(origin_row, origin_col - 1))
                {
                    if(board.getPieceFromCoords(origin_row, origin_col - 1) != null)
                    {
                        int difference = board.getPieceFromCoords(origin_row, origin_col - 1).getLastMoveTurn() - board.getTurn();
                        if (abs(difference) == 1)
                        {
                            moves.add(new Tuple<>(origin_row + 1, origin_col - 1));
                            return moves;
                        }
                    }
                }

                if(checkValidRange(origin_row, origin_col + 1))
                {
                    if(board.getPieceFromCoords(origin_row, origin_col + 1) != null)
                    {
                        int difference = board.getPieceFromCoords(origin_row, origin_col + 1).getLastMoveTurn() - board.getTurn();
                        if (abs(difference) == 1)
                        {
                            moves.add(new Tuple<>(origin_row + 1, origin_col + 1));
                            return moves;
                        }
                    }
                }
            }
        }
        return moves;
    }
}
