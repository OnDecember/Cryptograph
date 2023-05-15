package Cryptograph;

import util.LanguageUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cryptograph {

    private List<Character> alphabet;
    private final LanguageUtil util = new LanguageUtil();

    public String encrypt(Path src, int key) throws IOException {
        key = validateKey(key);
        char[] charsText = Files.readString(src).toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char symbol : charsText) {
            builder.append(encryptSymbol(symbol, key));
        }
        return builder.toString();
    }

    public String decrypt(Path src, int key) throws IOException {
        key = validateKey(key);
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
            for (int i = 1; i < 100; i++) {
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

    public int bruteForceStatistic(Path src, Path additionalFile) throws IOException {
        char[] charsText = Files.readString(additionalFile).toCharArray();
        double accuracy = charsText.length > 100_000 ? 0.01 : 0.1;
        Map<Character, Double> srcChancesMap = calculatedChance(src);
        Map<Character, Double> additionalChancesMap = calculatedChance(additionalFile);
        char symbol1 = 0;
        char symbol2 = 0;
        out:
        for (Map.Entry<Character, Double> srcChancesEntry : additionalChancesMap.entrySet()) {
            for (Map.Entry<Character, Double> additionalChancesEntry : srcChancesMap.entrySet()) {
                if (Math.abs(additionalChancesEntry.getValue() - srcChancesEntry.getValue()) < accuracy) {
                    if (Character.isLetter(srcChancesEntry.getKey())) {
                        symbol1 = srcChancesEntry.getKey();
                        symbol2 = additionalChancesEntry.getKey();
                        break out;
                    }
                }
            }
        }
        alphabet = util.alphabet(symbol1);
        int indexSymbol1 = alphabet.indexOf(symbol1);
        int indexSymbol2 = alphabet.indexOf(symbol2);
        int key = indexSymbol2 - indexSymbol1;
        key = key > 0 ? key : key + alphabet.size();
        return key;
    }

    private char encryptSymbol(char symbol, int key) {
        if (alphabet == null) {
            alphabet = util.alphabet(symbol);
        }
        if (alphabet.contains(symbol)) {
            int size = alphabet.size();
            int index = alphabet.indexOf(symbol);
            index = (index + key) % size;
            symbol = alphabet.get(index);
        }
        return symbol;
    }

    private char decryptSymbol(char symbol, int key) {
        if (alphabet == null) {
            alphabet = util.alphabet(symbol);
        }
        if (alphabet.contains(symbol)) {
            int size = alphabet.size();
            int index = alphabet.indexOf(symbol);
            index = (index - key) % size;
            index = index < 0 ? (index + size) : index;
            symbol = alphabet.get(index);
        }
        return symbol;
    }

    private Map<Character, Double> calculatedChance(Path file) throws IOException {
        Map<Character, Integer> countSymbolsMap = new HashMap<>();
        Map<Character, Double> chancesMap = new HashMap<>();
        char[] charsText = Files.readString(file).toCharArray();
        int length = charsText.length;
        for (char symbol : charsText) {
            Integer integer = countSymbolsMap.get(symbol);
            if (integer == null) {
                integer = 0;
            }
            countSymbolsMap.put(symbol, integer + 1);
        }
        for (Map.Entry<Character, Integer> characterIntegerEntry : countSymbolsMap.entrySet()) {
            chancesMap.put(characterIntegerEntry.getKey(), 1. * characterIntegerEntry.getValue() / length * 100);
        }
        return chancesMap;
    }

    private int validateKey(int key) {
        return Math.abs(key);
    }
}