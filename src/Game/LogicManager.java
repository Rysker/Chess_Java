package Game;

import Board.*;
import DataTypes.PieceColor;
import DataTypes.PieceType;
import DataTypes.Tuple;
import MoveChain.*;
import Pieces.Piece;
import Pieces.Queen;

import java.util.ArrayList;

public class LogicManager
{
    private static LogicManager instance = null;
    private MoveChain move_chain = new CastlingMove();
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
    }

    private void checkAllPossibilities(int turn, Board board)
    {
        Piece white_king = board.getWhite_king();
        Piece black_king = board.getBlack_king();
        //Black king moved last time
        removeStillEliminatingMoves(turn, white_king, board.getAllPieces(), board);
        removeStillEliminatingMoves(turn, black_king, board.getAllPieces(), board);
    }


    private void removeStillEliminatingMoves(int turn, Piece king, ArrayList<Piece> pieces, Board board)
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
                this.move_chain.performMove(turn, piece2, move, simulation_board);
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

    public Piece checkPromotion(Board board)
    {
        for(Piece piece: board.getAllPieces())
        {
            if(piece.getType() == PieceType.PAWN)
            {
                if (piece.getColor() == PieceColor.WHITE && piece.getCoords(board).getFirst() == 0)
                    return piece;
                if (piece.getColor() == PieceColor.BLACK && piece.getCoords(board).getFirst() == 7)
                    return piece;
            }
        }
        return null;
    }

    public void performPromotion(Piece piece, String choice, Board board)
    {
        Tuple<Integer, Integer> coords = board.getTupleFromPiece(piece);
        PieceColor color = piece.getColor();
        Block block = board.getBlockFromCoords(coords.getFirst(), coords.getSecond());
        block.removePiece();
        board.removePiece(piece);
        switch(choice)
        {
            case "QUEEN":
                board.addPiece(color, PieceType.QUEEN, block);
                break;
            case "BISHOP":
                board.addPiece(color, PieceType.BISHOP, block);
                break;
            case "ROOK":
                board.addPiece(color, PieceType.ROOK, block);
                break;
            case "KNIGHT":
                board.addPiece(color, PieceType.KNIGHT, block);
                break;
            default:
                System.out.println("ERROR!!!");;
                break;
        }
    }

    public int checkConditions(int turn, Board board)
    {
        Tuple<Integer, Integer> white_king = board.getWhite_king().getCoords(board);
        Tuple<Integer, Integer> black_king = board.getBlack_king().getCoords(board);
        ArrayList<Piece> pieces = new ArrayList<>();
        //Black's moved last turn
        if(turn % 2 == 0)
        {
            pieces = getEnemyPieces(PieceColor.BLACK, board);
            if(countMoves(pieces) == 0 && board.inEnemyMoves(PieceColor.WHITE, white_king.getFirst(), white_king.getSecond()))
                return 1;
            else if (countMoves(pieces) == 0 && !board.inEnemyMoves(PieceColor.WHITE, white_king.getFirst(), white_king.getSecond()))
                return 2;
            else
                return 0;
        }
        //White's moved last turn
        else
        {
            pieces = getEnemyPieces(PieceColor.WHITE, board);
            if(countMoves(pieces) == 0 && board.inEnemyMoves(PieceColor.BLACK, black_king.getFirst(), black_king.getSecond()))
                return 3;
            else if (countMoves(pieces) == 0 && !board.inEnemyMoves(PieceColor.BLACK, black_king.getFirst(), black_king.getSecond()))
                return 2;
            else
                return 0;
        }
    }

    private int countMoves(ArrayList<Piece> pieces)
    {
        int size = 0;
        for(Piece piece: pieces)
        {
            size += piece.getAttackingMoves().size();
        }
        return size;
    }

}
