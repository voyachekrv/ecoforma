package com.ecoforma.frontend.forms;

import com.ecoforma.db.services.HRService;
import com.ecoforma.frontend.components.JDateSpinner;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.WindowEvent;

import static com.ecoforma.App.hrForm;
import static com.ecoforma.frontend.services.Checker.checkNumericTextField;
import static com.ecoforma.frontend.services.Checker.checkTextField;
import static com.ecoforma.frontend.services.JComponentFactory.*;

public class NewEmployeeForm {
    JFrame frame;
    private final JTextField tfSurname;
    private final JTextField tfName;
    private final JTextField tfPatronymic;
    private final JTextField tfPassport;
    private final JTextField tfAddress;
    private final JTextField tfPhoneNumber;
    private final JTextField tfEmail;
    private final JTextField tfLogin;
    private final JTextField tfPassword;
    private final JTextArea textAreaEducation;
    private final JCheckBox cbNoEmail;
    private final JComboBox<String > comboBoxPost;
    private final JComboBox<String > comboBoxDepartment;
    private final JComboBox<String > comboBoxRole;
    private final JCheckBox cbAllowSignIn;
    private final JButton btnGenerateLogin;
    private final JButton btnGeneratePassword;
    private final JDateSpinner dateSpinner;

    private final HRService dbService;

