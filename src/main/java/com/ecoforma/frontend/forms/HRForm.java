package com.ecoforma.frontend.forms;

import com.ecoforma.db.entities.Employee;
import com.ecoforma.db.entities.EmployeeView;
import com.ecoforma.db.entities.RegistrationData;
import com.ecoforma.db.services.HRService;
import com.ecoforma.frontend.components.CompanyFrame;
import com.ecoforma.frontend.components.JDateSpinner;
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

import static com.ecoforma.App.COMPANY_NAME;
import static com.ecoforma.App.newEmployeeForm;
import static com.ecoforma.frontend.services.Checker.*;
import static com.ecoforma.frontend.services.JComponentFactory.*;

public class HRForm {
    CompanyFrame frame;
    private JTextField tfSearch;
    private JRadioButton rbID, rbName, rbPassport, rbEducation, rbAdress, rbPhoneNumber, rbEmail, rbPost, rbDepartment;
    private JScrollPane tableScroll;
    private JTable table;
    private JTextField tfName, tfPassport, tfEducation, tfAddress, tfPhoneNumber, tfEmail;
    private JSpinner spinnerPersonalSalary;
    private JComboBox cbboxPost, cbboxDepartment;
    private JTextField tfLogin, tfPassword;
    private JComboBox cbboxRole;
    private JCheckBox cbAllowSignIn;
    private JButton btnAcceptChanges;
    private JButton btnDeleteEmployee;
    private JButton btnUnpick;
    private JDateSpinner dateSpinner;

    private Action actionListener;
    private String[] columnsHeader = new String[] {
            "Табельный номер",
            "ФИО",
            "Дата рождения",
            "Номер паспорта",
            "Образование",
            "Адрес",
            "Номер телефона",
            "E-mail",
            "Дата приёма на работу",
            "Должность",
            "Отдел",
            "Заработная плата"
    };

    private Employee currentEmployee;
    private RegistrationData currentRegistrationData;

    private HRService dbService;

