package DataTypes;

public enum PieceColor
{
    WHITE,
    BLACK;

    public PieceColor getOpposite()
    {
        return switch(this) {
            case WHITE -> BLACK;
            case BLACK -> WHITE;
        };
    }
}
