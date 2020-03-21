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
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import static com.ecoforma.App.hrForm;

public class NewEmployeeForm {
    JFrame frame;
    JTextField tfSurname;
    JTextField tfName;
    JTextField tfPatronym;
    JTextField tfDateOfBirth;
    JTextField tfPassport;
    JTextField tfAdress;
    JTextField tfPhoneNumber;
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
        frame.setSize(800, 790); // Установка размеров
        frame.setLocation(598,  144);
        frame.setResizable(false);
        frame.setIconImage(ImageIO.read(getClass().getResource("/img/logo1.png")));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Установка операции закрытия окна приложения
        frame.getContentPane().setLayout(null);

        JPanel panel = initialisePanelBevel(10, 11, 764, 739);
        frame.getContentPane().add(panel);

        JPanel panelName = initialisePanelEtched(10, 11, 744, 72);
        panel.add(panelName);

        tfSurname = initialiseTextField(17, new Rectangle(10, 34, 244, 27));
        panelName.add(tfSurname);

        tfName = initialiseTextField(16, new Rectangle(264, 34, 230, 27));
        panelName.add(tfName);

        tfPatronym = initialiseTextField(16, new Rectangle(504, 34, 230, 27));
        panelName.add(tfPatronym);

        JLabel lSurname = hrForm.initialiseLabel("Фамилия", new Rectangle(10, 9, 230, 20));
        panelName.add(lSurname);

        JLabel lName = hrForm.initialiseLabel("Имя", new Rectangle(264, 12, 230, 20));
        panelName.add(lName);

        JLabel lPatronym = hrForm.initialiseLabel("Отчество", new Rectangle(504, 12, 230, 20));
        panelName.add(lPatronym);

        JPanel panelPassportData = initialisePanelEtched(10, 94, 744, 72);
        panel.add(panelPassportData);

        tfDateOfBirth = initialiseTextField(20, new Rectangle(10, 34, 258, 27));
        panelPassportData.add(tfDateOfBirth);

        tfPassport = initialiseTextField(10, new Rectangle(278, 34, 456, 27));
        panelPassportData.add(tfPassport);

        JLabel lDateOfBirth = hrForm.initialiseLabel("Дата рождения", new Rectangle(10, 11, 230, 20));
        panelPassportData.add(lDateOfBirth);

        JLabel lPassport = hrForm.initialiseLabel("Номер паспорта", new Rectangle(278, 14, 230, 20));
        panelPassportData.add(lPassport);

        JPanel panelEducation = initialisePanelTitled("Сведения об образовании", new Rectangle(10, 177, 744, 203));
        panel.add(panelEducation);

