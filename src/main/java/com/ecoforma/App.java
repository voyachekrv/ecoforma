package com.ecoforma;

import javax.swing.*;
import java.io.IOException;

// Основной класс приложения
public class App {
    public static final String COMPANY_NAME = "Экоформа-Пенза"; // Название компании

    private static FormSignIn formSignIn; // Форма регистрации в системе

    public static void main(String[] args) {
        // Запуск формы в потоке обработки событий
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    formSignIn = new FormSignIn();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        });
    }
}
