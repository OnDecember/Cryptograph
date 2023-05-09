package GUI;

import javax.swing.*;
import java.awt.*;

public class CryptographGUI extends JFrame {


    private static final int WIDTH = 400;
    private static final int HEIGHT = 200;

    private static final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();
    private static final Dimension SCREEN_SIZE = TOOLKIT.getScreenSize();

    public JTextField path = new JTextField(10);
    public JTextField key = new JTextField(10);
    public JRadioButton buttonE = new JRadioButton("encrypt");
    public JRadioButton buttonD = new JRadioButton("decrypt");
    public JRadioButton buttonBF = new JRadioButton("brute force");
    public JButton continueButton = new JButton();


    public CryptographGUI() {
        super("Cryptograph");
        this.setBounds(SCREEN_SIZE.width / 2 - WIDTH / 2, SCREEN_SIZE.height / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContinueButton();
        setRadioButtons();
        Container container = new Container();
        container.setLayout(new GridLayout(5, 2));
        JLabel titleField = new JLabel("Enter path: ");
        container.add(titleField);
        container.add(path);
        JLabel titleFieldKey = new JLabel("Enter key / path: ");
        container.add(titleFieldKey);
        container.add(key);
        JLabel titleFieldOperation = new JLabel("Choose an operation: ");
        container.add(titleFieldOperation);
        container.add(buttonE);
        container.add(buttonD);
        container.add(buttonBF);
        container.add(new JLabel(""));
        container.add(continueButton);
        container.setVisible(true);
        this.add(container);
    }

    private void setContinueButton() {
        continueButton.setText("Continue");
        continueButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
    }

    private void setRadioButtons() {
        ButtonGroup group = new ButtonGroup();
        buttonE.setSelected(true);
        group.add(buttonE);
        group.add(buttonD);
        group.add(buttonBF);
    }
}
