package Cryptographs;

import Contstants.Language;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static Contstants.Constants.ENG;
import static Contstants.Constants.UA;


public class BruteForce extends Cryptograph {

    private int key;

    @Override
    public Path createNewFile(Path srcPath) throws IOException {
        String srcFileName = srcPath.getFileName().toString();
        String srcFileExtension = srcFileName.substring(srcFileName.lastIndexOf("."));
        String newFileName = srcFileName.substring(0, srcFileName.lastIndexOf('.')) + "(B key-" + key + ")" + srcFileExtension;
        String parent = srcPath.getParent().toString();
        Path newFile = Path.of(parent, newFileName);
        if (Files.notExists(newFile)) {
            Files.createFile(newFile);
        }
        return newFile;
    }

    public int bruteForce(Path src) throws IOException {
        int key = 0;
        try (BufferedReader srcFile = new BufferedReader(new FileReader(src.toString()), 8192)) {
            StringBuilder builder = new StringBuilder();
            out:
            while (srcFile.ready()) {
                char[] line = srcFile.readLine().toCharArray();
                for (int i = 1; i < UA.size(); i++) {
                    for (char c : line) {
                        builder.append(codeByte(c, i));
                    }
                    String text = builder.toString();
                    if (checkLine(text)) {
                        key = i;
                        break out;
                    }
                    builder = new StringBuilder();
                }
            }
        }
        this.key = key;
        return key;
    }

    @Override
    public void codeText(Path src, Path out, int key) throws IOException {
        try (BufferedReader srcFile = new BufferedReader(new FileReader(src.toString()), 8192);
             BufferedWriter outFile = new BufferedWriter(new FileWriter(out.toString(), true))) {
            StringBuilder builder = new StringBuilder();
            while (srcFile.ready()) {
                int symbolByte;
                while ((symbolByte = srcFile.read()) != -1) {
                    builder.append(codeByte(symbolByte, key));
                }
                String text = builder.toString();
                outFile.write(text);
            }
        }
    }

    private char codeByte(int byteSymbol, int key) {
        char symbol = (char) byteSymbol;
        if (language == null) {
            checkLanguage(symbol);
        }
        List<Character> letters = language == Language.ENGLISH ? ENG : UA;
        return codeSymbol(symbol, key, letters);
    }

    private char codeSymbol(char symbol, int key, List<Character> letters) {
        if (letters.contains(symbol)) {
            int size = letters.size();
            int index = letters.indexOf(symbol);
            index = (index - key) % size;
            index = index < 0 ? (index + size) : index;
            symbol = letters.get(index);
        }
        return symbol;
    }

    private boolean checkLine(String line) {
        int length = line.length();
        return line.contains(", ") && Character.isLetter(line.charAt(0)) && Character.isLetter(line.charAt(length - 2));
    }
}