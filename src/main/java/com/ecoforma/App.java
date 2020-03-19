package com.ecoforma;

import com.ecoforma.db.DbSession;
import com.ecoforma.forms.*;
import org.apache.ibatis.session.SqlSession;

import javax.swing.*;
import java.io.IOException;

// Основной класс приложения
public class App {
    public static final String COMPANY_NAME = "Экоформа-Пенза"; // Название компании

    public static MainForm mainForm;
    public static SignInForm signInForm;
    public static SqlSession session = DbSession.startSession();

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
