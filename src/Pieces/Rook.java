package Pieces;

import DataTypes.PieceColor;
import DataTypes.PieceType;
import MoveStrategy.*;

import java.awt.image.BufferedImage;

public class Rook extends Piece
{
    public Rook(PieceColor color, PieceType type, int id)
    {
        super(color, type, id);
        this.addMoveStrategy(new OrthogonalMoveset());
    }
}
