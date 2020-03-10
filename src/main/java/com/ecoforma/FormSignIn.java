package com.ecoforma;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.ecoforma.App.COMPANY_NAME;

// Форма регистрации в систем
public class FormSignIn {
    private JFrame frame;
    private JTextField tfLogin; // Поле для ввода логина
    private JPasswordField tfPassword; // Поле для ввода пароля
    FormDirector director;

    private final String testLogin = "roma";
    private final char[] testPassword = {'1', '2', '3'};

    FormSignIn() throws IOException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Размеры окна
        Path pathToIcon = Paths.get("C:", "ecoforma", "src", "main", "resources", "logo1.png"); // Путь к иконке приложения

        frame = new JFrame(COMPANY_NAME + " - Вход в систему"); // Основная панель формы
        frame.setLayout(new FlowLayout());
        frame.setSize(375, 400); // Установка размеров
        frame.setLocation((screenSize.width / 2) - 130, (screenSize.height / 2) - 190); // Установка положения на экране
        frame.setResizable(false); // Запрет изменения размера окна
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Установка операции закрытия окна приложения
        frame.setIconImage(ImageIO.read(new File(pathToIcon.toString())));

        JPanel grid = new JPanel(); // Панель для компоновки элементов формы
        grid.setLayout(new GridLayout(3, 1, 1, 10)); // Установка диспетчера компоновки "Таблица"
        grid.setPreferredSize(new Dimension(300, 200)); // Установка размеров панели

        // Задание свойств полю логина
        tfLogin = new JTextField(20);
        tfLogin.setFont(new Font("Default", Font.PLAIN, 14));

        // Задание свойств полю пароля
        tfPassword = new JPasswordField(40);
        tfPassword.setFont(new Font("Default", Font.PLAIN, 14));

        // Кнопка подтверждения входа
        final JButton bSignIn = new JButton("Войти");
        bSignIn.setPreferredSize(new Dimension(88, 26));
        bSignIn.setFocusPainted(false);

        // Кнопка очистки данных
        JButton bClear = new JButton("Очистить");
        bClear.setFocusPainted(false);

        // Вспомогательный диспетчер компоновки для текстовых полей формы
        GridLayout tfLayout = new GridLayout(2, 1,1, 1);

        // Компоновка поля ввода логина
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(tfLayout);
        loginPanel.add(new JLabel("Логин"));
        loginPanel.add(tfLogin);

        // Компоновка поля ввода пароля
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(tfLayout);
        passwordPanel.add(new JLabel("Пароль"));
        passwordPanel.add(tfPassword);

        // Компоновка кнопки подтверждения входа
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 3.0;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;

        buttonPanel.add(bSignIn, constraints);
        buttonPanel.add(bClear);

        // Логотип фирмы
        Path pathToPicture = Paths.get("C:", "ecoforma", "src", "main", "resources", "logo2.png");
        ImageIcon mainIcon = new ImageIcon(pathToPicture.toString());

        // Добавление на форму всех элементов
        grid.add(loginPanel);
        grid.add(passwordPanel);
        grid.add(buttonPanel);

        frame.add(new JLabel(mainIcon));
        frame.add(grid);

        // Установка видимости формы
        frame.setVisible(true);

        // Очистка полей формы по кнопке "Очистить"
        bClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tfLogin.setText("");
                tfPassword.setText("");
            }
        });

        // Обработка входа
        bSignIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (tfLogin.getText().equals(testLogin) && passwordEqualing(testPassword, tfPassword.getPassword())) {
                    frame.setVisible(false);
                    frame = null;
                    try {
                        director = new FormDirector();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Неверный логин или пароль", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    tfLogin.setText("");
                    tfPassword.setText("");
                }
            }
        });
    }

    private boolean passwordEqualing(@NotNull char[] r, @NotNull char[] m) {
        for (byte i = 0; i < r.length; i++) {
            if (r[i] != m[i]) {
                return false;
            }
        }
        return true;
    }
}
