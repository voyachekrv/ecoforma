package com.ecoforma.frontend.forms;

import com.ecoforma.db.entities.ContractView;
import com.ecoforma.db.entities.IndividualPersonView;
import com.ecoforma.db.entities.LegalPersonView;
import com.ecoforma.db.services.SaleService;
import com.ecoforma.frontend.CompanyFrame;
import com.ecoforma.frontend.services.JComponentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Objects;

import static com.ecoforma.frontend.services.JComponentFactory.*;
import static com.ecoforma.frontend.services.Checker.*;

import static com.ecoforma.App.contractsForm;

public class SaleForm {
    CompanyFrame frame;
    JTabbedPane tabbedPane;
    JTextField tfSearch;
    JRadioButton rbLegal, rbIndividual;
    JTable tableCustomers;
    JScrollPane tableScroll;
    JTextField tfName, tfAdress, tfPhoneNumber;
    JComboBox cbbxContract;
    JButton btnContracts, btnAcceptChanges, btnNewCustomer, btnDelete;

    DefaultTableModel currentTableCustomersModel;
    Action saveCustomerListener;
    Action searchListener;

    private String[] columnsLegalsHeader = new String[]{
            "Код",
            "Название",
            "Юридический адрес",
            "Телефон",
            "Контракт с юридическим лицом",
            "Дата окончания действия контракта"
    };

    private String[] columnsIndividualHeader = new String[]{
            "Код",
            "ФИО",
            "Адрес",
            "Телефон"
    };

    SaleService dbService;

    IndividualPersonView currentIndividualPerson;
    LegalPersonView currentLegalPerson;

