package com.ecoforma.frontend.forms;

import javax.swing.*;

import java.awt.*;

import static com.ecoforma.App.COMPANY_NAME;
import static com.ecoforma.App.saleForm;

import static com.ecoforma.frontend.services.JComponentFactory.*;
import static com.ecoforma.db.services.SaleService.*;

public class CashBoxForm {
    private final char CURRENCY = '₽';

    JFrame frame;

    public CashBoxForm() {
        frame = newFrame(COMPANY_NAME + " - Касса", new Rectangle(323,  144, 1352, 790), JFrame.DO_NOTHING_ON_CLOSE);

        JToolBar toolBar = newToolBar(0, 0, 1356,44);
        frame.add(toolBar);

        JButton btnClose = newButtonEnabled("Закрыть", "icon-logout", new Rectangle(1, 1, 1, 1));
        toolBar.add(btnClose);

        frame.setVisible(true);

        btnClose.addActionListener(actionEvent -> closeForm());
    }

    private void closeForm() {
        frame.setVisible(false);
        frame = null;
        saleForm.frame.setExtendedState(JFrame.NORMAL);
    }
}
