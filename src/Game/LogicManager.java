package Game;

import Board.*;
import DataTypes.PieceColor;
import DataTypes.Tuple;
import MoveChain.*;
import Pieces.Piece;

import java.util.ArrayList;

public class LogicManager
{
    private static LogicManager instance = null;
    private MoveChain move_chain = new NormalMove();
    private LogicManager()
    {

    }

    public static LogicManager getInstance()
    {
        if(instance == null)
            instance = new LogicManager();
        return instance;
    }

    private void firstMovesUpdate(Board board)
    {
        ArrayList<Piece> pieces = board.getAllPieces();
        for(Piece piece: pieces)
        {
            piece.getPossibleMoves(board);
            piece.getAttackingMoves(board);
        }
    }

    public void finalLogic(int turn, Board board)
    {
        firstMovesUpdate(board);
        checkAllPossibilities(turn, board);
        System.out.println("Logic performed");
    }

    private void checkAllPossibilities(int turn, Board board)
    {
        Piece white_king = board.getWhite_king();
        Piece black_king = board.getBlack_king();
        //Black king moved last time
        removeStillEliminatingMoves(white_king, board.getAllPieces(), board);
        removeStillEliminatingMoves(black_king, board.getAllPieces(), board);
    }


    private void removeStillEliminatingMoves(Piece king, ArrayList<Piece> pieces, Board board)
    {
        Board simulation_board = board.clone();
        PieceColor color = king.getColor();
        ArrayList<Piece> pieces_same_color = new ArrayList<>();

        for (Piece piece : pieces)
        {
            if (piece.getColor().equals(color))
                pieces_same_color.add(piece);
        }

        for (Piece piece : pieces_same_color)
        {
            ArrayList<Tuple<Integer, Integer>> moves = new ArrayList<>(piece.getPossibleMoves());
            for (Tuple<Integer, Integer> move: moves)
            {
                simulation_board = board.clone();
                Piece piece2 = findSamePiece(piece, simulation_board);
                this.move_chain.performMove(piece2, move, simulation_board);
                this.firstMovesUpdate(simulation_board);
                king = getKingFromColor(color, simulation_board);
                if (kingInEnemyAttackingMoves(king, simulation_board))
                {
                    Piece toTrim = findSamePiece(piece, board);
                    toTrim.removeMove(move);
                }
            }
        }
    }
    private Piece getKingFromColor(PieceColor color, Board board)
    {
        if(color == PieceColor.WHITE)
            return board.getWhite_king();
        else
            return board.getBlack_king();
    }
    private Piece findSamePiece(Piece piece, Board board)
    {
        for(Block square: board.getSquares())
        {
            if(square.getPiece() != null && square.getPiece().getId() == piece.getId())
            {
                return square.getPiece();
            }
        }
        return null;
    }
    private boolean kingInEnemyAttackingMoves(Piece king, Board board)
    {
        Tuple<Integer, Integer> coords = king.getCoords(board);
        ArrayList<Piece> enemy_pieces = getEnemyPieces(king.getColor(), board);
        for(Piece piece: enemy_pieces)
        {
            for (Tuple<Integer, Integer> move : piece.getAttackingMoves())
            {
                if (coords.equals(move))
                    return true;
            }

        }
        return false;
    }

    private ArrayList<Piece> getEnemyPieces(PieceColor color, Board board)
    {
        ArrayList<Piece> tmp = new ArrayList<>();
        for(Piece piece: board.getAllPieces())
        {
            if(piece.getColor() != color)
                tmp.add(piece);
        }
        return tmp;
    }
}
