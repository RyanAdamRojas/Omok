import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUI {
    private JFrame frame = new JFrame("Omok");
    private JPanel masterPanel = new JPanel();
    private JPanel header = new JPanel();
    private JPanel body = new JPanel();
    private JPanel footer = new JPanel();
    private JLabel headerLabel = new JLabel();
    private final Font headerFont = new Font("SF Text", Font.BOLD, 24);
    private final Font largeBodyFont = new Font("SF Text", Font.BOLD, 16);
    private final Font bodyFont = new Font("SF Text", Font.BOLD, 12);
    private String player1Name;
    private String player2Name;

    GUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(550, 700));
        frame.setResizable(false);
        frame.add(masterPanel);
        initScreens();
        frame.pack();
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }

    private void initScreens() {
        masterPanel.setLayout(new BorderLayout());
        showGameSessionScreen(); // FIXME Change to TitleScreen
    }

    public void showTitleScreen() {
        // Clears previous content
        clearGUI();

        // Creates title screen components
        JButton goToSelectionScreenButton = createJButton("Start");
        goToSelectionScreenButton.addActionListener(e -> showSelectionScreen());
        setHeaderLabelAs("Omok");

        // Adding components to panels
        header.add(headerLabel);
        footer.add(goToSelectionScreenButton);

        // Adds components to panel
        masterPanel.add(header, BorderLayout.NORTH);
        masterPanel.add(footer, BorderLayout.SOUTH);
        refreshGUI();
    }

    public void showSelectionScreen(){
        // Clears previous content
        clearGUI();

        // Sets header label
        setHeaderLabelAs("Omok");

        // Creates body's setName panel
        JPanel setNamePanel = new JPanel();
        JLabel setNameLabel = createLargeBodyLabel("Name:");
        JTextField setNameTextField = createJTextField("Type here");
        addTextSelectionEffect(setNameTextField);
        setNamePanel.add(setNameLabel);
        setNamePanel.add(setNameTextField);

        // Creates body's setOpponents panel
        JPanel setOpponentPanel = new JPanel();
        JPanel setOpponentSubPanel = new JPanel();
        JLabel setOpponentLabel = createLargeBodyLabel("Opponent:");
        JRadioButton selectHumanButton = createJRadioButton("Human");
        JRadioButton selectComputerButton = createJRadioButton("Computer");
        setOpponentSubPanel.add(selectHumanButton);
        setOpponentSubPanel.add(selectComputerButton);
        setOpponentPanel.add(setOpponentLabel);
        setOpponentPanel.add(setOpponentSubPanel);

        // Creates body's dynamic message panel
        JPanel dynamicMessagePanel = new JPanel();
        JLabel messageLabel = createBodyLabel("Welcome!");
        messageLabel.setFont(bodyFont);
        dynamicMessagePanel.add(messageLabel);
        dynamicMessagePanel.add(messageLabel);

        // Creates footer's buttons
        JButton goToGameScreenButton = createJButton("Play");
        JButton backToTitleButton = createJButton("Back");

        // Setting up all the buttons actions
        selectHumanButton.addActionListener(e -> selectComputerButton.setSelected(false));
        selectComputerButton.addActionListener(e -> selectHumanButton.setSelected(false));
        backToTitleButton.addActionListener(e -> showTitleScreen());
        goToGameScreenButton.addActionListener(e -> {
            player1Name = setNameTextField.getText();
            showGameSessionScreen();
        });
        
        // Setting up the header, body, and footer (HBF) panels
        header.add(headerLabel, BorderLayout.CENTER);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.add(setNamePanel);
        body.add(setOpponentPanel);
        body.add(dynamicMessagePanel);
        body.add(Box.createVerticalGlue());
        footer.add(backToTitleButton);
        footer.add(goToGameScreenButton);

        // Adds the HBF panels to the masterPanel
        masterPanel.add(header, BorderLayout.NORTH);
        masterPanel.add(body, BorderLayout.CENTER);
        masterPanel.add(footer, BorderLayout.SOUTH);
        refreshGUI();
    }

    public void showGameSessionScreen() {
        // Clears previous content
        clearGUI();

        // Creates quit button
        JButton quitGameButton = new JButton("Quit");
        quitGameButton.addActionListener(e -> showSelectionScreen()); // TODO Add "are you sure" prompt

        // Sets up header
        setHeaderLabelAs("Game in session");

        // Sets up Body with a BoardPanel
        body.setLayout(new BorderLayout());
        BoardPanel boardPanel = new BoardPanel();
        body.add(boardPanel, BorderLayout.CENTER);

        // Sets up footer
        footer.add(quitGameButton);

        // Adds the HBF panels to masterPanel
        masterPanel.add(header, BorderLayout.NORTH);
        masterPanel.add(body, BorderLayout.CENTER);
        masterPanel.add(footer, BorderLayout.SOUTH);
        refreshGUI();
    }

    private void refreshGUI() {
        masterPanel.repaint();
        frame.pack();
    }

    private void clearGUI() {
        header.removeAll();
        body.removeAll();
        footer.removeAll();
        masterPanel.removeAll();
    }

    private void setHeaderLabelAs(String text) {
        headerLabel.setText(text);
        headerLabel.setFont(headerFont);
    }

    private JRadioButton createJRadioButton(String text) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setFont(bodyFont);
        return radioButton;
    }

    private JButton createJButton(String text) {
        JButton button = new JButton(text);
        button.setFont(bodyFont);
        addHoverEffect(button);
        return button;
    }

    private JLabel createBodyLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(bodyFont);
        return label;
    }

    private JLabel createLargeBodyLabel(String text){
        JLabel label = new JLabel(text);
        label.setFont(largeBodyFont);
        return label;
    }

    private JTextField createJTextField(String text){
        JTextField textField = new JTextField(text);
        textField.setSize(10, 50);
        textField.setColumns(20);
        textField.setFont(bodyFont);
        return textField;
    }

    private void addHoverEffect(JButton button){
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.darkGray);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(null);
            }
        });
    }

    public void addTextSelectionEffect(JTextField textField){
        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textField.selectAll();
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        };
        textField.addMouseListener(mouseListener);
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
            // Handle the exception here, perhaps reverting to a default Look & Feel
        }

        // Now, you initialize the Counter instance
        SwingUtilities.invokeLater(() -> {
            try {
                new GUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}