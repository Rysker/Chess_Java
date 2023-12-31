package SoundManager;

import java.util.HashMap;

public class SoundManager
{
    private static SoundManager instance = null;
    private HashMap<String, Sound> container;
    private boolean disabled = false;

    private SoundManager()
    {
        this.container = new HashMap<>();
        container.put("Normal", new Sound("/Assets/Sounds/" + "piece_move.wav"));
        container.put("Attack", new Sound("/Assets/Sounds/" + "piece_taken.wav"));
        container.put("Castling", new Sound("/Assets/Sounds/" + "castling.wav"));
        container.put("Check", new Sound("/Assets/Sounds/" + "king_check.wav"));
    }

    public static SoundManager getInstance()
    {
        if(instance == null)
            instance = new SoundManager();
        return instance;
    }

    public void playSound(String option)
    {
        if(this.disabled)
            return;
        Sound x = container.get(option);
        if(x != null)
        {
            try
            {
                x.play();
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    public void disable()
    {
        this.disabled = true;
    }

    public void enable()
    {
        this.disabled = false;
    }

}
