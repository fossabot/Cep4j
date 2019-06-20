package me.leo.cepj4;

import me.leo.cepj4.exceptions.InvalidCepLengthException;

public class Util {
    private Util() {
        throw new IllegalStateException("Utility class");
    }


    private static String removeSpecialCharacters(String cep) {
        return cep.replaceAll("\\D", "");
    }

    private static String validateInputLenght(String normalizedCep) {
        if (normalizedCep.length() != 8) {
            throw new InvalidCepLengthException(normalizedCep);
        }
        return normalizedCep;
    }

    public static String tratarInput(String input) {
        return validateInputLenght(removeSpecialCharacters(input));
    }

}
