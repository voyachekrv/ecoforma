package com.ecoforma.forms;

import com.ecoforma.db.DbSession;
import com.ecoforma.db.mappers.HRMapper;
import com.ecoforma.services.Checker;
import com.ecoforma.services.Initializer;
import org.apache.ibatis.session.SqlSession;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import static com.ecoforma.App.hrForm;

public class NewEmployeeForm {
    JFrame frame;
    private JTextField tfSurname;
    private JTextField tfName;
    private JTextField tfPatronym;
    private JTextField tfDateOfBirth;
    private JTextField tfPassport;
    private JTextField tfAdress;
    private JTextField tfPhoneNumber;
    private JTextField tfEmail;
    private JTextField tfLogin;
    private JTextField tfPassword;
    private JTextArea textAreaEducation;
    private JCheckBox cbNoEmail;
    private JComboBox cbbxPost;
    private JComboBox cbboxDepartment;
    private JComboBox cbbxRole;
    private JCheckBox cbAllowSignIn;
    private JButton btnGenerateLogin;
    private JButton btnGeneratePassword;
    private JButton btnCancel;
    private JButton btnSave;

    private Initializer initializer;

    NewEmployeeForm() throws IOException {
        initializer = new Initializer();

        frame = initializer.newFrame("Новый сотрудник",  new Rectangle(598,  144, 800, 790), JFrame.DO_NOTHING_ON_CLOSE);

        JPanel panel = initializer.newPanelBevel(10, 11, 764, 739);
        frame.getContentPane().add(panel);

        JPanel panelName = initializer.newPanelEtched(10, 11, 744, 72);
        panel.add(panelName);

        tfSurname = initializer.newTextFieldBigFont(17, new Rectangle(10, 34, 244, 27));
        panelName.add(tfSurname);

        tfName = initializer.newTextFieldBigFont(16, new Rectangle(264, 34, 230, 27));
        panelName.add(tfName);

        tfPatronym = initializer.newTextFieldBigFont(16, new Rectangle(504, 34, 230, 27));
        panelName.add(tfPatronym);

        JLabel lSurname = initializer.newLabel("Фамилия", new Rectangle(10, 9, 230, 20));
        panelName.add(lSurname);

        JLabel lName = initializer.newLabel("Имя", new Rectangle(264, 12, 230, 20));
        panelName.add(lName);

        JLabel lPatronym = initializer.newLabel("Отчество", new Rectangle(504, 12, 230, 20));
        panelName.add(lPatronym);

        JPanel panelPassportData = initializer.newPanelEtched(10, 94, 744, 72);
        panel.add(panelPassportData);

        tfDateOfBirth = initializer.newTextFieldBigFont(20, new Rectangle(10, 34, 258, 27));
        panelPassportData.add(tfDateOfBirth);

        tfPassport = initializer.newTextFieldBigFont(10, new Rectangle(278, 34, 456, 27));
        panelPassportData.add(tfPassport);

        JLabel lDateOfBirth = initializer.newLabel("Дата рождения", new Rectangle(10, 11, 230, 20));
        panelPassportData.add(lDateOfBirth);

        JLabel lPassport = initializer.newLabel("Номер паспорта", new Rectangle(278, 14, 230, 20));
        panelPassportData.add(lPassport);

        JPanel panelEducation = initializer.newPanelTitled("Сведения об образовании", new Rectangle(10, 177, 744, 203));
        panel.add(panelEducation);

        textAreaEducation = initializer.newTextAreaBigFont(
                10, 21, 724, 171,
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        tfAdress.requestFocus();
                    }
                });

        panelEducation.add(textAreaEducation);

        JPanel panelContactData = initializer.newPanelEtched(10, 391, 744, 131);
        panel.add(panelContactData);

        tfAdress = initializer.newTextFieldBigFont(200, new Rectangle(10, 34, 724, 27));
        panelContactData.add(tfAdress);

        tfEmail = initializer.newTextFieldBigFont(70, new Rectangle(10, 93, 361, 27));
        panelContactData.add(tfEmail);

        tfPhoneNumber = initializer.newTextFieldBigFont(12, new Rectangle(381, 93, 353, 27));
        panelContactData.add(tfPhoneNumber);

        cbNoEmail = initializer.newCheckBox("Почта отсутствует", new Rectangle(60, 68, 165, 20));
        panelContactData.add(cbNoEmail);

        JLabel lAdress = initializer.newLabel("Домашний адрес", new Rectangle(10, 9, 230, 20));
        panelContactData.add(lAdress);

        JLabel lEmail = initializer.newLabel("E-mail", new Rectangle(10, 68, 230, 20));
        panelContactData.add(lEmail);

        JLabel lPhoneNumber = initializer.newLabel("Номер телефона", new Rectangle(381, 68, 230, 20));
        panelContactData.add(lPhoneNumber);

        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);

            cbbxPost = initializer.newComboBox(mapper.getPostNames(), new Rectangle(176, 533, 200, 22));
            cbbxPost.setEnabled(true);
            panel.add(cbbxPost);

            cbboxDepartment = initializer.newComboBox(mapper.getDepartmentNames(), new Rectangle(452, 533, 200, 22));
            cbboxDepartment.setEnabled(true);
            panel.add(cbboxDepartment);
        }

        JLabel lAppointAs = initializer.newLabel("Назначить на должность: ", new Rectangle(10, 534, 157, 20));
        panel.add(lAppointAs);

        JPanel panelSignIn = initializer.newPanelEtched(10, 565, 744, 108);
        panel.add(panelSignIn);

        cbAllowSignIn = initializer.newCheckBox("Позволить регистрацию в системе", new Rectangle(6, 7, 265, 23));
        panelSignIn.add(cbAllowSignIn);

        tfLogin = initializer.newTextFieldDisabled(20, new Rectangle(10, 60, 162, 23));
        panelSignIn.add(tfLogin);

        tfPassword = initializer.newTextFieldDisabled(40, new Rectangle(281, 60, 162, 23));
        panelSignIn.add(tfPassword);

        btnGenerateLogin = initializer.newButton("Создать", new Rectangle(182, 60, 89, 23));
        panelSignIn.add(btnGenerateLogin);

        btnGeneratePassword = initializer.newButton("Создать", new Rectangle(453, 60, 89, 23));
        panelSignIn.add(btnGeneratePassword);

        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);
            cbbxRole = initializer.newComboBox(mapper.getRoleNames(), new Rectangle(552, 60, 182, 22));
            panelSignIn.add(cbbxRole);
        }

        JLabel lLogin = initializer.newLabel("Логин", new Rectangle(10, 37, 162, 20));
        panelSignIn.add(lLogin);

        JLabel lPassword = initializer.newLabel("Пароль", new Rectangle(281, 37, 162, 20));
        panelSignIn.add(lPassword);

        JLabel lRole = initializer.newLabel("Роль", new Rectangle(552, 37, 182, 20));
        panelSignIn.add(lRole);

        JLabel lInDepartment = initializer.newLabel("В отдел: ", new Rectangle(387, 533, 116, 20));
        panel.add(lInDepartment);

        btnCancel = initializer.newButtonEnabled("Отмена", new Rectangle(638, 698, 116, 30));
        panel.add(btnCancel);

        btnSave = initializer.newButtonEnabled("Сохранить", new Rectangle(512, 698, 116, 30));
        panel.add(btnSave);

        cbNoEmail.addItemListener(itemEvent -> {
            if (cbNoEmail.isSelected()) {
                tfEmail.setEnabled(false);
            } else {
                tfEmail.setEnabled(true);
            }
        });

        cbAllowSignIn.addItemListener(itemEvent -> {
            if (cbAllowSignIn.isSelected()) {
                tfLogin.setEnabled(true);
                tfPassword.setEnabled(true);
                cbbxRole.setEnabled(true);
                btnGenerateLogin.setEnabled(true);
                btnGeneratePassword.setEnabled(true);
            } else {
                tfLogin.setEnabled(false);
                tfPassword.setEnabled(false);
                cbbxRole.setEnabled(false);
                cbbxRole.setSelectedIndex(0);
                btnGenerateLogin.setEnabled(false);
                btnGeneratePassword.setEnabled(false);
            }
        });

        btnCancel.addActionListener(actionEvent -> {
            int result = JOptionPane.showConfirmDialog(
                    frame,
                    "Отменить добавление сотрудника?",
                    "Подтверждение операции",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
                frame.setVisible(false);
                hrForm.frame.setEnabled(true);
            }
        });

        btnSave.addActionListener(actionEvent -> addEmployee());

        btnGenerateLogin.addActionListener(actionEvent -> tfLogin.setText(generateString((byte) 10, "abcdefghijklmnopqrstuvwxyz")));

        btnGeneratePassword.addActionListener(actionEvent -> tfPassword.setText(generateString((byte) 15, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!№;%:?*()_+=")));

        frame.setVisible(true);
    }

    private void addEmployee() {
        int result = JOptionPane.showConfirmDialog(
                frame,
                "Добавить нового сотрудника " + tfSurname.getText()+ " " + tfName.getText() + " " + tfPatronym.getText() + "?",
                "Подтверждение операции",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            Checker checker = new Checker();
            if (
                    checker.checkTextField(tfSurname.getText(), tfSurname.getColumns()) &&
                    checker.checkTextField(tfName.getText(), tfName.getColumns()) &&
                    checker.checkTextField(tfPatronym.getText(), tfPatronym.getColumns()) &&
                    checker.checkDateTextField(tfDateOfBirth.getText()) &&
                    checker.checkNumericTextField(tfPassport.getText(), tfPassport.getColumns()) &&
                    checker.checkTextField(textAreaEducation.getText(), 300) &&
                    checker.checkTextField(tfAdress.getText(), tfAdress.getColumns()) &&
                    checker.checkNumericTextField(tfPhoneNumber.getText(), tfPhoneNumber.getColumns())
            ) {
                try (SqlSession session = DbSession.startSession()) {
                    HRMapper mapper = session.getMapper(HRMapper.class);

                    mapper.insertEmployee(
                            tfSurname.getText()+ " " + tfName.getText() + " " + tfPatronym.getText(),
                            tfDateOfBirth.getText(),
                            tfPassport.getText(),
                            textAreaEducation.getText(),
                            tfAdress.getText(),
                            tfPhoneNumber.getText(),
                            tfEmail.getText(),
                            cbbxPost.getSelectedIndex() + 1,
                            cbboxDepartment.getSelectedIndex() + 1
                    );

                    if ((cbbxPost.getSelectedIndex() + 1) == mapper.getChiefID()) {
                        mapper.setChiefWhenInsert(cbboxDepartment.getSelectedIndex() + 1);
                    }

                    if (cbAllowSignIn.isSelected()) {
                        if (
                                checker.checkTextField(tfLogin.getText(), tfLogin.getColumns()) &&
                                checker.checkTextField(tfPassword.getText(), tfPassword.getColumns())
                        ) {
                            mapper.insertRegistrationDataWithEmployee(tfLogin.getText(), tfPassword.getText(), cbbxRole.getSelectedIndex() + 1);
                        } else {
                            JOptionPane.showMessageDialog(
                                    frame,
                                    "Одно из полей пустое или содержит недопустимое значение.",
                                    "Ошибка при добавлении",
                                    JOptionPane.WARNING_MESSAGE
                            );
                        }
                    }
                    session.commit();
                }

                JOptionPane.showMessageDialog(
                        frame,
                        "Сотрудник успешно добавлен.",
                        "Добавление завершено",
                        JOptionPane.INFORMATION_MESSAGE
                );
                frame.setVisible(false);
                hrForm.frame.setEnabled(true);
                hrForm.unpick();
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

    @NotNull
    private String generateString(byte length, String symbols) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(symbols.charAt((int) Math.floor(Math.random() * symbols.length())));
        }

        return builder.toString();
    }
}
