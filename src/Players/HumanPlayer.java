package Players;

import DataTypes.PieceColor;
import DataTypes.Tuple;
import Board.*;

import java.util.ArrayList;
import Mouse.*;

public class HumanPlayer extends Player
{
    private Mouse mouse;
    public HumanPlayer(PieceColor color, Mouse mouse)
    {
        super(color);
        this.mouse = mouse;
    }

    @Override
    public ArrayList<Tuple<Integer, Integer>> getAction(Board board)
    {
        ArrayList<Tuple<Integer, Integer>> tmp = new ArrayList<>();
        mouse.enable();
        mouse.enable();

        while (!mouse.pressed)
        {
            try
            {
                Thread.sleep(10);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        tmp.add(new Tuple<>(mouse.x, mouse.y));
        mouse.pressed = false;
        return tmp;
    }
}
