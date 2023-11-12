import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class GUI {
    private Main main;
    private JFrame frame = new JFrame("Omok");
    private JPanel masterPanel = new JPanel();
    private JPanel header = new JPanel();
    private JPanel body = new JPanel();
    private JPanel footer = new JPanel();
    private JLabel headerLabel = new JLabel();
    private final Font headerFont = new Font("SF Text", Font.BOLD, 24);
    private final Font largeBodyFont = new Font("SF Text", Font.BOLD, 16);
    private final Font bodyFont = new Font("SF Text", Font.PLAIN, 12);
    private Player player1;
    private Player player2;

    GUI(Main main) {
        this.main = main;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(512, 700)); // DO NOT CHANGE
        frame.setResizable(false);
        frame.add(masterPanel);
        initScreens();
        frame.pack();
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }

    private void initScreens() {
        masterPanel.setLayout(new BorderLayout());
        showSelectionScreen(); // FIXME Change to TitleScreen
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

    public void showSelectionScreen() {
        // Clears previous content
        clearGUI();

        // Sets header label
        setHeaderLabelAs("Omok");

        // Creates setOpponents panel
        JRadioButton selectHumanButton = createJRadioButton("Human");
        JRadioButton selectComputerButton = createJRadioButton("Computer");
        JPanel setOpponentPanel = createSetOppPanel(selectHumanButton, selectComputerButton);

        // Creates setName panel
        JTextField name1TextField = createJTextField("");
        JTextField name2TextField = createJTextField("");
        JPanel setNamePanel1 = createSetNamePanel("Player 1", name1TextField); // Not visible by default
        JPanel setNamePanel2 = createSetNamePanel("Player 2", name2TextField); // Not visible by default

        // Creates dynamic message panel
        JLabel dynamicMessageLabel = new JLabel("");
        JPanel dynamicMessagePanel = createDynamicMessagePanel(dynamicMessageLabel);

        // Creates footer's buttons
        JButton goToGameScreenButton = createJButton("Play");
        JButton backToTitleButton = createJButton("Back");

        // Creates button actions
        selectHumanButton.addActionListener(e -> {
            selectComputerButton.setSelected(false);
            if (name1TextField.getText().isEmpty())
                name1TextField.setText("Type here");
//            if (name2TextField.getText().isEmpty())
            name2TextField.setText("Type here");
            name2TextField.setEditable(true);
            if (!setNamePanel1.isVisible())
                setNamePanel1.setVisible(true);
            if (!setNamePanel2.isVisible())
                setNamePanel2.setVisible(true);
        });
        selectComputerButton.addActionListener(e -> {
            selectHumanButton.setSelected(false);
            if (name1TextField.getText().isEmpty())
                name1TextField.setText("Type here");
            main.setPlayer2(new ComputerPlayer());
            player2 = main.getPlayer2();
            name2TextField.setText(player2.getName()); // FIXME Ensure computerPlayer sets own name
            name2TextField.setEditable(false);
            if (!setNamePanel1.isVisible())
                setNamePanel1.setVisible(true);
            if (!setNamePanel2.isVisible())
                setNamePanel2.setVisible(true);
        });
        backToTitleButton.addActionListener(e -> showTitleScreen());
        goToGameScreenButton.addActionListener(e -> {
            name1TextField.setText("Type here");
            setNamePanel1.setVisible(true);
            setNamePanel2.setVisible(true);
            String text = name1TextField.getText();
            if (text.equals("Name"))
                player1.setName("Player 1");
            else
                player1.setName(name1TextField.getText());
            showGameSessionScreen();
        });
        
        // Setting up the header, body, and footer (HBF) panels
        header.add(headerLabel, BorderLayout.CENTER);
        GridLayout gridLayout = new GridLayout(5,1);
        gridLayout.setVgap(5);
        body.setLayout(gridLayout);
        body.add(setOpponentPanel);
        body.add(setNamePanel1);
        body.add(setNamePanel2);
        body.add(new JPanel());
        body.add(dynamicMessagePanel, Component.BOTTOM_ALIGNMENT);
        footer.add(backToTitleButton);
        footer.add(goToGameScreenButton);

        // Adds the HBF panels to the masterPanel
        masterPanel.add(header, BorderLayout.NORTH);
        masterPanel.add(new JLabel("                "), BorderLayout.EAST); // Crude buffer space
        masterPanel.add(body, BorderLayout.CENTER);
        masterPanel.add(new JLabel("                "), BorderLayout.WEST); // Crude buffer space
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

    private JPanel createSetOppPanel(JRadioButton selectHumanButton, JRadioButton selectComputerButton) {
        // Creates components
        JPanel setOpponentPanel = new JPanel();
        JLabel setOpponentLabel = createLargeBodyLabel("Play Against");

        // Specifies look and layout
        setOpponentPanel.setLayout(new GridLayout(3,1));
        setOpponentPanel.add(setOpponentLabel);
        setOpponentPanel.add(selectHumanButton);
        setOpponentPanel.add(selectComputerButton);
        return setOpponentPanel;
    }

    private JPanel createSetNamePanel(String text, JTextField nameTextField) {
        // Creates components
        JPanel setNamePanel = new JPanel();
        JLabel setName1Label = createLargeBodyLabel(text);
        addTextSelectionEffect(nameTextField);

        // Specifies look and layout
        setNamePanel.setLayout(new GridLayout(2,1));
        setNamePanel.add(setName1Label);
        setNamePanel.add(nameTextField);
        setNamePanel.setVisible(false);
        return setNamePanel;
    }

    private JPanel createDynamicMessagePanel(JLabel messageLabel) {
        // Creates components
        JPanel jPanel = new JPanel();
        messageLabel = createBodyLabel("");

        // Specifies look and layout
        messageLabel.setFont(bodyFont);
        jPanel.add(messageLabel);
        return jPanel;
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

    private JLabel createLargeBodyLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(largeBodyFont);
        return label;
    }

    private JTextField createJTextField(String text) {
        JTextField textField = new JTextField(text);
        textField.setSize(10, 50);
        textField.setColumns(20);
        textField.setFont(bodyFont);
        return textField;
    }

    private void addHoverEffect(JButton button) {
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

    public void addTextSelectionEffect(JTextField textField) {
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
//
//        SwingUtilities.invokeLater(() -> {
//            try {
//                if (main == null)
//                    main = new Main();
//                new GUI();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
    }
}