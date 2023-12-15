package Board;

import DataTypes.PieceColor;
import DataTypes.PieceType;
import DataTypes.Tuple;
import Pieces.*;
import Players.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Board
{
    final int x_limit = 7;
    final int y_limit = 7;
    public final int square_size = 80;
    public final int offset = 60;
    private ArrayList<Block> squares;
    private Player white_player;
    private Player black_player;
    private Piece active_piece = null;
    private ArrayList<Piece> pieces;

    public Piece getActive_piece()
    {
        return active_piece;
    }

    public void setActive_piece(Piece active_piece)
    {
        this.active_piece = active_piece;
    }

    public Board()
    {
        this.squares = new ArrayList<>();
        this.pieces = new ArrayList<>();
    }
    public void setPlayers(Player player1, Player player2)
    {
        setSquares();
        initialize_game(player1, player2);
    }

    private void setSquares()
    {
        for(int i = 0; i <= x_limit; i++)
        {
            for(int j = 0 ; j <= y_limit ; j++)
            {
                Color color;
                if((i + j) % 2 == 0)
                    color = new Color(210, 165, 125);
                else
                    color = new Color(175, 115, 70);
                squares.add(new Block(i, j, color));
            }
        }
    }

    public void removePiece(Piece piece)
    {
        for(Piece x: pieces)
        {
            if(x.equals(piece))
            {
                pieces.remove(x);
                return;
            }
        }
    }

    public ArrayList<Piece> getAllPieces()
    {
        return this.pieces;
    }

    public List<Block> getSquares()
    {
        return this.squares;
    }

    public Block getBlockFromCoords(int row, int col)
    {
        return this.squares.get(Block.getBoardIndexRowCol(row, col));
    }
    public Tuple<Integer, Integer> getTupleFromPiece(Piece piece)
    {
        for(Block block: this.squares)
        {
            if(block.getPiece() != null)
            {
                if(block.getPiece().equals(piece))
                    return new Tuple<>(block.getRow(), block.getCol());
            }
        }
        return new Tuple<>(-1, -1);
    }

    public Piece getPieceFromCoords(int row, int col)
    {
        return this.squares.get(Block.getBoardIndexRowCol(row, col)).getPiece();
    }

    private void initialize_game(Player player1, Player player2)
    {
        this.pieces.addAll(createWhitePieces());
        this.pieces.addAll(createBlackPieces());
        if(player1.getColor() == PieceColor.WHITE)
        {
            this.white_player = player1;
            this.black_player = player2;
        }
        else
        {
            this.white_player = player2;
            this.black_player = player1;
        }
    }

    private ArrayList<Piece> createBlackPieces()
    {
        ArrayList<Piece> pieces = new ArrayList<>();
        PieceColor color = PieceColor.BLACK;
        pieces.add(new Rook(color, PieceType.ROOK));
        pieces.add(new Knight(color, PieceType.KNIGHT));
        pieces.add(new Bishop(color, PieceType.BISHOP));
        pieces.add(new Queen(color, PieceType.QUEEN));
        pieces.add(new King(color, PieceType.KING));
        pieces.add(new Bishop(color, PieceType.BISHOP));
        pieces.add(new Knight(color, PieceType.KNIGHT));
        pieces.add(new Rook(color, PieceType.ROOK));
        for(int i = 0; i <= 7; i++)
            pieces.add(new Pawn(color, PieceType.PAWN));

        int i = 0;
        for(Piece piece: pieces)
        {
            this.squares.get(i).setPiece(piece);
            i++;
        }

        return pieces;
    }

    private ArrayList<Piece> createWhitePieces()
    {
        PieceColor color = PieceColor.WHITE;
        ArrayList<Piece> pieces = new ArrayList<>();
        for(int i = 0; i <= 7; i++)
            pieces.add(new Pawn(color, PieceType.PAWN));
        pieces.add(new Rook(color, PieceType.ROOK));
        pieces.add(new Knight(color, PieceType.KNIGHT));
        pieces.add(new Bishop(color, PieceType.BISHOP));
        pieces.add(new Queen(color, PieceType.QUEEN));
        pieces.add(new King(color, PieceType.KING));
        pieces.add(new Bishop(color, PieceType.BISHOP));
        pieces.add(new Knight(color, PieceType.KNIGHT));
        pieces.add(new Rook(color, PieceType.ROOK));

        int i = 48;
        for(Piece piece: pieces)
        {
            this.squares.get(i).setPiece(piece);
            i++;
        }
        return pieces;
    }

    public Player getWhite_player()
    {
        return white_player;
    }

    public void setWhite_player(Player white_player)
    {
        this.white_player = white_player;
    }

    public Player getBlack_player()
    {
        return black_player;
    }

    public void setBlack_player(Player black_player)
    {
        this.black_player = black_player;
    }
}
