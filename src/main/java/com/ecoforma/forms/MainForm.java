package com.ecoforma.forms;

import com.ecoforma.db.mappers.HRMapper;
import com.ecoforma.entities.Employee;
import com.ecoforma.entities.EmployeeFull;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static com.ecoforma.App.*;

public class MainForm {
    JFrame frame;
    JToolBar toolBar;
    JButton btnSignOut;
    JTextField tfSearch;
    JRadioButton rbID, rbName, rbPassport, rbEducation, rbAdress, rbPhoneNumber, rbEmail, rbPost, rbDepartment;
    JButton btnSearch;
    JScrollPane tableScroll;
    JTable table;
    JTextField tfName, tfDateOfBirth, tfPassport, tfEducation, tfAddress, tfPhoneNumber, tfEmail, tfPersonalSalary;
    JButton btnAcceptChanges, btnDeleteEmployee, btnUnpick, btnNewEmployee;
    JComboBox cbboxPost, cbboxDepartment;

    String currentSession;
    Insets insets;
    HRMapper mapper = session.getMapper(HRMapper.class);

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

        tableScroll = new JScrollPane();
        tableScroll.setViewportView(table);
        tableScroll.setPreferredSize(new Dimension(1300, 420));
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

        JLabel lSearchOptions = new JLabel("Критерии поиска:");
        lSearchOptions.setBounds(252, 70, 105, 14);
        frame.getContentPane().add(lSearchOptions);

        rbID = new JRadioButton("Табельный номер");
        rbID.setBounds(355, 66, 135, 23);
        rbID.setFocusPainted(false);
        frame.getContentPane().add(rbID);

        rbName = new JRadioButton("Имя");
        rbName.setBounds(486, 66, 55, 23);
        rbName.setSelected(true);
        rbName.setFocusPainted(false);
        frame.getContentPane().add(rbName);

        rbPassport = new JRadioButton("Номер паспорта");
        rbPassport.setFocusPainted(false);
        rbPassport.setBounds(537, 66, 119, 23);
        frame.getContentPane().add(rbPassport);

        rbEducation = new JRadioButton("Образование");
        rbEducation.setFocusPainted(false);
        rbEducation.setBounds(656, 66, 106, 23);
        frame.getContentPane().add(rbEducation);

        rbAdress = new JRadioButton("Адрес");
        rbAdress.setFocusPainted(false);
        rbAdress.setBounds(760, 66, 67, 23);
        frame.getContentPane().add(rbAdress);

        rbPhoneNumber = new JRadioButton("Номер телефона");
        rbPhoneNumber.setFocusPainted(false);
        rbPhoneNumber.setBounds(827, 66, 125, 23);
        frame.getContentPane().add(rbPhoneNumber);

        rbEmail = new JRadioButton("E-mail");
        rbEmail.setFocusPainted(false);
        rbEmail.setBounds(950, 66, 67, 23);
        frame.getContentPane().add(rbEmail);

        rbPost = new JRadioButton("Должность");
        rbPost.setFocusPainted(false);
        rbPost.setBounds(1015, 66, 96, 23);
        frame.getContentPane().add(rbPost);

        rbDepartment = new JRadioButton("Отдел");
        rbDepartment.setFocusPainted(false);
        rbDepartment.setBounds(1110, 66, 67, 23);
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

        tfPersonalSalary = new JTextField();
        tfPersonalSalary.setBounds(612, 680, 185, 23);
        tfPersonalSalary.setEnabled(false);
        frame.getContentPane().add(tfPersonalSalary);
        tfPersonalSalary.setColumns(10);

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

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(listSelectionEvent -> prepareToEdit());

        btnUnpick.addActionListener(actionEvent -> unpick());
    }

    // Задание модели таблицы по умолчанию
    private DefaultTableModel setInitialTableModel() {
        DefaultTableModel tableModel = new DefaultTableModel();
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

        tableModel.setColumnIdentifiers(columnsHeader);

        ArrayList<EmployeeFull> employees = mapper.getAllEmployees();

        for (int i = 0; i < employees.size(); i++) {
            tableModel.insertRow(i, new Object[] {
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

        return tableModel;
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
        Employee row = mapper.getEmployeeByID(Integer.parseInt(table.getModel().getValueAt(rowIndex, 0).toString()));

        tfName.setText(row.getName());
        tfDateOfBirth.setText(row.getDateOfBirth());
        tfEmail.setText(row.getEmail());
        tfAddress.setText(row.getAdress());
        tfEducation.setText(row.getEducation());
        tfPassport.setText(row.getPassport());
        tfPersonalSalary.setText(row.getPersonalSalary());
        tfPhoneNumber.setText(row.getPhoneNumber());

        cbboxPost.setSelectedIndex((int) row.getPost_ID() - 1);
        cbboxDepartment.setSelectedIndex((int) row.getDepartment_ID() - 1);

        tfName.setEnabled(true);
        tfDateOfBirth.setEnabled(true);
        tfEmail.setEnabled(true);
        tfAddress.setEnabled(true);
        tfEducation.setEnabled(true);
        tfPassport.setEnabled(true);
        tfPersonalSalary.setEnabled(true);
        tfPhoneNumber.setEnabled(true);

        cbboxPost.setEnabled(true);
        cbboxDepartment.setEnabled(true);

        btnAcceptChanges.setEnabled(true);
        btnDeleteEmployee.setEnabled(true);
        btnUnpick.setEnabled(true);
    }

    private void unpick() {
        tableScroll.setViewportView(null);

        table = new JTable(setInitialTableModel());
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null);
        tableScroll.setViewportView(table);

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(listSelectionEvent -> prepareToEdit());

        tfName.setText("");
        tfDateOfBirth.setText("");
        tfEmail.setText("");
        tfAddress.setText("");
        tfEducation.setText("");
        tfPassport.setText("");
        tfPersonalSalary.setText("");
        tfPhoneNumber.setText("");

        tfName.setEnabled(false);
        tfDateOfBirth.setEnabled(false);
        tfEmail.setEnabled(false);
        tfAddress.setEnabled(false);
        tfEducation.setEnabled(false);
        tfPassport.setEnabled(false);
        tfPersonalSalary.setEnabled(false);
        tfPhoneNumber.setEnabled(false);

        cbboxPost.setSelectedIndex(0);
        cbboxPost.setEnabled(false);

        cbboxDepartment.setSelectedIndex(0);
        cbboxDepartment.setEnabled(false);

        btnAcceptChanges.setEnabled(false);
        btnDeleteEmployee.setEnabled(false);
        btnUnpick.setEnabled(false);
    }
}
