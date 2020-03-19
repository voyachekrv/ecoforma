package com.ecoforma.forms;

import com.ecoforma.db.mappers.HRMapper;
import com.ecoforma.entities.Employee;
import com.ecoforma.entities.EmployeeFull;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import static com.ecoforma.App.*;

public class MainForm {
    JFrame frame;
    JToolBar toolBar;
    JButton btnSignOut;
    JTextField tfSearch;
    JRadioButton rbID, rbName, rbPassport, rbEducation, rbAdress, rbPhoneNumber, rbEmail, rbPost, rbDepartment;
    JButton btnSearch, btnStopSearch;
    JScrollPane tableScroll;
    JTable table;
    JTextField tfName, tfDateOfBirth, tfPassport, tfEducation, tfAddress, tfPhoneNumber, tfEmail;
    JSpinner spinnerPersonalSalary;
    JButton btnAcceptChanges, btnDeleteEmployee, btnUnpick, btnNewEmployee;
    JComboBox cbboxPost, cbboxDepartment;

    Action actionListener;
    DefaultTableModel initialTableModel;

    String currentSession;
    Insets insets;
    HRMapper mapper = session.getMapper(HRMapper.class);
    Employee currentEmployee;

    public MainForm(String department) throws IOException {
        frame = new JFrame(COMPANY_NAME + " - " + department); // Основная панель формы
        frame.setSize(1362, 790); // Установка размеров
        frame.setLocation(323,  144);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Установка операции закрытия окна приложения
        frame.setIconImage(ImageIO.read(getClass().getResource("/img/logo1.png")));
        frame.setLayout(null);
        currentSession = department;
        insets = frame.getInsets();

        displayInterface(department);
    }

    private void displayInterface(String department) {
        switch (department) {
            case "Отдел кадров":
                displayHRInterface();
                break;
            default:
                JOptionPane.showMessageDialog(frame, "Для данной роли не доступен интерфейс", "Системная ошибка", JOptionPane.ERROR_MESSAGE);
                frame.setVisible(false);
                signInForm.frame.setVisible(true);
                break;
        }
    }

    // Отображение интерфейса отдела кадров
    private void displayHRInterface() {
        // Кнопка выхода из системы

        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBounds(0, 0, 1346, 44);
        frame.getContentPane().add(toolBar);

        btnSignOut = new JButton("Выход из системы", new ImageIcon(getClass().getResource("/img/icon-logout.png")));
        btnSignOut.setFocusPainted(false);
        toolBar.add(btnSignOut);

        // Таблица со списком сотрудников фирмы

        JPanel tablePanel = new JPanel();
        tablePanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        tablePanel.setBounds(10, 98, 1326, 430);
        frame.getContentPane().add(tablePanel);

        table = new JTable(setInitialTableModel());
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null);
        table.setCursor(new Cursor(Cursor.HAND_CURSOR));

        tableScroll = new JScrollPane();
        tableScroll.setViewportView(table);
        tableScroll.setPreferredSize(new Dimension(1300, 420));
        tableScroll.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tablePanel.add(tableScroll);

        // Интерфейс поиска

        tfSearch = new JTextField();
        tfSearch.setBounds(10, 64, 128, 23);
        frame.getContentPane().add(tfSearch);
        tfSearch.setColumns(50);

        btnSearch = new JButton("Поиск", new ImageIcon(getClass().getResource("/img/icon-search.png")));
        btnSearch.setBounds(145, 64, 90, 23);
        btnSearch.setFocusPainted(false);
        frame.getContentPane().add(btnSearch);

        btnStopSearch = new JButton(new ImageIcon(getClass().getResource("/img/icon-close.png")));
        btnStopSearch.setBounds(237, 64, 24, 23);
        btnStopSearch.setFocusPainted(false);
        btnStopSearch.setToolTipText("Очистка результов поиска");
        frame.getContentPane().add(btnStopSearch);

        JLabel lSearchOptions = new JLabel("Критерии поиска:");
        lSearchOptions.setBounds(267, 70, 105, 14);
        frame.getContentPane().add(lSearchOptions);

        rbID = new JRadioButton("Табельный номер");
        rbID.setBounds(370, 66, 135, 23);
        rbID.setFocusPainted(false);
        rbID.setActionCommand("employee.ID");
        rbID.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.getContentPane().add(rbID);

