package Cryptographs;

import Contstants.Language;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;

import static Contstants.Constants.ENG;
import static Contstants.Constants.UA;

public abstract class Cryptograph {
    protected Language language;

    abstract public Path createNewFile(Path srcPath) throws IOException;

    abstract public void codeText(Path src, Path out, int key) throws IOException;

    public static Cryptograph createCryptograph(String operation) {
        return switch (operation) {
            case "-e" -> new Encrypter();
            case "-d" -> new Decrypter();
            case "-bf" -> new BruteForce();
            default -> {
                JOptionPane.showMessageDialog(null, "Incorrect operation: " + operation, "Operation", JOptionPane.PLAIN_MESSAGE);
                throw new IllegalArgumentException("Incorrect operation");
            }
        };
    }

    protected void checkLanguage(char symbol) {
        if (ENG.contains(symbol)) {
            language = Language.ENGLISH;
        } else if (UA.contains(symbol)) {
            language = Language.UKRAINE;
        }
    }
}