package com.ecoforma.frontend.services;

import org.jetbrains.annotations.NotNull;

public final class Checker {
    public static boolean checkTextField(@NotNull String text, int length) {
        return text.length() <= length && !(text.equals(""));
    }

    public static boolean checkNumericTextField(@NotNull String text, int length) {
        if (checkTextField(text, length)) {
            try {
                Long.parseLong(text);
            } catch(NumberFormatException | NullPointerException e) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }
}
