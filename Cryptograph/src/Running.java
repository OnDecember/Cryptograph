import Cryptographs.BruteForce;
import Cryptographs.Cryptograph;
import GUI.CryptographGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
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
        if (!args[0].equals("-bf") && args.length < 3) {
            app();
        } else if (args[0].equals("-bf") && args.length < 2) {
            app();
        } else {
            operation = args[0];
            path = args[1];
            key = args[0].equals("-bf") ? 0 : Integer.parseInt(args[2]);
            coding(operation, path, key);
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

                coding(operation, path, key);
                JOptionPane.showMessageDialog(null, "Successfully", "Operation", JOptionPane.PLAIN_MESSAGE);
            }
        });
    }

    private void coding(String operation, String path, int key) {
        try {
            Cryptograph cryptograph = Cryptograph.createCryptograph(operation);
            Path srcFile = Path.of(path);
            Path outFile;

            if (cryptograph instanceof BruteForce) {
                key = ((BruteForce) cryptograph).bruteForce(srcFile);
            }
            outFile = cryptograph.createNewFile(srcFile);
            cryptograph.codeText(srcFile, outFile, key);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
