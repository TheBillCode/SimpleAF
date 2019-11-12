import java.io.File;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Sample {

    ArrayList<Clip> clip = new ArrayList<Clip>();
    private String filename;
    int buffer = 0;
    int bufferLength = 2;

    
    
    public Sample(String filename) {
        this.filename = filename;
        for (int i = 0; i < bufferLength; i++) {
            load();
        }
            
    }

    private void load() {
        try {
            File file = new File(filename);
            AudioInputStream sample = AudioSystem.getAudioInputStream(file);
            clip.add(AudioSystem.getClip());
            clip.get(clip.size()-1).open(sample);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        int buffer = 0;
        for (int i = 0; i < bufferLength; i++) {
           if (!clip.get(i).isRunning()) {
               clip.get(i).setFramePosition(0);
               buffer = i;
               break;
           } else {
               load();
               bufferLength++;
               buffer = i+1;
           }
        }
        clip.get(buffer).start();
        //System.out.println(bufferLength);

    } 
    
    public void stopAll() {
        for (int i = 0; i < bufferLength; i++) {
            clip.get(i).stop();   
        }
        buffer = 0;
    }
}
