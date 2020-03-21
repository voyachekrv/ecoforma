package com.ecoforma.forms;

import com.ecoforma.db.DbSession;
import com.ecoforma.db.mappers.HRMapper;
import com.ecoforma.entities.Employee;
import com.ecoforma.entities.EmployeeFull;
import com.ecoforma.entities.RegistrationData;
import org.apache.ibatis.session.SqlSession;
import org.jetbrains.annotations.NotNull;

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
import java.util.Objects;

import static com.ecoforma.App.*;

// TODO: Добавить правильную размерность для всех текстфилдов согласно тому что прописано при создании БД

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
    JComboBox cbboxPost, cbboxDepartment;
    JTextField tfLogin, tfPassword;
    JComboBox cbboxRole;
    JCheckBox cbAllowSignIn;
    JButton btnAcceptChanges, btnDeleteEmployee, btnUnpick, btnNewEmployee;

    Action actionListener;
    DefaultTableModel initialTableModel;
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

    String currentSession;
    Insets insets;
    Employee currentEmployee;
    RegistrationData currentRegistrationData;

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

    private void displayInterface(@NotNull String department) {
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

        btnSignOut = initialiseButton("Выход из системы", "icon-logout", new Rectangle(0, 0, 1346, 44));
        btnSignOut.setEnabled(true);
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
        addUnpickEscape();

        tableScroll = new JScrollPane();
        tableScroll.setViewportView(table);
        tableScroll.setPreferredSize(new Dimension(1300, 420));
        tableScroll.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tablePanel.add(tableScroll);

        // Интерфейс поиска

        tfSearch = initialiseTextField(50, new Rectangle(10, 64, 128, 23));
        tfSearch.setEnabled(true);
        frame.getContentPane().add(tfSearch);

        btnSearch = initialiseButton("Поиск", "icon-search", new Rectangle(145, 64, 90, 23));
        btnSearch.setEnabled(true);
        frame.getContentPane().add(btnSearch);

        btnStopSearch = initialiseButton(null, "icon-close", new Rectangle(237, 64, 24, 23));
        btnStopSearch.setEnabled(true);
        btnStopSearch.setToolTipText("Очистка результов поиска");
        frame.getContentPane().add(btnStopSearch);

        JLabel lSearchOptions = initialiseLabel("Критерии поиска:", new Rectangle (267, 70, 105, 14));
        frame.getContentPane().add(lSearchOptions);

        rbID = initialiseRadioButton("Табельный номер", "employee.ID", new Rectangle(370, 66, 135, 23));
        frame.getContentPane().add(rbID);

        rbName = initialiseRadioButton("Имя", "employee.name", new Rectangle(506, 66, 55, 23));
        rbName.setSelected(true);
        frame.getContentPane().add(rbName);

        rbPassport = initialiseRadioButton("Номер паспорта", "employee.passport", new Rectangle(557, 66, 119, 23));
        frame.getContentPane().add(rbPassport);

        rbEducation = initialiseRadioButton("Образование", "employee.education", new Rectangle(675, 66, 106, 23));
        frame.getContentPane().add(rbEducation);

        rbAdress = initialiseRadioButton("Адрес", "employee.adress", new Rectangle(777, 66, 67, 23));
        frame.getContentPane().add(rbAdress);

        rbPhoneNumber = initialiseRadioButton("Номер телефона", "employee.phoneNumber", new Rectangle(840, 66, 125, 23));
        frame.getContentPane().add(rbPhoneNumber);

        rbEmail = initialiseRadioButton("E-mail", "employee.email", new Rectangle(970, 66, 67, 23));
        frame.getContentPane().add(rbEmail);

        rbPost = initialiseRadioButton("Должность", "post.name", new Rectangle(1035, 66, 96, 23));
        frame.getContentPane().add(rbPost);

        rbDepartment = initialiseRadioButton("Отдел", "department.name", new Rectangle(1130, 66, 67, 23));
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
        tfName = initialiseTextFieldSave(10, new Rectangle(30, 562, 185, 23));
        frame.getContentPane().add(tfName);

        JLabel lName = initialiseLabel("ФИО", new Rectangle (30, 538, 46, 14));
        frame.getContentPane().add(lName);

        tfDateOfBirth = initialiseTextFieldSave(10, new Rectangle(222, 562, 185, 23));
        frame.getContentPane().add(tfDateOfBirth);

        JLabel lDateOfBirth = initialiseLabel("Дата рождения", new Rectangle (222, 539, 100, 14));
        frame.getContentPane().add(lDateOfBirth);

        tfPassport = initialiseTextFieldSave(10, new Rectangle(417, 562, 185, 23));
        frame.getContentPane().add(tfPassport);

        JLabel lPassport = initialiseLabel("Номер паспорта", new Rectangle (417, 538, 100, 14));
        frame.getContentPane().add(lPassport);

        tfEducation = initialiseTextFieldSave(10, new Rectangle(30, 621, 572, 23));
        addSaveKeyCombination(tfEducation);
        frame.getContentPane().add(tfEducation);

        JLabel lEducation = initialiseLabel("Образование", new Rectangle (30, 596, 84, 14));
        frame.getContentPane().add(lEducation);

        tfAddress = initialiseTextFieldSave(10, new Rectangle(30, 680, 572, 23));
        frame.getContentPane().add(tfAddress);

        JLabel lAddress = initialiseLabel("Адрес", new Rectangle (30, 655, 46, 14));
        frame.getContentPane().add(lAddress);

        tfPhoneNumber = initialiseTextFieldSave(10, new Rectangle(612, 562, 185, 23));
        frame.getContentPane().add(tfPhoneNumber);

        JLabel lPhoneNumber = initialiseLabel("Телефон", new Rectangle (612, 539, 83, 14));
        frame.getContentPane().add(lPhoneNumber);

        tfEmail = initialiseTextFieldSave(10, new Rectangle(612, 621, 185, 23));
        frame.getContentPane().add(tfEmail);

        JLabel lEmail = initialiseLabel("E-mail", new Rectangle (612, 596, 46, 14));
        frame.getContentPane().add(lEmail);

        JLabel lPersonalSalary = initialiseLabel("Заработная плата", new Rectangle (612, 655, 150, 14));
        frame.getContentPane().add(lPersonalSalary);

        spinnerPersonalSalary = new JSpinner();
        spinnerPersonalSalary.setBounds(612, 680, 185, 23);
        spinnerPersonalSalary.setEnabled(false);
        frame.add(spinnerPersonalSalary);
        spinnerPersonalSalary.setModel(new SpinnerNumberModel(1, 1, 3999999, 100));
        addSaveKeyCombination(spinnerPersonalSalary);

        JLabel lPost = initialiseLabel("Должность", new Rectangle (807, 538, 150, 14));
        frame.getContentPane().add(lPost);

        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);

            cbboxPost = initialiseComboBox(mapper.getPostNames(), new Rectangle(807, 562, 185, 23));
            frame.getContentPane().add(cbboxPost);

            JLabel lDepartment = initialiseLabel("Отдел", new Rectangle (1007, 538, 150, 14));
            frame.getContentPane().add(lDepartment);

            cbboxDepartment = initialiseComboBox(mapper.getDepartmentNames(), new Rectangle(1007, 562, 220, 23));
            frame.getContentPane().add(cbboxDepartment);

            JLabel lLogin = initialiseLabel("Логин", new Rectangle (807, 596, 150, 14));
            frame.getContentPane().add(lLogin);

            tfLogin = initialiseTextFieldSave(10, new Rectangle(807, 621, 185, 23));
            frame.getContentPane().add(tfLogin);

            JLabel lPassword = initialiseLabel("Пароль", new Rectangle (807, 655, 150, 14));
            frame.getContentPane().add(lPassword);

            tfPassword = initialiseTextFieldSave(10, new Rectangle(807, 680, 185, 23));
            frame.getContentPane().add(tfPassword);

            JLabel lRole = initialiseLabel("Роль в системе", new Rectangle (1007, 596, 150, 14));
            frame.getContentPane().add(lRole);

            cbboxRole = initialiseComboBox(mapper.getRoleNames(), new Rectangle(1007, 621, 220, 23));
            frame.getContentPane().add(cbboxRole);
        }

        cbAllowSignIn = new JCheckBox("Позволить регистрироваться в системе");
        cbAllowSignIn.setBounds(1007, 680, 270, 23);
        cbAllowSignIn.setFocusPainted(false);
        cbAllowSignIn.setSelected(false);
        cbAllowSignIn.setEnabled(false);
        frame.getContentPane().add(cbAllowSignIn);

        // Кнопки внизу панели редактирования сотрудника

        btnAcceptChanges = initialiseButton("Подтвердить изменения", "icon-accept", new Rectangle(30, 714, 210, 30));
        frame.add(btnAcceptChanges);

        btnDeleteEmployee = initialiseButton("Удалить сотрудника", "icon-delete", new Rectangle(250, 714, 185, 30));
        frame.getContentPane().add(btnDeleteEmployee);

        btnUnpick = initialiseButton("Снять выбор", "icon-unfocus", new Rectangle(445, 714, 140, 30));
        frame.getContentPane().add(btnUnpick);

        btnNewEmployee = initialiseButton("Новый сотрудник","icon-add", new Rectangle(595, 714, 170, 30));
        btnNewEmployee.setEnabled(true);
        frame.getContentPane().add(btnNewEmployee);

        // Выход из системы
        btnSignOut.addActionListener(actionEvent -> signOut());

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
            try {
              newEmployeeForm = new NewEmployeeForm();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // Инициализация кнопки
    @NotNull
    private JButton initialiseButton(String t, String i, @NotNull Rectangle r) {
        JButton b = new JButton(t, new ImageIcon(getClass().getResource("/img/" + i + ".png")));
        b.setBounds(r.x, r.y, r.width, r.height);
        b.setFocusPainted(false);
        b.setEnabled(false);
        return b;
    }

    // Инициализация радиокнопки
    @NotNull
    private JRadioButton initialiseRadioButton(String t, String c, @NotNull Rectangle r) {
        JRadioButton rb = new JRadioButton(t);
        rb.setFocusPainted(false);
        rb.setBounds(r.x, r.y, r.width, r.height);
        rb.setActionCommand(c);
        rb.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return rb;
    }

    // Инициализция надписи
    @NotNull
    private JLabel initialiseLabel(String t, @NotNull Rectangle r) {
        JLabel l = new JLabel(t);
        l.setBounds(r.x, r.y, r.width, r.height);
        return l;
    }

    // Инициализация текстового поля
    @NotNull
    private JTextField initialiseTextField(int c, @NotNull Rectangle r) {
        JTextField tf = new JTextField();
        tf.setColumns(c);
        tf.setBounds(r.x, r.y, r.width, r.height);
        tf.setEnabled(false);
        return tf;
    }

    // Инициализация текстового поля с добавленной по умолчанию маской ctrl+s
    @NotNull
    private JTextField initialiseTextFieldSave(int c, @NotNull Rectangle r) {
        JTextField tf = new JTextField();
        tf.setColumns(c);
        tf.setBounds(r.x, r.y, r.width, r.height);
        tf.setEnabled(false);
        addSaveKeyCombination(tf);
        return tf;
    }

    // Инициализация выпадающего списка
    @NotNull
    private JComboBox initialiseComboBox(String[] m, @NotNull Rectangle r) {
        JComboBox cbbx = new JComboBox(new DefaultComboBoxModel(m));
        cbbx.setBounds(r.x, r.y, r.width, r.height);
        cbbx.setEnabled(false);
        addSaveKeyCombination(cbbx);
        return cbbx;
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
    private DefaultTableModel setInitialTableModel() {
        initialTableModel = new DefaultTableModel();
        initialTableModel.setColumnIdentifiers(columnsHeader);

        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);
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
        } catch (Exception e) {
            e.printStackTrace();
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
        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);

            int rowIndex = table.getSelectedRow();
            currentEmployee = mapper.getEmployeeByID(Integer.parseInt(table.getModel().getValueAt(rowIndex, 0).toString()));

            tfName.setText(currentEmployee.getName());
            tfDateOfBirth.setText(currentEmployee.getDateOfBirth());
            tfEmail.setText(currentEmployee.getEmail());
            tfAddress.setText(currentEmployee.getAdress());
            tfEducation.setText(currentEmployee.getEducation());
            tfPassport.setText(currentEmployee.getPassport());
            spinnerPersonalSalary.setValue(currentEmployee.getPersonalSalary());
            tfPhoneNumber.setText(currentEmployee.getPhoneNumber());

            cbboxPost.setSelectedIndex(currentEmployee.getPost_ID() - 1);
            cbboxDepartment.setSelectedIndex(currentEmployee.getDepartment_ID() - 1);

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

            cbAllowSignIn.setEnabled(true);

            currentRegistrationData = mapper.getRegistrationData(currentEmployee.getID());

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
    }

    // Удаление фокуса с таблицы
    private void removeFocusFromTable() {
        tableScroll.setViewportView(null);

        table = new JTable(setInitialTableModel());
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null);
        table.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addUnpickEscape();
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
        tfLogin.setText("");
        tfPassword.setText("");
        cbAllowSignIn.setSelected(false);
        cbAllowSignIn.setEnabled(false);

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

        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);
            ArrayList<EmployeeFull> result = mapper.findEmployee(column, query);

            if (result.size() == 0) {
                JOptionPane.showMessageDialog(frame,
                        "По запросу \"" + tfSearch.getText() + "\" по критерию \"" + option +
                                "\" ничего не найдено.\nПопробуйте уточнить запрос или изменить критерий поиска.", "Не найдено",
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
            try (SqlSession session = DbSession.startSession()) {
                HRMapper mapper = session.getMapper(HRMapper.class);
                mapper.deleteEmployee(currentEmployee.getID());
                mapper.deleteRegistrationData((int) currentRegistrationData.getEmployee_ID());
                session.commit();
            }

            JOptionPane.showMessageDialog(
                    frame,
                    "Сотрудник " + currentEmployee.getName() + " успешно удалён.",
                    "Удаление завершено",
                    JOptionPane.INFORMATION_MESSAGE
            );

            currentEmployee = null;
            currentRegistrationData = null;
            unpick();
            tfSearch.setText("");
        }
    }

    // Обновление данных о сотруднике и его учётной записи в системе
    private void updateEmployee() {
        int result = JOptionPane.showConfirmDialog(
                frame,
                "Изменить данные сотрудника " + currentEmployee.getName() + "?",
                "Подтверждение операции",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            try (SqlSession session = DbSession.startSession()) {
                HRMapper mapper = session.getMapper(HRMapper.class);
                mapper.updateEmployee(
                        currentEmployee.getID(),
                        tfName.getText(),
                        tfDateOfBirth.getText(),
                        tfPassport.getText(),
                        tfEducation.getText(),
                        tfAddress.getText(),
                        tfPhoneNumber.getText(),
                        tfEmail.getText(),
                        cbboxPost.getSelectedIndex() + 1,
                        cbboxDepartment.getSelectedIndex() + 1,
                        Integer.parseInt(spinnerPersonalSalary.getValue().toString())
                );

                if (cbAllowSignIn.isSelected() && !(Objects.isNull(currentRegistrationData))) {
                    mapper.updateRegistrationData(
                            (int) currentRegistrationData.getEmployee_ID(),
                            tfLogin.getText(),
                            tfPassword.getText(),
                            cbboxRole.getSelectedIndex() + 1
                    );
                } else if (cbAllowSignIn.isSelected() && Objects.isNull(currentRegistrationData)) {
                    mapper.insertRegistrationData(
                            currentEmployee.getID(),
                            tfLogin.getText(),
                            tfPassword.getText(),
                            cbboxRole.getSelectedIndex() + 1
                    );
                } else if (!(cbAllowSignIn.isSelected()) && !(Objects.isNull(currentRegistrationData))) {
                    mapper.deleteRegistrationData((int) currentRegistrationData.getEmployee_ID());
                    currentRegistrationData = null;
                }

                session.commit();
            }
            unpick();
        }
    }
}
