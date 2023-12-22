package Board;

import DataTypes.PieceColor;
import DataTypes.PieceType;
import DataTypes.Tuple;
import Pieces.*;
import Players.Player;

import java.awt.*;
import java.util.ArrayList;

public class Board implements Cloneable
{
    final int x_limit = 7;
    final int y_limit = 7;
    private ArrayList<Block> squares;
    private Player white_player;
    private Player black_player;
    private ArrayList<Piece> pieces;
    private Piece active_piece = null;
    private Piece white_king = null;
    private Piece black_king = null;
    private int turn;
    private int nextPieceID = 32;

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

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

    public Piece addPiece(PieceColor color, PieceType type, Block block)
    {
        Piece tmp;
        switch(type)
        {
            case QUEEN:
            {
                tmp = new Queen(color, type, nextPieceID);
                break;
            }
            case BISHOP:
            {
                tmp = new Bishop(color, type, nextPieceID);
                break;
            }
            case ROOK:
            {
                tmp = new Rook(color, type, nextPieceID);
                break;
            }
            case KNIGHT:  // Fixed the missing case label
            {
                tmp = new Knight(color, type, nextPieceID);
                break;
            }
            default:  // Handle the default case, which is a good practice
            {
                tmp = null;  // or throw an exception depending on your design
                break;
            }
        }
        this.pieces.add(tmp);
        block.setPiece(tmp);
        return tmp;
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

    public ArrayList<Block> getSquares()
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

    public boolean inEnemyMoves(PieceColor color, int origin_row, int origin_col)
    {
        ArrayList<Piece> enemies = new ArrayList<>();
        Piece piece = getPieceFromCoords(origin_row, origin_col);
        if(color == PieceColor.WHITE)
            enemies = getBlackPieces();
        else
            enemies = getWhitePieces();

        for(Piece piece1: enemies)
        {
            for(Tuple<Integer, Integer> moves: piece1.getAttackingMoves())
            {
                if(moves.getFirst() == origin_row && moves.getSecond() == origin_col)
                    return true;
            }
        }
        return false;
    }

    private ArrayList<Piece> getWhitePieces()
    {
        ArrayList<Piece> tmp = new ArrayList<>();
        for(Piece piece: this.pieces)
        {
            if(piece.getColor() == PieceColor.WHITE)
                tmp.add(piece);
        }
        return tmp;
    }

    private ArrayList<Piece> getBlackPieces()
    {
        ArrayList<Piece> tmp = new ArrayList<>();
        for(Piece piece: this.pieces)
        {
            if(piece.getColor() == PieceColor.BLACK)
                tmp.add(piece);
        }
        return tmp;
    }

    public int getLastTurnFromPieces()
    {
        int turn = 0;
        for(Piece piece: pieces)
        {
            if(turn < piece.getLastMoveTurn())
                turn = piece.getLastMoveTurn();
        }
        return turn;
    }

    public int getPlayerScore(PieceColor color)
    {
        int score = 0;
        for(Piece piece: pieces)
        {
            if(piece.getColor() == color)
                score += piece.getType().getValue();
        }
        return score - PieceType.KING.getValue();
    }

    public boolean kingChecked(PieceColor color)
    {
        Piece king;
        if(color == PieceColor.WHITE)
            king = getWhite_king();
        else
            king = getBlack_king();
        for(Piece piece: pieces)
        {
            if(piece.getColor() != color)
            {
                for(Tuple<Integer, Integer> move: piece.getAttackingMoves())
                if(king.inMoves(move.getFirst(), move.getSecond()))
                    return true;
            }
        }
        return false;
    }
}