        rbName = new JRadioButton("Имя");
        rbName.setBounds(506, 66, 55, 23);
        rbName.setSelected(true);
        rbName.setFocusPainted(false);
        rbName.setActionCommand("employee.name");
        rbName.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.getContentPane().add(rbName);

        rbPassport = new JRadioButton("Номер паспорта");
        rbPassport.setFocusPainted(false);
        rbPassport.setBounds(557, 66, 119, 23);
        rbPassport.setActionCommand("employee.passport");
        rbPassport.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.getContentPane().add(rbPassport);

        rbEducation = new JRadioButton("Образование");
        rbEducation.setFocusPainted(false);
        rbEducation.setBounds(675, 66, 106, 23);
        rbEducation.setActionCommand("employee.education");
        rbEducation.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.getContentPane().add(rbEducation);

        rbAdress = new JRadioButton("Адрес");
        rbAdress.setFocusPainted(false);
        rbAdress.setBounds(777, 66, 67, 23);
        rbAdress.setActionCommand("employee.adress");
        rbAdress.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.getContentPane().add(rbAdress);

        rbPhoneNumber = new JRadioButton("Номер телефона");
        rbPhoneNumber.setFocusPainted(false);
        rbPhoneNumber.setBounds(840, 66, 125, 23);
        rbPhoneNumber.setActionCommand("employee.phoneNumber");
        rbPhoneNumber.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.getContentPane().add(rbPhoneNumber);

        rbEmail = new JRadioButton("E-mail");
        rbEmail.setFocusPainted(false);
        rbEmail.setBounds(970, 66, 67, 23);
        rbEmail.setActionCommand("employee.email");
        rbEmail.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.getContentPane().add(rbEmail);

        rbPost = new JRadioButton("Должность");
        rbPost.setFocusPainted(false);
        rbPost.setBounds(1035, 66, 96, 23);
        rbPost.setActionCommand("post.name");
        rbPost.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.getContentPane().add(rbPost);

        rbDepartment = new JRadioButton("Отдел");
        rbDepartment.setFocusPainted(false);
        rbDepartment.setBounds(1130, 66, 67, 23);
        rbDepartment.setActionCommand("department.name");
        rbDepartment.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
        tfName = new JTextField();
        tfName.setBounds(30, 562, 185, 23);
        tfName.setEnabled(false);
        frame.getContentPane().add(tfName);
        tfName.setColumns(10);

        JLabel lName = new JLabel("ФИО");
        lName.setBounds(30, 538, 46, 14);
        frame.getContentPane().add(lName);

        tfDateOfBirth = new JTextField();
        tfDateOfBirth.setBounds(222, 562, 185, 23);
        tfDateOfBirth.setEnabled(false);
        frame.getContentPane().add(tfDateOfBirth);
        tfDateOfBirth.setColumns(10);

        JLabel lDateOfBirth = new JLabel("Дата рождения");
        lDateOfBirth.setBounds(222, 539, 100, 14);
        frame.getContentPane().add(lDateOfBirth);

        tfPassport = new JTextField();
        tfPassport.setBounds(417, 562, 185, 23);
        tfPassport.setEnabled(false);
        frame.getContentPane().add(tfPassport);
        tfPassport.setColumns(10);

        JLabel lPassport = new JLabel("Номер паспорта");
        lPassport.setBounds(417, 538, 100, 14);
        frame.getContentPane().add(lPassport);

        tfEducation = new JTextField();
        tfEducation.setBounds(30, 621, 572, 23);
        tfEducation.setEnabled(false);
        frame.getContentPane().add(tfEducation);
        tfEducation.setColumns(10);

        JLabel lEducation = new JLabel("Образование");
        lEducation.setBounds(30, 596, 84, 14);
        frame.getContentPane().add(lEducation);

        tfAddress = new JTextField();
        tfAddress.setBounds(30, 680, 572, 23);
        tfAddress.setEnabled(false);
        frame.getContentPane().add(tfAddress);
        tfAddress.setColumns(10);

        JLabel lAddress = new JLabel("Адрес");
        lAddress.setBounds(30, 655, 46, 14);
        frame.getContentPane().add(lAddress);

