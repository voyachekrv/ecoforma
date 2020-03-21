package com.ecoforma.forms;

import com.ecoforma.db.DbSession;
import com.ecoforma.db.mappers.HRMapper;
import org.apache.ibatis.session.SqlSession;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;

import static com.ecoforma.App.mainForm;

public class NewEmployeeForm {
    JFrame frame;
    JTextField tfSurname;
    JTextField tfName;
    JTextField tfPatronym;
    JTextField tfDateOfBirth;
    JTextField tfPassport;
    JTextField tfAdress;
    JTextField tfEmail;
    JTextField tfLogin;
    JTextField tfPassword;
    JEditorPane editorEducation;
    JCheckBox cbNoEmail;
    JComboBox cbbxPost;
    JComboBox cbboxDepartment;
    JComboBox cbbxRole;
    JCheckBox cbAllowSignIn;
    JButton btnGenerateLogin;
    JButton btnGeneratePassword;
    JButton btnCancel;
    JButton btnSave;

    NewEmployeeForm() throws IOException {
        frame = new JFrame("Новый сотрудник"); // Основная панель формы
        frame.setSize(790, 790); // Установка размеров
        frame.setLocation(598,  144);
        frame.setResizable(false);
        frame.setIconImage(ImageIO.read(getClass().getResource("/img/logo1.png")));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Установка операции закрытия окна приложения
        frame.getContentPane().setLayout(null);

        JPanel panel = initialisePanelBevel(10, 11, 764, 739);
        frame.getContentPane().add(panel);

        JPanel panelName = initialisePanelEtched(10, 11, 744, 72);
        panel.add(panelName);

        tfSurname = mainForm.initialiseTextField(17, new Rectangle(10, 34, 244, 27));
        tfSurname.setEnabled(true);
        panelName.add(tfSurname);

        tfName = mainForm.initialiseTextField(16, new Rectangle(264, 34, 230, 27));
        tfName.setEnabled(true);
        panelName.add(tfName);

        tfPatronym = mainForm.initialiseTextField(16, new Rectangle(504, 34, 230, 27));
        tfPatronym.setEnabled(true);
        panelName.add(tfPatronym);

        JLabel lSurname = mainForm.initialiseLabel("Фамилия", new Rectangle(10, 9, 230, 20));
        lSurname.setEnabled(true);
        panelName.add(lSurname);

        JLabel lName = mainForm.initialiseLabel("Имя", new Rectangle(264, 12, 230, 20));
        lName.setEnabled(true);
        panelName.add(lName);

        JLabel lPatronym = mainForm.initialiseLabel("Отчество", new Rectangle(504, 12, 230, 20));
        lPatronym.setEnabled(true);
        panelName.add(lPatronym);

        JPanel panelPassportData = initialisePanelEtched(10, 94, 744, 72);
        panel.add(panelPassportData);

        tfDateOfBirth = mainForm.initialiseTextField(20, new Rectangle(10, 34, 258, 27));
        tfDateOfBirth.setEnabled(true);
        panelPassportData.add(tfDateOfBirth);

        tfPassport = mainForm.initialiseTextField(10, new Rectangle(278, 34, 456, 27));
        tfPassport.setEnabled(true);
        panelPassportData.add(tfPassport);

        JLabel lDateOfBirth = mainForm.initialiseLabel("Дата рождения", new Rectangle(10, 11, 230, 20));
        panelPassportData.add(lDateOfBirth);

        JLabel lPassport = mainForm.initialiseLabel("Номер паспорта", new Rectangle(278, 14, 230, 20));
        panelPassportData.add(lPassport);

        JPanel panelEducation = initialisePanelTitled("Сведения об образовании", new Rectangle(10, 177, 744, 203));
        panel.add(panelEducation);

        editorEducation = new JEditorPane();
        editorEducation.setBounds(10, 21, 724, 171);
        panelEducation.add(editorEducation);

        JPanel panelContactData = initialisePanelEtched(10, 391, 744, 131);
        panel.add(panelContactData);

        tfAdress = mainForm.initialiseTextField(200, new Rectangle(10, 34, 724, 27));
        tfAdress.setEnabled(true);
        panelContactData.add(tfAdress);

        tfEmail = mainForm.initialiseTextField(70, new Rectangle(10, 93, 361, 27));
        tfEmail.setEnabled(true);
        panelContactData.add(tfEmail);

        cbNoEmail = initialiseCheckBox("Почта отсутствует", new Rectangle(377, 93, 357, 27));
        panelContactData.add(cbNoEmail);

        JLabel lAdress = mainForm.initialiseLabel("Домашний адрес", new Rectangle(10, 9, 230, 20));
        panelContactData.add(lAdress);

        JLabel lEmail = mainForm.initialiseLabel("E-mail", new Rectangle(10, 68, 230, 20));
        panelContactData.add(lEmail);

        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);

