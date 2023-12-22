package ViewManager;

import DataTypes.PieceColor;

import javax.swing.*;
import java.awt.*;
import Board.*;
import Mouse.*;
import ScoreSheet.ScoreSheet;

public class UIManager extends JPanel
{
    public static final int WINDOW_WIDTH = 1300;
    public static final int WINDOW_HEIGHT = 750;
    BoardViewer boardmng;
    OptionsViewer optionsmng;
    GameEndingMenu endingmenu;
    PromotionMenu promotion;
    public UIManager(Mouse mouse, Board board)
    {
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setBackground(Color.black);

        this.boardmng = new BoardViewer(mouse, board);
        this.promotion = new PromotionMenu();
        this.optionsmng = new OptionsViewer(board);
        this.endingmenu = new GameEndingMenu();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g1 = (Graphics2D)g;
        boardmng.draw(g1);
        optionsmng.draw(g1);
    }
    public void demandUpdate()
    {
        repaint();
    }

    public String getPromotionChoice(PieceColor color)
    {
        return this.promotion.invokeMenuAndWait(color);
    }

    public void getEndingWindow(ScoreSheet sheet)
    {
        this.endingmenu.invokeMenuAndWait(sheet);
    }
}
