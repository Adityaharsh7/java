import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class LoginLogoutPage extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton logoutButton;
    private JLabel statusLabel;

    public LoginLogoutPage() {
        setTitle("Login/Logout Page");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        // Username field
        usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        add(usernameField);

        // Password field
        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        add(passwordField);

        // Login button
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (authenticate(username, password)) {
                    statusLabel.setText("Login successful");
                    usernameField.setEnabled(false);
                    passwordField.setEnabled(false);
                    loginButton.setEnabled(false);
                    logoutButton.setEnabled(true);

                    // Display the Traffic Signal window
                    Traffic_Signal trafficSignal = new Traffic_Signal("Traffic Light");
                    trafficSignal.setSize(500, 500);
                    trafficSignal.setVisible(true);
                } else {
                    statusLabel.setText("Invalid username or password");
                }
            }
        });
        add(loginButton);

        // Logout button
        logoutButton = new JButton("Logout");
        logoutButton.setEnabled(false);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Logged out");
                usernameField.setEnabled(true);
                passwordField.setEnabled(true);
                loginButton.setEnabled(true);
                logoutButton.setEnabled(false);
            }
        });
        add(logoutButton);

        // Status label
        statusLabel = new JLabel("");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(statusLabel);

        setVisible(true);
    }

    private boolean authenticate(String username, String password) {
        // Add your authentication logic here
        return username.equals("admin") && password.equals("admin");
    }

    public static void main(String[] args) {
        new LoginLogoutPage();
    }
}

class Traffic_Signal extends JFrame implements ItemListener {

    JRadioButton jr1;
    JRadioButton jr2;
    JRadioButton jr3;
    JTextField j1 = new JTextField(10);
    ButtonGroup b = new ButtonGroup();
    String msg = " ";
    int x = 0, y = 0, z = 0;
    Timer timer;

    public Traffic_Signal(String msg) {
        super(msg);
        setLayout(new BorderLayout());

        // Create a JLabel with a background image
        ImageIcon backgroundIcon = new ImageIcon("background.jpg");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        add(backgroundLabel, BorderLayout.CENTER);

        // Create a panel for the traffic signal controls
        JPanel controlPanel = new JPanel();
        controlPanel.setOpaque(false); // Make the panel transparent
        controlPanel.setLayout(new FlowLayout());

        jr1 = new JRadioButton("Red");
        jr2 = new JRadioButton("Yellow");
        jr3 = new JRadioButton("Green");

        jr1.addItemListener(this);
        jr2.addItemListener(this);
        jr3.addItemListener(this);

        controlPanel.add(jr1);
        controlPanel.add(jr2);
        controlPanel.add(jr3);
        b.add(jr1);
        b.add(jr2);
        b.add(jr3);
        controlPanel.add(j1);

        // Start and stop buttons
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.start();
            }
        });
        controlPanel.add(startButton);

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
            }
        });
        controlPanel.add(stopButton);

        add(controlPanel, BorderLayout.SOUTH);

        // Timer to change the traffic light every 10 seconds
        timer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jr1.isSelected()) {
                    jr2.setSelected(true);
                } else if (jr2.isSelected()) {
                    jr3.setSelected(true);
                } else if (jr3.isSelected()) {
                    jr1.setSelected(true);
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void itemStateChanged(ItemEvent ie) {
        if (ie.getSource() == jr1) {
            if (ie.getStateChange() == 1) {
                msg = "Stop!";
                x = 1;
                repaint();
            } else {
                msg = "";
            }
        }
        if (ie.getSource() == jr2) {
            if (ie.getStateChange() == 1) {
                msg = "Get Ready to go!";
                y = 1;
                repaint();
            } else {
                msg = "";
            }
        }
        if (ie.getSource() == jr3) {
            if (ie.getStateChange() == 1) {
                msg = "Go!!";
                z = 1;
                repaint();
            } else {
                msg = "";
            }
        }
        j1.setText(msg);
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawRect(100, 105, 110, 270);
        g.drawOval(120, 150, 60, 60);
        g.drawOval(120, 230, 60, 60);
        g.drawOval(120, 300, 60, 60);

        if (x == 1) {
            g.setColor(Color.RED);
            g.fillOval(120, 150, 60, 60);
            g.setColor(Color.WHITE);
            g.fillOval(120, 230, 60, 60);
            g.setColor(Color.WHITE);
            g.fillOval(120, 300, 60, 60);
            x = 0;
        }
        if (y == 1) {
            g.setColor(Color.WHITE);
            g.fillOval(120, 150, 60, 60);
            g.setColor(Color.YELLOW);
            g.fillOval(120, 230, 60, 60);
            g.setColor(Color.WHITE);
            g.fillOval(120, 300, 60, 60);
            y = 0;
        }
        if (z == 1) {
            g.setColor(Color.WHITE);
            g.fillOval(120, 150, 60, 60);
            g.setColor(Color.WHITE);
            g.fillOval(120, 230, 60, 60);
            g.setColor(Color.GREEN);
            g.fillOval(120, 300, 60, 60);
            z = 0;
        }
    }
}