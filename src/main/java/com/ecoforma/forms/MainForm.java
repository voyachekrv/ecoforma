package com.ecoforma.forms;

import com.ecoforma.db.mappers.HRMapper;
import com.ecoforma.entities.Employee;

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
    JTable table;
    JTextField tfName, tfDateOfBirth, tfPassport, tfEducation, tfAddress, tfPhone, tfEmail;

    String currentSession;
    Insets insets;

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
                displayHumanResourceInterface();
                break;
            default:
                JOptionPane.showMessageDialog(frame, "Для данной роли не доступен интерфейс", "Системная ошибка", JOptionPane.ERROR_MESSAGE);
                frame.setVisible(false);
                signInForm.frame.setVisible(true);
                break;
        }
    }

    private void displayHumanResourceInterface() {
        // Кнопка выхода из системы

        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBounds(0, 0, 1346, 44);
        frame.getContentPane().add(toolBar);

        btnSignOut = new JButton("Выход из системы");
        btnSignOut.setFocusPainted(false);
        toolBar.add(btnSignOut);

        btnSignOut.addActionListener(actionEvent -> signOut());

        // Таблица со списком сотрудников фирмы

        JPanel tablePanel = new JPanel();
        tablePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        tablePanel.setBounds(10, 98, 1326, 430);
        frame.getContentPane().add(tablePanel);

        table = new JTable(setInitialTableModel());
        table.setRowHeight(30);

        JScrollPane tableScroll = new JScrollPane();
        tableScroll.setViewportView(table);
        tableScroll.setPreferredSize(new Dimension(1300, 420));
        tablePanel.add(tableScroll);

        // Интерфейс поиска

        tfSearch = new JTextField();
        tfSearch.setBounds(10, 64, 128, 23);
        frame.getContentPane().add(tfSearch);
        tfSearch.setColumns(50);

        btnSearch = new JButton("Поиск");
        btnSearch.setBounds(145, 64, 70, 23);
        btnSearch.setFocusPainted(false);
        frame.getContentPane().add(btnSearch);

        JLabel lSearchOptions = new JLabel("Критерии поиска:");
        lSearchOptions.setBounds(222, 70, 105, 14);
        frame.getContentPane().add(lSearchOptions);

        rbID = new JRadioButton("Табельный номер");
        rbID.setBounds(325, 66, 135, 23);
        rbID.setFocusPainted(false);
        frame.getContentPane().add(rbID);

        rbName = new JRadioButton("Имя");
        rbName.setBounds(456, 66, 55, 23);
        rbName.setSelected(true);
        rbName.setFocusPainted(false);
        frame.getContentPane().add(rbName);

        rbPassport = new JRadioButton("Номер паспорта");
        rbPassport.setFocusPainted(false);
        rbPassport.setBounds(507, 66, 119, 23);
        frame.getContentPane().add(rbPassport);

        rbEducation = new JRadioButton("Образование");
        rbEducation.setFocusPainted(false);
        rbEducation.setBounds(626, 66, 106, 23);
        frame.getContentPane().add(rbEducation);

        rbAdress = new JRadioButton("Адрес");
        rbAdress.setFocusPainted(false);
        rbAdress.setBounds(730, 66, 67, 23);
        frame.getContentPane().add(rbAdress);

        rbPhoneNumber = new JRadioButton("Номер телефона");
        rbPhoneNumber.setFocusPainted(false);
        rbPhoneNumber.setBounds(797, 66, 125, 23);
        frame.getContentPane().add(rbPhoneNumber);

        rbEmail = new JRadioButton("E-mail");
        rbEmail.setFocusPainted(false);
        rbEmail.setBounds(920, 66, 67, 23);
        frame.getContentPane().add(rbEmail);

        rbPost = new JRadioButton("Должность");
        rbPost.setFocusPainted(false);
        rbPost.setBounds(985, 66, 96, 23);
        frame.getContentPane().add(rbPost);

        rbDepartment = new JRadioButton("Отдел");
        rbDepartment.setFocusPainted(false);
        rbDepartment.setBounds(1080, 66, 67, 23);
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
        tfName.setBounds(30, 554, 86, 20);
        frame.getContentPane().add(tfName);
        tfName.setColumns(10);

        JLabel lName = new JLabel("ФИО");
        lName.setBounds(30, 529, 46, 14);
        frame.getContentPane().add(lName);

        tfDateOfBirth = new JTextField();
        tfDateOfBirth.setBounds(126, 554, 86, 20);
        frame.getContentPane().add(tfDateOfBirth);
        tfDateOfBirth.setColumns(10);

        JLabel lDateOfBirth = new JLabel("Дата рождения");
        lDateOfBirth.setBounds(126, 529, 86, 14);
        frame.getContentPane().add(lDateOfBirth);

        tfPassport = new JTextField();
        tfPassport.setBounds(222, 554, 86, 20);
        frame.getContentPane().add(tfPassport);
        tfPassport.setColumns(10);

        JLabel lPassport = new JLabel("Номер паспорта");
        lPassport.setBounds(222, 529, 86, 14);
        frame.getContentPane().add(lPassport);

        tfEducation = new JTextField();
        tfEducation.setBounds(318, 554, 86, 20);
        frame.getContentPane().add(tfEducation);
        tfEducation.setColumns(10);

        JLabel lEducation = new JLabel("Образование");
        lEducation.setBounds(320, 529, 84, 14);
        frame.getContentPane().add(lEducation);

        tfAddress = new JTextField();
        tfAddress.setBounds(414, 554, 86, 20);
        frame.getContentPane().add(tfAddress);
        tfAddress.setColumns(10);

        JLabel lAddress = new JLabel("Адрес");
        lAddress.setBounds(414, 529, 46, 14);
        frame.getContentPane().add(lAddress);

        tfPhone = new JTextField();
        tfPhone.setBounds(510, 554, 86, 20);
        frame.getContentPane().add(tfPhone);
        tfPhone.setColumns(10);

        JLabel lblNewLabel_6 = new JLabel("Телефон");
        lblNewLabel_6.setBounds(510, 529, 83, 14);
        frame.getContentPane().add(lblNewLabel_6);

        tfEmail = new JTextField();
        tfEmail.setBounds(606, 554, 86, 20);
        frame.getContentPane().add(tfEmail);
        tfEmail.setColumns(10);

        JLabel lblNewLabel_7 = new JLabel("E-mail");
        lblNewLabel_7.setBounds(610, 529, 46, 14);
        frame.getContentPane().add(lblNewLabel_7);
    }

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

        HRMapper mapper = session.getMapper(HRMapper.class);
        ArrayList<Employee> employees = mapper.getAllEmployees();

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

    private void signOut() {
        currentSession = null;
        frame.setVisible(false);
        signInForm.frame.setVisible(true);
    }
}