            cbbxPost = mainForm.initialiseComboBox(mapper.getPostNames(), new Rectangle(176, 533, 200, 22));
            cbbxPost.setEnabled(true);
            panel.add(cbbxPost);

            cbboxDepartment = mainForm.initialiseComboBox(mapper.getDepartmentNames(), new Rectangle(452, 533, 200, 22));
            cbboxDepartment.setEnabled(true);
            panel.add(cbboxDepartment);
        }

        JLabel lAppointAs = mainForm.initialiseLabel("Назначить на должность: ", new Rectangle(10, 534, 157, 20));
        panel.add(lAppointAs);

        JPanel panelSignIn = initialisePanelEtched(10, 565, 744, 108);
        panel.add(panelSignIn);

        cbAllowSignIn = initialiseCheckBox("Позволить регистрацию в системе", new Rectangle(6, 7, 265, 23));
        panelSignIn.add(cbAllowSignIn);

        tfLogin = mainForm.initialiseTextField(20, new Rectangle(10, 60, 162, 23));
        panelSignIn.add(tfLogin);

        tfPassword = mainForm.initialiseTextField(40, new Rectangle(281, 60, 162, 23));
        panelSignIn.add(tfPassword);

        btnGenerateLogin = initialiseButton("Создать", new Rectangle(182, 60, 89, 23));
        panelSignIn.add(btnGenerateLogin);

        btnGeneratePassword = initialiseButton("Создать", new Rectangle(453, 60, 89, 23));
        panelSignIn.add(btnGeneratePassword);

        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);
            cbbxRole = mainForm.initialiseComboBox(mapper.getRoleNames(), new Rectangle(552, 60, 182, 22));
            panelSignIn.add(cbbxRole);
        }

        JLabel lLogin = mainForm.initialiseLabel("Логин", new Rectangle(10, 37, 162, 20));
        panelSignIn.add(lLogin);

        JLabel lPassword = mainForm.initialiseLabel("Пароль", new Rectangle(281, 37, 162, 20));
        panelSignIn.add(lPassword);

        JLabel lRole = mainForm.initialiseLabel("Роль", new Rectangle(552, 37, 182, 20));
        panelSignIn.add(lRole);

        JLabel lInDepartment = mainForm.initialiseLabel("В отдел: ", new Rectangle(387, 533, 116, 20));
        panel.add(lInDepartment);

        btnCancel = initialiseButton("Отмена", new Rectangle(638, 698, 116, 30));
        btnCancel.setEnabled(true);
        panel.add(btnCancel);

        btnSave = initialiseButton("Сохранить", new Rectangle(512, 698, 116, 30));
        btnSave.setEnabled(true);
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
                    JOptionPane.WARNING_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
                frame.setVisible(false);
                mainForm.frame.setEnabled(true);
            }
        });

        frame.setVisible(true);
    }

    @NotNull
    private JPanel initialisePanelEtched(int x, int y, int width, int height) {
        JPanel p = new JPanel();
        p.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        p.setBounds(x, y, width, height);
        p.setLayout(null);
        return p;
    }

    @NotNull
    private JPanel initialisePanelBevel(int x, int y, int width, int height) {
        JPanel p = new JPanel();
        p.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        p.setBounds(x, y, width, height);
        p.setLayout(null);
        return p;
    }

    @NotNull
    private JPanel initialisePanelTitled(String t, @NotNull Rectangle r) {
        JPanel p = new JPanel();
        p.setBorder(new TitledBorder(null, t, TitledBorder.LEADING, TitledBorder.TOP, null, null));
        p.setBounds(r.x, r.y, r.width, r.height);
        p.setLayout(null);
        return p;
    }

    @NotNull
    private JCheckBox initialiseCheckBox(String t, @NotNull Rectangle r) {
        JCheckBox cb = new JCheckBox(t);
        cb.setBounds(r.x, r.y, r.width, r.height);
        cb.setFocusPainted(false);
        return cb;
    }

    @NotNull
    public JButton initialiseButton(String t, @NotNull Rectangle r) {
        JButton b = new JButton(t);
        b.setBounds(r.x, r.y, r.width, r.height);
        b.setFocusPainted(false);
        b.setEnabled(false);
        return b;
    }

}
