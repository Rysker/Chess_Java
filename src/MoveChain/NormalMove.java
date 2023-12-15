package MoveChain;

import Board.*;
import DataTypes.Tuple;
import Pieces.Piece;

public class NormalMove extends MoveChain
{
    @Override
    public boolean performMove(Piece piece, Tuple<Integer, Integer> ending, Board board)
    {
        if(piece.inMoves(ending.getFirst(), ending.getSecond()))
        {
            Block destination = board.getBlockFromCoords(ending.getFirst(), ending.getSecond());
            Tuple<Integer, Integer> coords = piece.getCoords(board);
            Block origin = board.getBlockFromCoords(coords.getFirst(), coords.getSecond());
            //Normal move, no enemy
            if(destination.getPiece() == null)
            {
                origin.removePiece();
                destination.setPiece(piece);
            }
            //Enemy in destination
            else
            {
                Piece captured = destination.getPiece();
                destination.removePiece();
                origin.removePiece();
                board.removePiece(captured);
                destination.setPiece(piece);
            }
            return true;
        }
        return false;
    }
}
