package ViewManager;

import DataTypes.PieceColor;
import ScoreSheet.ScoreSheet;

import javax.swing.*;
import java.util.Scanner;
import java.io.File;

import static java.lang.System.exit;

public class GameEndingMenu extends JFrame
{
    public void invokeMenuAndWait(ScoreSheet sheet, PieceColor color)
    {
        String message;
        if (color != null)
        {
            message = color + " player" + " won the game!";
        }
        else
        {
            message = "There is a draw!";
        }
        JOptionPane.showMessageDialog(null, message, "Game Result", JOptionPane.INFORMATION_MESSAGE);

        int option = JOptionPane.showConfirmDialog(
                this,
                "Do you want to save the game?",
                "Save Game",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION)
        {
           showSaveDialog(this, sheet);
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press any key to close the game");
        scanner.nextLine();
        exit(1);
    }

    private void showSaveDialog(JFrame parentFrame, ScoreSheet sheet)
    {
        JFileChooser fileChooser = new JFileChooser();
        int saveDialogResult = fileChooser.showSaveDialog(parentFrame);
        if (saveDialogResult == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = fileChooser.getSelectedFile();
            sheet.saveSheet(selectedFile.toPath());
        }
    }
}
