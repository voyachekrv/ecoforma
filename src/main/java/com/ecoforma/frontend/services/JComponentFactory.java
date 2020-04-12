package com.ecoforma.frontend.services;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

public final class JComponentFactory {
    @NotNull
    public static JFrame newFrame(String t, @NotNull Rectangle r, WindowListener l) {
        JFrame f = new JFrame(t);
        f.setSize(r.width, r.height); // Установка размеров
        f.setLocation(r.x,  r.y);
        f.setResizable(false);

        f.addWindowListener(l);

        try {
            f.setIconImage(ImageIO.read(JComponentFactory.class.getResource("/img/logo1.png")));
        } catch (IOException e) {
            throw new RuntimeException();
        }

        f.getContentPane().setLayout(null);
        return f;
    }

    @NotNull
    public static JPanel newPanelDefault(int x, int y, int width, int height) {
        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBounds(x, y, width, height);
        return p;
    }

    @NotNull
    public static JPanel newPanelEtched(int x, int y, int width, int height) {
        JPanel p = new JPanel();
        p.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        p.setBounds(x, y, width, height);
        p.setLayout(null);
        return p;
    }

    @NotNull
    public static JPanel newPanelEtchedRaised(int x, int y, int width, int height) {
        JPanel p = new JPanel();
        p.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        p.setBounds(x, y, width, height);
        p.setLayout(null);
        return p;
    }

    @NotNull
    public static JPanel newPanelBevel(int x, int y, int width, int height) {
        JPanel p = new JPanel();
        p.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        p.setBounds(x, y, width, height);
        p.setLayout(null);
        return p;
    }

    @NotNull
    public static JPanel newPanelBevelTable(int x, int y, int width, int height) {
        JPanel p = new JPanel();
        p.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        p.setBounds(x, y, width, height);
        return p;
    }

    @NotNull
    public static JPanel newPanelTitled(String t, @NotNull Rectangle r) {
        JPanel p = new JPanel();
        p.setBorder(new TitledBorder(null, t, TitledBorder.LEADING, TitledBorder.TOP, null, null));
        p.setBounds(r.x, r.y, r.width, r.height);
        p.setLayout(null);
        return p;
    }

    @NotNull
    public static JScrollPane newTableScroll(JTable table, int width, int height) {
        JScrollPane tableScroll = new JScrollPane();
        tableScroll.setViewportView(table);
        tableScroll.setPreferredSize(new Dimension(width, height));
        tableScroll.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return tableScroll;
    }

