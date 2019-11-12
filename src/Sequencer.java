import java.awt.Color;
import java.util.List;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.swing.SwingWorker;

public class Sequencer {

    private static int lastLed = 0;
    private static boolean looping = false; //track is playing
    private static int position = 0; //beat position
    private static int bpm = 100;
    private static Mixer mixer;
    //Create drum objects
    private static Sample kickDrum = new Sample("./src/resources/kickIt.wav");
    private static Sample snareDrum = new Sample("./src/resources/snare.wav");
    private static Sample hiHatClosed = new Sample("./src/resources/closedHat.wav");
    private static Sample clap = new Sample("./src/resources/clap.wav");
    private static Sample bass = new Sample("./src/resources/bass1.wav");

    boolean[][] track = new boolean[5][16];

    public Sequencer() {
        //setup for audio output
        Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo(); //create mixer object
        mixer = AudioSystem.getMixer(mixerInfo[0]); //set mixer to default audio
        tempo(bpm);
    }

    public static void tempo(int bpm) {
        try {
            Thread.sleep((60000 / bpm / 4));

        } catch (Exception e) {
        }
    }

    public void start() {
        looping = true;
        loop();
    }

    public void stop() {
        looping = false;
        position = 0;
        kickDrum.stopAll();
        snareDrum.stopAll();
        clap.stopAll();
        hiHatClosed.stopAll();
        bass.stopAll();
    }

    public void pause() {
        if (!looping) {
            looping = true;
            loop();
        } else {
            looping = !looping;
        }
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int n) {
        if (bpm >= 140 && n > 0) {
            return;
        } else if (bpm <= 60 && n < 0) {
            return;
        } else {
            bpm += n;
        }
    }

    public void loop() {
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (looping) {
                    //loop through tack positions
                    if (track[0][position]) {
                        kickDrum.play();
                    }
                    if (track[1][position]) {
                        snareDrum.play();
                    }
                    if (track[2][position]) {
                        hiHatClosed.play();
                    }
                    if (track[3][position]) {
                        clap.play();
                    }
                    if (track[4][position]) {
                        bass.play();
                    }
                    if (position == track[0].length - 1) {
                        position = 0; //at end of track
                    } else {
                        position++;
                    }
                    //System.out.println(position + " " + track[0][position]);
                    if (!looping) {
                        break;
                    }
                    tempo(bpm);
                    publish(position);
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                int value = chunks.get(chunks.size() - 1);
                //System.out.println(value);
                main.gui.ledLabel[value].setForeground(Color.red);
                main.gui.ledLabel[lastLed].setForeground(Color.black);
                lastLed = value;
            }

        };
        worker.execute();

    }
    

}
