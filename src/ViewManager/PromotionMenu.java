package ViewManager;

import DataTypes.PieceColor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class PromotionMenu extends JFrame
{
    private static final String[] PIECE_OPTIONS = {"QUEEN", "BISHOP", "KNIGHT", "ROOK"};
    private String userChoice;

    public String invokeMenuAndWait(PieceColor color)
    {
        setTitle("Chess Promotion Menu");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);

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

        for (int i = 0; i < PIECE_OPTIONS.length; i++)
        {
            JButton button = createPieceButton(PIECE_OPTIONS[i], Color.WHITE);
            button.addActionListener(choiceListener);
            panel.add(button);
        }

        setVisible(true);

        try
        {
            semaphore.acquire();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        dispose();
        return userChoice;
    }

    private JButton createPieceButton(String pieceName, Color color)
    {
        JButton button = new JButton(new ImageIcon(getPieceImage(pieceName, color)));
        button.setToolTipText(pieceName);
        return button;
    }

    private Image getPieceImage(String pieceName, Color color)
    {
        BufferedImage image = null;
        String path = "/assets/" + "white" + "_" + pieceName.toLowerCase() + ".png";
        try
        {
            image = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }
        return image;
    }
}
