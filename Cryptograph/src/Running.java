import Cryptograph.Cryptograph;
import GUI.CryptographGUI;
import util.FileUtility;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Running {

    String[] args;
    private String operation;
    private String path;
    private int key;


    public Running(String[] args) {
        this.args = args;
    }

    public void run() {
        if (args.length == 0) {
            app();
        } else if (!"-bf".equals(args[0]) && args.length < 3) {
            app();
        } else if ("-bf".equals(args[0]) && args.length < 2) {
            app();
        } else {
            operation = args[0];
            path = args[1];
            key = "-bf".equals(args[0]) ? 0 : Integer.parseInt(args[2]);
            coding(path, operation, key);
        }
    }

    private void app() {
        CryptographGUI gui = new CryptographGUI();
        gui.setVisible(true);
        gui.continueButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gui.buttonE.isSelected()) {
                    operation = "-e";
                    key = Integer.parseInt(gui.key.getText());
                } else if (gui.buttonD.isSelected()) {
                    operation = "-d";
                    key = Integer.parseInt(gui.key.getText());
                } else {
                    operation = "-bf";
                }
                path = gui.path.getText();

                coding(path, operation, key);
                JOptionPane.showMessageDialog(null, "Successfully", "Operation", JOptionPane.PLAIN_MESSAGE);
            }
        });
    }

    private void coding(String path, String operation, int key) {
        try {
            Cryptograph cryptograph = new Cryptograph();
            Path srcFile = Path.of(path);
            String text;
            if ("-e".equals(operation)) {
                text = cryptograph.encrypt(srcFile, key);
            } else if ("-d".equals(operation)) {
                text = cryptograph.decrypt(srcFile, key);
            } else if ("-bf".equals(operation)) {
                key = cryptograph.bruteForce(srcFile);
                text = cryptograph.decrypt(srcFile, key);
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect operation: " + operation, "Operation", JOptionPane.PLAIN_MESSAGE);
                throw new IllegalArgumentException("Incorrect operation");
            }
            Path outFile = FileUtility.createNewFile(srcFile, operation, key);
            Files.writeString(outFile, text);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
