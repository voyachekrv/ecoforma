package com.ecoforma.frontend.forms;

import com.ecoforma.db.entities.OrderWithPrepayment;
import com.ecoforma.db.entities.OrderWithoutPrepayment;
import com.ecoforma.db.services.SaleService;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;

import static com.ecoforma.frontend.services.JComponentFactory.*;
import static com.ecoforma.App.saleForm;

public class TableOrders {
    JTextField tfSearch;
    JTable table;
    JScrollPane tableScroll;
    JRadioButton rbWithPrepayment, rbWithoutPrepayment;
    JRadioButton rbDate, rbCustomer, rbProduct, rbStore, rbEmployee;
    JSpinner spinnerSurcharge;
    JButton btnAddSurcharge;

    DefaultTableModel currentTableModel;

    private String[] columnsPrepaymentHeader = new String[] {
            "Код",
            "Дата",
            "Заказчик",
            "Товар",
            "Склад",
            "Продавец",
            "Количество",
            "Тип оплаты",
            "Предоплата",
            "Полная оплата"
    };

    private String[] columnsCommonHeader = new String[] {
            "Код",
            "Дата",
            "Заказчик",
            "Товар",
            "Склад",
            "Продавец",
            "Количество",
            "Тип оплаты",
            "Полная оплата"
    };

    SaleService dbService;

    int currentOrderID;

    public TableOrders(@NotNull JPanel panel) {
        dbService = new SaleService();

        tfSearch = newTextFieldEnabled(50, new Rectangle(12, 12, 128, 23));
        panel.add(tfSearch);

        JButton btnSearch = newButtonEnabled("Поиск", "icon-search", new Rectangle(152, 12, 90, 23));
        panel.add(btnSearch);

        JButton btnStopSearch = newButtonEnabled(null, "icon-close", new Rectangle(254, 12, 24, 23));
        btnStopSearch.setToolTipText("Очистка результов поиска");
        panel.add(btnStopSearch);

        JPanel panelTableOrders = newPanelBevelTable(12, 75, 1305, 483);
        panel.add(panelTableOrders);

        table = newTable(setCommonTableModel());
        currentTableModel = setCommonTableModel();

        tableScroll = newTableScroll(table, 1281, 458);
        panelTableOrders.add(tableScroll);

        rbWithoutPrepayment = newRadioButton("Без предоплаты", "null", new Rectangle(12, 43, 147, 24));
        rbWithoutPrepayment.setSelected(true);
        panel.add(rbWithoutPrepayment);

        rbWithPrepayment = newRadioButton("С предоплатой", "null", new Rectangle(162, 43, 134, 24));
        panel.add(rbWithPrepayment);

        ButtonGroup paymentTypesGroup = new ButtonGroup();
        paymentTypesGroup.add(rbWithoutPrepayment);
        paymentTypesGroup.add(rbWithPrepayment);

        rbDate = newRadioButton("Дата","orders.date", new Rectangle(286, 11, 59, 24));
        rbDate.setSelected(true);
        rbCustomer = newRadioButton("Заказчик", "customer.name", new Rectangle(349, 11, 90, 24));
        rbProduct = newRadioButton("Товар", "product.name", new Rectangle(443, 11, 65, 24));
        rbStore = newRadioButton("Склад", "store.name", new Rectangle(512, 11, 65, 24));
        rbEmployee = newRadioButton("Продавец", "employee.name", new Rectangle(581, 11,90, 24));

        panel.add(rbDate);
        panel.add(rbCustomer);
        panel.add(rbProduct);
        panel.add(rbStore);
        panel.add(rbEmployee);

        ButtonGroup searchGroup = new ButtonGroup();
        searchGroup.add(rbDate);
        searchGroup.add(rbCustomer);
        searchGroup.add(rbProduct);
        searchGroup.add(rbStore);
        searchGroup.add(rbEmployee);

        JLabel lAddSurcharge = newLabel("Добавить сумму", new Rectangle(12, 564, 101, 20));
        panel.add(lAddSurcharge);

        spinnerSurcharge = newSpinnerNumericDisabled(
                new SpinnerNumberModel(1, 1, 2999999, 100),
                new Rectangle(12, 596, 101, 22)
        );
        panel.add(spinnerSurcharge);

        btnAddSurcharge = newButton("Подтвердить", new Rectangle(125, 593, 111, 26));
        panel.add(btnAddSurcharge);

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(listSelectionEvent -> enableAddingOfSurcharge());

        rbWithPrepayment.addActionListener(actionEvent -> switchBetweenOrderTypes());
        rbWithoutPrepayment.addActionListener(actionEvent -> switchBetweenOrderTypes());

        btnSearch.addActionListener(actionEvent -> searchOrder());
        tfSearch.addActionListener(actionEvent -> searchOrder());

        btnStopSearch.addActionListener(actionEvent -> stopSearch());

        btnAddSurcharge.addActionListener(actionEvent -> addSurcharge(saleForm.frame));
    }

