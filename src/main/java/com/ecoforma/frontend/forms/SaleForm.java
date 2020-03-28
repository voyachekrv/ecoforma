package com.ecoforma.frontend.forms;

import com.ecoforma.frontend.CompanyFrame;
import com.ecoforma.frontend.services.JComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SaleForm {
    CompanyFrame frame;
    JTabbedPane tabbedPane;
    JTextField tfSearch;

    JComponentFactory factory;

    public SaleForm(String department) throws IOException {
        factory = new JComponentFactory();

        frame = new CompanyFrame(department);

        tabbedPane = factory.newTabbedPane(0, 45, 1346, 716);
        frame.add(tabbedPane);

        JPanel panelOrders = factory.newTabbedPaneElement();
        tabbedPane.addTab("Список заказов", null, panelOrders, null);

        JPanel panelCustomers = factory.newTabbedPaneElement();
        tabbedPane.addTab("Список покупателей", null, panelCustomers, null);

        tfSearch = frame.factory.newTextFieldEnabled(50, new Rectangle(12, 12, 128, 23));
        panelCustomers.add(tfSearch);

        JButton btnSearch = frame.factory.newButtonEnabled("Поиск", "icon-search", new Rectangle(152, 12, 90, 23));
        panelCustomers.add(btnSearch);

        JButton btnStopSearch = frame.factory.newButtonEnabled(null, "icon-close", new Rectangle(254, 12, 24, 23));
        btnStopSearch.setToolTipText("Очистка результов поиска");
        panelCustomers.add(btnStopSearch);

    }
}