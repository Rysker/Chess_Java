package Pieces;

import DataTypes.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import Board.*;

import MoveStrategy.*;

import javax.imageio.ImageIO;

public abstract class Piece implements Cloneable
{
    private BufferedImage image;
    private int id;
    private PieceColor color;
    private PieceType type;
    private int lastMoveTurn = -1;
    private int double_turn = -1;
    private ArrayList<Tuple<Integer, Integer>> moves;
    private ArrayList<Tuple<Integer, Integer>> attackingMoves;
    private ArrayList<MoveStrategy> availableStrategies;
    public Piece(PieceColor color, PieceType type, int id)
    {
        this.id = id;
        this.color = color;
        this.type = type;
        this.moves = new ArrayList<>();
        this.attackingMoves = new ArrayList<>();
        this.availableStrategies = new ArrayList<>();
        this.image = prepareImage();
    }

    public int getDouble_turn()
    {
        return double_turn;
    }

    public void setDouble_turn(int double_turn)
    {
        this.double_turn = double_turn;
    }
    public void addMoveStrategy(MoveStrategy strategy)
    {
        availableStrategies.add(strategy);
    }

    public void getPossibleMoves(Board board)
    {
        this.moves.clear();
        Tuple<Integer, Integer> coords = this.getCoords(board);
        for (MoveStrategy strategy : availableStrategies)
        {
            List<Tuple<Integer, Integer>> strategyMoves = strategy.firstMoveCheck(coords.getFirst(), coords.getSecond(), board);
            moves.addAll(strategyMoves);
        }
        Set<Tuple<Integer, Integer>> set = new HashSet<>(this.moves);
        this.moves.clear();
        this.moves.addAll(set);
    }

    public void removeMove(Tuple<Integer, Integer> move)
    {
        this.moves.removeIf(x -> x.equals(move));
        this.attackingMoves.removeIf(x -> x.equals(move));
    }

    public void setColor(PieceColor color)
    {
        this.color = color;
    }

    public PieceType getType()
    {
        return type;
    }

    public void getAttackingMoves(Board board)
    {
        this.attackingMoves.clear();
        if (this instanceof Pawn)
        {
            ArrayList<Tuple<Integer, Integer>> tmp = ((Pawn) this).getAttackedBlocks(board);
            attackingMoves.addAll(tmp);
            moves.addAll(tmp);
        }
        for (MoveStrategy strategy : availableStrategies)
        {
            Tuple<Integer, Integer> coords = board.getTupleFromPiece(this);
            ArrayList<Tuple<Integer, Integer>> strategyMoves = strategy.firstMoveCheck(coords.getFirst(), coords.getSecond(), board);
            this.attackingMoves.addAll(strategyMoves);
        }
        Set<Tuple<Integer, Integer>> set = new HashSet<>(this.attackingMoves);
        Set<Tuple<Integer, Integer>> set2 = new HashSet<>(this.moves);

        this.attackingMoves.clear();
        this.attackingMoves.addAll(set);

        this.moves.clear();
        this.moves.addAll(set2);
    }

    public PieceColor getColor()
    {
        return color;
    }

    public BufferedImage getImage()
    {
        return image;
    }

    private BufferedImage prepareImage()
    {
        BufferedImage image = null;
        String path = "/Assets/Pieces/Default/" + getColor().toString().toLowerCase() + "_" + getType().toString().toLowerCase() + ".png";
        try
        {
            image = ImageIO.read(getClass().getResourceAsStream(path));
        }
        catch (IOException e)
        {
            System.exit(2);
        }
        return image;
    }

    public boolean inMoves(int row, int col)
    {
        if (this.moves == null)
            return false;
        for (Tuple<Integer, Integer> coords : this.moves)
        {
            if (coords.getFirst() == row && coords.getSecond() == col)
                return true;
        }
        return false;
    }

    public boolean inAttackingMoves(int row, int col)
    {
        if (this.moves == null)
            return false;
        for (Tuple<Integer, Integer> coords : this.attackingMoves)
        {
            if (coords.getFirst() == row && coords.getSecond() == col)
                return true;
        }
        return false;
    }

    public Tuple<Integer, Integer> getCoords(Board board)
    {
        return board.getTupleFromPiece(this);
    }

    public ArrayList<Tuple<Integer, Integer>> getAttackingMoves()
    {
        return attackingMoves;
    }

    public ArrayList<Tuple<Integer, Integer>> getPossibleMoves()
    {
        return moves;
    }

    public int getLastMoveTurn()
    {
        return lastMoveTurn;
    }

    public void setLastMoveTurn(int lastMoveTurn)
    {
        this.lastMoveTurn = lastMoveTurn;
    }

    public int getId()
    {
        return id;
    }

    @Override
    public Piece clone()
    {
        try
        {
            Piece clone = (Piece) super.clone();
            clone.moves = new ArrayList<>(this.moves);
            clone.attackingMoves = new ArrayList<>(this.attackingMoves);
            clone.availableStrategies = new ArrayList<>(this.availableStrategies);
            clone.id = this.id;
            clone.image = this.image;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

}
