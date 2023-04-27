package Cryptographs;

import Contstants.Language;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static Contstants.Constants.ENG;
import static Contstants.Constants.UA;

public class Decrypter extends Cryptograph {

    @Override
    public void codeText(Path src, Path out, int key) throws IOException {
        char[] charsText = Files.readString(src).toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char symbol : charsText) {
            builder.append(checkLanguage(symbol, key));
        }
        Files.writeString(out, builder.toString());
    }

    private char checkLanguage(char symbol, int key) {
        if (language == null) {
            language(symbol);
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
}