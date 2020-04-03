package com.ecoforma.frontend;

import com.ecoforma.frontend.services.JComponentFactory;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.io.IOException;

import static com.ecoforma.App.COMPANY_NAME;

import static com.ecoforma.App.signInForm;
import static com.ecoforma.App.hrForm;
import static com.ecoforma.App.newEmployeeForm;
import static com.ecoforma.App.storeForm;
import static com.ecoforma.App.saleForm;

import static com.ecoforma.frontend.services.JComponentFactory.*;

public class CompanyFrame extends JFrame {
    public JComponentFactory factory;

    public CompanyFrame(String title) {
        factory = new JComponentFactory();

        this.setTitle(COMPANY_NAME + " - " + title);
        this.setSize(1352, 790); // Установка размеров
        this.setLocation(323,  144);
        this.setResizable(false);

        try {
            this.setIconImage(ImageIO.read(getClass().getResource("/img/logo1.png")));
        } catch (IOException e) {
            throw new RuntimeException("Иконка не найдена");
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Установка операции закрытия окна приложения
        this.getContentPane().setLayout(null);

        JToolBar toolBar = newToolBar(0, 0, 1356, 44);
        this.getContentPane().add(toolBar);

        JButton btnSignOut = newButtonEnabled("Выход из системы", "icon-logout", new Rectangle(0, 0, 1350, 44));
        toolBar.add(btnSignOut);

        btnSignOut.addActionListener(actionEvent -> signOut());
    }

    private void signOut() {
        this.setVisible(false);
        signInForm.frame.setVisible(true);
        newEmployeeForm = null;
        storeForm = null;
        hrForm = null;
        saleForm = null;
    }
}