    @NotNull
    public static JScrollPane newTableDisabledScroll(JTable table, int width, int height) {
        JScrollPane tableScroll = new JScrollPane();
        tableScroll.setViewportView(table);
        tableScroll.setPreferredSize(new Dimension(width, height));
        try {
            tableScroll.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                    new ImageIcon(ImageIO.read(JComponentFactory.class.getResource("/img/not-allowed.png"))).getImage(),
                    new Point(0,0),"not-allowed cursor"));
        } catch (IOException e) {
            throw new RuntimeException("Файл курсора найден");
        }

        return tableScroll;
    }

    @NotNull
    public static JButton newButton(String t, String i, @NotNull Rectangle r) {
        JButton b = new JButton(t, new ImageIcon(JComponentFactory.class.getResource("/img/" + i + ".png")));
        b.setBounds(r.x, r.y, r.width, r.height);
        b.setFocusPainted(false);
        b.setEnabled(false);
        return b;
    }

    @NotNull
    public static JButton newButton(String t, @NotNull Rectangle r) {
        JButton b = new JButton(t);
        b.setBounds(r.x, r.y, r.width, r.height);
        b.setFocusPainted(false);
        b.setEnabled(false);
        return b;
    }

    @NotNull
    public static JButton newButtonEnabled(String t, @NotNull Rectangle r) {
        JButton b = new JButton(t);
        b.setBounds(r.x, r.y, r.width, r.height);
        b.setFocusPainted(false);
        return b;
    }

    @NotNull
    public static JButton newButtonEnabled(String t, String i, @NotNull Rectangle r) {
        JButton b = new JButton(t, new ImageIcon(JComponentFactory.class.getResource("/img/" + i + ".png")));
        b.setBounds(r.x, r.y, r.width, r.height);
        b.setFocusPainted(false);
        return b;
    }

    @NotNull
    public static JButton newButtonHelp() {
        JButton b = new JButton("Справка", new ImageIcon(JComponentFactory.class.getResource("/img/icon-help.png")));

        b.addActionListener(actionEvent -> {
            try {
                File htmlFile = new File(JComponentFactory.class.getResource("/htm/help.html").getFile());
                Desktop.getDesktop().browse(htmlFile.toURI());
            }
            catch (IOException ex) {
                throw new RuntimeException("Файл справки не найден");
            }
        });

        return b;
    }

    @NotNull
    public static JRadioButton newRadioButton(String t, String c, @NotNull Rectangle r) {
        JRadioButton rb = new JRadioButton(t);
        rb.setFocusPainted(false);
        rb.setBounds(r.x, r.y, r.width, r.height);
        rb.setActionCommand(c);
        rb.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return rb;
    }

    @NotNull
    public static JCheckBox newCheckBox(String t, @NotNull Rectangle r) {
        JCheckBox cb = new JCheckBox(t);
        cb.setBounds(r.x, r.y, r.width, r.height);
        cb.setFocusPainted(false);
        return cb;
    }

    @NotNull
    public static JCheckBox newCheckBoxDisabled(String t, @NotNull Rectangle r) {
        JCheckBox cb = new JCheckBox(t);
        cb.setBounds(r.x, r.y, r.width, r.height);
        cb.setFocusPainted(false);
        cb.setEnabled(false);
        return cb;
    }

    @NotNull
    public static JLabel newLabel(String t, @NotNull Rectangle r) {
        JLabel l = new JLabel(t);
        l.setBounds(r.x, r.y, r.width, r.height);
        return l;
    }

    @NotNull
    public static JLabel newLabelBigFont(String t, @NotNull Rectangle r) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Dialog", Font.BOLD, 14));
        l.setBounds(r.x, r.y, r.width, r.height);
        return l;
    }

    @NotNull
    public static JLabel newLabelColored(String t, @NotNull Rectangle r) {
        JLabel l = new JLabel(t);
        l.setForeground(Color.RED);
        l.setFont(new Font("Dialog", Font.BOLD, 15));
        l.setBounds(r.x, r.y, r.width, r.height);
        return l;
    }

    @NotNull
    public static JLabel newLabelFinalPrice(String t, @NotNull Rectangle r) {
        JLabel l = new JLabel(t);
        l.setForeground(Color.RED);
        l.setFont(new Font("Dialog", Font.BOLD, 20));
        l.setBounds(r.x, r.y, r.width, r.height);
        return l;
    }

    @NotNull
    public static JTextField newTextFieldEnabled(int c, @NotNull Rectangle r) {
        JTextField tf = new JTextField();
        tf.setColumns(c);
        tf.setBounds(r.x, r.y, r.width, r.height);
        return tf;
    }

    @NotNull
    public static JTextField newTextFieldBigFont(int c, @NotNull Rectangle r) {
        JTextField tf = new JTextField();
        tf.setColumns(c);
        tf.setBounds(r.x, r.y, r.width, r.height);
        tf.setFont(new Font("Default", Font.PLAIN, 14));
        return tf;
    }

    @NotNull
    public static JTextField newTextFieldBigFontDisable(int c, @NotNull Rectangle r) {
        JTextField tf = new JTextField();
        tf.setColumns(c);
        tf.setBounds(r.x, r.y, r.width, r.height);
        tf.setFont(new Font("Default", Font.PLAIN, 14));
        tf.setEnabled(false);
        return tf;
    }

    @NotNull
    public static JTextField newTextFieldDisabled(int c, @NotNull Rectangle r) {
        JTextField tf = new JTextField();
        tf.setColumns(c);
        tf.setBounds(r.x, r.y, r.width, r.height);
        tf.setEnabled(false);
        return tf;
    }

    // Инициализация выпадающего списка
    @NotNull
    public static JComboBox newComboBox(String[] m, @NotNull Rectangle r) {
        JComboBox cbbx = new JComboBox(new DefaultComboBoxModel(m));
        cbbx.setBounds(r.x, r.y, r.width, r.height);
        cbbx.setEnabled(false);
        return cbbx;
    }

    @NotNull
    public static JToolBar newToolBar(int x, int y, int width, int height) {
        JToolBar tb = new JToolBar();
        tb.setFloatable(false);
        tb.setBounds(x, y, width, height);
        return tb;
    }

    @NotNull
    public static JTable newTable(DefaultTableModel tableModel) {
        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null);
        table.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return table;
    }

    @NotNull
    public static JTable newTableDisabled(DefaultTableModel tableModel) {
        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null);
        table.setEnabled(false);
        return table;
    }

    @NotNull
    public static JSpinner newSpinnerNumericDisabled(SpinnerNumberModel model, @NotNull Rectangle r) {
        JSpinner spinner = new JSpinner();
        spinner.setBounds(r.x, r.y, r.width, r.height);
        spinner.setModel(model);
        spinner.setEnabled(false);
        return spinner;
    }

    @NotNull
    public static JSpinner newSpinnerNumericEnabled(SpinnerNumberModel model, @NotNull Rectangle r) {
        JSpinner spinner = new JSpinner();
        spinner.setBounds(r.x, r.y, r.width, r.height);
        spinner.setModel(model);
        return spinner;
    }

    @NotNull
    public static JSpinner newSpinnerList(SpinnerListModel model, @NotNull Rectangle r) {
        JSpinner spinner = new JSpinner();
        spinner.setBounds(r.x, r.y, r.width, r.height);
        spinner.setModel(model);
        return spinner;
    }

    @NotNull
    public static JTextArea newTextAreaEnabled(int x, int y, int width, int height, int rows, int cols) {
        JTextArea textArea = new JTextArea();
        textArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        textArea.setBounds(x, y, width, height);
        textArea.setRows(rows);
        textArea.setColumns(cols);
        return textArea;
    }

    @NotNull
    public static JTextArea newTextAreaDisabled(int x, int y, int width, int height, int rows, int cols) {
        JTextArea textArea = new JTextArea();
        textArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        textArea.setBounds(x, y, width, height);
        textArea.setEnabled(false);
        textArea.setRows(rows);
        textArea.setColumns(cols);
        return textArea;
    }

    @NotNull
    public static JTextArea newTextAreaBigFont(int x, int y, int width, int height, Action action) {
        JTextArea textArea = new JTextArea();
        textArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        textArea.setBounds(x, y, width, height);
        textArea.setFont(new Font("Default", Font.PLAIN, 14));
        textArea.setColumns(100);
        textArea.setRows(30);

        String ACTION_KEY = "saveAction";
        KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK);
        InputMap inputMap = textArea.getInputMap(JComponent.WHEN_FOCUSED);
        inputMap.put(ctrlS, ACTION_KEY);
        ActionMap actionMap = textArea.getActionMap();
        actionMap.put(ACTION_KEY, action);
        textArea.setActionMap(actionMap);
        return textArea;
    }

    @NotNull
    public static JPanel newTabbedPaneElement() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        return panel;
    }

    @NotNull
    public static JTabbedPane newTabbedPane(int x, int y, int width, int height) {
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(x, y, width, height);
        return tabbedPane;
    }
}
