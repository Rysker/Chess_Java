package Pieces;

import DataTypes.*;
import MoveStrategy.*;
import Board.*;

import java.util.ArrayList;

public class Pawn extends Piece
{
    public Pawn(PieceColor color, PieceType type, int id)
    {
        super(color, type, id);
        this.addMoveStrategy(new PawnMoveset());
    }

    public ArrayList<Tuple<Integer, Integer>> getAttackedBlocks(Board board)
    {
        Tuple<Integer, Integer> coords = this.getCoords(board);
        return new PawnAttackMoveset().firstMoveCheck(coords.getFirst(), coords.getSecond(), board);
    }
}
