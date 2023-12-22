package ViewManager;

import Board.Board;
import DataTypes.PieceColor;

import javax.swing.*;
import java.awt.*;

public class ScoreViewer extends JFrame
{
    private Board board;
    private static final int SCORE_SQUARE_SIZE = 80;
    private static final int SCORE_OFFSET_X = 850;
    private static final int SCORE_OFFSET_Y = 50;


    public ScoreViewer(Board board)
    {
        this.board = board;
    }
    public void draw(Graphics2D g)
    {
        drawScore(g);
    }
    private void drawScore(Graphics2D g)
    {
        drawScoreSquare(g, "White", SCORE_OFFSET_X, SCORE_OFFSET_Y);
        drawScoreSquare(g, "Black", SCORE_OFFSET_X + 2 * SCORE_SQUARE_SIZE + 50, SCORE_OFFSET_Y);

        drawScoreText(g, Integer.toString(board.getPlayerScore(PieceColor.WHITE)), SCORE_OFFSET_X + 5, SCORE_OFFSET_Y + 10);
        drawScoreText(g, Integer.toString(board.getPlayerScore(PieceColor.BLACK)), SCORE_OFFSET_X + 2 * SCORE_SQUARE_SIZE + 55, SCORE_OFFSET_Y + 10);

        drawScoreText(g, getComparisonSymbol(), SCORE_OFFSET_X + SCORE_SQUARE_SIZE + 34, SCORE_OFFSET_Y + 8);
    }

    private void drawScoreSquare(Graphics2D g, String text, int x, int y)
    {
        g.setColor(Color.GRAY);
        g.fillRect(x, y, SCORE_SQUARE_SIZE, SCORE_SQUARE_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, SCORE_SQUARE_SIZE, SCORE_SQUARE_SIZE);
        g.setColor(Color.WHITE);
        g.drawString(text, x + SCORE_SQUARE_SIZE - 80, 30);
    }

    private void drawScoreText(Graphics2D g, String text, int x, int y)
    {
        g.setColor(Color.WHITE);
        g.drawString(text, x + SCORE_SQUARE_SIZE / 4, y + SCORE_SQUARE_SIZE / 2);
    }

    private String getComparisonSymbol()
    {
        int white_score = board.getPlayerScore(PieceColor.WHITE);
        int black_score = board.getPlayerScore(PieceColor.BLACK);
        if (white_score > black_score)
            return ">";
        else if (white_score < black_score)
            return "<";
        else
            return "=";
    }
}
