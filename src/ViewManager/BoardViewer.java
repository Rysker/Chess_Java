package ViewManager;

import Board.Block;
import Board.Board;
import DataTypes.PieceColor;
import DataTypes.Tuple;
import Pieces.*;

import java.awt.*;

public class BoardViewer
{
    private LegendViewer legend;
    public final int square_size = 80;
    public final int offset = 60;
    private Board board;

    public BoardViewer(Board board)
    {
        this.legend = new LegendViewer();
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
            {
                drawPossibleMoves(g, square);
                if (square.getPiece() == board.getActive_piece())
                    g.setColor(new Color(0x1333B9));
            }
            g.fillRect(square.getCol() * square_size + offset, square.getRow() * square_size + offset, square_size, square_size);
        }
        drawChecked(g);
    }

    private void drawPossibleMoves(Graphics2D g, Block square)
    {
        if(board.getActive_piece().inMoves(square.getRow(), square.getCol()))
        {
            g.setColor(new Color(0x349D24));
            if(square.getPiece() != null && square.getPiece().getColor() != board.getActive_piece().getColor())
                g.setColor(new Color(0xC50633));
        }
    }

    private void drawChecked(Graphics2D g)
    {
        Piece king = null;
        if(board.kingChecked(PieceColor.WHITE))
            king = board.getWhite_king();
        else if(board.kingChecked(PieceColor.BLACK))
            king = board.getBlack_king();

        if(king != null)
        {
            Tuple<Integer, Integer> coords = king.getCoords(board);
            Block square = board.getBlockFromCoords(coords.getFirst(), coords.getSecond());
            g.setColor(new Color(0xEADF12));
            g.fillRect(square.getCol() * square_size + offset, square.getRow() * square_size + offset, square_size, square_size);
        }
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
