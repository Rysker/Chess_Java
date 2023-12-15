package MoveChain;

import Board.Board;
import DataTypes.Tuple;
import Pieces.Piece;

public abstract class MoveChain
{
    protected MoveChain nextMoveChain;
    public void setNext(MoveChain next)
    {
        this.nextMoveChain = next;
    }
    abstract public boolean performMove(Piece piece, Tuple<Integer, Integer> ending, Board board);
}
