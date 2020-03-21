package com.ecoforma.forms;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.io.IOException;

public class NewEmployeeForm {
    JFrame frame;

    NewEmployeeForm() throws IOException {
        frame = new JFrame("Новый сотрудник"); // Основная панель формы
        frame.setSize(790, 790); // Установка размеров
        frame.setLocation(598,  144);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Установка операции закрытия окна приложения
        frame.setIconImage(ImageIO.read(getClass().getResource("/img/logo1.png")));
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
