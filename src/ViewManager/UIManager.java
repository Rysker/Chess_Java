package ViewManager;

import DataTypes.PieceColor;

import javax.swing.*;
import java.awt.*;

public class UIManager extends JPanel implements Runnable
{
    public static final int WINDOW_WIDTH = 1300;
    public static final int WINDOW_HEIGHT = 750;
    BoardViewer boardmng;
    PromotionMenu promotion;
    public UIManager(BoardViewer boardmng)
    {
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setBackground(Color.black);
        this.boardmng = boardmng;
        this.promotion = new PromotionMenu();
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

    public String getPromotionChoice(PieceColor color)
    {
        return this.promotion.invokeMenuAndWait(color);
    }

}
