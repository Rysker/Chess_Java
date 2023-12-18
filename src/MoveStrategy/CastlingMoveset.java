package MoveStrategy;

import Board.*;
import DataTypes.PieceColor;
import DataTypes.PieceType;
import DataTypes.Tuple;

import java.util.ArrayList;

public class CastlingMoveset extends MoveStrategy
{
    @Override
    public ArrayList<Tuple<Integer, Integer>> firstMoveCheck(int origin_row, int origin_col, Board board)
    {
        ArrayList<Tuple<Integer, Integer>> moves = new ArrayList<>();
        PieceColor color = board.getPieceFromCoords(origin_row, origin_col).getColor();
        ArrayList<Block> squares = board.getSquares();
        if(board.getPieceFromCoords(origin_row, origin_col).getLastMoveTurn() > -1)
        {
            return moves;
        }
        if (color == PieceColor.WHITE)
        {
            // Check short Castling
            if (squares.get(61).getPiece() == null && squares.get(62).getPiece() == null &&
                    squares.get(63).getPiece() != null &&
                    squares.get(63).getPiece().getType() == PieceType.ROOK &&
                    squares.get(63).getPiece().getLastMoveTurn() == -1 && board.inEnemyMoves(color, 7, 5)) {
                moves.add(new Tuple<>(7, 6));
            }

            // Check long Castling
            if (squares.get(59).getPiece() == null && squares.get(58).getPiece() == null &&
                    squares.get(57).getPiece() == null &&
                    squares.get(56).getPiece() != null &&
                    squares.get(56).getPiece().getType() == PieceType.ROOK &&
                    squares.get(56).getPiece().getLastMoveTurn() == -1 &&
                    !board.inEnemyMoves(color, 7, 3))
            {
                moves.add(new Tuple<>(7, 2));
            }
        }
        else
        {
            // Check short Castling
            if (squares.get(5).getPiece() == null && squares.get(6).getPiece() == null &&
                    squares.get(7).getPiece() != null &&
                    squares.get(7).getPiece().getType() == PieceType.ROOK &&
                    squares.get(7).getPiece().getLastMoveTurn() == -1 && !board.inEnemyMoves(color, 0, 5)) {
                moves.add(new Tuple<>(0, 6));
            }

            // Check long Castling
            if (squares.get(1).getPiece() == null && squares.get(2).getPiece() == null &&
                    squares.get(3).getPiece() == null &&
                    squares.get(0).getPiece() != null &&
                    squares.get(0).getPiece().getType() == PieceType.ROOK &&
                    squares.get(0).getPiece().getLastMoveTurn() == -1 &&
                    !board.inEnemyMoves(color, 0, 3)) {
                moves.add(new Tuple<>(0, 2));
            }
        }
        return moves;
    }
}
