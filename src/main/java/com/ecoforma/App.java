package com.ecoforma;

import com.ecoforma.frontend.forms.*;

import javax.swing.*;

// Основной класс приложения
public class App {
    public static final String COMPANY_NAME = "Экоформа-Пенза"; // Название компании

    public static SignInForm signInForm;
    public static HRForm hrForm;
    public static NewEmployeeForm newEmployeeForm;
    public static StoreForm storeForm;
    public static SaleForm saleForm;
    public static ContractsForm contractsForm;
    public static CashBoxForm cashBoxForm;
    public static CustomerListFrom customerListFrom;
    public static DeliveryForm deliveryForm;

    public static void main(String[] args) {
        // Запуск формы в потоке обработки событий
        SwingUtilities.invokeLater(() -> {
            signInForm = new SignInForm();
        });
    }
}
