import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Path;

public class Main {
    private static String operation;
    private static String path;
    private static int key;

    public static void main(String[] args) {
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

                Cryptograph cryptograph = Cryptograph.createCryptograph(operation);
                try {
                    Path srcFile = Path.of(path);
                    Path outFile = cryptograph.createNewFile(srcFile);

                    if (cryptograph instanceof BruteForce) {
                        key = ((BruteForce) cryptograph).bruteForce(srcFile, outFile);
                    } else {
                        cryptograph.code(srcFile, outFile, key);
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Successfully" + (operation.equals("-bf") ? "\nkey was: " + key : ""), "Operation", JOptionPane.PLAIN_MESSAGE);
            }
        });
    }
}