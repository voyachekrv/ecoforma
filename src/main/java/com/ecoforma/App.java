package com.ecoforma;

import com.ecoforma.forms.*;

import javax.swing.*;
import java.io.IOException;

// Основной класс приложения
public class App {
    public static final String COMPANY_NAME = "Экоформа-Пенза"; // Название компании

    public static SignInForm signInForm;
    public static HRForm hrForm;
    public static NewEmployeeForm newEmployeeForm;
    public static StoreForm storeForm;
    public static SaleForm saleForm;

    public static void main(String[] args) {
        // Запуск формы в потоке обработки событий
        SwingUtilities.invokeLater(() -> {
            try {
                signInForm = new SignInForm();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
