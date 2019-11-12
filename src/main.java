
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class main {
    static GUISequencer gui;
    static Sequencer sequencer;

    public static void main(String[] args) {
        //startRocking();
        sequencer = new Sequencer();;
        gui = new GUISequencer();
        setListner();

    }

    static private void setListner() {
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(gui.playButton)) {
                    sequencer.start();
                } else if (e.getSource().equals(gui.stopButton)) {
                    sequencer.stop();
                } else if (e.getSource().equals(gui.pauseButton)) {
                    sequencer.pause();
                } else if (e.getSource().equals(gui.incBpm)) {
                    sequencer.setBpm(1);
                    gui.setBpm(sequencer.getBpm());
                } else if (e.getSource().equals(gui.decBpm)) {
                    sequencer.setBpm(-1);
                    gui.setBpm(sequencer.getBpm());
                } else {
                    for (int i = 0; i < gui.tracksButtons.length; i++) {
                        if (e.getSource().equals(gui.tracksButtons[i])) {
                            gui.currentTrack = i;
                            gui.setButtonColors();
                            gui.setTrackColors();
                        }
                    }
                    for (int i = 0; i < gui.padButton.length; i++) {
                        if (e.getSource().equals(gui.padButton[i])) {
                            sequencer.track[gui.currentTrack][i] = !sequencer.track[gui.currentTrack][i];
                            gui.setButtonColors();
                        }
                    }
                }
            }
        };

        gui.playButton.addActionListener(listener);
        gui.stopButton.addActionListener(listener);
        gui.pauseButton.addActionListener(listener);
        gui.incBpm.addActionListener(listener);
        gui.decBpm.addActionListener(listener);
        for (int i = 0; i < gui.tracksButtons.length; i++) {
            gui.tracksButtons[i].addActionListener(listener);
        }
        for (int i = 0; i < gui.padButton.length; i++) {
            gui.padButton[i].addActionListener(listener);
        }
    }

    static public void startRocking() {
        Object[] options = { "No, I'm Scared","Totally!"};
        int rocking = JOptionPane.showOptionDialog(null, "Are you ready to "
                        + "rock?", "SimpleAF", JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, null);
        if (rocking == JOptionPane.YES_OPTION) System.exit(rocking);
    }
}
