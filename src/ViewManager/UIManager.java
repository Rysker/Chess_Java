package ViewManager;

import javax.swing.*;
import java.awt.*;

import DataTypes.Tuple;
import Mouse.*;

public class UIManager extends JPanel implements Runnable
{
    public static final int WINDOW_WIDTH = 1300;
    public static final int WINDOW_HEIGHT = 750;
    BoardViewer boardmng;
    public UIManager(BoardViewer boardmng)
    {
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setBackground(Color.black);
        this.boardmng = boardmng;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g1 = (Graphics2D)g;
        boardmng.draw(g1);
    }

    @Override
    public void run()
    {

    }

    public void demandUpdate()
    {
        repaint();
    }


}
