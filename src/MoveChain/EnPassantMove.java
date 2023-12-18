package MoveChain;

import Board.*;
import DataTypes.PieceColor;
import DataTypes.PieceType;
import DataTypes.Tuple;
import Pieces.Piece;

public class EnPassantMove extends MoveChain
{
    public EnPassantMove()
    {
        this.nextMoveChain = new NormalMove();
    }
    @Override
    public boolean performMove(int turn, Piece piece, Tuple<Integer, Integer> ending, Board board)
    {
        Block destination = board.getBlockFromCoords(ending.getFirst(), ending.getSecond());
        Tuple<Integer, Integer> coords = piece.getCoords(board);
        Block origin = board.getBlockFromCoords(coords.getFirst(), coords.getSecond());
        if (piece.getType() == PieceType.PAWN && (coords.getFirst() == 3 || coords.getFirst() == 4))
        {
            if(piece.getColor() == PieceColor.WHITE)
            {
                Piece enemy = board.getPieceFromCoords(ending.getFirst() + 1, ending.getSecond());
                if(enemy != null && enemy.getType() == PieceType.PAWN && enemy.getColor() != piece.getColor() && (enemy.getDouble_turn() + 1) == turn)
                {
                    Block enemy_block = board.getBlockFromCoords(ending.getFirst() + 1, ending.getSecond());
                    destination.setPiece(piece);
                    origin.removePiece();
                    enemy_block.removePiece();
                    board.removePiece(enemy);
                    piece.setLastMoveTurn(turn);
                    return true;
                }
            }
            else
            {
                Piece enemy = board.getPieceFromCoords(ending.getFirst() - 1, ending.getSecond());
                if(enemy != null && enemy.getType() == PieceType.PAWN && enemy.getColor() != piece.getColor() && (enemy.getDouble_turn() + 1) == turn)
                {
                    Block enemy_block = board.getBlockFromCoords(ending.getFirst() - 1, ending.getSecond());
                    destination.setPiece(piece);
                    origin.removePiece();
                    enemy_block.removePiece();
                    board.removePiece(enemy);
                    piece.setLastMoveTurn(turn);
                    return true;
                }
            }
        }
        return this.nextMoveChain.performMove(turn, piece, ending, board);
    }
}
