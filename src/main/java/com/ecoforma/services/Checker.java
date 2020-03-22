package com.ecoforma.services;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class Checker {
    public boolean checkTextField(@NotNull JTextField tf) {
        return tf.getText().length() <= tf.getColumns() && !(tf.getText().equals(""));
    }

    public boolean checkTextField(@NotNull JEditorPane editorPane, int length) {
        return editorPane.getText().length() <= length && !(editorPane.getText().equals(""));
    }

    public boolean checkNumericTextField(@NotNull JTextField tf) {
        if (checkTextField(tf)) {
            try {
                Long.parseLong(tf.getText());
            } catch(NumberFormatException | NullPointerException e) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean checkDateTextField(@NotNull JTextField tf) {
        if (checkTextField(tf)) {
            String[] date = tf.getText().split("-");
            int numericMonth;
            int numericDay;

            if (date.length != 3) {
                return false;
            }
            if (date[0].length() == 4) {
                int numericYear;
                try {
                    numericYear = Integer.parseInt(date[0]);
                } catch(NumberFormatException | NullPointerException e) {
                    return false;
                }
                if (numericYear > 2038 || numericYear < 1970) {
                    return false;
                }
            } else  {
                return false;
            }

            if (date[1].length() == 2) {
                try {
                    numericMonth = Integer.parseInt(date[1]);
                } catch(NumberFormatException | NullPointerException e) {
                    return false;
                }
                if (numericMonth > 12 || numericMonth < 1) {
                    return false;
                }
            } else {
                return false;
            }

            if (date[2].length() == 2) {
                try {
                    numericDay = Integer.parseInt(date[2]);
                } catch(NumberFormatException | NullPointerException e) {
                    return false;
                }

                if (numericDay > 31 || numericDay < 1) {
                    return false;
                }

                if (numericDay == 31 && (numericMonth == 4 || numericMonth == 6 || numericMonth == 9 || numericMonth == 11)) {
                    return false;
                }

                if (numericDay > 28 && numericMonth == 2) {
                    return false;
                }
            } else {
                return false;
            }

            return true;

        } else {
            return false;
        }
    }
}