        tfPhoneNumber = new JTextField();
        tfPhoneNumber.setBounds(612, 562, 185, 23);
        tfPhoneNumber.setEnabled(false);
        frame.getContentPane().add(tfPhoneNumber);
        tfPhoneNumber.setColumns(10);

        JLabel lPhoneNumber = new JLabel("Телефон");
        lPhoneNumber.setBounds(612, 539, 83, 14);
        frame.getContentPane().add(lPhoneNumber);

        tfEmail = new JTextField();
        tfEmail.setBounds(612, 621, 185, 23);
        tfEmail.setEnabled(false);
        frame.getContentPane().add(tfEmail);
        tfEmail.setColumns(10);

        JLabel lEmail = new JLabel("E-mail");
        lEmail.setBounds(612, 596, 46, 14);
        frame.getContentPane().add(lEmail);

        spinnerPersonalSalary = new JSpinner();
        spinnerPersonalSalary.setBounds(612, 680, 185, 23);
        spinnerPersonalSalary.setEnabled(false);
        frame.add(spinnerPersonalSalary);
        spinnerPersonalSalary.setModel(new SpinnerNumberModel(1, 1, 3999999, 1));

        JLabel lPersonalSalary = new JLabel("Заработная плата");
        lPersonalSalary.setBounds(612, 655, 150, 14);
        frame.getContentPane().add(lPersonalSalary);

        // Кнопки внизу панели редактирования сотрудника

        btnAcceptChanges = new JButton("Подтвердить изменения", new ImageIcon(getClass().getResource("/img/icon-accept.png")));
        btnAcceptChanges.setBounds(30, 714, 210, 30);
        btnAcceptChanges.setFocusPainted(false);
        btnAcceptChanges.setEnabled(false);
        frame.getContentPane().add(btnAcceptChanges);

        btnDeleteEmployee = new JButton("Удалить сотрудника", new ImageIcon(getClass().getResource("/img/icon-delete.png")));
        btnDeleteEmployee.setBounds(250, 714, 185, 30);
        btnDeleteEmployee.setFocusPainted(false);
        btnDeleteEmployee.setEnabled(false);
        frame.getContentPane().add(btnDeleteEmployee);

        btnUnpick = new JButton("Снять выбор", new ImageIcon(getClass().getResource("/img/icon-unfocus.png")));
        btnUnpick.setBounds(445, 714, 140, 30);
        btnUnpick.setFocusPainted(false);
        btnUnpick.setEnabled(false);
        frame.getContentPane().add(btnUnpick);

        btnNewEmployee = new JButton("Новый сотрудник", new ImageIcon(getClass().getResource("/img/icon-add.png")));
        btnNewEmployee.setBounds(595, 714, 170, 30);
        btnNewEmployee.setFocusPainted(false);
        frame.getContentPane().add(btnNewEmployee);

        JLabel lPost = new JLabel("Должность");
        lPost.setBounds(807, 538, 150, 14);
        frame.getContentPane().add(lPost);

        cbboxPost = new JComboBox();
        cbboxPost.setModel(new DefaultComboBoxModel(mapper.getPostNames()));
        cbboxPost.setBounds(807, 562, 185, 23);
        cbboxPost.setEnabled(false);
        frame.getContentPane().add(cbboxPost);

        JLabel lDepartment = new JLabel("Отдел");
        lDepartment.setBounds(1007, 538, 150, 14);
        frame.getContentPane().add(lDepartment);

        cbboxDepartment = new JComboBox();
        cbboxDepartment.setModel(new DefaultComboBoxModel(mapper.getDepartmentNames()));
        cbboxDepartment.setBounds(1007, 562, 220, 23);
        cbboxDepartment.setEnabled(false);
        frame.getContentPane().add(cbboxDepartment);

        // Выход из системы
        btnSignOut.addActionListener(actionEvent -> signOut());

        // Добавление сотрудника в поля для редактирования
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(listSelectionEvent -> prepareToEdit());

        // Снятие фокуса с таблицы
        btnUnpick.addActionListener(actionEvent -> unpick());

        // Добавление комбинации ctrl+f для активации формы поиска
        String ACTION_KEY = "theAction";

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

