package com.ecoforma;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.ecoforma.App.COMPANY_NAME;

public class FormDirector {
    FormDirector() throws IOException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Размеры окна
        Path pathToIcon = Paths.get("C:", "ecoforma", "src", "main", "resources", "img/logo1.png"); // Путь к иконке приложения

        JFrame frame = new JFrame(COMPANY_NAME + " - Директор"); // Основная панель формы
        frame.setLayout(new FlowLayout());
        frame.setSize(375, 400); // Установка размеров
        frame.setLocation((screenSize.width / 2) - 130, (screenSize.height / 2) - 190); // Установка положения на экране
        frame.setResizable(false); // Запрет изменения размера окна
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Установка операции закрытия окна приложения
        frame.setIconImage(ImageIO.read(new File(pathToIcon.toString())));
        frame.setVisible(true);
    }
}
