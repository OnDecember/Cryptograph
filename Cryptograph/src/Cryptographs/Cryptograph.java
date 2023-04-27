package Cryptographs;

import Contstants.Language;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static Contstants.Constants.ENG;
import static Contstants.Constants.UA;

public abstract class Cryptograph {

    protected Language language;

    public Path createNewFile(Path srcPath) throws IOException {
        String suffix = "";
        if (this instanceof BruteForce) {
            suffix = "(B key-" + ((BruteForce) this).getKey() + ")";
        } else if (this instanceof Encrypter) {
            suffix = " [ENCRYPTED]";
        } else if (this instanceof  Decrypter) {
            suffix = " [DECRYPTED]";
        }
        String srcFileName = srcPath.getFileName().toString();
        String srcFileExtension = srcFileName.substring(srcFileName.lastIndexOf("."));
        String newFileName = srcFileName.substring(0, srcFileName.lastIndexOf('.')) + suffix + srcFileExtension;
        String parent = srcPath.getParent().toString();
        Path newFile = Path.of(parent, newFileName);
        if (Files.notExists(newFile)) {
            Files.createFile(newFile);
        }
        return newFile;
    }

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

    protected void language(char symbol) {
        if (ENG.contains(symbol)) {
            language = Language.ENGLISH;
        } else if (UA.contains(symbol)) {
            language = Language.UKRAINE;
        }
    }
}