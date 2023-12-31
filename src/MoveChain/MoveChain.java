package MoveChain;

import Board.Board;
import DataTypes.Tuple;
import Pieces.Piece;
import SoundManager.SoundManager;

public abstract class MoveChain
{
    protected MoveChain nextMoveChain;
    public void setNext(MoveChain next)
    {
        this.nextMoveChain = next;
    }
    abstract public Tuple<Boolean, String> performMove(int turn, Piece piece, Tuple<Integer, Integer> ending, Board board);
}
