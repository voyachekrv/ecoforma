package com.ecoforma.frontend;

import com.ecoforma.frontend.services.Initializer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.io.IOException;

import static com.ecoforma.App.COMPANY_NAME;
import static com.ecoforma.App.signInForm;

public class CompanyFrame extends JFrame {
    public Initializer initializer;

    public CompanyFrame(String title) throws IOException {
        initializer = new Initializer();

        this.setTitle(COMPANY_NAME + " - " + title);
        this.setSize(1362, 790); // Установка размеров
        this.setLocation(323,  144);
        this.setResizable(false);
        this.setIconImage(ImageIO.read(getClass().getResource("/img/logo1.png")));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Установка операции закрытия окна приложения
        this.getContentPane().setLayout(null);

        JToolBar toolBar = initializer.newToolBar(0, 0, 1346, 44);
        this.getContentPane().add(toolBar);

        JButton btnSignOut = initializer.newButtonEnabled("Выход из системы", "icon-logout", new Rectangle(0, 0, 1350, 44));
        toolBar.add(btnSignOut);

        btnSignOut.addActionListener(actionEvent -> signOut());
    }

    private void signOut() {
        this.setVisible(false);
        signInForm.frame.setVisible(true);
    }
}