    HRForm(String department) {
        dbService = new HRService();

        frame = new CompanyFrame(COMPANY_NAME + " - " + department);

        // Таблица со списком сотрудников фирмы

        JPanel tablePanel = newPanelBevelTable(10, 98, 1326, 430);
        frame.getContentPane().add(tablePanel);

        table = newTable(setInitialTableModel());
        addUnpickEscape();

        tableScroll = newTableScroll(table, 1300, 420);
        tablePanel.add(tableScroll);

        // Интерфейс поиска

        tfSearch = newTextFieldEnabled(50, new Rectangle(10, 64, 128, 23));
        frame.getContentPane().add(tfSearch);

        JButton btnSearch = newButtonEnabled("Поиск", "icon-search", new Rectangle(145, 64, 90, 23));
        frame.getContentPane().add(btnSearch);

        JButton btnStopSearch = newButtonEnabled(null, "icon-close", new Rectangle(237, 64, 24, 23));
        btnStopSearch.setToolTipText("Очистка результов поиска");
        frame.getContentPane().add(btnStopSearch);

        JLabel lSearchOptions = newLabel("Критерии поиска:", new Rectangle (267, 70, 105, 14));
        frame.getContentPane().add(lSearchOptions);

        rbID = newRadioButton("Табельный номер", "employee.ID", new Rectangle(370, 66, 135, 23));
        frame.getContentPane().add(rbID);

        rbName = newRadioButton("Имя", "employee.name", new Rectangle(506, 66, 55, 23));
        rbName.setSelected(true);
        frame.getContentPane().add(rbName);

        rbPassport = newRadioButton("Номер паспорта", "employee.passport", new Rectangle(557, 66, 119, 23));
        frame.getContentPane().add(rbPassport);

        rbEducation = newRadioButton("Образование", "employee.education", new Rectangle(675, 66, 106, 23));
        frame.getContentPane().add(rbEducation);

        rbAdress = newRadioButton("Адрес", "employee.adress", new Rectangle(777, 66, 67, 23));
        frame.getContentPane().add(rbAdress);

        rbPhoneNumber = newRadioButton("Номер телефона", "employee.phoneNumber", new Rectangle(840, 66, 125, 23));
        frame.getContentPane().add(rbPhoneNumber);

        rbEmail = newRadioButton("E-mail", "employee.email", new Rectangle(970, 66, 67, 23));
        frame.getContentPane().add(rbEmail);

        rbPost = newRadioButton("Должность", "post.name", new Rectangle(1035, 66, 96, 23));
        frame.getContentPane().add(rbPost);

        rbDepartment = newRadioButton("Отдел", "department.name", new Rectangle(1130, 66, 67, 23));
        frame.getContentPane().add(rbDepartment);

        ButtonGroup searchGroup = new ButtonGroup();
        searchGroup.add(rbID);
        searchGroup.add(rbName);
        searchGroup.add(rbPassport);
        searchGroup.add(rbEducation);
        searchGroup.add(rbAdress);
        searchGroup.add(rbPhoneNumber);
        searchGroup.add(rbEmail);
        searchGroup.add(rbPost);
        searchGroup.add(rbDepartment);

        // Интерфейс изменения данных о сотруднике
        tfName = newTextFieldDisabled(50, new Rectangle(30, 562, 185, 23));
        addSaveKeyCombination(tfName);
        frame.getContentPane().add(tfName);

        JLabel lName = newLabel("ФИО", new Rectangle (30, 538, 46, 14));
        frame.getContentPane().add(lName);

        dateSpinner = new JDateSpinner(222, 562, 185, 23, false);
        addSaveKeyCombination(dateSpinner);
        frame.getContentPane().add(dateSpinner);

        JLabel lDateOfBirth = newLabel("Дата рождения", new Rectangle (222, 539, 100, 14));
        frame.getContentPane().add(lDateOfBirth);

        tfPassport = newTextFieldDisabled(10, new Rectangle(417, 562, 185, 23));
        addSaveKeyCombination(tfPassport);
        frame.getContentPane().add(tfPassport);

        JLabel lPassport = newLabel("Номер паспорта", new Rectangle (417, 538, 100, 14));
        frame.getContentPane().add(lPassport);

        tfEducation = newTextFieldDisabled(300, new Rectangle(30, 621, 572, 23));
        addSaveKeyCombination(tfEducation);
        frame.getContentPane().add(tfEducation);

        JLabel lEducation = newLabel("Образование", new Rectangle (30, 596, 84, 14));
        frame.getContentPane().add(lEducation);

        tfAddress = newTextFieldDisabled(200, new Rectangle(30, 680, 572, 23));
        addSaveKeyCombination(tfAddress);
        frame.getContentPane().add(tfAddress);

        JLabel lAddress = newLabel("Адрес", new Rectangle (30, 655, 46, 14));
        frame.getContentPane().add(lAddress);

        tfPhoneNumber = newTextFieldDisabled(12, new Rectangle(612, 562, 185, 23));
        addSaveKeyCombination(tfPhoneNumber);
        frame.getContentPane().add(tfPhoneNumber);

        JLabel lPhoneNumber = newLabel("Телефон", new Rectangle (612, 539, 83, 14));
        frame.getContentPane().add(lPhoneNumber);

        tfEmail = newTextFieldDisabled(70, new Rectangle(612, 621, 185, 23));
        addSaveKeyCombination(tfEmail);
        frame.getContentPane().add(tfEmail);

        JLabel lEmail = newLabel("E-mail", new Rectangle (612, 596, 46, 14));
        frame.getContentPane().add(lEmail);

        JLabel lPersonalSalary = newLabel("Заработная плата", new Rectangle (612, 655, 150, 14));
        frame.getContentPane().add(lPersonalSalary);

        spinnerPersonalSalary = newSpinnerNumericDisabled(
                new SpinnerNumberModel(1, 1, 3999999, 100),
                new Rectangle(612, 680, 185, 23)
        );
        frame.add(spinnerPersonalSalary);
        addSaveKeyCombination(spinnerPersonalSalary);

        JLabel lPost = newLabel("Должность", new Rectangle (807, 538, 150, 14));
        frame.getContentPane().add(lPost);

        cbboxPost = newComboBox(dbService.getPostNames(), new Rectangle(807, 562, 185, 23));
        addSaveKeyCombination(cbboxPost);
        frame.getContentPane().add(cbboxPost);

        JLabel lDepartment = newLabel("Отдел", new Rectangle (1007, 538, 150, 14));
        frame.getContentPane().add(lDepartment);

        cbboxDepartment = newComboBox(dbService.getDepartmentNames(), new Rectangle(1007, 562, 220, 23));
        addSaveKeyCombination(cbboxDepartment);
        frame.getContentPane().add(cbboxDepartment);

        JLabel lLogin = newLabel("Логин", new Rectangle (807, 596, 150, 14));
        frame.getContentPane().add(lLogin);

        tfLogin = newTextFieldDisabled(20, new Rectangle(807, 621, 185, 23));
        addSaveKeyCombination(tfLogin);
        frame.getContentPane().add(tfLogin);

        JLabel lPassword = newLabel("Пароль", new Rectangle (807, 655, 150, 14));
        frame.getContentPane().add(lPassword);

        tfPassword = newTextFieldDisabled(40, new Rectangle(807, 680, 185, 23));
        addSaveKeyCombination(tfPassword);
        frame.getContentPane().add(tfPassword);

        JLabel lRole = newLabel("Роль в системе", new Rectangle (1007, 596, 150, 14));
        frame.getContentPane().add(lRole);

        cbboxRole = newComboBox(dbService.getRoleNames(), new Rectangle(1007, 621, 220, 23));
        addSaveKeyCombination(cbboxRole);
        frame.getContentPane().add(cbboxRole);

        cbAllowSignIn = newCheckBoxDisabled("Позволить регистрироваться в системе", new Rectangle(1007, 680, 270, 23));
        cbAllowSignIn.setSelected(false);
        frame.getContentPane().add(cbAllowSignIn);

        // Кнопки внизу панели редактирования сотрудника

        btnAcceptChanges = newButton("Подтвердить изменения", "icon-accept", new Rectangle(30, 714, 210, 30));
        frame.add(btnAcceptChanges);

        btnDeleteEmployee = newButton("Удалить сотрудника", "icon-delete", new Rectangle(250, 714, 185, 30));
        frame.getContentPane().add(btnDeleteEmployee);

        btnUnpick = newButton("Снять выбор", "icon-unfocus", new Rectangle(445, 714, 140, 30));
        frame.getContentPane().add(btnUnpick);

        JButton btnNewEmployee = newButtonEnabled("Новый сотрудник", "icon-add", new Rectangle(595, 714, 170, 30));
        frame.getContentPane().add(btnNewEmployee);

        // Выход из системы

        // Добавление сотрудника в поля для редактирования
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(listSelectionEvent -> prepareToEdit());

        // Снятие фокуса с таблицы
        btnUnpick.addActionListener(actionEvent -> unpick());

        // Добавление комбинации ctrl+f для активации формы поиска
        String ACTION_KEY = "searchAction";
        actionListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tfSearch.requestFocus();
            }
        };
        KeyStroke ctrlF = KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK);
        InputMap inputMap = tfSearch.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(ctrlF, ACTION_KEY);
        ActionMap actionMap = tfSearch.getActionMap();
        actionMap.put(ACTION_KEY, actionListener);
        tfSearch.setActionMap(actionMap);

        // Поиск по нажатию кнопки
        btnSearch.addActionListener(actionEvent -> searchEmployee());

        // Поиск сотрудника по нажатию Enter после ввода данных в поле
        tfSearch.addActionListener(actionEvent -> searchEmployee());

        // Очистка результатов поиска
        btnStopSearch.addActionListener(actionEvent -> clearSearchResult());

        // Очистка результатов поиска клавишей Escape
        tfSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    clearSearchResult();
                }
            }
        });

        // Удаление сотрудника
        btnDeleteEmployee.addActionListener(actionEvent -> deleteEmployee());

        // Обработка события по нажатию флажка "Позволить регистрацию в системе"
        cbAllowSignIn.addItemListener(itemEvent -> {
            if (cbAllowSignIn.isSelected()) {
                tfLogin.setEnabled(true);
                tfPassword.setEnabled(true);
                cbboxRole.setEnabled(true);
            } else {
                tfLogin.setEnabled(false);
                tfPassword.setEnabled(false);
                cbboxRole.setEnabled(false);
            }
        });

        // Обновление данных о сотруднике
        btnAcceptChanges.addActionListener(actionEvent -> updateEmployee());

        btnNewEmployee.addActionListener(actionEvent -> {
            frame.setEnabled(false);
            newEmployeeForm = new NewEmployeeForm();
        });
    }

    // Escape для удаления фокуса с таблицы
    void addUnpickEscape() {
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

    // Комбинация ctrl+s для сохранения результатов изменения данных сотрудника
    private void addSaveKeyCombination(@NotNull JComponent component) {
        String ACTION_KEY = "saveAction";
        actionListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                updateEmployee();
            }
        };
        KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK);
        InputMap inputMap = component.getInputMap(JComponent.WHEN_FOCUSED);
        inputMap.put(ctrlS, ACTION_KEY);
        ActionMap actionMap = component.getActionMap();
        actionMap.put(ACTION_KEY, actionListener);
        component.setActionMap(actionMap);
    }

    // Задание модели таблицы по умолчанию
    @NotNull
    private DefaultTableModel setInitialTableModel() {
        DefaultTableModel initialTableModel = new DefaultTableModel();
        initialTableModel.setColumnIdentifiers(columnsHeader);

        ArrayList<EmployeeView> employees = dbService.getAllEmployees();

        for (int i = 0; i < employees.size(); i++) {
            initialTableModel.insertRow(i, new Object[] {
                    employees.get(i).getID(),
                    employees.get(i).getName(),
                    employees.get(i).getDateOfBirth(),
                    employees.get(i).getPassport(),
                    employees.get(i).getEducation(),
                    employees.get(i).getAdress(),
                    employees.get(i).getPhoneNumber(),
                    employees.get(i).getEmail(),
                    employees.get(i).getDateOfEmployment(),
                    employees.get(i).getPost(),
                    employees.get(i).getDepartment(),
                    employees.get(i).getPersonalSalary()
            });
        }

        return initialTableModel;
    }

    // Помещение значений данных о сотруднике в поля редактирования
    private void prepareToEdit() {
        int rowIndex = table.getSelectedRow();
        currentEmployee = dbService.getEmployeeByID(Integer.parseInt(table.getModel().getValueAt(rowIndex, 0).toString()));

        tfName.setText(currentEmployee.getName());
        dateSpinner.setDateToSpinner(currentEmployee.getDateOfBirth());
        tfEmail.setText(currentEmployee.getEmail());
        tfAddress.setText(currentEmployee.getAdress());
        tfEducation.setText(currentEmployee.getEducation());
        tfPassport.setText(currentEmployee.getPassport());
        spinnerPersonalSalary.setValue(currentEmployee.getPersonalSalary());
        tfPhoneNumber.setText(currentEmployee.getPhoneNumber());

        cbboxPost.setSelectedIndex(currentEmployee.getPost_ID() - 1);
        cbboxDepartment.setSelectedIndex(currentEmployee.getDepartment_ID() - 1);

        tfName.setEnabled(true);
        dateSpinner.setEnabled(true);
        tfEmail.setEnabled(true);
        tfAddress.setEnabled(true);
        tfEducation.setEnabled(true);
        tfPassport.setEnabled(true);
        spinnerPersonalSalary.setEnabled(true);
        tfPhoneNumber.setEnabled(true);

        cbboxPost.setEnabled(true);
        cbboxDepartment.setEnabled(true);

        btnAcceptChanges.setEnabled(true);
        btnDeleteEmployee.setEnabled(true);
        btnUnpick.setEnabled(true);

        cbAllowSignIn.setEnabled(true);

        currentRegistrationData = dbService.getRegistrationData(currentEmployee.getID());

        if (!(Objects.isNull(currentRegistrationData))) {
            tfLogin.setEnabled(true);
            tfPassword.setEnabled(true);
            cbboxRole.setEnabled(true);

            cbAllowSignIn.setSelected(true);
            tfLogin.setText(currentRegistrationData.getLogin());
            tfPassword.setText(currentRegistrationData.getPassword());
            cbboxRole.setSelectedIndex(currentRegistrationData.getRole_ID() - 1);
        } else {
            tfLogin.setText("");
            tfPassword.setText("");
            cbboxRole.setSelectedIndex(0);
            cbAllowSignIn.setSelected(false);

            tfLogin.setEnabled(false);
            tfPassword.setEnabled(false);
            cbboxRole.setEnabled(false);
        }
    }

    // Удаление фокуса с таблицы
    private void removeFocusFromTable() {
        tableScroll.setViewportView(null);

        table = newTable(setInitialTableModel());
        addUnpickEscape();
        tableScroll.setViewportView(table);
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(listSelectionEvent -> prepareToEdit());
    }

    // Перевод полей редактирования сотрудника в неактивное состояние + удаление фокуса с таблицы
    public void unpick() {
        removeFocusFromTable();

        tfName.setText("");
        dateSpinner.setCurrentDate();
        tfEmail.setText("");
        tfAddress.setText("");
        tfEducation.setText("");
        tfPassport.setText("");
        spinnerPersonalSalary.setValue(1);
        tfPhoneNumber.setText("");
        tfLogin.setText("");
        tfPassword.setText("");
        cbAllowSignIn.setSelected(false);
        cbAllowSignIn.setEnabled(false);

        tfName.setEnabled(false);
        dateSpinner.setEnabled(false);
        tfEmail.setEnabled(false);
        tfAddress.setEnabled(false);
        tfEducation.setEnabled(false);
        tfPassport.setEnabled(false);
        spinnerPersonalSalary.setEnabled(false);
        tfPhoneNumber.setEnabled(false);

        cbboxPost.setSelectedIndex(0);
        cbboxPost.setEnabled(false);

        cbboxDepartment.setSelectedIndex(0);
        cbboxDepartment.setEnabled(false);

        btnAcceptChanges.setEnabled(false);
        btnDeleteEmployee.setEnabled(false);
        btnUnpick.setEnabled(false);
    }

    // Поиск сотрудника
    private void searchEmployee() {
        unpick();

        String column = "";
        String query = "'%" + tfSearch.getText() + "%'";
        String option = "";

        if (rbID.isSelected()) {
            column = rbID.getActionCommand();
            option = rbID.getText();
        } else if (rbName.isSelected()) {
            column = rbName.getActionCommand();
            option = rbName.getText();
        } else if (rbPassport.isSelected()) {
            column = rbPassport.getActionCommand();
            option = rbPassport.getText();
        } else if (rbEducation.isSelected()) {
            column = rbEducation.getActionCommand();
            option = rbEducation.getText();
        } else if (rbAdress.isSelected()) {
            column = rbAdress.getActionCommand();
            option = rbAdress.getText();
        } else if (rbPhoneNumber.isSelected()) {
            column = rbPhoneNumber.getActionCommand();
            option = rbPhoneNumber.getText();
        } else if (rbEmail.isSelected()) {
            column = rbEmail.getActionCommand();
            option = rbEmail.getText();
        } else if (rbPost.isSelected()) {
            column = rbPost.getActionCommand();
            option = rbPost.getText();
        } else if (rbDepartment.isSelected()) {
            column = rbDepartment.getActionCommand();
            option = rbDepartment.getText();
        }

        ArrayList<EmployeeView> result = dbService.findEmployee(column, query);

        if (result.size() == 0) {
            JOptionPane.showMessageDialog(
                    frame,
                    String.format(
                            "По запросу \"%s\" по критерию \"%s\" ничего не найдено.\n" +
                            "Попробуйте уточнить запрос или сменить категорию",
                            tfSearch.getText(), option
                    ),
                    "Не найдено",
                    JOptionPane.INFORMATION_MESSAGE
            );
            tfSearch.setText("");
        } else {
            DefaultTableModel searchTableModel = new DefaultTableModel();
            searchTableModel.setColumnIdentifiers(columnsHeader);

            for (int i = 0; i < result.size(); i++) {
                searchTableModel.insertRow(i, new Object[] {
                        result.get(i).getID(),
                        result.get(i).getName(),
                        result.get(i).getDateOfBirth(),
                        result.get(i).getPassport(),
                        result.get(i).getEducation(),
                        result.get(i).getAdress(),
                        result.get(i).getPhoneNumber(),
                        result.get(i).getEmail(),
                        result.get(i).getDateOfEmployment(),
                        result.get(i).getPost(),
                        result.get(i).getDepartment(),
                        result.get(i).getPersonalSalary()
                });
            }
            table.setModel(searchTableModel);
        }
    }

    // Очистка результатов поиска
    private void clearSearchResult() {
        unpick();
        tfSearch.setText("");
    }

    // Удаление сотрудника
    private void deleteEmployee() {
        int result = JOptionPane.showConfirmDialog(
                frame,
                String.format("Удалить сотрудника %s?", currentEmployee.getName()),
                "Подтверждение операции",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {

            if (cbAllowSignIn.isSelected()) {
                dbService.deleteRegistrationData(currentRegistrationData.getEmployee_ID());
            }

            dbService.deleteEmployee(currentEmployee.getID());

            JOptionPane.showMessageDialog(
                    frame,
                    String.format("Сотрудник %s успешно удалён.", currentEmployee.getName()),
                    "Удаление завершено",
                    JOptionPane.INFORMATION_MESSAGE
            );
            if (!(Objects.isNull(currentRegistrationData))) {
                currentRegistrationData = null;
            }

            currentEmployee = null;
            unpick();
            tfSearch.setText("");
        }
    }

    // Обновление данных о сотруднике и его учётной записи в системе
    private void updateEmployee() {
        int result = JOptionPane.showConfirmDialog(
                frame,
                String.format("Изменить данные сотрудника %s?", currentEmployee.getName()),
                "Подтверждение операции",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            if (
                    checkTextField(tfName.getText(), tfName.getColumns()) &&
                    checkTextField(tfEducation.getText(), tfEducation.getColumns()) &&
                    checkTextField(tfAddress.getText(), tfAddress.getColumns()) &&
                    checkNumericTextField(tfPassport.getText(), tfPassport.getColumns()) &&
                    checkNumericTextField(tfPhoneNumber.getText(), tfPhoneNumber.getColumns())
            ) {
                dbService.updateEmployee(
                        currentEmployee.getID(),
                        tfName.getText(),
                        dateSpinner.getDatabaseFormatDate(),
                        tfPassport.getText(),
                        tfEducation.getText(),
                        tfAddress.getText(),
                        tfPhoneNumber.getText(),
                        tfEmail.getText(),
                        cbboxPost.getSelectedIndex() + 1,
                        cbboxDepartment.getSelectedIndex() + 1,
                        Integer.parseInt(spinnerPersonalSalary.getValue().toString())
                );

                if (cbboxPost.getSelectedIndex() + 1 == dbService.getChiefID()) {
                    dbService.setChiefWhenUpdate(cbboxDepartment.getSelectedIndex() + 1, currentEmployee.getID());
                }

                if (cbAllowSignIn.isSelected() && !(Objects.isNull(currentRegistrationData))) {
                    if (
                            checkTextField(tfLogin.getText(), tfLogin.getColumns()) &&
                            checkTextField(tfPassword.getText(), tfPassword.getColumns())
                    ) {
                        dbService.updateRegistrationData(
                                currentRegistrationData.getEmployee_ID(),
                                tfLogin.getText(),
                                tfPassword.getText(),
                                cbboxRole.getSelectedIndex() + 1
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                frame,
                                "Одно из полей пустое или содержит недопустимое значение.",
                                "Ошибка при обновлении",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                } else if (cbAllowSignIn.isSelected() && Objects.isNull(currentRegistrationData)) {
                    if (
                            checkTextField(tfLogin.getText(), tfLogin.getColumns()) && checkTextField(tfPassword.getText(), tfPassword.getColumns())
                    ) {
                        dbService.insertRegistrationData(
                                currentEmployee.getID(),
                                tfLogin.getText(),
                                tfPassword.getText(),
                                cbboxRole.getSelectedIndex() + 1
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                frame,
                                "Одно из полей пустое или содержит недопустимое значение.",
                                "Ошибка при обновлении",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                } else if (!(cbAllowSignIn.isSelected()) && !(Objects.isNull(currentRegistrationData))) {
                    dbService.deleteRegistrationData(currentRegistrationData.getEmployee_ID());
                    currentRegistrationData = null;
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
}