    @NotNull
    private DefaultTableModel setPrepaymentTableModel() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnsPrepaymentHeader);

        ArrayList<OrderWithPrepayment> result = dbService.getAllOrdersWithPrepayment();

        for (int i = 0; i < result.size(); i++) {
            model.insertRow(i, new Object[]{
                    result.get(i).getID(),
                    result.get(i).getDate(),
                    result.get(i).getCustomer(),
                    result.get(i).getProduct(),
                    result.get(i).getStore(),
                    result.get(i).getEmployee(),
                    result.get(i).getCount(),
                    result.get(i).getPaymentType(),
                    result.get(i).getPrepayment(),
                    result.get(i).getFullPayment()
            });
        }
        return model;
    }

    @NotNull
    private DefaultTableModel setCommonTableModel() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnsCommonHeader);

        ArrayList<OrderWithoutPrepayment> result = dbService.getAllOrdersWithoutPrepayment();

        for (int i = 0; i < result.size(); i++) {
            model.insertRow(i, new Object[]{
                    result.get(i).getID(),
                    result.get(i).getDate(),
                    result.get(i).getCustomer(),
                    result.get(i).getProduct(),
                    result.get(i).getStore(),
                    result.get(i).getEmployee(),
                    result.get(i).getCount(),
                    result.get(i).getPaymentType(),
                    result.get(i).getFullPayment()
            });
        }

        return model;
    }

    private void switchBetweenOrderTypes() {
        spinnerSurcharge.setEnabled(false);
        btnAddSurcharge.setEnabled(false);

        tableScroll.setViewportView(null);
        table = newTable(currentTableModel);
        tableScroll.setViewportView(table);

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(listSelectionEvent -> enableAddingOfSurcharge());

        if (rbWithoutPrepayment.isSelected()) {
            table.setModel(setCommonTableModel());
            currentTableModel = setCommonTableModel();
        } else if (rbWithPrepayment.isSelected()) {
            table.setModel(setPrepaymentTableModel());
            currentTableModel = setPrepaymentTableModel();
        }
    }

    private void showUnsuccessfulSearch(JFrame frame, String option, String query) {
        JOptionPane.showMessageDialog(
                frame,
                String.format(
                        "По запросу \"%s\" по критерию \"%s\" ничего не найдено.\n" +
                                "Попробуйте уточнить запрос или сменить категорию.",
                        query, option
                ),
                "Не найдено",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void searchOrder() {
        String query = "'%" + tfSearch.getText() + "%'";
        String column = "";
        String option = "";

        if (rbDate.isSelected()) {
            column = rbDate.getActionCommand();
            option = rbDate.getText();
        } else if (rbCustomer.isSelected()) {
            column = rbCustomer.getActionCommand();
            option = rbCustomer.getText();
        } else if (rbProduct.isSelected()) {
            column = rbProduct.getActionCommand();
            option = rbProduct.getText();
        } else if (rbStore.isSelected()) {
            column = rbStore.getActionCommand();
            option = rbStore.getText();
        } else if (rbEmployee.isSelected()) {
            column = rbEmployee.getActionCommand();
            option = rbEmployee.getText();
        }

        DefaultTableModel searchModel = new DefaultTableModel();

        if (rbWithoutPrepayment.isSelected()) {
            ArrayList<OrderWithoutPrepayment> result = dbService.searchOrdersWithoutPrepayment(column, query);

            if (result.size() == 0) {
                showUnsuccessfulSearch(saleForm.frame, option, tfSearch.getText());
            } else {
                searchModel.setColumnIdentifiers(columnsCommonHeader);
                for (int i = 0; i < result.size(); i++) {
                    searchModel.insertRow(i, new Object[]{
                            result.get(i).getID(),
                            result.get(i).getDate(),
                            result.get(i).getCustomer(),
                            result.get(i).getProduct(),
                            result.get(i).getStore(),
                            result.get(i).getEmployee(),
                            result.get(i).getCount(),
                            result.get(i).getPaymentType(),
                            result.get(i).getFullPayment()
                    });
                    table.setModel(searchModel);
                }
            }
        } else if (rbWithPrepayment.isSelected()) {
            ArrayList<OrderWithPrepayment> result = dbService.searchOrdersWithPrepayment(column, query);

            if (result.size() == 0) {
                showUnsuccessfulSearch(saleForm.frame, option, tfSearch.getText());
            } else {
                searchModel.setColumnIdentifiers(columnsPrepaymentHeader);
                for (int i = 0; i < result.size(); i++) {
                    searchModel.insertRow(i, new Object[]{
                            result.get(i).getID(),
                            result.get(i).getDate(),
                            result.get(i).getCustomer(),
                            result.get(i).getProduct(),
                            result.get(i).getStore(),
                            result.get(i).getEmployee(),
                            result.get(i).getCount(),
                            result.get(i).getPaymentType(),
                            result.get(i).getPrepayment(),
                            result.get(i).getFullPayment()
                    });
                    table.setModel(searchModel);
                }
            }
        }
    }

    private void stopSearch() {
        if (rbWithoutPrepayment.isSelected()) {
            table.setModel(setCommonTableModel());
        } else if (rbWithPrepayment.isSelected()) {
            table.setModel(setPrepaymentTableModel());
        }

        tfSearch.setText("");
    }

    private void enableAddingOfSurcharge() {
        if (rbWithPrepayment.isSelected()) {
            spinnerSurcharge.setEnabled(true);
            btnAddSurcharge.setEnabled(true);
            int rowIndex = table.getSelectedRow();
            currentOrderID = Integer.parseInt(table.getModel().getValueAt(rowIndex, 0).toString());
        }
    }

    private void addSurcharge(JFrame frame) {
        int surcharge = Integer.parseInt(spinnerSurcharge.getValue().toString());

        if (surcharge <= 0) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Вы не можете внести такую сумму.",
                    "Ошибка при обновлении",
                    JOptionPane.WARNING_MESSAGE
            );
        } else {
            dbService.addSurcharge(currentOrderID, surcharge);

            JOptionPane.showMessageDialog(
                    frame,
                    "Сумма успешно добавлена.",
                    "Добавлено",
                    JOptionPane.INFORMATION_MESSAGE
            );

            tableScroll.setViewportView(null);
            table = newTable(setPrepaymentTableModel());
            tableScroll.setViewportView(table);

            ListSelectionModel selectionModel = table.getSelectionModel();
            selectionModel.addListSelectionListener(listSelectionEvent -> enableAddingOfSurcharge());

            spinnerSurcharge.setEnabled(false);
            btnAddSurcharge.setEnabled(false);
        }
    }
}
