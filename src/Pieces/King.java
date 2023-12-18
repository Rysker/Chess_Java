package Pieces;

import DataTypes.PieceColor;
import DataTypes.PieceType;
import MoveStrategy.CastlingMoveset;
import MoveStrategy.KingMoveset;

import java.awt.image.BufferedImage;

public class King extends Piece
{
    public King(PieceColor color, PieceType type, int id)
    {
        super(color, type, id);
        this.addMoveStrategy(new KingMoveset());
        this.addMoveStrategy(new CastlingMoveset());
    }
}
