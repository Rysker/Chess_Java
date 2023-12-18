package Pieces;

import DataTypes.PieceColor;
import DataTypes.PieceType;
import MoveStrategy.DiagonalMoveset;

import java.awt.image.BufferedImage;

public class Bishop extends Piece
{
    public Bishop(PieceColor color, PieceType type, int id)
    {
        super(color, type, id);
        this.addMoveStrategy(new DiagonalMoveset());
    }
}
