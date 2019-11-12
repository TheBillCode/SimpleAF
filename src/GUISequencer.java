
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author williambechdel
 */
public class GUISequencer extends JPanel {

    //create gui panels
    private JPanel padPanel;
    private JPanel controlPanel;
    private JPanel trackPanel;
    private JPanel ledPanel;
    private JPanel topPanel;

    //Logo
    private ImageIcon logoImage;
    private JLabel logo;

    //control buttons
    JButton playButton;
    JButton stopButton;
    JButton pauseButton;
    JButton incBpm;
    JButton decBpm;
    JLabel bpmLabel;

    //pad buttons
    JButton[] padButton = new JButton[16];
    public JLabel[] ledLabel = new JLabel[16];

    //track buttons
    JButton[] tracksButtons = new JButton[main.sequencer.track.length];
    int currentTrack = 0;
    int bpm = 0;

    public GUISequencer() {
        setControlButtons(); //sets layout for control buttons
        setPadButtons(); //sets laytout for pad buttons
        setTrackButtons(); //sets layout for track buttons
        topPanel();

        JFrame jf = new JFrame();
        jf.setTitle("SimpleAF");
        jf.setSize(600, 250);
        jf.setLocationRelativeTo(null);

        Container pane = jf.getContentPane();
        GridBagLayout fl = new GridBagLayout();
        
        pane.setLayout(fl);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;

        //add Control Buttons to frame;
        c.fill = GridBagConstraints.HORIZONTAL;
        jf.add(topPanel, c);

        //add track buttons to frame
        c.gridy++;
        jf.add(trackPanel, c);

        c.gridy++;
        jf.add(ledPanel, c);

        //add Pad Buttons to frame
        c.gridy++;
        jf.add(padPanel, c);

        jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }

    private void topPanel() {
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout());
        //topPanel.setBackground(Color.DARK_GRAY);

        //Set logo
        logoImage = new ImageIcon("./src/resources/SimpleAF.png");
        logo = new JLabel(logoImage);
        topPanel.add(logo);

        //add control panel
        topPanel.add(controlPanel);

    }

    private void setControlButtons() {
        //Control pad layout
        controlPanel = new JPanel();
        controlPanel.setMinimumSize(new Dimension(300, 0));
        controlPanel.setLayout(new GridLayout(1, 5));
        controlPanel.add(incBpm = new JButton(">"), 4, 0);
        //add bpm
        bpmLabel = new JLabel("" + main.sequencer.getBpm());
        controlPanel.add(bpmLabel, 6, 0);
        bpmLabel.setFont(new Font("Serif", Font.BOLD, 30));
        bpmLabel.setHorizontalAlignment(JLabel.CENTER);
        controlPanel.add(decBpm = new JButton("<"), 5, 0);
        controlPanel.add(stopButton = new JButton("Stop"), 2, 0);
        controlPanel.add(pauseButton = new JButton("Pause"), 1, 0);
        controlPanel.add(playButton = new JButton("Play"), 0, 0);

    }

    private void setPadButtons() {
        //set up led button labels
        ledPanel = new JPanel();
        ledPanel.setLayout(new GridLayout(1, 16));
        //ledPanel.setBackground(Color.DARK_GRAY);
        

        //set up pad buttons
        padPanel = new JPanel();
        padPanel.setLayout(new GridLayout(1, 16));
        padPanel.setBackground(Color.DARK_GRAY);

        padPanel.setMinimumSize(new Dimension(50, 50));
        ledPanel.setMinimumSize(new Dimension(30, 40));

        for (int i = 15; i >= 0; i--) {
            padPanel.add(padButton[i] = new JButton(Integer.toString(i + 1)), i, 0);
            ledPanel.add(ledLabel[i] = new JLabel("."), i, 0);
            ledLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
            ledLabel[i].setFont(new Font("Serif", Font.BOLD, 50));
            padButton[i].setForeground(Color.black);
            if ((i-3) % 4 == 0) {
                padButton[i].setBorder(BorderFactory.createEtchedBorder());
            }
        }
    }

    private void setTrackButtons() {
        trackPanel = new JPanel();
        trackPanel.setLayout(new GridLayout(1, 5));
        trackPanel.setBackground(Color.DARK_GRAY);

        //button size
        trackPanel.setMinimumSize(new Dimension(150, 80));
        trackPanel.setMaximumSize(new Dimension(150, 80));
        trackPanel.setPreferredSize(new Dimension(150, 80));

        trackPanel.add(tracksButtons[4] = new JButton("Bass"), 4, 0);
        trackPanel.add(tracksButtons[3] = new JButton("Clap"), 0, 0);
        trackPanel.add(tracksButtons[2] = new JButton("Closed Hat"), 1, 0);
        trackPanel.add(tracksButtons[1] = new JButton("Snare"), 2, 0);
        trackPanel.add(tracksButtons[0] = new JButton("Kick"), 3, 0);
        setTrackColors();

    }

    public void setButtonColors() {
        for (int i = 0; i < 16; i++) {
            if (main.sequencer.track[main.gui.currentTrack][i]) {
                padButton[i].setForeground(Color.red);
            } else {
                padButton[i].setForeground(Color.black);
            }
        }
    }

    public void setTrackColors() {
        for (int i = 0; i < tracksButtons.length; i++) {
            if (currentTrack == i) {
                tracksButtons[i].setForeground(Color.red);
            } else {
                tracksButtons[i].setForeground(Color.black);
            }
        }
    }

    public void setBpm(int b) {
        bpm = b;
        bpmLabel.setText("" + bpm);
    }
}
