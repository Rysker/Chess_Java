package Game;

import Board.*;
import DataTypes.*;
import MoveChain.*;
import Pieces.Piece;
import Players.*;
import ScoreSheet.ScoreSheet;
import SoundManager.SoundManager;
import ViewManager.UIManager;
import Mouse.*;

import javax.swing.*;

import java.util.ArrayList;

public class GameManager
{
    private Mouse mouse;
    private int turn = 0;
    private LogicManager logic;
    private Player activePlayer;
    private UIManager uiManager;
    private ScoreSheet sheet;
    private Board board;
    private JFrame window;
    private int last_move_turn = -1;
    public GameManager()
    {
        this.board = new Board();
        this.mouse = new Mouse();
        this.sheet = new ScoreSheet();
        this.board.setPlayers(new HumanPlayer(PieceColor.WHITE, mouse), new HumanPlayer(PieceColor.BLACK, mouse));
        this.logic = LogicManager.getInstance();
        this.uiManager = new UIManager(this.board);
        uiManager.addMouseMotionListener(this.mouse);
        uiManager.addMouseListener(this.mouse);
    }

    public static void main(String[] args)
    {

        JFrame window = new JFrame("Chess Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GameManager game = new GameManager();
        game.setWindow(window);

        window.add(game.uiManager);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        while(true)
        {
            game.gameTurn();
        }
    }

    private void gameTurn()
    {
        window.repaint();
        uiManager.demandUpdate();
        SoundManager.getInstance().disable();
        logic.finalLogic(this.turn, this.board);
        isChecked();
        handleConditions();
        changeActivePlayer();
        while(last_move_turn != turn)
        {
            SoundManager.getInstance().enable();
            handleMoveFromPlayer();
            SoundManager.getInstance().disable();
        }
        turn ++;
        board.setTurn(turn);
        SoundManager.getInstance().enable();
        checkPromotion();
    }

    private void handleMoveFromPlayer()
    {
        window.repaint();
        uiManager.demandUpdate();
        //Human player
        ArrayList<Tuple<Integer, Integer>> moves = this.activePlayer.getAction(board);
        if(moves.size() == 1)
        {
            Tuple<Integer, Integer> move = moves.get(0);
            changeBoard(move.getFirst(), move.getSecond());
        }
        //AI Player
        else
        {
            Tuple<Integer, Integer> moveFrom = moves.get(0);
            Tuple<Integer, Integer> moveTo = moves.get(1);
            Piece piece = board.getPieceFromCoords(moveFrom.getFirst(), moveFrom.getSecond());
            Tuple<Boolean, String> move = new CastlingMove().performMove(turn, board.getActive_piece(), moveTo, board);
            if(move.getFirst())
            {
                this.sheet.addMove(board.getActive_piece().getCoords(board), moveTo, piece, move.getSecond());
                this.last_move_turn++;
            }
        }
        window.repaint();
        uiManager.demandUpdate();
    }

    private boolean changeBoard(int x, int y)
    {
        int row = ((y - 60) / 80);
        int col = ((x - 60) / 80);
        if((0 <= row && row <= 7) && (0 <= col && col <= 7))
        {
            Piece piece = board.getPieceFromCoords(row, col);
            if(board.getActive_piece() == null && piece != null)
            {
                if (piece.getColor().equals(this.activePlayer.getColor()))
                {
                    board.setActive_piece(piece);
                    return true;
                }
                return false;
            }

            if(board.getActive_piece() != null)
            {
                Tuple<Integer, Integer> origin = board.getActive_piece().getCoords(board);
                Tuple<Boolean, String> move = new CastlingMove().performMove(turn, board.getActive_piece(), new Tuple<>(row, col), board);
                if(move.getFirst())
                {
                    this.sheet.addMove(origin, new Tuple<>(row, col), board.getActive_piece(), move.getSecond());
                    this.last_move_turn++;
                }
                board.setActive_piece(null);
                return true;
            }
        }
        return false;
    }
    private void changeActivePlayer()
    {
        if(this.turn % 2 == 0)
            this.activePlayer = board.getWhite_player();
        else
            this.activePlayer = board.getBlack_player();
    }
    public void setWindow(JFrame window)
    {
        this.window = window;
    }

    private void isChecked()
    {
        if(this.turn % 2 == 0)
        {
            if(board.kingChecked(PieceColor.WHITE))
                this.sheet.addChecked();
        }
        else
        {
            if(board.kingChecked(PieceColor.BLACK))
                this.sheet.addChecked();
        }
    }


    private void checkPromotion()
    {
        Piece piece = this.logic.checkPromotion(board);
        if(this.logic.checkPromotion(board) != null)
        {
            String choice;
            if(piece.getColor() == PieceColor.WHITE)
                choice = this.uiManager.getPromotionChoice(PieceColor.WHITE);
            else
                choice = this.uiManager.getPromotionChoice(PieceColor.BLACK);

            Tuple<Integer, Integer> coords = piece.getCoords(board);
            this.logic.performPromotion(piece, choice, board);
            piece = board.getBlockFromCoords(coords.getFirst(), coords.getSecond()).getPiece();
            this.sheet.addMove(null, null, piece, "Promotion");
        }
    }

    private void handleConditions()
    {
        int answer = logic.checkConditions(turn, board);
        PieceColor winner = null;
        if(answer != 0)
        {
            if(answer == 1)
            {
                System.out.println("Black player won!");
                this.sheet.addWin(PieceColor.BLACK);
                winner = PieceColor.BLACK;
            }

            if(answer == 2)
            {
                System.out.println("Draw");
                this.sheet.addDraw();
            }

            if(answer == 3)
            {
                System.out.println("White player won!");
                this.sheet.addWin(PieceColor.WHITE);
                winner = PieceColor.WHITE;
            }

            this.uiManager.getEndingWindow(sheet, winner);
        }
    }

}
