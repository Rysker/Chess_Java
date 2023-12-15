package Game;

import Board.*;
import Pieces.Piece;

import java.util.ArrayList;

public class LogicManager
{
    private static LogicManager instance = null;

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
            piece.getPossibleMoves(board);
    }



    public void finalLogic(int turn, Board board)
    {
        System.out.println("Logic performed");
        firstMovesUpdate(board);
    }
}
