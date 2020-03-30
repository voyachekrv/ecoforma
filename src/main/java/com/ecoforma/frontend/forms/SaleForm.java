package com.ecoforma.frontend.forms;

import com.ecoforma.frontend.CompanyFrame;
import com.ecoforma.frontend.services.JComponentFactory;

import javax.swing.*;
import java.awt.*;

import static com.ecoforma.frontend.services.JComponentFactory.*;

public class SaleForm {
    CompanyFrame frame;
    JTabbedPane tabbedPane;
    JTextField tfSearch;

    JComponentFactory factory;

    public SaleForm(String department) {
        factory = new JComponentFactory();

        frame = new CompanyFrame(department);

        tabbedPane = JComponentFactory.newTabbedPane(0, 45, 1346, 716);
        frame.add(tabbedPane);

        JPanel panelOrders = newTabbedPaneElement();
        tabbedPane.addTab("Список заказов", null, panelOrders, null);

        JPanel panelCustomers = newTabbedPaneElement();
        tabbedPane.addTab("Список покупателей", null, panelCustomers, null);

        tfSearch = newTextFieldEnabled(50, new Rectangle(12, 12, 128, 23));
        panelCustomers.add(tfSearch);

        JButton btnSearch = newButtonEnabled("Поиск", "icon-search", new Rectangle(152, 12, 90, 23));
        panelCustomers.add(btnSearch);

        JButton btnStopSearch = newButtonEnabled(null, "icon-close", new Rectangle(254, 12, 24, 23));
        btnStopSearch.setToolTipText("Очистка результов поиска");
        panelCustomers.add(btnStopSearch);

    }
}