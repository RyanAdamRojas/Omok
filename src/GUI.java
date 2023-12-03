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
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GUI {
    private JFrame frame = new JFrame("Omok");
    private JPanel masterPanel = new JPanel();
    private JPanel topPanel = new JPanel();
    private JPanel middlePanel = new JPanel();
    private JPanel bottomPanel = new JPanel();
    private JLabel topPanelLabel = new JLabel();
    private final Font headerFont = new Font("SF Text", Font.BOLD, 24);
    private final Font subHeaderFont = new Font("SF Text", Font.BOLD, 16);
    private final Font bodyFont = new Font("SF Text", Font.PLAIN, 12);
    private Player tempP1, tempP2;
    private BoardPanel boardPanel;

    GUI() {
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
        setHeaderLabel("Welcome To");
        JLabel title = new JLabel("OMOK");
        Font titleFont =  new Font("SF Text", Font.BOLD, 100);
        title.setFont(titleFont);

        // Adding components to panels
        topPanel.add(topPanelLabel);
        middlePanel.add(title, BorderLayout.CENTER);
        bottomPanel.add(goToSelectionScreenButton);

        refreshMasterPanel();
        refreshGUI();
    }

    public void showSelectionScreen() {
        // Clears previous content
        clearGUI();

        // Sets topPanel label
        setHeaderLabel("Lets Set Things Up");

        // Creates setOpponents panel
        JRadioButton humanButton = createJRadioButton("Human");
        JRadioButton computerButton = createJRadioButton("Computer");
        JPanel setOpponentPanel = createSetOppPanel(humanButton, computerButton);

        // Creates setName panel
        JTextField name1TextField = createJTextField("Type here");
        JTextField name2TextField = createJTextField("Type here");
        JPanel setNamePanel1 = createSetNamePanel("Player 1", name1TextField); // Not visible by default
        JPanel setNamePanel2 = createSetNamePanel("Player 2", name2TextField); // Not visible by default

        // Creates bottomPanel's buttons
        JButton goToGameScreenButton = createJButton("Play");
        JButton backToTitleButton = createJButton("Back");

        // Creates button actions
        humanButton.addActionListener(e -> {
            tempP2 = new HumanPlayer();    // Creates new player
            tempP2.setStoneColor(StoneColor.RED);
            setNamePanel1.setVisible(true); // Sets subsequent panels visible
            setNamePanel2.setVisible(true);

            if (computerButton.isSelected()){
                computerButton.setSelected(false); // Deselects other button
                name2TextField.setText("Type here"); // Resets player 2 text
            }
        });

        computerButton.addActionListener(e -> {
            tempP2 = new ComputerPlayer(); // Creates new player
            tempP2.setStoneColor(StoneColor.WHITE);
            setNamePanel1.setVisible(true); // Sets subsequent panels visible
            setNamePanel2.setVisible(true);

            if (humanButton.isSelected())
                humanButton.setSelected(false);  // Deselects other button

            name2TextField.setText(tempP2.getName()); // Sets text as computers random name
        });

        backToTitleButton.addActionListener(e -> showTitleScreen());

        goToGameScreenButton.addActionListener(e -> {
            if (humanButton.isSelected() || computerButton.isSelected()) {
                tempP1 = new HumanPlayer();
                tempP1.setStoneColor(StoneColor.BLUE);

                // Sets Up Players in Main
                tempP1.setName(name1TextField.getText());
                tempP2.setName(name2TextField.getText());
                if (tempP1.getName().equals("Type here"))
                    tempP1.setName("Player 1");
                if (tempP2.getName().equals("Type here"))
                    tempP2.setName("Player 2");
                showGameSessionScreen();
            }
            else {
                setHeaderLabel("Choose an opponent first!");
            }
        });

        // Setting up the topPanel, middlePanel, and bottomPanel (HBF) panels
        topPanel.add(topPanelLabel, BorderLayout.CENTER);
        GridLayout gridLayout = new GridLayout(7,1);
        gridLayout.setVgap(5);
        middlePanel.setLayout(gridLayout);
        middlePanel.add(setOpponentPanel);
        middlePanel.add(setNamePanel1);
        middlePanel.add(setNamePanel2);
        bottomPanel.add(backToTitleButton);
        bottomPanel.add(goToGameScreenButton);

        // Adds the HBF panels to the masterPanel
        masterPanel.add(topPanel, BorderLayout.NORTH);
        masterPanel.add(new JLabel("                      "), BorderLayout.EAST); // Crude buffer
        masterPanel.add(middlePanel, BorderLayout.CENTER);
        masterPanel.add(new JLabel("                      "), BorderLayout.WEST); // Crude buffer
        masterPanel.add(bottomPanel, BorderLayout.SOUTH);
        refreshGUI();
    }

    public void showGameSessionScreen() {
        // Clears previous content
        clearGUI();

        // Sets up the board panel
        BoardPanel boardPanel;
        if (tempP1 == null || tempP2 == null)
            boardPanel = new BoardPanel(this, new HumanPlayer("John", StoneColor.BLUE), new HumanPlayer("Server", StoneColor.RED));
        else
            boardPanel = new BoardPanel(this, tempP1, tempP2);


        // Creates quit button
        JButton quitGameButton = new JButton("Quit");
        quitGameButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to quit?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    new ImageIcon("res/Images/exit.png"));
            if (response == JOptionPane.YES_OPTION)
                showTitleScreen();
        });

        // Set up Master Panel
        topPanel.add(topPanelLabel);
        middlePanel.setLayout(new BorderLayout());
        middlePanel.add(boardPanel, BorderLayout.CENTER);
        bottomPanel.add(quitGameButton);
        bottomPanel.add(createToolBar(), BorderLayout.NORTH);
        refreshMasterPanel();
        refreshGUI();
    }

    private void refreshMasterPanel() {
        masterPanel.add(topPanel, BorderLayout.NORTH);
        masterPanel.add(middlePanel, BorderLayout.CENTER);
        masterPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void refreshGUI() {
        masterPanel.repaint();
        frame.pack();
    }

    private void clearGUI() {
        topPanel.removeAll();
        middlePanel.removeAll();
        bottomPanel.removeAll();
        masterPanel.removeAll();
    }

    public void setHeaderLabel(String text) {
        topPanelLabel.setText(text);
        topPanelLabel.setFont(headerFont);
        topPanel.repaint();
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
        JMenuBar menuBar = new JMenuBar();

        // Create a menu
        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F); // Mnemonic 'F' for 'File'

        // Create a menu item with an icon, mnemonic, and accelerator
        JMenuItem menuItem = new JMenuItem("Open", new ImageIcon("path/to/open_icon.png"));
        menuItem.setMnemonic(KeyEvent.VK_O); // Mnemonic 'O' for 'Open'
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menu.add(menuItem);

        // Add the menu to the menu bar
        menuBar.add(menu);

        return menuBar;
    }

    public JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();

        ImageIcon icon1 = new ImageIcon("res/Images/play.png");
        JButton button1 = new JButton(resizeIcon(icon1, 24, 24));
        button1.setToolTipText("Play");
        toolBar.add(button1);

        ImageIcon icon2 = new ImageIcon("res/Images/online.png");
        JButton button2 = new JButton(resizeIcon(icon2, 24, 24));
        button2.setToolTipText("Connect");
        button2.addActionListener(al -> {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(URI.create("http://omok.atwebpages.com/info/"))
                            .GET()
                            .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(System.out::println)
                    .join();
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println("Response status code: " + response.statusCode());
                System.out.println("Response body: " + response.body());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        toolBar.add(button2);

        ImageIcon icon3 = new ImageIcon("res/Images/power.png");
        JButton button3 = new JButton(resizeIcon(icon3, 24, 24));
        button3.setToolTipText("Cheat mode");
        toolBar.add(button3);

        ImageIcon icon4 = new ImageIcon("res/Images/settings.png");
        JButton button4 = new JButton(resizeIcon(icon4, 24, 24));
        button4.setToolTipText("Settings");
        toolBar.add(button4);

        return toolBar;
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    private JButton createJButton(String text) {
        JButton button = new JButton(text);
        button.setFont(bodyFont);
        addHoverEffect(button);
        return button;
    }

    private JLabel createLargeBodyLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(subHeaderFont);
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