        editorEducation = new JEditorPane();
        editorEducation.setBounds(10, 21, 724, 171);
        editorEducation.setFont(new Font("Default", Font.PLAIN, 14));
        String ACTION_KEY = "saveAction";
        Action actionListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tfAdress.requestFocus();
            }
        };
        KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK);
        InputMap inputMap = editorEducation.getInputMap(JComponent.WHEN_FOCUSED);
        inputMap.put(ctrlS, ACTION_KEY);
        ActionMap actionMap = editorEducation.getActionMap();
        actionMap.put(ACTION_KEY, actionListener);
        editorEducation.setActionMap(actionMap);
        panelEducation.add(editorEducation);

        JPanel panelContactData = initialisePanelEtched(10, 391, 744, 131);
        panel.add(panelContactData);

        tfAdress = initialiseTextField(200, new Rectangle(10, 34, 724, 27));
        panelContactData.add(tfAdress);

        tfEmail = initialiseTextField(70, new Rectangle(10, 93, 361, 27));
        panelContactData.add(tfEmail);

        tfPhoneNumber = initialiseTextField(12, new Rectangle(381, 93, 353, 27));
        panelContactData.add(tfPhoneNumber);

        cbNoEmail = initialiseCheckBox("Почта отсутствует", new Rectangle(60, 68, 165, 20));
        panelContactData.add(cbNoEmail);

        JLabel lAdress = hrForm.initialiseLabel("Домашний адрес", new Rectangle(10, 9, 230, 20));
        panelContactData.add(lAdress);

        JLabel lEmail = hrForm.initialiseLabel("E-mail", new Rectangle(10, 68, 230, 20));
        panelContactData.add(lEmail);

        JLabel lphoneNumber = hrForm.initialiseLabel("Номер телефона", new Rectangle(381, 68, 230, 20));
        panelContactData.add(lphoneNumber);

        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);

            cbbxPost = hrForm.initialiseComboBox(mapper.getPostNames(), new Rectangle(176, 533, 200, 22));
            cbbxPost.setEnabled(true);
            panel.add(cbbxPost);

            cbboxDepartment = hrForm.initialiseComboBox(mapper.getDepartmentNames(), new Rectangle(452, 533, 200, 22));
            cbboxDepartment.setEnabled(true);
            panel.add(cbboxDepartment);
        }

        JLabel lAppointAs = hrForm.initialiseLabel("Назначить на должность: ", new Rectangle(10, 534, 157, 20));
        panel.add(lAppointAs);

        JPanel panelSignIn = initialisePanelEtched(10, 565, 744, 108);
        panel.add(panelSignIn);

        cbAllowSignIn = initialiseCheckBox("Позволить регистрацию в системе", new Rectangle(6, 7, 265, 23));
        panelSignIn.add(cbAllowSignIn);

        tfLogin = initialiseTextField(20, new Rectangle(10, 60, 162, 23));
        tfLogin.setEnabled(false);
        tfLogin.setFont(new Font("Default", Font.PLAIN, 12));
        panelSignIn.add(tfLogin);

        tfPassword = initialiseTextField(40, new Rectangle(281, 60, 162, 23));
        tfPassword.setEnabled(false);
        tfPassword.setFont(new Font("Default", Font.PLAIN, 12));
        panelSignIn.add(tfPassword);

        btnGenerateLogin = initialiseButton("Создать", new Rectangle(182, 60, 89, 23));
        panelSignIn.add(btnGenerateLogin);

        btnGeneratePassword = initialiseButton("Создать", new Rectangle(453, 60, 89, 23));
        panelSignIn.add(btnGeneratePassword);

        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);
            cbbxRole = hrForm.initialiseComboBox(mapper.getRoleNames(), new Rectangle(552, 60, 182, 22));
            panelSignIn.add(cbbxRole);
        }

        JLabel lLogin = hrForm.initialiseLabel("Логин", new Rectangle(10, 37, 162, 20));
        panelSignIn.add(lLogin);

        JLabel lPassword = hrForm.initialiseLabel("Пароль", new Rectangle(281, 37, 162, 20));
        panelSignIn.add(lPassword);

        JLabel lRole = hrForm.initialiseLabel("Роль", new Rectangle(552, 37, 182, 20));
        panelSignIn.add(lRole);

        JLabel lInDepartment = hrForm.initialiseLabel("В отдел: ", new Rectangle(387, 533, 116, 20));
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
                hrForm.frame.setEnabled(true);
            }
        });

        btnSave.addActionListener(actionEvent -> addEmployee());

        btnGenerateLogin.addActionListener(actionEvent -> tfLogin.setText(generateLogin()));

        btnGeneratePassword.addActionListener(actionEvent -> tfPassword.setText(generatePassword()));

        frame.setVisible(true);
    }

    @NotNull
    private JTextField initialiseTextField(int c, @NotNull Rectangle r) {
        JTextField tf = new JTextField();
        tf.setColumns(c);
        tf.setBounds(r.x, r.y, r.width, r.height);
        tf.setFont(new Font("Default", Font.PLAIN, 14));
        return tf;
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

    private void addEmployee() {
        int result = JOptionPane.showConfirmDialog(
                frame,
                "Добавить нового сотрудника " + tfSurname.getText()+ " " + tfName.getText() + " " + tfPatronym.getText() + "?",
                "Подтверждение операции",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            try (SqlSession session = DbSession.startSession()) {
                HRMapper mapper = session.getMapper(HRMapper.class);

                mapper.insertEmployee(
                        tfSurname.getText()+ " " + tfName.getText() + " " + tfPatronym.getText(),
                        tfDateOfBirth.getText(),
                        tfPassport.getText(),
                        editorEducation.getText(),
                        tfAdress.getText(),
                        tfPhoneNumber.getText(),
                        tfEmail.getText(),
                        cbbxPost.getSelectedIndex() + 1,
                        cbboxDepartment.getSelectedIndex() + 1
                );

                if (cbAllowSignIn.isSelected()) {
                    mapper.insertRegistrationDataWithEmployee(tfLogin.getText(), tfPassword.getText(), cbbxRole.getSelectedIndex() + 1);
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
        }
    }

    private String generateLogin() {
        String[] syllable_1 = new String[] { "Ald", "Aeld", "Alf", "Aelf", "Alh", "Aelh", "Athel", "Aethel", "Beo", "Beor", "Berh", "Brih", "Briht", "Cad", "Cead", "Cen", "Coel", "Cuth", "Cyne", "Ed", "Ead", "El", "Eal", "Eld", "Eg", "Ecg", "Eorp", "God", "Guth", "Har", "Hwaet", "Leo", "Leof", "Oft", "Ot", "Oth", "Os", "Osw", "Peht", "Pleg", "Rad", "Raed", "Sig", "Sige", "Si", "Sihr", "Tat", "Tath", "Tost", "Ut", "Uht", "Ul", "Ulf", "Wal", "Walth", "Wer", "Wit", "Wiht", "Wil", "Wulf" };

        String[] syllable_2 = new String[] { "gar", "heah", "here", "bald", "war", "weard", "wulf", "dred", "red", "stan", "wold", "tric", "ric", "wald", "mon", "wal", "walla", "wealh", "frith", "gyth", "rum", "bert", "berht", "gar", "win", "wine", "wiu", "for", "mund", "thoef", "eof", "had", "erth", "ferth", "thin", "er", "ther", "tar", "thar", "wig", "wicg", "mer", "floed", "ith", "hild", "run", "drun", "ny" };

        return (syllable_1[(int) Math.round(Math.random()*(60 - 1))] + syllable_2[(int) Math.round(Math.random()*(48 - 1))]);
    }

    private String generatePassword() {
        final int LENGTH = 25;
        StringBuilder password = new StringBuilder();
        String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!№;%:?*()_+=";

        for (int i = 0; i < LENGTH; i++) {
            password.append(symbols.charAt((int) Math.floor(Math.random() * symbols.length())));
        }

        return password.toString();
    }
}