    NewEmployeeForm() {
        dbService = new HRService();

        frame = newFrame("Новый сотрудник",  new Rectangle(598,  144, 800, 790),
                new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        frame.setVisible(false);
                        hrForm.frame.setEnabled(true);
                    }
                }
        );

        JPanel panel = newPanelBevel(10, 11, 764, 739);
        frame.getContentPane().add(panel);

        JPanel panelName = newPanelEtched(10, 11, 744, 72);
        panel.add(panelName);

        tfSurname = newTextFieldBigFont(17, new Rectangle(10, 34, 244, 27));
        panelName.add(tfSurname);

        tfName = newTextFieldBigFont(16, new Rectangle(264, 34, 230, 27));
        panelName.add(tfName);

        tfPatronymic = newTextFieldBigFont(16, new Rectangle(504, 34, 230, 27));
        panelName.add(tfPatronymic);

        JLabel lSurname = newLabel("Фамилия", new Rectangle(10, 9, 230, 20));
        panelName.add(lSurname);

        JLabel lName = newLabel("Имя", new Rectangle(264, 12, 230, 20));
        panelName.add(lName);

        JLabel lPatronymic = newLabel("Отчество", new Rectangle(504, 12, 230, 20));
        panelName.add(lPatronymic);

        JPanel panelPassportData = newPanelEtched(10, 94, 744, 72);
        panel.add(panelPassportData);

        dateSpinner = new JDateSpinner(10, 34, 258, 27, true);
        panelPassportData.add(dateSpinner);

        tfPassport = newTextFieldBigFont(10, new Rectangle(278, 34, 456, 27));
        panelPassportData.add(tfPassport);

        JLabel lDateOfBirth = newLabel("Дата рождения", new Rectangle(10, 11, 230, 20));
        panelPassportData.add(lDateOfBirth);

        JLabel lPassport = newLabel("Номер паспорта", new Rectangle(278, 14, 230, 20));
        panelPassportData.add(lPassport);

        JPanel panelEducation = newPanelTitled("Сведения об образовании", new Rectangle(10, 177, 744, 203));
        panel.add(panelEducation);

        textAreaEducation = newTextAreaBigFont(
                10, 21, 724, 171,
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        tfAddress.requestFocus();
                    }
                });

        panelEducation.add(textAreaEducation);

        JPanel panelContactData = newPanelEtched(10, 391, 744, 131);
        panel.add(panelContactData);

        tfAddress = newTextFieldBigFont(200, new Rectangle(10, 34, 724, 27));
        panelContactData.add(tfAddress);

        tfEmail = newTextFieldBigFont(70, new Rectangle(10, 93, 361, 27));
        panelContactData.add(tfEmail);

        tfPhoneNumber = newTextFieldBigFont(12, new Rectangle(381, 93, 353, 27));
        panelContactData.add(tfPhoneNumber);

        cbNoEmail = newCheckBox("Почта отсутствует", new Rectangle(60, 68, 165, 20));
        panelContactData.add(cbNoEmail);

        JLabel lAddress = newLabel("Домашний адрес", new Rectangle(10, 9, 230, 20));
        panelContactData.add(lAddress);

        JLabel lEmail = newLabel("E-mail", new Rectangle(10, 68, 230, 20));
        panelContactData.add(lEmail);

        JLabel lPhoneNumber = newLabel("Номер телефона", new Rectangle(381, 68, 230, 20));
        panelContactData.add(lPhoneNumber);

        comboBoxPost = newComboBox(dbService.getPostNames(), new Rectangle(176, 533, 200, 22));
        comboBoxPost.setEnabled(true);
        panel.add(comboBoxPost);

        comboBoxDepartment = newComboBox(dbService.getDepartmentNames(), new Rectangle(452, 533, 200, 22));
        comboBoxDepartment.setEnabled(true);
        panel.add(comboBoxDepartment);

        JLabel lAppointAs = newLabel("Назначить на должность: ", new Rectangle(10, 534, 157, 20));
        panel.add(lAppointAs);

        JPanel panelSignIn = newPanelEtched(10, 565, 744, 108);
        panel.add(panelSignIn);

        cbAllowSignIn = newCheckBox("Позволить регистрацию в системе", new Rectangle(6, 7, 265, 23));
        panelSignIn.add(cbAllowSignIn);

        tfLogin = newTextFieldDisabled(20, new Rectangle(10, 60, 162, 23));
        panelSignIn.add(tfLogin);

        tfPassword = newTextFieldDisabled(40, new Rectangle(281, 60, 162, 23));
        panelSignIn.add(tfPassword);

        btnGenerateLogin = newButton("Создать", new Rectangle(182, 60, 89, 23));
        panelSignIn.add(btnGenerateLogin);

        btnGeneratePassword = newButton("Создать", new Rectangle(453, 60, 89, 23));
        panelSignIn.add(btnGeneratePassword);

        comboBoxRole = newComboBox(dbService.getRoleNames(), new Rectangle(552, 60, 182, 22));
        panelSignIn.add(comboBoxRole);

        JLabel lLogin = newLabel("Логин", new Rectangle(10, 37, 162, 20));
        panelSignIn.add(lLogin);

        JLabel lPassword = newLabel("Пароль", new Rectangle(281, 37, 162, 20));
        panelSignIn.add(lPassword);

        JLabel lRole = newLabel("Роль", new Rectangle(552, 37, 182, 20));
        panelSignIn.add(lRole);

        JLabel lInDepartment = newLabel("В отдел: ", new Rectangle(387, 533, 116, 20));
        panel.add(lInDepartment);

        JButton btnCancel = newButtonEnabled("Отмена", new Rectangle(638, 698, 116, 30));
        panel.add(btnCancel);

        JButton btnSave = newButtonEnabled("Сохранить", new Rectangle(512, 698, 116, 30));
        panel.add(btnSave);

        cbNoEmail.addItemListener(this::itemStateChanged);

        cbAllowSignIn.addItemListener(itemEvent -> {
            if (cbAllowSignIn.isSelected()) {
                tfLogin.setEnabled(true);
                tfPassword.setEnabled(true);
                comboBoxRole.setEnabled(true);
                btnGenerateLogin.setEnabled(true);
                btnGeneratePassword.setEnabled(true);
            } else {
                tfLogin.setEnabled(false);
                tfPassword.setEnabled(false);
                comboBoxRole.setEnabled(false);
                comboBoxRole.setSelectedIndex(0);
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
                String.format(
                        "Добавить нового сотрудника %s %s %s?",
                        tfSurname.getText(), tfName.getText(), tfPatronymic.getText()
                ),
                "Подтверждение операции",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            if (
                    checkTextField(tfSurname.getText(), tfSurname.getColumns()) &&
                    checkTextField(tfName.getText(), tfName.getColumns()) &&
                    checkTextField(tfPatronymic.getText(), tfPatronymic.getColumns()) &&
                    checkNumericTextField(tfPassport.getText(), tfPassport.getColumns()) &&
                    checkTextField(textAreaEducation.getText(), 300) &&
                    checkTextField(tfAddress.getText(), tfAddress.getColumns()) &&
                    checkNumericTextField(tfPhoneNumber.getText(), tfPhoneNumber.getColumns())
            ) {
                dbService.insertEmployee(
                    tfSurname.getText()+ " " + tfName.getText() + " " + tfPatronymic.getText(),
                    dateSpinner.getDatabaseFormatDate(),
                    tfPassport.getText(),
                    textAreaEducation.getText(),
                    tfAddress.getText(),
                    tfPhoneNumber.getText(),
                    tfEmail.getText(),
                    comboBoxPost.getSelectedIndex() + 1,
                    comboBoxDepartment.getSelectedIndex() + 1
                );

                if ((comboBoxPost.getSelectedIndex() + 1) == dbService.getChiefID()) {
                    dbService.setChiefWhenInsert(comboBoxDepartment.getSelectedIndex() + 1);
                }

                if (cbAllowSignIn.isSelected()) {
                    if (
                            checkTextField(tfLogin.getText(), tfLogin.getColumns()) &&
                            checkTextField(tfPassword.getText(), tfPassword.getColumns())
                    ) {
                        dbService.insertRegistrationDataWithEmployee(tfLogin.getText(), tfPassword.getText(), comboBoxRole.getSelectedIndex() + 1);
                    } else {
                        JOptionPane.showMessageDialog(
                                frame,
                                "Одно из полей пустое или содержит недопустимое значение.",
                                "Ошибка при добавлении",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
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

    private void itemStateChanged(ItemEvent itemEvent) {
        tfEmail.setEnabled(!cbNoEmail.isSelected());
    }
}
