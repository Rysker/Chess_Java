package Board;

import DataTypes.PieceColor;
import DataTypes.PieceType;
import DataTypes.Tuple;
import Pieces.*;
import Players.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Board implements Cloneable
{
    final int x_limit = 7;
    final int y_limit = 7;
    public final int square_size = 80;
    public final int offset = 60;
    private ArrayList<Block> squares;
    private Player white_player;
    private Player black_player;
    private ArrayList<Piece> pieces;
    private Piece active_piece = null;
    private Piece white_king = null;
    private Piece black_king = null;
    private int nextPieceID = 32;

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
        pieces.add(new Rook(color, PieceType.ROOK, 0));
        pieces.add(new Knight(color, PieceType.KNIGHT, 1));
        pieces.add(new Bishop(color, PieceType.BISHOP, 2));
        pieces.add(new Queen(color, PieceType.QUEEN, 3));
        this.black_king = new King(color, PieceType.KING,4 );
        pieces.add(black_king);
        pieces.add(new Bishop(color, PieceType.BISHOP, 5));
        pieces.add(new Knight(color, PieceType.KNIGHT, 6));
        pieces.add(new Rook(color, PieceType.ROOK,7));
        for(int i = 0; i <= 7; i++)
            pieces.add(new Pawn(color, PieceType.PAWN, 8 + i));

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
            pieces.add(new Pawn(color, PieceType.PAWN, 16 + i));
        pieces.add(new Rook(color, PieceType.ROOK, 24));
        pieces.add(new Knight(color, PieceType.KNIGHT, 25));
        pieces.add(new Bishop(color, PieceType.BISHOP, 26));
        pieces.add(new Queen(color, PieceType.QUEEN, 27));
        this.white_king = new King(color, PieceType.KING, 28);
        pieces.add(this.white_king);
        pieces.add(new Bishop(color, PieceType.BISHOP, 29));
        pieces.add(new Knight(color, PieceType.KNIGHT, 30));
        pieces.add(new Rook(color, PieceType.ROOK, 31));

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
    public Player getBlack_player()
    {
        return black_player;
    }

    public Piece getWhite_king()
    {
        return white_king;
    }

    public Piece getBlack_king()
    {
        return black_king;
    }

    @Override
    public Board clone() {
        try
        {
            Board clonedBoard = (Board) super.clone();

            clonedBoard.squares = new ArrayList<>();
            for (Block originalBlock : this.squares)
            {
                clonedBoard.squares.add(originalBlock.clone());
            }

            clonedBoard.pieces = new ArrayList<>();
            for (Block originalBlock : clonedBoard.squares)
            {
                if (originalBlock.getPiece() != null)
                    clonedBoard.pieces.add(originalBlock.getPiece());
            }

            for(Block originalBlock : clonedBoard.squares)
            {
                if (originalBlock.getPiece() != null)
                {
                    if(originalBlock.getPiece().getType() == PieceType.KING && originalBlock.getPiece().getColor() == PieceColor.WHITE)
                        clonedBoard.white_king = originalBlock.getPiece();
                    if(originalBlock.getPiece().getType() == PieceType.KING && originalBlock.getPiece().getColor() == PieceColor.BLACK)
                        clonedBoard.black_king = originalBlock.getPiece();
                }
            }
            clonedBoard.white_player = this.white_player;
            clonedBoard.black_player = this.black_player;
            clonedBoard.active_piece = null;

            return clonedBoard;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }
}
