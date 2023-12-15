package Pieces;

import DataTypes.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import Board.*;

import MoveStrategy.*;

import javax.imageio.ImageIO;

public abstract class Piece {
    private BufferedImage image;
    private PieceColor color;
    private PieceType type;
    private int movesCount;
    private List<Tuple<Integer, Integer>> moves;
    private List<MoveStrategy> availableStrategies;
    public List<Tuple<Integer, Integer>> getMoves() {
        return moves;
    }

    public void setMoves(List<Tuple<Integer, Integer>> moves) {
        this.moves = moves;
    }

    public int getMovesCount() {
        return movesCount;
    }

    public void setMovesCount(int movesCount) {
        this.movesCount = movesCount;
    }

    public Piece(PieceColor color, PieceType type)
    {
        this.color = color;
        this.type = type;
        this.moves = new ArrayList<>();
        this.movesCount = 0;
        this.availableStrategies = new ArrayList<>();
        this.image = prepareImage();
    }

    public void addMoveStrategy(MoveStrategy strategy) {
        availableStrategies.add(strategy);
    }

    public void removeMoveStrategies() {
        availableStrategies.clear();
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

    public void setColor(PieceColor color) {
        this.color = color;
    }

    public PieceType getType() {
        return type;
    }

    public void setType(PieceType type) {
        this.type = type;
    }

    public void getAttackingMoves(Board board)
    {
        if (this instanceof Pawn)
        {
            ArrayList<Tuple<Integer, Integer>> tmp = ((Pawn) this).getAttackedBlocks(board);
            moves.addAll(tmp);
            return;
        }
        for (MoveStrategy strategy : availableStrategies)
        {
            Tuple<Integer, Integer> coords = board.getTupleFromPiece(this);
            ArrayList<Tuple<Integer, Integer>> strategyMoves = strategy.firstMoveCheck(coords.getFirst(), coords.getSecond(), board);
            moves.addAll(strategyMoves);
        }
        Set<Tuple<Integer, Integer>> set = new HashSet<>(this.moves);
        this.moves.clear();
        this.moves.addAll(set);
    }

    public PieceColor getColor() {
        return color;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    private BufferedImage prepareImage() {
        BufferedImage image = null;
        String path = "/assets/" + getColor().toString().toLowerCase() + "_" + getType().toString().toLowerCase() + ".png";
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
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

    public Tuple<Integer, Integer> getCoords(Board board)
    {
        Tuple<Integer, Integer> coords = board.getTupleFromPiece(this);
        return coords;
    }
}
