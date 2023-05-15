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
    private String key;


    public Running(String[] args) {
        this.args = args;
    }

    public void run() {
        if (args.length == 0) {
            app();
        } else {
            operation = args[0];
            path = args[1];
            key = args.length < 3 ? "" : args[2];
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
                    key = gui.key.getText();
                } else if (gui.buttonD.isSelected()) {
                    operation = "-d";
                    key = gui.key.getText();
                } else {
                    operation = "-bf";
                    key = gui.key.getText();
                }
                path = gui.path.getText();

                coding(path, operation, key);
                JOptionPane.showMessageDialog(null, "Successfully", "Operation", JOptionPane.PLAIN_MESSAGE);
            }
        });
    }

    private void coding(String path, String operation, String key) {
        try {
            Cryptograph cryptograph = new Cryptograph();
            FileUtility utility = new FileUtility();
            Path srcFile = Path.of(path);
            String text;
            int intKey;
            if ("-e".equals(operation)) {
                intKey = Integer.parseInt(key);
                text = cryptograph.encrypt(srcFile, intKey);
            } else if ("-d".equals(operation)) {
                intKey = Integer.parseInt(key);
                text = cryptograph.decrypt(srcFile, intKey);
            } else if ("-bf".equals(operation) && args.length < 3 && "".equals(key)) {
                intKey = cryptograph.bruteForce(srcFile);
                text = cryptograph.decrypt(srcFile, intKey);
            } else if ("-bf".equals(operation) && (args.length == 3 || !"".equals(key))) {
                Path additionalFile = Path.of(key);
                intKey = cryptograph.bruteForceStatistic(srcFile, additionalFile);
                text = cryptograph.decrypt(srcFile, intKey);
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect operation: " + operation, "Operation", JOptionPane.PLAIN_MESSAGE);
                throw new IllegalArgumentException("Incorrect operation");
            }
            Path outFile = null;
            try {
                outFile = utility.createNewFile(srcFile, operation, intKey);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "File not exist", "File", JOptionPane.PLAIN_MESSAGE);
                throw new IllegalArgumentException("Incorrect file path");
            }
            Files.writeString(outFile, text);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
