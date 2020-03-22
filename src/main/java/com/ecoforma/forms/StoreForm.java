package com.ecoforma.forms;

import com.ecoforma.services.Initializer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static com.ecoforma.App.COMPANY_NAME;

public class StoreForm {
    JFrame frame;
    JToolBar toolBar;
    JButton btnSignOut;

    Initializer initializer;

    public StoreForm(String department) throws IOException {
        initializer = new Initializer();

        frame = initializer.newFrame(COMPANY_NAME + " - " + department, new Rectangle(323,  144, 1362, 790), JFrame.EXIT_ON_CLOSE);

        toolBar = initializer.newToolBar(0, 0, 1346, 44);
        frame.getContentPane().add(toolBar);

        btnSignOut = initializer.newButtonEnabled("Выход из системы", "icon-logout", new Rectangle(0, 0, 1346, 44));
        toolBar.add(btnSignOut);

        frame.setVisible(true);
    }
}
