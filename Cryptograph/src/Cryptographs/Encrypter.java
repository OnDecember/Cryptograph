package Cryptographs;

import Contstants.Language;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static Contstants.Constants.ENG;
import static Contstants.Constants.UA;

public class Encrypter extends Cryptograph {

    @Override
    public Path createNewFile(Path srcPath) throws IOException {
        String srcFileName = srcPath.getFileName().toString();
        String srcFileExtension = srcFileName.substring(srcFileName.lastIndexOf("."));
        String newFileName = srcFileName.substring(0, srcFileName.lastIndexOf('.')) + " [ENCRYPTED]" + srcFileExtension;
        String parent = srcPath.getParent().toString();
        Path newFile = Path.of(parent, newFileName);
        if (Files.notExists(newFile)) {
            Files.createFile(newFile);
        }
        return newFile;
    }

    @Override
    public void codeText(Path src, Path out, int key) throws IOException {
        try (BufferedReader srcFile = new BufferedReader(new FileReader(src.toString()), 8192);
             BufferedWriter outFile = new BufferedWriter(new FileWriter(out.toString(), true))) {
            StringBuilder builder = new StringBuilder();
            while (srcFile.ready()) {
                int byteSymbol;
                while ((byteSymbol = srcFile.read()) != -1) {
                    builder.append(codeByte(byteSymbol, key));
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
            index = (index + key) % size;
            symbol = letters.get(index);
        }
        return symbol;
    }
}