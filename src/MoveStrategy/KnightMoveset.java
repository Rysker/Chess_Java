package MoveStrategy;

import DataTypes.Tuple;
import Board.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KnightMoveset extends MoveStrategy
{
    @Override
    public ArrayList<Tuple<Integer, Integer>> firstMoveCheck(int origin_row, int origin_col, Board board)
    {
        ArrayList tmp = new ArrayList<>
                (
                        Arrays.asList(
                                Tuple.of(origin_row + 2, origin_col + 1),
                                Tuple.of(origin_row + 2, origin_col - 1),
                                Tuple.of(origin_row - 2, origin_col + 1),
                                Tuple.of(origin_row - 2, origin_col - 1),
                                Tuple.of(origin_row + 1, origin_col + 2),
                                Tuple.of(origin_row + 1, origin_col - 2),
                                Tuple.of(origin_row - 1, origin_col + 2),
                                Tuple.of(origin_row - 1, origin_col - 2)
                        )
                );
        tmp = super.returnValidMoves(tmp);
        tmp = super.eliminateBlocked(origin_row, origin_col, tmp, board);
        return tmp;
    }
}
