package ViewManager;

import Board.Block;
import Board.Board;
import DataTypes.Tuple;
import Pieces.*;
import Mouse.*;

import java.awt.*;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseMotionListener;

public class BoardViewer
{
    private LegendViewer legend;
    private PromotionMenu promotion;
    private Mouse mouse;
    private Board board;
    public final int piece_offset = 100;

    public BoardViewer(Mouse mouse, Board board)
    {
        this.legend = new LegendViewer();
        this.mouse = mouse;
        this.board = board;
    }
    public void draw(Graphics2D g)
    {
        drawChessboard(g);
        legend.drawLegend(g);
        this.drawPieces(g);
    }
    private void drawChessboard(Graphics2D g)
    {
        for(Block square: board.getSquares())
        {
            g.setColor(square.getColor());
            if(board.getActive_piece() != null)
                drawPossibleMoves(g, square);
            g.fillRect(square.getCol() * board.square_size + board.offset, square.getRow() * board.square_size + board.offset, board.square_size, board.square_size);
        }
    }

    private void drawPossibleMoves(Graphics2D g, Block square)
    {
        if(board.getActive_piece().inMoves(square.getRow(), square.getCol()))
            g.setColor(new Color(0x349D24));
    }

    private void drawPieces(Graphics2D g)
    {
        for(Piece piece: board.getAllPieces())
        {
            drawPiece(g, piece);
        }
    }

    private void drawPiece(Graphics2D g, Piece piece)
    {
        Tuple<Integer, Integer> coords = piece.getCoords(board);
        g.drawImage(piece.getImage(), coords.getSecond() * board.square_size + board.offset, coords.getFirst() * board.square_size + board.offset, board.square_size, board.square_size, null, null);
    }
}
