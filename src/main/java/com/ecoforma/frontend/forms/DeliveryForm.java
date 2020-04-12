package com.ecoforma.frontend.forms;

import com.ecoforma.db.entities.Employee;
import com.ecoforma.db.entities.Order;
import com.ecoforma.db.services.DeliveryService;
import com.ecoforma.frontend.components.CompanyFrame;
import com.ecoforma.frontend.components.JLabelDefaultText;
import com.ecoforma.frontend.services.JComponentFactory;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static com.ecoforma.frontend.services.Checker.checkNumericTextField;
import static com.ecoforma.frontend.services.JComponentFactory.*;

public class DeliveryForm {
    JFrame frame;

    JTable tableOrders, tableEmployees;
    JScrollPane tableOrdersScroll, tableEmployeesScroll;
    JTextField tfSearch;
    JSpinner spinnerDeliveryStatus;
    JButton btnDeliveryStatus;
    JLabelDefaultText lInfProduct, lInfCount, lInfStoreAddress, lInfCustomerAddress, lInfCustomerName, lInfCustomerPhone;
    JButton btnStopSearch, btnSearch;

    DeliveryService dbService;

    Integer currentOrderID;
    Integer currentEmployeeID;

    private final String[] employeeColumns = new String[] {
            "Код",
            "Имя",
            "Телефон"
    };

    DeliveryForm(String role) {
        dbService = new DeliveryService();

        frame = new CompanyFrame(role);

        JPanel ordersPanel = newPanelBevelTable(12, 56, 918, 693);
        frame.add(ordersPanel);

        tableOrders = newTable(setOrdersTableModel());

        tableOrdersScroll = newTableScroll(tableOrders, 894, 669);
        ordersPanel.add(tableOrdersScroll);

        JPanel panelOrderInformation = newPanelTitled("Заказ", new Rectangle(942, 56, 392, 154));
        frame.add(panelOrderInformation);

        lInfProduct = new JLabelDefaultText("Товар: ", new Rectangle(12, 23, 180, 23));
        panelOrderInformation.add(lInfProduct);

        lInfCount = new JLabelDefaultText("Количество: ", new Rectangle(12, 73, 151, 23));
        panelOrderInformation.add(lInfCount);

        lInfStoreAddress = new JLabelDefaultText("Адр. склада: ", new Rectangle(12, 119, 151, 23));
        panelOrderInformation.add(lInfStoreAddress);

        lInfCustomerAddress = new JLabelDefaultText("Адр. доставки: ", new Rectangle(204, 20, 176, 28));
        panelOrderInformation.add(lInfCustomerAddress);

        lInfCustomerName = new JLabelDefaultText("Заказчик: ", new Rectangle(204, 73, 176, 23));
        panelOrderInformation.add(lInfCustomerName);

        lInfCustomerPhone = new JLabelDefaultText("Тел.:", new Rectangle(204, 119, 176, 23));
        panelOrderInformation.add(lInfCustomerPhone);

        JLabel lAddEmployee = newLabel("Назначить водителя", new Rectangle(942, 222, 122, 14));
        frame.add(lAddEmployee);

        tfSearch = newTextFieldDisabled(50, new Rectangle(942, 248, 198, 22));
        frame.add(tfSearch);

        btnSearch = newButton("Поиск", "icon-search", new Rectangle(1152, 247, 98, 23));
        frame.add(btnSearch);

        btnStopSearch = newButton(null, "icon-close", new Rectangle(1262, 246, 23, 24));
        frame.add(btnStopSearch);

        JPanel panelEmployees = newPanelBevelTable(942, 282, 392, 339);
        frame.add(panelEmployees);

        tableEmployees = newTableDisabled(setEmployeesTableModel());

        tableEmployeesScroll = newTableDisabledScroll(tableEmployees, 368, 315);
        panelEmployees.add(tableEmployeesScroll);

        JPanel panelDeliveryStatus = newPanelEtched(942, 633, 392, 78);
        frame.add(panelDeliveryStatus);

        JLabel lDeliveryStatus = newLabel("Статус доставки", new Rectangle(12, 16, 106, 16));
        panelDeliveryStatus.add(lDeliveryStatus);

        spinnerDeliveryStatus = newSpinnerList(
                new SpinnerListModel(
                    new String[] {
                        "Ожидание водителя",
                        "Погрузка",
                        "В процессе доставки",
                        "Доставлено"
                    }),
                new Rectangle(136, 12, 181, 25)
        );
        spinnerDeliveryStatus.setEnabled(false);
        panelDeliveryStatus.add(spinnerDeliveryStatus);

        btnDeliveryStatus = newButton("Изменить", new Rectangle(274, 46, 106, 25));
        panelDeliveryStatus.add(btnDeliveryStatus);

        ListSelectionModel selectionOrdersModel = tableOrders.getSelectionModel();
        selectionOrdersModel.addListSelectionListener(listSelectionEvent -> setOrderInformation());

        ListSelectionModel selectionEmployeeModel = tableEmployees.getSelectionModel();
        selectionEmployeeModel.addListSelectionListener(listSelectionEvent -> setEmployeeID());

        btnSearch.addActionListener(actionEvent -> searchEmployee());

        tfSearch.addActionListener(actionEvent -> searchEmployee());
        
        btnStopSearch.addActionListener(actionEvent -> stopSearch());

        btnDeliveryStatus.addActionListener(actionEvent -> updateStatus());
    }

