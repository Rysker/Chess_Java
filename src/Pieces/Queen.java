package Pieces;

import DataTypes.PieceColor;
import DataTypes.PieceType;
import MoveStrategy.DiagonalMoveset;
import MoveStrategy.OrthogonalMoveset;

import java.awt.image.BufferedImage;

public class Queen extends Piece
{
    public Queen(PieceColor color, PieceType type)
    {
        super(color, type);
        this.addMoveStrategy(new DiagonalMoveset());
        this.addMoveStrategy(new OrthogonalMoveset());
    }
}
