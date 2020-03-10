package com.ecoforma;

import javax.swing.*;

// Основной класс приложения
public class App {
    public static final String COMPANY_NAME = "Экоформа-Пенза"; // Название компании

    private static FormSignIn formSignIn; // Форма регистрации в системе

    public static void main(String[] args) {
        // Запуск формы в потоке обработки событий
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                formSignIn = new FormSignIn();
            }
        });
    }
}
