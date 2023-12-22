package DataTypes;

public enum PieceType
{
    ROOK,
    PAWN,
    KNIGHT,
    KING,
    QUEEN,
    BISHOP;

    public int getValue()
    {
        return switch(this) {
            case PAWN -> 1;
            case KNIGHT, BISHOP -> 3;
            case ROOK -> 5;
            case QUEEN -> 9;
            case KING -> 1000000;
        };
    }
}
