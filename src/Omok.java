package noapplet.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Omok {
    public Omok() {
        var frame = new JFrame("Omok");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        var panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 200));
        JRadioButton human = new JRadioButton("Human");
        JRadioButton computer = new JRadioButton("Computer");
        JButton play = new JButton("Play");
        ButtonGroup group = new ButtonGroup();

        JLabel select = new JLabel("Select opponent:");

        panel.add(select);
        panel.add(human);
        panel.add(computer);
        panel.add(play);

        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String displayOp = "";
                if (human.isSelected() && computer.isSelected()){
                    displayOp = "Only choose one!";
                }
                else if(human.isSelected()){
                    displayOp = "Human Selected!";
                }
                else if(computer.isSelected()){
                    displayOp = "Computer Selected!";
                }
                else{
                    displayOp = "No Selection was made!";
                }
                JOptionPane.showMessageDialog(panel, displayOp); // fancy dialog window
            }

        });

        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new Omok());
    }

}
