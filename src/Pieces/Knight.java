package Pieces;

import DataTypes.*;
import MoveStrategy.*;

import java.awt.image.BufferedImage;

public class Knight extends Piece
{
    public Knight(PieceColor color, PieceType type)
    {
        super(color, type);
        this.addMoveStrategy(new KnightMoveset());
    }

}
