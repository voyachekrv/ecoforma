package com.ecoforma.frontend.forms;

import com.ecoforma.db.entities.Contract;
import com.ecoforma.db.services.SaleService;
import com.ecoforma.frontend.components.JDateSpinner;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

import static com.ecoforma.frontend.services.JComponentFactory.*;
import static com.ecoforma.frontend.services.Checker.*;

import static com.ecoforma.App.saleForm;

public class ContractsForm {
    JFrame frame;
    JTable table;
    JScrollPane tableScroll;
    JTextField tfSearch, tfName;
    JDateSpinner dateSpinner;
    JButton btnSearch, btnStopSearch, btnQuit, btnAcceptChanges, btnNewContract, btnDeleteContract;

    Action searchListener, saveContractListener;

    private String[] columnsHeader = new String[]{
            "Код",
            "Название",
            "Дата окончания",
    };

    SaleService dbService;

    Contract currentContract;

    ContractsForm() {
        dbService = new SaleService();

        frame = newFrame("Контракты с юридическими лицами", new Rectangle(598,  234, 800, 600),
                new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        frame.setVisible(false);
                        saleForm.frame.setEnabled(true);
                    }
                }
        );
        frame.setVisible(true);

        tfSearch = newTextFieldEnabled(50, new Rectangle(12, 12, 200, 23));
        frame.add(tfSearch);

        btnSearch = newButtonEnabled("Поиск", "icon-search", new Rectangle(224, 12, 113, 23));
        frame.add(btnSearch);

        btnStopSearch = newButtonEnabled(null, "icon-close", new Rectangle(349, 12, 24, 23));
        frame.add(btnStopSearch);

        JPanel tablePanel = newPanelBevelTable(12, 47, 770, 439);
        frame.add(tablePanel);

        table = newTable(setInitialTableModel());
        addUnpickEscape();

        tableScroll = newTableScroll(table, 746, 415);
        tablePanel.add(tableScroll);

        JLabel lName = newLabel("Название контракта", new Rectangle(12, 498, 190, 20));
        frame.add(lName);

        JLabel lDateOfEnd = newLabel("Дата прекращения", new Rectangle(214, 498, 123, 20));
        frame.add(lDateOfEnd);

        tfName = newTextFieldEnabled(200, new Rectangle(12, 526, 190, 23));
        frame.add(tfName);
        addSaveKeyCombination(tfName);

        dateSpinner = new JDateSpinner(214, 526, 123, 23, true);
        frame.add(dateSpinner);
        addSaveKeyCombination(dateSpinner);

        btnNewContract = newButtonEnabled("Нов.контр.", new Rectangle(354, 524, 98, 26));
        btnNewContract.setToolTipText("Добавить новый контракт");
        frame.add(btnNewContract);

        btnAcceptChanges = newButton("Изменить", new Rectangle(464, 524, 98, 26));
        frame.add(btnAcceptChanges);

        btnDeleteContract = newButton("Удалить", new Rectangle(574, 524, 98, 26));
        frame.add(btnDeleteContract);

        btnQuit = newButtonEnabled("Выход", new Rectangle(684, 524, 98, 26));
        frame.add(btnQuit);

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

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(listSelectionEvent -> prepareToEdit());

        btnQuit.addActionListener(actionEvent -> {
            frame.setVisible(false);
            saleForm.frame.setEnabled(true);
        });

        btnNewContract.addActionListener(actionEvent -> addContract());

        btnAcceptChanges.addActionListener(actionEvent -> updateContract());

        btnDeleteContract.addActionListener(actionEvent -> deleteContract());

        btnSearch.addActionListener(actionEvent -> searchContract());

        btnStopSearch.addActionListener(actionEvent -> clearSearchResults());

        tfSearch.addActionListener(actionEvent -> searchContract());
    }

    @NotNull
    private DefaultTableModel setInitialTableModel() {
        DefaultTableModel initialTableModel = new DefaultTableModel();
        initialTableModel.setColumnIdentifiers(columnsHeader);

        ArrayList<Contract> contracts = dbService.getAllContracts();

        for (int i = 0; i < contracts.size(); i++) {
            initialTableModel.insertRow(i, new Object[] {
                    contracts.get(i).getID(),
                    contracts.get(i).getName(),
                    contracts.get(i).getDateOfEnd()
            });
        }
        return initialTableModel;
    }

    private void prepareToEdit() {
        btnNewContract.setEnabled(false);
        btnAcceptChanges.setEnabled(true);
        btnDeleteContract.setEnabled(true);

        int rowIndex = table.getSelectedRow();

        currentContract = dbService.getContractByID(Integer.parseInt(table.getModel().getValueAt(rowIndex, 0).toString()));

        tfName.setText(currentContract.getName());
        dateSpinner.setDateToSpinner(currentContract.getDateOfEnd());
    }

    private void removeFocusFromTable() {
        currentContract = null;

        tableScroll.setViewportView(null);

        table = newTable(setInitialTableModel());
        tableScroll.setViewportView(table);
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(listSelectionEvent -> prepareToEdit());

        addUnpickEscape();
    }

    private void addUnpickEscape() {
        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    unpick();
                }
            }
        });
    }

    private void addSaveKeyCombination(@NotNull JComponent component) {
        String ACTION_KEY = "saveAction";
        saveContractListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (Objects.isNull(currentContract)) {
                    addContract();
                } else {
                    updateContract();
                }
            }
        };
        KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK);
        InputMap inputMap = component.getInputMap(JComponent.WHEN_FOCUSED);
        inputMap.put(ctrlS, ACTION_KEY);
        ActionMap actionMap = component.getActionMap();
        actionMap.put(ACTION_KEY, saveContractListener);
        component.setActionMap(actionMap);
    }

    private void unpick() {
        removeFocusFromTable();
        tfName.setText("");
        dateSpinner.setCurrentDate();
        btnAcceptChanges.setEnabled(false);
        btnDeleteContract.setEnabled(false);
        btnNewContract.setEnabled(true);
    }

    private void addContract() {
        int result = JOptionPane.showConfirmDialog(
                frame,
                "Добавить контракт?",
                "Подтверждение операции",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            if (checkTextField(tfName.getText(), tfName.getColumns())) {
                dbService.insertContract(tfName.getText(), dateSpinner.getDatabaseFormatDate());

                JOptionPane.showMessageDialog(
                        frame,
                        "Контракт успешно добавлен.",
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

    private void updateContract() {
        if (checkTextField(tfName.getText(), tfName.getColumns())) {
            dbService.updateContract(currentContract.getID(), tfName.getText(), dateSpinner.getDatabaseFormatDate());
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

    private void deleteContract() {
        int result = JOptionPane.showConfirmDialog(
                frame,
                "Удалить контракт?",
                "Подтверждение операции",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            dbService.deleteContract(currentContract.getID());

            JOptionPane.showMessageDialog(
                    frame,
                    "Контракт успешно удалён.",
                    "Добавление завершено",
                    JOptionPane.INFORMATION_MESSAGE
            );

            unpick();
            currentContract = null;
        }
    }

    private void searchContract() {
        unpick();

        DefaultTableModel searchModel = new DefaultTableModel();
        String query = "'%" + tfSearch.getText() + "%'";

        ArrayList<Contract> searchResult = dbService.searchContracts(query);

        if (searchResult.size() == 0) {
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
            searchModel.setColumnIdentifiers(columnsHeader);

            for (int i = 0; i < searchResult.size(); i++) {
                searchModel.insertRow(i, new Object[] {
                        searchResult.get(i).getID(),
                        searchResult.get(i).getName(),
                        searchResult.get(i).getDateOfEnd()
                });
            }
            table.setModel(searchModel);
        }
    }

    private void clearSearchResults() {
        unpick();
        tfSearch.setText("");
        table.setModel(setInitialTableModel());
    }
}
