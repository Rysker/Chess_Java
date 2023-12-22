package ViewManager;

import Board.Board;

import java.awt.*;

public class OptionsViewer
{
    private ScoreViewer score_viewer;

    public OptionsViewer(Board board)
    {
        this.score_viewer = new ScoreViewer(board);
    }
    public void draw(Graphics2D g)
    {
        drawAll(g);
    }
    private void drawAll(Graphics2D g)
    {
        this.score_viewer.draw(g);
    }


}
