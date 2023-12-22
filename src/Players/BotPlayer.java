package Players;

import Board.Board;
import DataTypes.PieceColor;
import DataTypes.Tuple;
import Game.LogicManager;
import Mouse.Mouse;

import java.util.ArrayList;
public class BotPlayer extends Player
{
    public BotPlayer(PieceColor color, Mouse mouse)
    {
        super(color);

    }
    @Override
    public ArrayList<Tuple<Integer, Integer>> getAction(Board board)
    {
        ArrayList<Tuple<Integer, Integer>> evaluated;
        LogicManager logic = LogicManager.getInstance();
        evaluated = logic.rankMovesBoard(board);
        return evaluated;
    }
}
