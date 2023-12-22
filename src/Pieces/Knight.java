package Pieces;

import DataTypes.*;
import MoveStrategy.*;

public class Knight extends Piece
{
    public Knight(PieceColor color, PieceType type, int id)
    {
        super(color, type, id);
        this.addMoveStrategy(new KnightMoveset());
    }

}
