package Cryptograph;

import util.LanguageUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Cryptograph {

    private List<Character> alphabet;

    public String encrypt(Path src, int key) throws IOException {
        char[] charsText = Files.readString(src).toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char symbol : charsText) {
            builder.append(encryptSymbol(symbol, key));
        }
        return builder.toString();
    }

    public String decrypt(Path src, int key) throws IOException {
        char[] charsText = Files.readString(src).toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char symbol : charsText) {
            builder.append(decryptSymbol(symbol, key));
        }
        return builder.toString();
    }

    public int bruteForce(Path src) throws IOException {
        int key = 0;
        List<String> strings = Files.readAllLines(src);
        StringBuilder builder = new StringBuilder();
        out:
        for (String string : strings) {
            for (int i = 1; i < 70; i++) {
                for (char symbol : string.toCharArray()) {
                    builder.append(decryptSymbol(symbol, i));
                }
                if (builder.toString().matches("\\s*[A-ZА-ЯІЇЄ0-9][a-zа-яіїє0-9]*,?(\\s[A-ZА-ЯІЇЄ0-9]?[a-zа-яіїє0-9]{1,12}\\b,?)+\\.?\n?")) {
                    key = i;
                    break out;
                }
                builder = new StringBuilder();
            }
        }
        return key;
    }

    private char encryptSymbol(char symbol, int key) {
        alphabet = LanguageUtil.alphabet(symbol);
        if (alphabet.contains(symbol)) {
            int size = alphabet.size();
            int index = alphabet.indexOf(symbol);
            index = (index + key) % size;
            symbol = alphabet.get(index);
        }
        return symbol;
    }

    private char decryptSymbol(char symbol, int key) {
        alphabet = LanguageUtil.alphabet(symbol);
        if (alphabet.contains(symbol)) {
            int size = alphabet.size();
            int index = alphabet.indexOf(symbol);
            index = (index - key) % size;
            index = index < 0 ? (index + size) : index;
            symbol = alphabet.get(index);
        }
        return symbol;
    }
}