        // Добавление события поиска по нажатию кнопки
        btnSearch.addActionListener(actionEvent -> searchEmployee());

        // Поиск сотрудника по нажатию Enter после ввода данных в поле
        tfSearch.addActionListener(actionEvent -> searchEmployee());

        // Событие очистки результатов поиска
        btnStopSearch.addActionListener(actionEvent -> clearSearchResult());

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
    }

    // Задание модели таблицы по умолчанию
    private DefaultTableModel setInitialTableModel() {
        initialTableModel = new DefaultTableModel();
        String[] columnsHeader = new String[] {
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

        initialTableModel.setColumnIdentifiers(columnsHeader);

        ArrayList<EmployeeFull> employees = mapper.getAllEmployees();

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

    // Выход из системы
    private void signOut() {
        currentSession = null;
        frame.setVisible(false);
        signInForm.frame.setVisible(true);
    }

    // Помещение значений данных о сотруднике в поля редактирования
    private void prepareToEdit() {
        int rowIndex = table.getSelectedRow();
        currentEmployee = mapper.getEmployeeByID(Integer.parseInt(table.getModel().getValueAt(rowIndex, 0).toString()));

        tfName.setText(currentEmployee.getName());
        tfDateOfBirth.setText(currentEmployee.getDateOfBirth());
        tfEmail.setText(currentEmployee.getEmail());
        tfAddress.setText(currentEmployee.getAdress());
        tfEducation.setText(currentEmployee.getEducation());
        tfPassport.setText(currentEmployee.getPassport());
        spinnerPersonalSalary.setValue(Integer.parseInt(currentEmployee.getPersonalSalary()));
        tfPhoneNumber.setText(currentEmployee.getPhoneNumber());

        cbboxPost.setSelectedIndex((int) currentEmployee.getPost_ID() - 1);
        cbboxDepartment.setSelectedIndex((int) currentEmployee.getDepartment_ID() - 1);

        tfName.setEnabled(true);
        tfDateOfBirth.setEnabled(true);
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
    }

    // Удаление фокуса с таблицы
    private void removeFocusFromTable() {
        tableScroll.setViewportView(null);

        table = new JTable(setInitialTableModel());
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null);
        tableScroll.setViewportView(table);

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(listSelectionEvent -> prepareToEdit());
    }

    // Перевод полей редактирования сотрудника в неактивное состояние + удаление фокуса с таблицы
    private void unpick() {
        removeFocusFromTable();

        tfName.setText("");
        tfDateOfBirth.setText("");
        tfEmail.setText("");
        tfAddress.setText("");
        tfEducation.setText("");
        tfPassport.setText("");
        spinnerPersonalSalary.setValue(1);
        tfPhoneNumber.setText("");

        tfName.setEnabled(false);
        tfDateOfBirth.setEnabled(false);
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

        ArrayList<EmployeeFull> result = mapper.findEmployee(column, query);

        if (result.size() == 0) {
            JOptionPane.showMessageDialog(frame,
                    "По запросу \"" + tfSearch.getText() + "\" по критерию \"" + option +
                            "\" ничего не найдено.\nПопробуйте уточнить запрос или изменить критерий поиска.", "Не найдено",
                    JOptionPane.INFORMATION_MESSAGE
            );
            tfSearch.setText("");
            return;
        }

        DefaultTableModel searchTableModel = new DefaultTableModel();
        String[] columnsHeader = new String[] {
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

    // Очистка результатов поиска
    private void clearSearchResult() {
        unpick();
        tfSearch.setText("");
    }

    // Удаление сотрудника
    private void deleteEmployee() {
        int result = JOptionPane.showConfirmDialog(
                frame,
                "Удалить сотрудника " + currentEmployee.getName() + "?",
                "Подтверждение операции",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            mapper.deleteEmployee(currentEmployee.getID());
            JOptionPane.showMessageDialog(
                    frame,
                    "Сотрудник " + currentEmployee.getName() + " успешно удалён.",
                    "Удаление завершено",
                    JOptionPane.INFORMATION_MESSAGE
            );

            currentEmployee = null;
            unpick();
            tfSearch.setText("");
        }

        if (result == JOptionPane.NO_OPTION) {
            return;
        }
    }
}
