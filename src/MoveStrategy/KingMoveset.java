package MoveStrategy;

import Board.Board;
import DataTypes.Tuple;

import java.util.ArrayList;
import java.util.Arrays;

public class KingMoveset extends MoveStrategy
{

    @Override
    public ArrayList<Tuple<Integer, Integer>> firstMoveCheck(int origin_row, int origin_col, Board board)
    {

        ArrayList tmp = new ArrayList<>
                (
                        Arrays.asList(
                                Tuple.of(origin_row + 1, origin_col - 1),
                                Tuple.of(origin_row + 1, origin_col),
                                Tuple.of(origin_row + 1, origin_col + 1),
                                Tuple.of(origin_row - 1, origin_col + 1),
                                Tuple.of(origin_row - 1, origin_col),
                                Tuple.of(origin_row - 1, origin_col - 1),
                                Tuple.of(origin_row, origin_col - 1),
                                Tuple.of(origin_row, origin_col + 1)
                        )
                );
        tmp = super.returnValidMoves(tmp);
        tmp = super.eliminateBlocked(origin_row, origin_col, tmp, board);
        return tmp;
    }
}
