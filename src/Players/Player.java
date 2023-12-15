package Players;

import DataTypes.PieceType;
import DataTypes.Tuple;
import Pieces.*;
import DataTypes.PieceColor;

import java.util.ArrayList;
import java.util.List;

public abstract class Player
{
    private PieceColor color;
    private ArrayList<Tuple<Integer, Integer>> possibleMoves;
    private ArrayList<Tuple<Integer, Integer>> attackingMoves;

    public Player(PieceColor color)
    {
        this.color = color;
        this.possibleMoves = new ArrayList<Tuple<Integer, Integer>>();
        this.attackingMoves = new ArrayList<Tuple<Integer, Integer>>();
    }

    abstract public ArrayList<Tuple<Integer, Integer>> getAction();

    public PieceColor getColor()
    {
        return color;
    }

    public void setColor(PieceColor color)
    {
        this.color = color;
    }
}


