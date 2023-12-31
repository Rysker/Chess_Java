package MoveChain;

import Board.*;
import DataTypes.PieceType;
import DataTypes.Tuple;
import Pieces.Piece;
import SoundManager.SoundManager;

import static java.lang.Math.abs;

public class NormalMove extends MoveChain
{
    public NormalMove()
    {
        this.nextMoveChain = null;
    }
    @Override
    public Tuple<Boolean, String> performMove(int turn, Piece piece, Tuple<Integer, Integer> ending, Board board)
    {
        if(piece.inMoves(ending.getFirst(), ending.getSecond()))
        {
            Block destination = board.getBlockFromCoords(ending.getFirst(), ending.getSecond());
            Tuple<Integer, Integer> coords = piece.getCoords(board);
            Block origin = board.getBlockFromCoords(coords.getFirst(), coords.getSecond());

            //Normal move, no enemy
            if(destination.getPiece() == null)
            {
                if(piece.getType() == PieceType.PAWN)
                    return handlePieceDoubleMove(turn, piece, ending, board);
                origin.removePiece();
                destination.setPiece(piece);
                piece.setLastMoveTurn(turn);
                SoundManager.getInstance().playSound("Normal");
                return new Tuple<>(Boolean.TRUE, "Normal");
            }
            //Enemy in destination
            else
            {
                Piece captured = destination.getPiece();
                destination.removePiece();
                origin.removePiece();
                board.removePiece(captured);
                destination.setPiece(piece);
                piece.setLastMoveTurn(turn);
                SoundManager.getInstance().playSound("Normal");
                return new Tuple<>(Boolean.TRUE, "Attacking");
            }
        }
        return new Tuple<>(Boolean.FALSE, "NoMove");
    }

    private  Tuple<Boolean, String> handlePieceDoubleMove(int turn, Piece piece, Tuple<Integer, Integer> ending, Board board)
    {
        Block destination = board.getBlockFromCoords(ending.getFirst(), ending.getSecond());
        Tuple<Integer, Integer> coords = piece.getCoords(board);
        Block origin = board.getBlockFromCoords(coords.getFirst(), coords.getSecond());
        if(abs(coords.getFirst() - ending.getFirst()) == 2)
        {
            piece.setDouble_turn(turn);
            piece.setLastMoveTurn(turn);
            origin.removePiece();
            destination.setPiece(piece);
        }
        else
        {
            Piece captured = destination.getPiece();
            destination.removePiece();
            origin.removePiece();
            board.removePiece(captured);
            destination.setPiece(piece);
        }
        piece.setLastMoveTurn(turn);
        SoundManager.getInstance().playSound("Normal");
        return new Tuple<>(Boolean.TRUE, "Normal");
    }
}
