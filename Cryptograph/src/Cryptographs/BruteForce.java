package Cryptographs;

import Contstants.Language;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static Contstants.Constants.ENG;
import static Contstants.Constants.UA;


public class BruteForce extends Cryptograph {

    private int key;

    public int bruteForce(Path src) throws IOException {
        int key = 0;
        List<String> strings = Files.readAllLines(src);
        StringBuilder builder = new StringBuilder();
        out:
        for (String string : strings) {
            for (int i = 1; i < UA.size(); i++) {
                for (char symbol : string.toCharArray()) {
                    builder.append(checkLanguage(symbol, i));
                }
                if (builder.toString().matches("\\s*[A-ZА-ЯІЇЄ0-9][a-zа-яіїє0-9]*,?(\\s[A-ZА-ЯІЇЄ0-9]?[a-zа-яіїє0-9]{1,12}\\b,?)+\\.?\n?")) {
                    key = i;
                    break out;
                }
                builder = new StringBuilder();
            }
        }
        this.key = key;
        return key;
    }

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

    public int getKey() {
        return key;
    }
}