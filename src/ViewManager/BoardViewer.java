package ViewManager;

import Board.Block;
import Board.Board;
import DataTypes.Tuple;
import Pieces.*;
import Mouse.*;

import java.awt.*;

public class BoardViewer
{
    private LegendViewer legend;
    public final int square_size = 80;
    public final int offset = 60;
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
            g.fillRect(square.getCol() * square_size + offset, square.getRow() * square_size + offset, square_size, square_size);
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
        g.drawImage(piece.getImage(), coords.getSecond() * square_size + offset, coords.getFirst() * square_size + offset, square_size, square_size, null, null);
    }
}
