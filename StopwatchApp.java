import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopwatchApp {
    private JFrame frame;
    private JLabel timeLabel;
    private JButton startButton;
    private JButton pauseButton;
    private JButton resetButton;
    private Timer timer;
    private long startTime;
    private long elapsedTime;
    private boolean isRunning;

    public StopwatchApp() {
        frame = new JFrame("Stopwatch");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new GridBagLayout());

        timeLabel = new JLabel("00:00:000");
        timeLabel.setFont(new Font("Serif", Font.BOLD, 30));
        frame.add(timeLabel, createConstraints(0, 0, 3, 1));

        startButton = new JButton("Start");
        startButton.addActionListener(new StartButtonListener());
        frame.add(startButton, createConstraints(0, 1, 1, 1));

        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new PauseButtonListener());
        frame.add(pauseButton, createConstraints(1, 1, 1, 1));

        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ResetButtonListener());
        frame.add(resetButton, createConstraints(2, 1, 1, 1));

        timer = new Timer(1, new TimerListener());
        isRunning = false;

        frame.setVisible(true);
    }

    private GridBagConstraints createConstraints(int x, int y, int width, int height) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        return gbc;
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isRunning) {
                isRunning = true;
                startTime = System.currentTimeMillis() - elapsedTime;
                timer.start();
            }
        }
    }

    private class PauseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isRunning) {
                isRunning = false;
                elapsedTime = System.currentTimeMillis() - startTime;
                timer.stop();
            }
        }
    }

    private class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            isRunning = false;
            timer.stop();
            elapsedTime = 0;
            timeLabel.setText("00:00:000");
        }
    }

    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            long currentTime = System.currentTimeMillis();
            long timePassed = currentTime - startTime;
            int minutes = (int) (timePassed / 60000);
            int seconds = (int) ((timePassed / 1000) % 60);
            int milliseconds = (int) (timePassed % 1000);
            timeLabel.setText(String.format("%02d:%02d:%03d", minutes, seconds, milliseconds));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StopwatchApp::new);
    }
}