    @NotNull
    private DefaultTableModel setOrdersTableModel() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] {
                "Код заказа",
                "Товар",
                "Количество",
                "Склад",
                "Адрес склада",
                "Адрес доставки",
                "Заказчик",
                "Телефон",
                "Статус",
                "Код водителя"
        });

        ArrayList<Order> orders = dbService.getAllOrders();

        for (int i = 0; i < orders.size(); i++) {
            model.insertRow(i, new Object[] {
                    orders.get(i).getOrderID(),
                    orders.get(i).getProductName(),
                    orders.get(i).getCount(),
                    orders.get(i).getStoreName(),
                    orders.get(i).getAddressFrom(),
                    orders.get(i).getAddressTo(),
                    orders.get(i).getCustomerName(),
                    orders.get(i).getPhoneNumber(),
                    orders.get(i).getStatus(),
                    orders.get(i).getEmployeeID(),
            });
        }

        return model;
    }

    @NotNull
    private DefaultTableModel setEmployeesTableModel() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] {
                "Код",
                "Имя",
                "Телефон"
        });

        ArrayList<Employee> employees = dbService.getAllDrivers();

        for (int i = 0; i < employees.size(); i++) {
            model.insertRow(i, new Object[] {
                    employees.get(i).getID(),
                    employees.get(i).getName(),
                    employees.get(i).getPhoneNumber()
            });
        }

        return model;
    }

    @NotNull
    private Cursor setNotAllowedCursor() throws IOException {
        return Toolkit.getDefaultToolkit().createCustomCursor(
                new ImageIcon(ImageIO.read(JComponentFactory.class.getResource("/img/not-allowed.png"))).getImage(),
                new Point(0,0),"not-allowed cursor");
    }

    private void returnToInitialState() {
        currentOrderID = null;
        currentEmployeeID = null;

        tableOrders = null;
        tableOrders = newTable(setOrdersTableModel());
        tableOrdersScroll.setViewportView(null);
        tableOrdersScroll.setViewportView(tableOrders);
        ListSelectionModel selectionOrdersModel = tableOrders.getSelectionModel();
        selectionOrdersModel.addListSelectionListener(listSelectionEvent -> setOrderInformation());

        lInfProduct.resetText();
        lInfCount.resetText();
        lInfStoreAddress.resetText();
        lInfCustomerAddress.resetText();
        lInfCustomerName.resetText();
        lInfCustomerPhone.resetText();

        tfSearch.setEnabled(false);
        btnSearch.setEnabled(false);
        btnStopSearch.setEnabled(false);
        tfSearch.setText("");

        tableEmployees = null;
        tableEmployeesScroll.setViewportView(null);
        tableEmployees = newTableDisabled(setEmployeesTableModel());
        tableEmployeesScroll.setViewportView(tableEmployees);
        tableEmployeesScroll.setEnabled(false);
        tableEmployees.setEnabled(false);

        try {
            tableEmployeesScroll.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                    new ImageIcon(ImageIO.read(JComponentFactory.class.getResource("/img/not-allowed.png"))).getImage(),
                    new Point(0,0),"not-allowed cursor"));

            tableEmployees.setCursor(setNotAllowedCursor());
            tableEmployeesScroll.setCursor(setNotAllowedCursor());
        } catch (IOException e) {
            throw new RuntimeException("Файл курсора не найден");
        }

        spinnerDeliveryStatus.setEnabled(false);
        btnDeliveryStatus.setEnabled(false);
    }

    private void checkIDsNotNull() {
        if (Objects.nonNull(currentOrderID) && Objects.nonNull(currentEmployeeID) && currentEmployeeID != 0) {
            spinnerDeliveryStatus.setEnabled(true);
            btnDeliveryStatus.setEnabled(true);
        } else {
            spinnerDeliveryStatus.setEnabled(false);
            btnDeliveryStatus.setEnabled(false);
        }
    }

    private void setOrderInformation() {
        int rowIndex = tableOrders.getSelectedRow();

        lInfProduct.appendText(tableOrders.getModel().getValueAt(rowIndex, 1).toString());
        lInfCount.appendText(tableOrders.getModel().getValueAt(rowIndex, 2).toString());
        lInfStoreAddress.appendText(tableOrders.getModel().getValueAt(rowIndex, 4).toString());
        lInfCustomerAddress.appendText(tableOrders.getModel().getValueAt(rowIndex, 5).toString());
        lInfCustomerName.appendText(tableOrders.getModel().getValueAt(rowIndex, 6).toString());
        lInfCustomerPhone.appendText(tableOrders.getModel().getValueAt(rowIndex, 7).toString());

        currentOrderID = Integer.parseInt(tableOrders.getModel().getValueAt(rowIndex, 0).toString());
        currentEmployeeID = Integer.parseInt(tableOrders.getModel().getValueAt(rowIndex, 9).toString());

        if (Integer.parseInt(tableOrders.getModel().getValueAt(rowIndex, 9).toString()) == 0) {
            tableEmployees.setEnabled(true);
            tableEmployeesScroll.setEnabled(true);

            tableEmployees.setCursor(new Cursor(Cursor.HAND_CURSOR));
            tableEmployeesScroll.setCursor(new Cursor(Cursor.HAND_CURSOR));

            tfSearch.setEnabled(true);
            btnSearch.setEnabled(true);
            btnStopSearch.setEnabled(true);
        } else {
            tableEmployees.setEnabled(false);
            tableEmployeesScroll.setEnabled(false);

            try {
                tableEmployees.setCursor(setNotAllowedCursor());
                tableEmployeesScroll.setCursor(setNotAllowedCursor());
            } catch (IOException e) {
                e.getMessage();
            }

            tfSearch.setEnabled(false);
            btnSearch.setEnabled(false);
            btnStopSearch.setEnabled(false);
        }

        spinnerDeliveryStatus.setValue(tableOrders.getModel().getValueAt(rowIndex, 8).toString());

        checkIDsNotNull();
    }

    private void setEmployeeID() {
        int rowIndex = tableEmployees.getSelectedRow();
        currentEmployeeID = Integer.parseInt(tableEmployees.getModel().getValueAt(rowIndex, 0).toString());
        checkIDsNotNull();
    }

    private void searchEmployee() {
        DefaultTableModel model = new DefaultTableModel();
        ArrayList<Employee> result;

        if (checkNumericTextField(tfSearch.getText(), tfSearch.getColumns())) {
            result = dbService.getDriverByID(Integer.parseInt(tfSearch.getText()));
        } else {
            String query = "'%" + tfSearch.getText() + "%'";
            result = dbService.getDriverByName(query);
        }

        if (result.size() > 0) {
            model.setColumnIdentifiers(employeeColumns);

            for (int i = 0; i < result.size(); i++) {
                model.insertRow(i, new Object[] {
                        result.get(i).getID(),
                        result.get(i).getName(),
                        result.get(i).getPhoneNumber()
                });
            }
        } else {
            JOptionPane.showMessageDialog(
                    frame,
                    String.format(
                            "По запросу \"%s\" по ничего не найдено.\n" +
                                    "Попробуйте уточнить запрос.",
                            tfSearch.getText()
                    ),
                    "Не найдено",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

        ListSelectionModel selectionEmployeeModel = tableEmployees.getSelectionModel();
        selectionEmployeeModel.addListSelectionListener(listSelectionEvent -> setEmployeeID());
    }

    private void stopSearch() {
        tableEmployees = null;
        tableEmployees = newTable(setEmployeesTableModel());
        tableEmployeesScroll.setViewportView(null);
        tableEmployeesScroll.setViewportView(tableEmployees);

        ListSelectionModel selectionEmployeeModel = tableEmployees.getSelectionModel();
        selectionEmployeeModel.addListSelectionListener(listSelectionEvent -> setEmployeeID());

        tfSearch.setText("");
        currentEmployeeID = null;
        checkIDsNotNull();
    }

    private void updateStatus() {
        if (Integer.parseInt(tableOrders.getModel().getValueAt(tableOrders.getSelectedRow(), 9).toString()) == 0) {
            if ((spinnerDeliveryStatus.getValue().toString()).equals("Погрузка")) {
                dbService.setDriverOnDelivery(currentOrderID, currentEmployeeID, "Погрузка");
                JOptionPane.showMessageDialog(
                        frame,
                        "Статус обновлён, водитель добавлен на заказ.",
                        "Обновлено",
                        JOptionPane.INFORMATION_MESSAGE
                );
                returnToInitialState();
            } else {
                JOptionPane.showMessageDialog(
                        frame,
                        "Вы не можете выставить данный статус без наличия водителя",
                        "Ошибка при обновлении",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        } else {
            dbService.setStatus(currentOrderID, spinnerDeliveryStatus.getValue().toString());
            JOptionPane.showMessageDialog(
                    frame,
                    "Статус успешно обновлён.",
                    "Обновлено",
                    JOptionPane.INFORMATION_MESSAGE
            );
            returnToInitialState();
        }
    }
}