    public SaleForm(String department) {
        dbService = new SaleService();

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

        rbLegal = newRadioButton("Юридические лица", "null", new Rectangle(12, 43, 147, 24));
        rbLegal.setSelected(true);
        panelCustomers.add(rbLegal);

        rbIndividual = newRadioButton("Физические лица", "null", new Rectangle(162, 43, 134, 24));
        panelCustomers.add(rbIndividual);

        ButtonGroup personTypes = new ButtonGroup();
        personTypes.add(rbLegal);
        personTypes.add(rbIndividual);

        JPanel panelTableCustomers = newPanelBevelTable(12, 75, 1305, 483);
        panelCustomers.add(panelTableCustomers);

        tableCustomers = newTable(setInitialTableLegalsModel());
        currentTableCustomersModel = setInitialTableLegalsModel();
        addUnpickEscape();

        tableScroll = newTableScroll(tableCustomers, 1281, 458);
        panelTableCustomers.add(tableScroll);

        JLabel lName = newLabel("Имя", new Rectangle(12, 572, 318, 20));
        panelCustomers.add(lName);

        JLabel lAdress = newLabel("Адрес", new Rectangle(339, 572, 318, 20));
        panelCustomers.add(lAdress);

        JLabel lPhoneNumber = newLabel("Телефон", new Rectangle(669, 572, 318, 20));
        panelCustomers.add(lPhoneNumber);

        JLabel lContract = newLabel("Контракт с юр. лицом", new Rectangle(999, 572, 318, 20));
        panelCustomers.add(lContract);

        tfName = newTextFieldBigFont(50, new Rectangle(12, 600, 318, 32));
        addSaveKeyCombination(tfName);
        panelCustomers.add(tfName);

        tfAdress = newTextFieldBigFont(200, new Rectangle(339, 600, 318, 32));
        addSaveKeyCombination(tfAdress);
        panelCustomers.add(tfAdress);

        tfPhoneNumber = newTextFieldBigFont(12, new Rectangle(669, 600, 318, 32));
        addSaveKeyCombination(tfPhoneNumber);
        panelCustomers.add(tfPhoneNumber);

        cbbxContract = newComboBox(setComboBoxContractsModel(), new Rectangle(999, 600, 318, 32));
        cbbxContract.setEnabled(true);
        addSaveKeyCombination(cbbxContract);
        panelCustomers.add(cbbxContract);

        btnContracts = newButtonEnabled("Контракты", new Rectangle(541, 644, 185, 32));
        panelCustomers.add(btnContracts);

        btnAcceptChanges = newButton("Изменить данные", "icon-accept", new Rectangle(935, 644, 185, 32));
        panelCustomers.add(btnAcceptChanges);

        btnNewCustomer = newButtonEnabled("Новый заказчик", "icon-add", new Rectangle(1132, 644, 185, 32));
        panelCustomers.add(btnNewCustomer);

        btnDelete = newButton("Удалить заказчика", "icon-delete", new Rectangle(738, 644, 185, 32));
        panelCustomers.add(btnDelete);

        String ACTION_KEY = "searchAction";
        searchListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tfSearch.requestFocus();
            }
        };
        KeyStroke ctrlF = KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK);
        InputMap inputMap = tfSearch.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(ctrlF, ACTION_KEY);
        ActionMap actionMap = tfSearch.getActionMap();
        actionMap.put(ACTION_KEY, searchListener);
        tfSearch.setActionMap(actionMap);

        ListSelectionModel selectionModel = tableCustomers.getSelectionModel();
        selectionModel.addListSelectionListener(listSelectionEvent -> prepareToEdit());

        rbIndividual.addActionListener(actionEvent -> switchBetweenPersonTypes());

        rbLegal.addActionListener(actionEvent -> switchBetweenPersonTypes());

        btnDelete.addActionListener(actionEvent -> deleteCustomer());

        btnAcceptChanges.addActionListener(actionEvent -> updateCustomer());

        btnNewCustomer.addActionListener(actionEvent -> addCustomer());

        btnSearch.addActionListener(actionEvent -> searchCustomer());

        btnStopSearch.addActionListener(actionEvent -> clearSearchResults());

        tfSearch.addActionListener(actionEvent -> searchCustomer());

        btnContracts.addActionListener(actionEvent -> {
            frame.setEnabled(false);
            contractsForm = new ContractsForm();
        });
    }

    @NotNull
    private DefaultTableModel setInitialTableLegalsModel() {
        DefaultTableModel initialTableModel = new DefaultTableModel();
        initialTableModel.setColumnIdentifiers(columnsLegalsHeader);

        ArrayList<LegalPersonView> legals = dbService.getAllLegalPersons();

        for (int i = 0; i < legals.size(); i++) {
            initialTableModel.insertRow(i, new Object[]{
                    legals.get(i).getID(),
                    legals.get(i).getName(),
                    legals.get(i).getAdress(),
                    legals.get(i).getPhoneNumber(),
                    legals.get(i).getContractName(),
                    legals.get(i).getEndOfContract()
            });
        }

        return initialTableModel;
    }

    @NotNull
    private DefaultTableModel setInitialTableIndividualModel() {
        DefaultTableModel initialTableModel = new DefaultTableModel();
        initialTableModel.setColumnIdentifiers(columnsIndividualHeader);

        ArrayList<IndividualPersonView> individuals = dbService.getAllIndividualPersons();

        for (int i = 0; i < individuals.size(); i++) {
            initialTableModel.insertRow(i, new Object[] {
                    individuals.get(i).getID(),
                    individuals.get(i).getName(),
                    individuals.get(i).getAdress(),
                    individuals.get(i).getPhoneNumber()
            });
        }

        return initialTableModel;
    }

    @NotNull
    private String[] setComboBoxContractsModel() {
        ArrayList<ContractView> contractsList = dbService.getAllContractsModel();
        String[] contracts = new String[contractsList.size()];

        for (int i = 0; i < contracts.length; i++) {
            contracts[i] = contractsList.get(i).getName();
        }

        return contracts;
    }

    private void addSaveKeyCombination(@NotNull JComponent component) {
        String ACTION_KEY = "saveAction";
        saveCustomerListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (Objects.isNull(currentLegalPerson) && Objects.isNull(currentIndividualPerson)) {
                    addCustomer();
                } else {
                    updateCustomer();
                }
            }
        };
        KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK);
        InputMap inputMap = component.getInputMap(JComponent.WHEN_FOCUSED);
        inputMap.put(ctrlS, ACTION_KEY);
        ActionMap actionMap = component.getActionMap();
        actionMap.put(ACTION_KEY, saveCustomerListener);
        component.setActionMap(actionMap);
    }

    private void switchBetweenPersonTypes() {
        tfName.setText("");
        tfAdress.setText("");
        tfPhoneNumber.setText("");

        btnAcceptChanges.setEnabled(false);
        btnDelete.setEnabled(false);
        btnNewCustomer.setEnabled(true);

        cbbxContract.setSelectedIndex(0);

        if (rbIndividual.isSelected()) {
            removeFocusFromTable(setInitialTableIndividualModel());
            cbbxContract.setEnabled(false);
            currentLegalPerson = null;
            currentTableCustomersModel = setInitialTableIndividualModel();
        } else {
            removeFocusFromTable(setInitialTableLegalsModel());
            cbbxContract.setEnabled(true);
            currentIndividualPerson = null;
            currentTableCustomersModel = setInitialTableLegalsModel();
        }
    }

    private void removeFocusFromTable(DefaultTableModel m) {
        currentIndividualPerson = null;
        currentLegalPerson = null;

        tableScroll.setViewportView(null);

        tableCustomers = newTable(m);
        tableScroll.setViewportView(tableCustomers);
        ListSelectionModel selectionModel = tableCustomers.getSelectionModel();
        selectionModel.addListSelectionListener(listSelectionEvent -> prepareToEdit());
        addUnpickEscape();
    }

    private void prepareToEdit() {
        btnNewCustomer.setEnabled(false);
        btnAcceptChanges.setEnabled(true);
        btnDelete.setEnabled(true);

        int rowIndex = tableCustomers.getSelectedRow();

        if (rbLegal.isSelected()) {
            currentIndividualPerson = null;
            currentLegalPerson = dbService.getLegalPersonByID(Integer.parseInt(tableCustomers.getModel().getValueAt(rowIndex, 0).toString()));
            tfAdress.setText(currentLegalPerson.getAdress());
            tfName.setText(currentLegalPerson.getName());
            tfPhoneNumber.setText(currentLegalPerson.getPhoneNumber());
            cbbxContract.setSelectedItem(currentLegalPerson.getContractName());
        } else {
            currentLegalPerson = null;
            currentIndividualPerson = dbService.getIndividualPersonByID(Integer.parseInt(tableCustomers.getModel().getValueAt(rowIndex, 0).toString()));
            tfAdress.setText(currentIndividualPerson.getAdress());
            tfName.setText(currentIndividualPerson.getName());
            tfPhoneNumber.setText(currentIndividualPerson.getPhoneNumber());
        }
    }

    private void unpick() {
        removeFocusFromTable(currentTableCustomersModel);

        btnNewCustomer.setEnabled(true);
        btnAcceptChanges.setEnabled(false);
        btnDelete.setEnabled(false);

        tfName.setText("");
        tfAdress.setText("");
        tfPhoneNumber.setText("");
        cbbxContract.setSelectedIndex(0);
    }

    private void addUnpickEscape() {
        tableCustomers.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    unpick();
                }
            }
        });
    }

    private void deleteCustomer() {
        if (rbLegal.isSelected()) {
            int result = JOptionPane.showConfirmDialog(
                    frame,
                    String.format(
                            "Удалить заказчика %s?\nОбратите внимание, " +
                                    "что при удалении юридического лица автоматически удаляется заключённый с ним контракт.",
                            currentLegalPerson.getName()),
                    "Подтверждение операции",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
                int contractID = dbService.getContractID(currentLegalPerson.getContractName(), currentLegalPerson.getEndOfContract());

                if (!(Objects.isNull(contractID))) {
                    dbService.deleteContract(contractID);
                    currentLegalPerson.setContractName(null);
                    currentLegalPerson.setEndOfContract(null);
                }

                dbService.deleteCustomer(currentLegalPerson.getID());
                currentLegalPerson = null;
                currentTableCustomersModel = setInitialTableLegalsModel();
            }
        } else {
            int result = JOptionPane.showConfirmDialog(
                    frame,
                    String.format(
                            "Удалить заказчика %s?",
                            currentIndividualPerson.getName()),
                    "Подтверждение операции",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
                dbService.deleteCustomer(currentIndividualPerson.getID());
                currentIndividualPerson = null;
                currentTableCustomersModel = setInitialTableIndividualModel();
            }
        }

        JOptionPane.showMessageDialog(
                frame,
                "Заказчик успешно удалён.",
                "Удаление завершено",
                JOptionPane.INFORMATION_MESSAGE
        );
        unpick();
        tfSearch.setText("");
    }

    private void updateCustomer() {
        int result = JOptionPane.showConfirmDialog(
                frame,
                "Изменить данные заказчика?",
                "Подтверждение операции",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            if (
                    checkTextField(tfName.getText(), tfName.getColumns()) &&
                            checkTextField(tfAdress.getText(), tfAdress.getColumns()) &&
                            checkNumericTextField(tfPhoneNumber.getText(), tfPhoneNumber.getColumns())
            ) {
                if (rbLegal.isSelected()) {
                    dbService.updateCustomer(currentLegalPerson.getID(), tfName.getText(), tfAdress.getText(), tfPhoneNumber.getText());

                    int newContractID = dbService.getContractID((String) cbbxContract.getSelectedItem());
                    dbService.updateContractWithLegal(currentLegalPerson.getID(), newContractID);

                    currentLegalPerson = null;
                    currentTableCustomersModel = setInitialTableLegalsModel();
                } else {
                    dbService.updateCustomer(currentIndividualPerson.getID(), tfName.getText(), tfAdress.getText(), tfPhoneNumber.getText());

                    currentIndividualPerson = null;
                    currentTableCustomersModel = setInitialTableIndividualModel();
                }
                unpick();
            } else {
                JOptionPane.showMessageDialog(
                        frame,
                        "Одно из полей пустое или содержит недопустимое значение.",
                        "Ошибка при обновлении",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }
    }

    private void addCustomer() {
        int result = JOptionPane.showConfirmDialog(
                frame,
                "Добавить нового заказчика?",
                "Подтверждение операции",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            if (
                    checkTextField(tfName.getText(), tfName.getColumns()) &&
                            checkTextField(tfAdress.getText(), tfAdress.getColumns()) &&
                            checkNumericTextField(tfPhoneNumber.getText(), tfPhoneNumber.getColumns())
            ) {
                if (rbLegal.isSelected()) {
                    int contractID = dbService.getContractID((String) cbbxContract.getSelectedItem());
                    dbService.insertLegalPerson(tfName.getText(), tfAdress.getText(), contractID, tfPhoneNumber.getText());
                    currentTableCustomersModel = setInitialTableLegalsModel();
                } else {
                    dbService.insertIndividualPerson(tfName.getText(), tfAdress.getText(), tfPhoneNumber.getText());
                    currentTableCustomersModel = setInitialTableIndividualModel();
                }

                JOptionPane.showMessageDialog(
                        frame,
                        "Заказчик успешно Добавлен.",
                        "Добавление завершено",
                        JOptionPane.INFORMATION_MESSAGE
                );

                unpick();
            } else {
                JOptionPane.showMessageDialog(
                        frame,
                        "Одно из полей пустое или содержит недопустимое значение.",
                        "Ошибка при добавлении",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }
    }

    private void searchCustomer() {
        unpick();

        DefaultTableModel searchModel = new DefaultTableModel();
        String query = "'%" + tfSearch.getText() + "%'";

        if (rbLegal.isSelected()) {
            ArrayList<LegalPersonView> legalPersonResult = dbService.searchLegalPerson(query);

            if (legalPersonResult.size() == 0) {
                JOptionPane.showMessageDialog(
                        frame,
                        String.format(
                                "По запросу \"%s\" ничего не найдено.\n" +
                                        "Попробуйте уточнить запрос.",
                                tfSearch.getText()
                        ),
                        "Не найдено",
                        JOptionPane.INFORMATION_MESSAGE
                );
                tfSearch.setText("");
            } else {
                searchModel.setColumnIdentifiers(columnsLegalsHeader);

                for (int i = 0; i < legalPersonResult.size(); i++) {
                    searchModel.insertRow(i, new Object[] {
                            legalPersonResult.get(i).getID(),
                            legalPersonResult.get(i).getName(),
                            legalPersonResult.get(i).getAdress(),
                            legalPersonResult.get(i).getPhoneNumber(),
                            legalPersonResult.get(i).getContractName(),
                            legalPersonResult.get(i).getEndOfContract()
                    });
                }
                tableCustomers.setModel(searchModel);
            }

        } else {
            ArrayList<IndividualPersonView> individualPersonResult = dbService.searchIndividualPerson(query);

            if (individualPersonResult.size() == 0) {
                JOptionPane.showMessageDialog(
                        frame,
                        String.format(
                                "По запросу \"%s\" ничего не найдено.\n" +
                                        "Попробуйте уточнить запрос.",
                                tfSearch.getText()
                        ),
                        "Не найдено",
                        JOptionPane.INFORMATION_MESSAGE
                );
                tfSearch.setText("");
            } else {
                searchModel.setColumnIdentifiers(columnsIndividualHeader);

                for (int i = 0; i < individualPersonResult.size(); i++) {
                    searchModel.insertRow(i, new Object[] {
                            individualPersonResult.get(i).getID(),
                            individualPersonResult.get(i).getName(),
                            individualPersonResult.get(i).getAdress(),
                            individualPersonResult.get(i).getPhoneNumber(),
                    });
                }
                tableCustomers.setModel(searchModel);
            }
        }
    }

    private void clearSearchResults() {
        unpick();
        tfSearch.setText("");
        tableCustomers.setModel(currentTableCustomersModel);
    }
}