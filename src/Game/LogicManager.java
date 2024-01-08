package Game;

import Board.*;
import DataTypes.PieceColor;
import DataTypes.PieceType;
import DataTypes.Tuple;
import MoveChain.*;
import Pieces.Piece;
import Players.Player;

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
        Board simulation_board;
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
                System.out.println("ERROR!!!");
                break;
        }
    }

    public int checkConditions(int turn, Board board)
    {
        Tuple<Integer, Integer> white_king = board.getWhite_king().getCoords(board);
        Tuple<Integer, Integer> black_king = board.getBlack_king().getCoords(board);
        ArrayList<Piece> pieces;

        if(checkInsufficientMaterial(board))
            return 2;
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

    private boolean checkInsufficientMaterial(Board board)
    {
        ArrayList<Piece> pieces = board.getAllPieces();
        ArrayList<Piece> whites = new ArrayList<>();
        ArrayList<Piece> blacks = new ArrayList<>();
        for(Piece piece: pieces)
        {
            if(piece.getColor() == PieceColor.WHITE)
                whites.add(piece);
            else
                blacks.add(piece);
        }

        int whiteCount = whites.size();
        int blackCount = blacks.size();

        // King vs King scenario
        if (whiteCount == 1 && blackCount == 1)
        {
            return true;
        }
        else if (whiteCount == 1 && blackCount == 2)
        {
            if (containsBishopOrKnight(blacks))
            {
                return true;
            }
        }
        else if (whiteCount == 2 && blackCount == 1)
        {
            if (containsBishopOrKnight(whites))
            {
                return true;
            }
        }
        else if (whiteCount == 2 && blackCount == 2)
        {
            int whiteIndex = getBishopIndex(whites);
            int blackIndex = getBishopIndex(blacks);

            if (whiteIndex != -1 && blackIndex != -1)
            {
                Tuple<Integer, Integer> whiteCoords = whites.get(whiteIndex).getCoords(board);
                Tuple<Integer, Integer> blackCoords = blacks.get(whiteIndex).getCoords(board);
                int x = (whiteCoords.getFirst() + whiteCoords.getSecond()) % 2;
                int y = (blackCoords.getFirst() + blackCoords.getSecond()) % 2;

                if (x == y)
                {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean containsBishopOrKnight(ArrayList<Piece> pieces)
    {
        for (Piece piece : pieces)
        {
            if (piece.getType() == PieceType.BISHOP || piece.getType() == PieceType.KNIGHT)
            {
                return true;
            }
        }
        return false;
    }

    private int getBishopIndex(ArrayList<Piece> pieces)
    {
        for (int i = 0; i < pieces.size(); i++)
        {
            if (pieces.get(i).getType() == PieceType.BISHOP)
            {
                return i;
            }
        }
        return -1;
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

    public ArrayList<Tuple<Integer, Integer>> rankMovesBoard(Board board)
    {
        int last_turn = board.getLastTurnFromPieces();
        ArrayList<Piece> pieces;
        Piece king;
        //White's moved last turn
        if(last_turn % 2 == 0)
        {
            pieces = getEnemyPieces(PieceColor.WHITE, board);
            king = getKingFromColor(PieceColor.BLACK, board);
        }
        else
        {
            pieces = getEnemyPieces(PieceColor.BLACK, board);
            king = getKingFromColor(PieceColor.WHITE, board);
        }
        return simulateMoves(last_turn + 1, king, pieces, board);
    }

    private int evaluateMove(int turn, Board board, Piece piece2, Piece enemy_block_piece)
    {
        int score = 0;
        ArrayList<Piece> allies;
        ArrayList<Piece> enemies;
        if(piece2.getColor() == PieceColor.WHITE)
        {
            allies = getEnemyPieces(PieceColor.BLACK, board);
            enemies = getEnemyPieces(PieceColor.WHITE, board);
        }
        else
        {
            allies = getEnemyPieces(PieceColor.WHITE, board);
            enemies = getEnemyPieces(PieceColor.BLACK, board);
        }

        for(Piece ally: allies)
        {
            for(Piece enemy: enemies)
            {
                for(Tuple<Integer, Integer> mov: enemy.getAttackingMoves())
                {
                    if(ally.getCoords(board).equals(mov) && ally.equals(piece2) && enemy_block_piece != null)
                    {
                        int loss = enemy_block_piece.getType().getValue() - ally.getType().getValue();
                        if(loss < 0)
                            score -= 1000;
                    }
                    else if (ally.getCoords(board).equals(mov))
                        score -= ally.getType().getValue();
                }
            }
        }

        for(Piece enemy: enemies)
        {
            for(Piece ally: allies)
            {
                for(Tuple<Integer, Integer> mov: ally.getAttackingMoves())
                {
                    if (enemy.getCoords(board).equals(mov))
                        score += ally.getType().getValue();
                }
            }
        }

        int conditions = checkConditions(turn, board);
        if(conditions != 0 && conditions != 2)
            score += 50000000;
        return score;
    }
    private ArrayList<Tuple<Integer, Integer>> simulateMoves(int turn, Piece king, ArrayList<Piece> pieces, Board board)
    {
        ArrayList<Tuple<Integer, Integer>> evaluatedMoves = new ArrayList<>();
        int biggest_evaluation = -10000;
        Board simulation_board;
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
            for (Tuple<Integer, Integer> move : moves)
            {
                simulation_board = board.clone();
                Piece enemy_block_piece = board.getPieceFromCoords(move.getFirst(), move.getSecond());
                if (enemy_block_piece != null)
                {
                    enemy_block_piece = enemy_block_piece.clone();
                }
                Piece piece2 = findSamePiece(piece, simulation_board);
                this.move_chain.performMove(turn, piece2, move, simulation_board);
                this.firstMovesUpdate(simulation_board);
                removeStillEliminatingMoves(turn + 1, king, simulation_board.getAllPieces(), simulation_board);
                king = getKingFromColor(color, simulation_board);
                int evaluation = evaluateMove(turn, simulation_board, piece2, enemy_block_piece);

                if (biggest_evaluation < evaluation)
                {
                    evaluatedMoves.clear();
                    Tuple<Integer, Integer> origin = piece.getCoords(board);
                    evaluatedMoves.add(origin);
                    evaluatedMoves.add(move);
                    biggest_evaluation = evaluation;
                }
            }
        }
        return evaluatedMoves;
    }
}
