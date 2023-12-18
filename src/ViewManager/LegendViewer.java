package ViewManager;

import java.awt.*;

public class LegendViewer
{
    public void drawLegend(Graphics2D g)
    {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.setColor(Color.WHITE);
        int i = 1;
        for (char letter = 'A'; letter <= 'H'; letter++)
        {
            g.drawString(String.valueOf(letter), 12 + 80 * i, 40);
            i++;
        }

        for (int number = 1; number <= 8; number++)
        {
            g.drawString(String.valueOf(9 - number), 24, 30 + 80 * number);
        }
    }
}
