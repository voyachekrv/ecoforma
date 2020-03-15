package com.ecoforma.forms;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static com.ecoforma.App.COMPANY_NAME;

public class MainForm {
    JFrame frame;
    String currentSession;
    public MainForm(String department) throws IOException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Размеры окна

        frame = new JFrame(COMPANY_NAME + " - " + department); // Основная панель формы
        frame.setLayout(new FlowLayout());
        frame.setSize(screenSize.width, screenSize.height); // Установка размеров
        frame.setMinimumSize(new Dimension(375, 400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Установка операции закрытия окна приложения
        frame.setIconImage(ImageIO.read(getClass().getResource("/img/logo1.png")));

        currentSession = department;
    }
}
