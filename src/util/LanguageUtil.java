package util;

import Contstants.Language;

import java.util.List;

import static Contstants.Constants.ENG;
import static Contstants.Constants.UA;

public class LanguageUtil {

    public List<Character> alphabet(char symbol) {
        return language(symbol) == Language.ENGLISH ? ENG : UA;
    }

    private Language language(char symbol) {
        Language language = null;
        if (ENG.contains(symbol)) {
            language = Language.ENGLISH;
        } else if (UA.contains(symbol)) {
            language = Language.UKRAINE;
        }
        return language;
    }
}
