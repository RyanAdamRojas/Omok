import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Color;

public class GUI {
    private final Main MAIN;
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

    GUI(Main MAIN) {
        this.MAIN = MAIN;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(512, 700)); // DO NOT CHANGE
        frame.setResizable(false);
        frame.add(masterPanel);
        initGUI();
        frame.pack();
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }

    private void initGUI() {
        masterPanel.setLayout(new BorderLayout());
        showTitleScreen();
    }

    public void showTitleScreen() {
        // Clears previous content
        clearGUI();

        // Creates title screen components
        JButton goToSelectionScreenButton = createJButton("Start");
        goToSelectionScreenButton.addActionListener(e -> showSelectionScreen());
        setHeaderLabelAs("Welcome To");
        JLabel title = new JLabel("OMOK");
        Font titleFont =  new Font("SF Text", Font.BOLD, 100);
        title.setFont(titleFont);

        // Adding components to panels
        header.add(headerLabel);
        body.add(title, BorderLayout.CENTER);
        footer.add(goToSelectionScreenButton);

        refreshMasterPanel();
        refreshGUI();
    }

    public void showSelectionScreen() {
        // Clears previous content
        clearGUI();

        // Sets header label
        setHeaderLabelAs("Lets Set Things Up");

        // Creates setOpponents panel
        JRadioButton humanButton = createJRadioButton("Human");
        JRadioButton computerButton = createJRadioButton("Computer");
        JPanel setOpponentPanel = createSetOppPanel(humanButton, computerButton);

        // Creates setName panel
        JTextField name1TextField = createJTextField("Type here");
        JTextField name2TextField = createJTextField("Type here");
        JPanel setNamePanel1 = createSetNamePanel("Player 1", name1TextField); // Not visible by default
        JPanel setNamePanel2 = createSetNamePanel("Player 2", name2TextField); // Not visible by default

        // Creates footer's buttons
        JButton goToGameScreenButton = createJButton("Play");
        JButton backToTitleButton = createJButton("Back");

        // Creates button actions
        humanButton.addActionListener(e -> {
            player2 = new HumanPlayer();    // Creates new player
            player2.setStoneColor(StoneColor.RED);
            setNamePanel1.setVisible(true); // Sets subsequent panels visible
            setNamePanel2.setVisible(true);

            if (computerButton.isSelected()){
                computerButton.setSelected(false); // Deselects other button
                name2TextField.setText("Type here"); // Resets player 2 text
            }
        });

        computerButton.addActionListener(e -> {
            player2 = new ComputerPlayer(); // Creates new player
            player2.setStoneColor(StoneColor.WHITE);
            setNamePanel1.setVisible(true); // Sets subsequent panels visible
            setNamePanel2.setVisible(true);

            if (humanButton.isSelected())
                humanButton.setSelected(false);  // Deselects other button

            name2TextField.setText(player2.getName()); // Sets text as computers random name
        });

        backToTitleButton.addActionListener(e -> showTitleScreen());

        goToGameScreenButton.addActionListener(e -> {
            if (humanButton.isSelected() || computerButton.isSelected()) {
                player1 = new HumanPlayer();
                player1.setStoneColor(StoneColor.BLUE);

                // Sets Up Players in Main
                player1.setName(name1TextField.getText());
                player2.setName(name2TextField.getText());
                if (player1.getName().equals("Type here"))
                    player1.setName("Player 1");
                if (player2.getName().equals("Type here"))
                    player2.setName("Player 2");
                MAIN.setPlayer1(player1);
                MAIN.setPlayer2(player2);
                MAIN.setCurrentPlayer(player1);
                showGameSessionScreen();
            }
            else {
                setHeaderLabelAs("Choose an opponent first!");
            }
        });

        // Setting up the header, body, and footer (HBF) panels
        header.add(headerLabel, BorderLayout.CENTER);
        GridLayout gridLayout = new GridLayout(7,1);
        gridLayout.setVgap(5);
        body.setLayout(gridLayout);
        body.add(setOpponentPanel);
        body.add(setNamePanel1);
        body.add(setNamePanel2);
        footer.add(backToTitleButton);
        footer.add(goToGameScreenButton);

        // Adds the HBF panels to the masterPanel
        masterPanel.add(header, BorderLayout.NORTH);
        masterPanel.add(new JLabel("                      "), BorderLayout.EAST); // Crude buffer
        masterPanel.add(body, BorderLayout.CENTER);
        masterPanel.add(new JLabel("                      "), BorderLayout.WEST); // Crude buffer
        masterPanel.add(footer, BorderLayout.SOUTH);
        refreshGUI();
    }

    public void showGameSessionScreen() {
        // Clears previous content
        clearGUI();

        // Creates quit button
        JButton quitGameButton = new JButton("Quit");
        quitGameButton.addActionListener(e -> showSelectionScreen()); // TODO Add "are you sure" prompt

        // Set up menu bar
        frame.add(createJMenuBar(), BorderLayout.NORTH);

        // Set up Master Panel
        setHeaderLabelAs(MAIN.getCurrentPlayer().getName() + " goes first!");
        header.add(headerLabel);
        body.setLayout(new BorderLayout());
        body.add(new BoardPanel(), BorderLayout.CENTER);
        footer.add(quitGameButton);
        refreshMasterPanel();
        refreshGUI();
    }

    private void refreshMasterPanel() {
        masterPanel.add(header, BorderLayout.NORTH);
        masterPanel.add(body, BorderLayout.CENTER);
        masterPanel.add(footer, BorderLayout.SOUTH);
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

    public void setHeaderLabelAs(String text) {
        headerLabel.setText(text);
        headerLabel.setFont(headerFont);
        header.repaint();
    }

    private JPanel createSetOppPanel(JRadioButton humanButton, JRadioButton computerButton) {
        // Creates components
        JPanel setOpponentPanel = new JPanel();
        JLabel setOpponentLabel = createLargeBodyLabel("Play Against");

        // Specifies look and layout
        setOpponentPanel.setLayout(new GridLayout(3,1));
        setOpponentPanel.add(setOpponentLabel);
        setOpponentPanel.add(humanButton);
        setOpponentPanel.add(computerButton);
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

    private JRadioButton createJRadioButton(String text) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setFont(bodyFont);
        return radioButton;
    }

    private JMenuBar createJMenuBar() {
        // This methods URL: https://www.demo2s.com/java/java-swing-menu-items-mnemonics-and-accelerators.html#google_vignette
        // Nov 12, 2023

        // Create a label that will display the menu selection.
        JLabel jlab = new JLabel();

        // Create the menu bar.
        JMenuBar jmb = new JMenuBar();
        JMenu jmFile = new JMenu("File");
        jmFile.setMnemonic(KeyEvent.VK_F);

        JMenuItem jmiOpen = new JMenuItem("Open", KeyEvent.VK_O);
        jmiOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                InputEvent.CTRL_DOWN_MASK));

        JMenuItem jmiClose = new JMenuItem("Close", KeyEvent.VK_C);
        jmiClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
                InputEvent.CTRL_DOWN_MASK));

        JMenuItem jmiSave = new JMenuItem("Save", KeyEvent.VK_S);
        jmiSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                InputEvent.CTRL_DOWN_MASK));

        JMenuItem jmiExit = new JMenuItem("Exit", KeyEvent.VK_E);
        jmiExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
                InputEvent.CTRL_DOWN_MASK));

        jmFile.add(jmiOpen);
        jmFile.add(jmiClose);

        jmFile.add(jmiSave);
        jmFile.addSeparator();
        jmFile.add(jmiExit);
        jmb.add(jmFile);

        // Create the Options menu.
        JMenu jmOptions = new JMenu("Options");

        // Create the Colors submenu.
        JMenu jmColors = new JMenu("Colors");
        JMenuItem jmiRed = new JMenuItem("Red");
        JMenuItem jmiGreen = new JMenuItem("Green");
        JMenuItem jmiBlue = new JMenuItem("Blue");
        jmColors.add(jmiRed);
        jmColors.add(jmiGreen);
        jmColors.add(jmiBlue);
        jmOptions.add(jmColors);

        // Create the Priority submenu.
        JMenu jmPriority = new JMenu("Priority");
        JMenuItem jmiHigh = new JMenuItem("High");
        JMenuItem jmiLow = new JMenuItem("Low");
        jmPriority.add(jmiHigh);
        jmPriority.add(jmiLow);
        jmOptions.add(jmPriority);

        // Create the Reset menu item.
        JMenuItem jmiReset = new JMenuItem("Reset");
        jmOptions.addSeparator();
        jmOptions.add(jmiReset);
        // Finally, add the entire options menu to
        // the menu bar
        jmb.add(jmOptions);

        // Create the Help menu.
        JMenu jmHelp = new JMenu("Help");
        JMenuItem jmiAbout = new JMenuItem("About");
        jmHelp.add(jmiAbout);
        jmb.add(jmHelp);
        return jmb;
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

    public static void MAIN(String[] args) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
            // Handle the exception here, perhaps reverting to a default Look & Feel
        }
//
//        SwingUtilities.invokeLater(() -> {
//            try {
//                if (MAIN == null)
//                    MAIN = new Main();
//                new GUI();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
    }
}