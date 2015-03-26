package programmingproject;

import com.github.authorfu.lrcparser.LrcParser;
import com.github.authorfu.lrcparser.Lyric;
import com.github.authorfu.lrcparser.parser.Sentence;
import ddf.minim.Minim;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Audio //Cal'd Own Tried and Tested Audio Playing Package!
{

    ArrayList<SoundClip> clips = new ArrayList<SoundClip>();
    ArrayList<Lyrics> lyrics = new ArrayList<Lyrics>();
    Minim minim;
    int currentClip = -1;

    public Audio(RenderArea renderArea)
    {
        minim = new Minim(renderArea);
    }

    public void loadClip(String file, String name, int offset, double audioEdge, double shakeValue)
    {
        System.out.println("Loading " + file);
        clips.add(new SoundClip(minim.loadFile(file), name, audioEdge, shakeValue));
        lyrics.add(new Lyrics(name, offset));
    }

    public void playClip(String name)
    {
        for (SoundClip clip : clips)
        {
            clip.clip.pause();
        }
        for (int i = 0; i < clips.size(); i++)
        {
            SoundClip clip = clips.get(i);
            if (clip.name.equals(name))
            {
                currentClip = i;
                clip.clip.rewind();
                clip.clip.play();
            }
        }
    }

    public void playClip(int clipNum)
    {
        for (SoundClip clip : clips)
        {
            clip.clip.pause();
        }
        if (clipNum < clips.size())
        {
            SoundClip clip = clips.get(clipNum);
            currentClip = clipNum;
            clip.clip.rewind();
            clip.clip.play();
        }

    }

    public int getCurrentPosition()
    {
        if (currentClip != -1)
        {
            return clips.get(currentClip).clip.position();
        } else
        {
            return -1;
        }
    }

    public String getCurrentLyric()
    {
        if (currentClip != -1)
        {
            return lyrics.get(currentClip).getCurrent(getCurrentPosition());
        }
        return "";
    }

    public double getAudioShake()
    {
        if (currentClip != -1)
        {
            return clips.get(currentClip).shakeValue;
        }
        return 0.0;
    }

    public double getAudioEdge()
    {
        if (currentClip != -1)
        {
            return clips.get(currentClip).audioEdge;
        }
        return 0.3;
    }

    public float getCurrentLevel()
    {
        if (currentClip != -1)
        {
            return ((clips.get(currentClip).clip.mix.level()));
        }
        return 0;
    }

    public void stopAllClips()
    {
        for (SoundClip clip : clips)
        {
            clip.clip.pause();
        }
    }

    public void playClip(String name, float volume)
    {
        for (int i = 0; i < clips.size(); i++)
        {
            SoundClip clip = clips.get(i);
            if (clip.name.equals(name))
            {
                currentClip = i;
                clip.clip.rewind();
                float maxGain = clip.clip.gain().getMaximum();
                float minGain = clip.clip.gain().getMinimum();
                float gain = (maxGain + minGain) * (volume / 100);
                System.out.println(gain);
                clip.clip.setGain(gain);
                clip.clip.play();
            }
        }
    }
    
    
    /*
     *
     * @author Cal
     */

    public class Lyrics
    {

        ArrayList<Sentence> sentences;
        int offset = 0;

        public Lyrics(String name, int offset)
        {
            if (new File("music/" + name + ".lrc").exists())
            {
                loadFile("music/" + name + ".lrc");
                this.offset = offset;
            }
        }

        public Lyrics(String name)
        {
            if (new File("C:/Lyrics/" + name + ".lrc").exists())
            {
                loadFile("C:/Lyrics/" + name + ".lrc");
            }
        }

        public void loadFile(String file)
        {
            try
            {
                BufferedReader buff = new BufferedReader(new FileReader(file));
                Lyric lyric;
                try
                {
                    lyric = LrcParser.create(buff);
                    sentences = lyric.findAllSentences(-1, -1);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            } catch (Exception ex)
            {
                Logger.getLogger(Lyrics.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        public String getCurrent(int position)
        {
            if (sentences != null)
            {
                for (int i = 0; i < sentences.size(); i++)
                {
                    if (sentences.get(i).isInTime(position + offset))
                    {
                        //System.out.println(position);
                        return sentences.get(i).getContent();
                    }
                }
            }
            return "";
        }
    }

}
