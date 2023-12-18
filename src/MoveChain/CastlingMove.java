package MoveChain;

import Board.*;
import DataTypes.PieceType;
import DataTypes.Tuple;
import Pieces.Piece;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class CastlingMove extends MoveChain
{
    public CastlingMove()
    {
        this.setNext(new EnPassantMove());
    }
    @Override
    public boolean performMove(int turn, Piece piece, Tuple<Integer, Integer> ending, Board board)
    {
        if (piece.getType() == PieceType.KING && piece.inMoves(ending.getFirst(), ending.getSecond()) && abs(piece.getCoords(board).getSecond() - ending.getSecond()) == 2)
        {
            ArrayList<Block> squares = board.getSquares();

            //Perform white short castling
            if (ending.getFirst() == 7 && ending.getSecond() == 6)
            {
                squares.get(62).setPiece(squares.get(60).getPiece());
                squares.get(60).setPiece(null);
                squares.get(62).getPiece().setLastMoveTurn(turn);

                squares.get(61).setPiece(squares.get(63).getPiece());
                squares.get(63).setPiece(null);
                squares.get(63).getPiece().setLastMoveTurn(turn);

                return true;
            }

            //Perform white long castling
            if (ending.getFirst() == 7 && ending.getSecond() == 2)
            {
                squares.get(58).setPiece(squares.get(60).getPiece());
                squares.get(60).setPiece(null);
                squares.get(58).getPiece().setLastMoveTurn(turn);

                squares.get(59).setPiece(squares.get(56).getPiece());
                squares.get(56).setPiece(null);
                squares.get(59).getPiece().setLastMoveTurn(turn);

                return true;
            }

            //Perform black short castling
            if (ending.getFirst() == 0 && ending.getSecond() == 6)
            {
                squares.get(6).setPiece(squares.get(4).getPiece());
                squares.get(4).setPiece(null);
                squares.get(6).getPiece().setLastMoveTurn(turn);

                squares.get(5).setPiece(squares.get(7).getPiece());
                squares.get(7).setPiece(null);
                squares.get(5).getPiece().setLastMoveTurn(turn);

                return true;
            }

            //Perform white long castling
            if (ending.getFirst() == 0 && ending.getSecond() == 2)
            {
                squares.get(2).setPiece(squares.get(4).getPiece());
                squares.get(4).setPiece(null);
                squares.get(2).getPiece().setLastMoveTurn(turn);

                squares.get(3).setPiece(squares.get(0).getPiece());
                squares.get(0).setPiece(null);
                squares.get(3).getPiece().setLastMoveTurn(turn);

                return true;
            }
        }
        return this.nextMoveChain.performMove(turn, piece, ending, board);
    }
}
