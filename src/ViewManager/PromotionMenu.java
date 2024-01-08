package ViewManager;

import DataTypes.PieceColor;
import Pieces.Queen;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class PromotionMenu extends JFrame
{
    private static final String[] PIECE_OPTIONS = {"QUEEN", "BISHOP", "KNIGHT", "ROOK"};
    private String userChoice = "QUEEN";

    public String invokeMenuAndWait(PieceColor color)
    {
        Frame frame = new JFrame("Promotion Menu");
        frame.setSize(600, 150);

        JPanel panel = new JPanel(new GridLayout(1, PIECE_OPTIONS.length));
        frame.add(panel);

        Semaphore semaphore = new Semaphore(0);
        ActionListener choiceListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                userChoice = ((JButton) e.getSource()).getToolTipText();
                semaphore.release();
            }
        };

        WindowListener windowListener = new WindowListener()
        {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosed(WindowEvent e) {}

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e)
            {
                semaphore.release();
            }
        };
        frame.addWindowListener(windowListener);

        Color color_tmp;
        if (color == PieceColor.WHITE)
            color_tmp = Color.WHITE;
        else
            color_tmp = Color.BLACK;

        for (String pieceOption : PIECE_OPTIONS)
        {
            JButton button = createPieceButton(pieceOption, color_tmp);
            button.addActionListener(choiceListener);
            panel.add(button);
        }

        frame.setVisible(true);

        try
        {
            semaphore.acquire();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        frame.dispose();
        return userChoice;
    }

    private JButton createPieceButton(String pieceName, Color color)
    {
        JButton button = new JButton(new ImageIcon(getPieceImage(pieceName, color)));
        button.setToolTipText(pieceName);
        Dimension buttonSize = new Dimension(150, 150);
        button.setPreferredSize(buttonSize);
        return button;
    }

    private Image getPieceImage(String pieceName, Color color)
    {
        String color_tmp = (color == Color.WHITE) ? "white" : "black";
        BufferedImage image = null;
        String path = "/Assets/Pieces/Default/" + color_tmp + "_" + pieceName.toLowerCase() + ".png";
        try
        {
            image = ImageIO.read(getClass().getResourceAsStream(path));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(2);
        }

        int width = 120;
        int height = 120;
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}
