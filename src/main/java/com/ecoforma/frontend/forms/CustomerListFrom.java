package com.ecoforma.frontend.forms;

import com.ecoforma.db.entities.Customer;
import com.ecoforma.db.services.SaleService;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Objects;

import static com.ecoforma.frontend.services.JComponentFactory.*;

import static com.ecoforma.App.cashBoxForm;

public class CustomerListFrom {
    JFrame frame;
    JTable table;
    JScrollPane tableScroll;
    JTextField tfSearch;
    JButton btnSearch, btnStopSearch, btnAccept, btnCancel;
    JRadioButton rbIndividual, rbLegal;
    JRadioButton rbName, rbAddress, rbPhone;

    SaleService dbService;

    Customer currentCustomer;

    private String[] columnsHeader = new String[] {
            "Код заказчика",
            "Имя",
            "Адрес доставки",
            "Телефон"
    };

    public CustomerListFrom() {
        dbService = new SaleService();

        frame = newFrame("Список заказчиков", new Rectangle(598,  234, 800, 600),
                new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        quit();
                    }
                }
        );
        frame.setVisible(true);

        tfSearch = newTextFieldEnabled(50, new Rectangle(12, 12, 200, 26));
        frame.add(tfSearch);

        btnSearch = newButtonEnabled("Поиск", "icon-search", new Rectangle(232, 14, 122, 23));
        frame.add(btnSearch);

        btnStopSearch = newButtonEnabled(null, "icon-close", new Rectangle(366, 14, 24, 23));
        frame.add(btnStopSearch);

        rbIndividual = newRadioButton("Физ.лица", "0", new Rectangle(398, 13, 91, 24));
        rbIndividual.setSelected(true);
        frame.add(rbIndividual);

        rbLegal = newRadioButton("Юрид.лица", "1", new Rectangle(493, 13, 91, 24));
        frame.add(rbLegal);

        ButtonGroup personGroup = new ButtonGroup();
        personGroup.add(rbIndividual);
        personGroup.add(rbLegal);

        JPanel tablePanel = newPanelBevelTable(12, 78, 770, 445);
        frame.add(tablePanel);

        table = newTable(setInitialTableModel());

        tableScroll = newTableScroll(table, 746, 421);
        tablePanel.add(tableScroll);

        rbName = newRadioButton("Имя", "name", new Rectangle(12, 46, 56, 24));
        rbName.setSelected(true);
        frame.add(rbName);

        rbAddress = newRadioButton("Адрес", "adress", new Rectangle(72, 46, 68, 24));
        frame.add(rbAddress);

        rbPhone = newRadioButton("Телефон", "phoneNumber", new Rectangle(144, 46, 79, 24));
        frame.add(rbPhone);

        ButtonGroup customerDataGroup = new ButtonGroup();
        customerDataGroup.add(rbName);
        customerDataGroup.add(rbAddress);
        customerDataGroup.add(rbPhone);

        btnAccept = newButton("Добавить заказчика", new Rectangle(493, 533, 179, 26));
        frame.add(btnAccept);

        btnCancel = newButtonEnabled("Отмена", new Rectangle(684, 533, 98, 26));
        frame.add(btnCancel);

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(listSelectionEvent -> selectCustomer());

        rbLegal.addActionListener(actionEvent -> switchBetweenPersons());

        rbIndividual.addActionListener(actionEvent -> switchBetweenPersons());

        btnSearch.addActionListener(actionEvent -> searchCustomer());

        btnStopSearch.addActionListener(actionEvent -> stopSearch());

        btnCancel.addActionListener(actionEvent -> quit());

        btnAccept.addActionListener(actionEvent -> setCustomerInformation());
    }

    private void switchBetweenPersons() {
        stopSearch();
        table.setModel(setInitialTableModel());
    }

    @NotNull
    private DefaultTableModel setInitialTableModel() {
        DefaultTableModel initialTableModel = new DefaultTableModel();
        initialTableModel.setColumnIdentifiers(columnsHeader);

        ArrayList<Customer> data;

        if (rbLegal.isSelected()) {
            data = dbService.getCustomersOnList(Integer.parseInt(rbLegal.getActionCommand()));
        } else {
            data = dbService.getCustomersOnList(Integer.parseInt(rbIndividual.getActionCommand()));
        }

        for (int i = 0; i < data.size(); i++) {
            initialTableModel.insertRow(i, new Object[] {
                    data.get(i).getID(),
                    data.get(i).getName(),
                    data.get(i).getAdress(),
                    data.get(i).getPhoneNumber()
            });
        }
        return initialTableModel;
    }

    private void selectCustomer() {
        int rowIndex = table.getSelectedRow();
        currentCustomer = new Customer();
        currentCustomer.setID(Integer.parseInt(table.getModel().getValueAt(rowIndex, 0).toString()));
        currentCustomer.setName(table.getModel().getValueAt(rowIndex, 1).toString());
        currentCustomer.setAdress(table.getModel().getValueAt(rowIndex, 2).toString());
        currentCustomer.setPhoneNumber(table.getModel().getValueAt(rowIndex, 3).toString());

        btnAccept.setEnabled(true);
    }

    private void searchCustomer() {
        DefaultTableModel searchModel = new DefaultTableModel();
        searchModel.setColumnIdentifiers(columnsHeader);

        String column = "";
        String query = "'%" + tfSearch.getText() + "%'";
        String option = "";

        ArrayList<Customer> result = new ArrayList<>();

        if (rbName.isSelected()) {
            column = rbName.getActionCommand();
            option = rbName.getText();
        } else if (rbAddress.isSelected()) {
            column = rbAddress.getActionCommand();
            option = rbAddress.getText();
        } else if(rbPhone.isSelected()) {
            column = rbPhone.getActionCommand();
            option = rbPhone.getText();
        }

        if (rbIndividual.isSelected()) {
            result = dbService.searchCustomerOnList(column, query, Integer.parseInt(rbIndividual.getActionCommand()));
        } else if (rbLegal.isSelected()) {
            result = dbService.searchCustomerOnList(column, query, Integer.parseInt(rbLegal.getActionCommand()));
        }

        if (result.size() == 0) {
            JOptionPane.showMessageDialog(
                    frame,
                    String.format(
                            "По запросу \"%s\" по критерию \"%s\" ничего не найдено.\n" +
                                    "Попробуйте уточнить запрос или сменить категорию.",
                            tfSearch.getText(), option
                    ),
                    "Не найдено",
                    JOptionPane.INFORMATION_MESSAGE
            );
            tfSearch.setText("");
            table.setModel(setInitialTableModel());
        } else {
            for (int i = 0; i < result.size(); i++) {
                searchModel.insertRow(i, new Object[] {
                        result.get(i).getID(),
                        result.get(i).getName(),
                        result.get(i).getAdress(),
                        result.get(i).getPhoneNumber()
                });
            }
            table.setModel(searchModel);
        }
    }

    private void stopSearch() {
        if (!(Objects.isNull(table.getSelectedRow()))) {
            tableScroll.setViewportView(null);

            table = newTable(setInitialTableModel());
            tableScroll.setViewportView(table);
            ListSelectionModel selectionModel = table.getSelectionModel();
            selectionModel.addListSelectionListener(listSelectionEvent -> selectCustomer());
        }

        if (!(table.getModel().equals(setInitialTableModel()))) {
            table.setModel(setInitialTableModel());
        }

        tfSearch.setText("");
        currentCustomer = null;
        btnAccept.setEnabled(false);
    }

    private void quit() {
        frame.setVisible(false);
        cashBoxForm.frame.setEnabled(true);
        frame = null;
    }

    private void setCustomerInformation() {
        cashBoxForm.currentCustomer = new Customer();
        cashBoxForm.currentCustomer.setID(this.currentCustomer.getID());
        cashBoxForm.currentCustomer.setName(this.currentCustomer.getName());
        cashBoxForm.currentCustomer.setAdress(this.currentCustomer.getAdress());
        cashBoxForm.currentCustomer.setPhoneNumber(this.currentCustomer.getPhoneNumber());

        cashBoxForm.lCustomerNameVal.setText(cashBoxForm.currentCustomer.getName());
        cashBoxForm.lCustomerAddressVal.setText(cashBoxForm.currentCustomer.getAdress());
        cashBoxForm.lCustomerPhoneVal.setText(cashBoxForm.currentCustomer.getPhoneNumber());
        cashBoxForm.setPaymentEnabled();
        quit();
    }
}
