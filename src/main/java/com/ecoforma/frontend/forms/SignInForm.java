package com.ecoforma.frontend.forms;

import com.ecoforma.db.services.SignInService;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Objects;

import static com.ecoforma.App.*;

// Форма регистрации в систем
public class SignInForm {
    public JFrame frame;
    private JTextField tfLogin; // Поле для ввода логина
    private JPasswordField tfPassword; // Поле для ввода пароля

    private SignInService dbService;

    public SignInForm() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Размеры окна
        dbService = new SignInService();

        // Форма
        frame = new JFrame(COMPANY_NAME + " - Вход в систему"); // Основная панель формы
        frame.setLayout(new FlowLayout());
        frame.setSize(375, 400); // Установка размеров
        frame.setLocation((screenSize.width / 3) + 130, (screenSize.height / 4) + 30); // Установка положения на экране
        frame.setResizable(false); // Запрет изменения размера окна
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Установка операции закрытия окна приложения

        try {
            frame.setIconImage(ImageIO.read(getClass().getResource("/img/logo1.png")));
        } catch (IOException e) {
            throw new RuntimeException();
        }

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
        JButton bSignIn = new JButton("Войти");
        bSignIn.setPreferredSize(new Dimension(88, 26));
        bSignIn.setFocusPainted(false);

        // Кнопка очистки данных
        JButton bClear = new JButton("Очистить");
        bClear.setFocusPainted(false);

        // Вспомогательный диспетчер компоновки для текстовых полей формы
        GridLayout tfLayout = new GridLayout(2, 1, 1, 1);

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

        // Добавление на форму всех элементов
        grid.add(loginPanel);
        grid.add(passwordPanel);
        grid.add(buttonPanel);

        frame.add(new JLabel(new ImageIcon(getClass().getResource("/img/logo2.png")))); // Логотип фирмы
        frame.add(grid);

        bClear.addActionListener(actionEvent -> clearTextFields()); // Очистка полей формы по кнопке "Очистить"

        bSignIn.addActionListener(actionEvent -> signIn()); // Обработка входа

        // Добавление нажатия кнопки Enter для входа в систему
        tfPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    signIn();
                }
            }
        });

        // Установка видимости формы
        frame.setVisible(true);

        tfLogin.setText("kassa");
        tfPassword.setText("kkk");
    }

    // Очистка полей ввода
    private void clearTextFields() {
        tfLogin.setText("");
        tfPassword.setText("");
        tfLogin.requestFocus();
    }

    // Преобразование в строку пароля, полученного с форма
    @NotNull
    private String buildClientPassword(@NotNull char[] chars) {
        StringBuilder clientPassword = new StringBuilder();

        for (char passwordChar: chars) {
            clientPassword.append(passwordChar);
        }

        return clientPassword.toString();
    }

    // Вход в систему
    private void signIn() {
        String sessionType = dbService.getSessionType(tfLogin.getText(), buildClientPassword(tfPassword.getPassword()));

        if (Objects.isNull(sessionType)) {
            JOptionPane.showMessageDialog(frame, "Неверный логин или пароль", "Ошибка", JOptionPane.WARNING_MESSAGE);
            clearTextFields();
        } else  {
            frame.setVisible(false);
            switch (sessionType) {
                case "Отдел кадров":
                    hrForm = new HRForm(sessionType);
                    hrForm.frame.setVisible(true);
                    break;
                case "Склад":
                    storeForm = new StoreForm(tfLogin.getText(), buildClientPassword(tfPassword.getPassword()));
                    storeForm.frame.setVisible(true);
                    break;
                case "Отдел продаж":
                    saleForm = new SaleForm(sessionType);
                    saleForm.frame.setVisible(true);
                    break;
                default: JOptionPane.showMessageDialog(
                        frame,
                        "Для данной роли не доступен интерфейс",
                        "Системная ошибка",
                        JOptionPane.ERROR_MESSAGE
                );
                    frame.setVisible(true);
                    break;
            }
        }
    }
}

