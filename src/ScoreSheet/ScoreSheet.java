package ScoreSheet;

import DataTypes.PieceColor;
import DataTypes.PieceType;
import DataTypes.Tuple;
import Pieces.Piece;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;

public class ScoreSheet {
    private ArrayList<String> sheet;
    private static final Map<PieceType, String> pieces = new HashMap<>();
    private static final Map<Integer, String> columns = new HashMap<>();
    private static final Map<Integer, String> rows = new HashMap<>();

    static {
        pieces.put(PieceType.PAWN, "");
        pieces.put(PieceType.BISHOP, "B");
        pieces.put(PieceType.QUEEN, "Q");
        pieces.put(PieceType.ROOK, "R");
        pieces.put(PieceType.KNIGHT, "N");
        pieces.put(PieceType.KING, "K");

        columns.put(0, "a");
        columns.put(1, "b");
        columns.put(2, "c");
        columns.put(3, "d");
        columns.put(4, "e");
        columns.put(5, "f");
        columns.put(6, "g");
        columns.put(7, "h");

        rows.put(0, "8");
        rows.put(1, "7");
        rows.put(2, "6");
        rows.put(3, "5");
        rows.put(4, "4");
        rows.put(5, "3");
        rows.put(6, "2");
        rows.put(7, "1");
    }

    public ScoreSheet() {
        this.sheet = new ArrayList<>();
    }

    public void addMove(Tuple<Integer, Integer> origin, Tuple<Integer, Integer> ending, Piece piece, String option)
    {
        if(option == "Normal")
            addNormalMove(origin, ending, piece);
        else if(option == "Attacking")
            addAttackingMove(origin, ending, piece);
        else if(option == "EnPassant")
            addEnPassantMove(origin, ending, piece);
        else if(option == "Castling")
            addCastlingMove(origin, ending, piece);
        else if(option == "Promotion")
            addPromotion(origin, ending, piece);
        System.out.println(this.displayPGN());
    }

    private void addNormalMove(Tuple<Integer, Integer> origin, Tuple<Integer, Integer> ending, Piece piece)
    {
        StringBuilder builder = new StringBuilder();
        builder.append(pieces.get(piece.getType()) + columns.get(ending.getSecond()) + rows.get(ending.getFirst()));
        this.sheet.add(builder.toString());
    }

    public void addAttackingMove(Tuple<Integer, Integer> origin, Tuple<Integer, Integer> ending, Piece piece)
    {
        StringBuilder builder = new StringBuilder();
        if(piece.getType() == PieceType.PAWN)
            builder.append(columns.get(origin.getSecond()) + "x" + columns.get(ending.getSecond()) + rows.get(ending.getFirst()));
        else
            builder.append(pieces.get(piece.getType()) + "x" + columns.get(ending.getSecond()) + rows.get(ending.getFirst()));
        this.sheet.add(builder.toString());
    }
    public void addEnPassantMove(Tuple<Integer, Integer> origin, Tuple<Integer, Integer> ending, Piece piece)
    {
        StringBuilder builder = new StringBuilder();
        builder.append(columns.get(origin.getSecond()) + "x" + columns.get(ending.getSecond()) + rows.get(ending.getFirst()) + "e.p");
        this.sheet.add(builder.toString());
    }
    public void addCastlingMove(Tuple<Integer, Integer> origin, Tuple<Integer, Integer> ending, Piece piece)
    {
        StringBuilder builder = new StringBuilder();
        if(abs(origin.getSecond() - ending.getSecond()) == 2)
            builder.append("O-O");
        else
            builder.append("O-O-O");
        this.sheet.add(builder.toString());
    }

    public void addPromotion(Tuple<Integer, Integer> origin, Tuple<Integer, Integer> ending, Piece piece)
    {
        StringBuilder builder = new StringBuilder();
        String tmp = this.sheet.get(this.sheet.size() - 1);
        builder.append(tmp);
        builder.append(piece.getType());
        this.sheet.remove(this.sheet.size());
        this.sheet.add(builder.toString());
    }

    public void addChecked()
    {
        StringBuilder builder = new StringBuilder();
        String tmp = this.sheet.get(this.sheet.size() - 1);
        builder.append(tmp);
        builder.append("+");
        this.sheet.remove(this.sheet.size());
        this.sheet.add(builder.toString());
    }

    private String displayPGN()
    {
        StringBuilder pgn = new StringBuilder("");
        int count_moves = 1;
        int count_lines = 1;
        for(String move : this.sheet)
        {
            if(count_moves % 2 == 1)
            {
                pgn.append(count_lines + ". ");

            }
            pgn.append(move + " ");
            if(count_moves % 2 == 0)
            {
                pgn.append("\n");
                count_lines += 1;
            }
            count_moves += 1;
        }

        pgn.append("\n");
        return pgn.toString();
    }

    public void saveSheet(Path filePath)
    {
        if (!filePath.toString().toLowerCase().endsWith(".txt"))
        {
            filePath = Paths.get(filePath + ".txt");
        }

        try {
            Files.write(filePath, displayPGN().getBytes());
            System.out.println("Game score sheet has been saved in a file named " + filePath.getFileName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addWin(PieceColor color)
    {
        StringBuilder builder = new StringBuilder();
        if(color == PieceColor.WHITE)
            builder.append("1-0");
        else
            builder.append("0-1");
        this.sheet.add(builder.toString());
    }

    public void addDraw()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("1/2 - 1/2");
        this.sheet.add(builder.toString());
    }
}
