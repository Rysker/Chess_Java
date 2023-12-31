package Board;

import Pieces.Piece;

import java.awt.*;
import java.util.Objects;

public class Block implements Cloneable
{
    private final int row;
    private final int col;
    private Color color;
    private Piece piece;

    public Block(int row, int col, Color color)
    {
        this.row = row;
        this.col = col;
        this.color = color;
        this.piece = null;
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public Color getColor()
    {
        return this.color;
    }

    public void setPiece(Piece piece)
    {
        this.piece = piece;
    }

    public Piece getPiece()
    {
        return piece;
    }

    public void removePiece()
    {
        this.piece = null;
    }

    public static int getBoardIndexRowCol(int row, int col)
    {
        return col + row * 8;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block tile = (Block) o;
        return this.col == tile.col && this.row == tile.row;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(row, col);
    }

    @Override
    public Block clone()
    {
        try
        {
            Block clone = (Block) super.clone();
            clone.color = new Color(this.color.getRGB());
            if(this.piece != null)
                clone.piece = piece.clone();
            else
                clone.piece = null;
            return clone;
        }
        catch (CloneNotSupportedException e)
        {
            throw new InternalError(e);
        }
    }
}
