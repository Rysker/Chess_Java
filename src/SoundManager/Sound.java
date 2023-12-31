package SoundManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.sound.sampled.*;

public class Sound
{
    private File file;

    public Sound(String path)
    {
        String relativePath = "/src" + path;
        String absolutePath = Paths.get(System.getProperty("user.dir"), relativePath).toString();
        file = new File(absolutePath);
    }

    public void play()
    {
        Thread soundThread = new Thread(() ->
        {
            try
            {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(this.file));
                clip.start();
                while (!clip.isRunning())
                    Thread.sleep(10);
                while (clip.isRunning())
                    Thread.sleep(10);
                clip.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
        soundThread.start();
    }
}
