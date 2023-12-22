package Players;

import DataTypes.Tuple;
import Board.*;
import DataTypes.PieceColor;

import java.util.ArrayList;


public abstract class Player
{
    private PieceColor color;

    public Player(PieceColor color)
    {
        this.color = color;
    }

    abstract public ArrayList<Tuple<Integer, Integer>> getAction(Board board);

    public PieceColor getColor()
    {
        return color;
    }

    public void setColor(PieceColor color)
    {
        this.color = color;
    }
}


