package Game;

import Board.*;
import DataTypes.*;
import MoveChain.*;
import Pieces.Piece;
import Players.HumanPlayer;
import Players.Player;
import ViewManager.BoardViewer;
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
    private Board board;
    private JFrame window;
    private int last_move_turn = -1;
    public GameManager()
    {
        this.board = new Board();
        this.mouse = new Mouse();
        this.board.setPlayers(new HumanPlayer(PieceColor.WHITE, mouse), new HumanPlayer(PieceColor.BLACK, mouse));
        this.logic = LogicManager.getInstance();
        this.uiManager = new UIManager(new BoardViewer(this.mouse, this.board));
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
        logic.finalLogic(this.turn, this.board);
        changeActivePlayer();
        while(last_move_turn != turn)
        {
            handleMoveFromPlayer();
        }
        turn ++;
    }

    private void handleMoveFromPlayer()
    {
        //Human player
        ArrayList<Tuple<Integer, Integer>> moves = this.activePlayer.getAction();
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
            if(new NormalMove().performMove(piece, moveTo, board))
            {
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
                if(new NormalMove().performMove(board.getActive_piece(), new Tuple<>(row, col), board))
                {
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
}
