package com.ecoforma.services;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;

public class Initializer {
    @NotNull
    public JFrame newFrame(String t, Rectangle r, int operation) throws IOException {
        JFrame f = new JFrame(t);
        f.setSize(r.width, r.height); // Установка размеров
        f.setLocation(r.x,  r.y);
        f.setResizable(false);
        f.setIconImage(ImageIO.read(getClass().getResource("/img/logo1.png")));
        f.setDefaultCloseOperation(operation); // Установка операции закрытия окна приложения
        f.getContentPane().setLayout(null);
        return f;
    }

    @NotNull
    public JButton newButton(String t, String i, @NotNull Rectangle r) {
        JButton b = new JButton(t, new ImageIcon(getClass().getResource("/img/" + i + ".png")));
        b.setBounds(r.x, r.y, r.width, r.height);
        b.setFocusPainted(false);
        b.setEnabled(false);
        return b;
    }

    @NotNull
    public JButton newButton(String t, @NotNull Rectangle r) {
        JButton b = new JButton(t);
        b.setBounds(r.x, r.y, r.width, r.height);
        b.setFocusPainted(false);
        b.setEnabled(false);
        return b;
    }

    @NotNull
    public JButton newButtonEnabled(String t, @NotNull Rectangle r) {
        JButton b = new JButton(t);
        b.setBounds(r.x, r.y, r.width, r.height);
        b.setFocusPainted(false);
        return b;
    }

    @NotNull
    public JButton newButtonEnabled(String t, String i, @NotNull Rectangle r) {
        JButton b = new JButton(t, new ImageIcon(getClass().getResource("/img/" + i + ".png")));
        b.setBounds(r.x, r.y, r.width, r.height);
        b.setFocusPainted(false);
        return b;
    }

    @NotNull
    public JRadioButton newRadioButton(String t, String c, @NotNull Rectangle r) {
        JRadioButton rb = new JRadioButton(t);
        rb.setFocusPainted(false);
        rb.setBounds(r.x, r.y, r.width, r.height);
        rb.setActionCommand(c);
        rb.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return rb;
    }

    @NotNull
    public JCheckBox newCheckBox(String t, @NotNull Rectangle r) {
        JCheckBox cb = new JCheckBox(t);
        cb.setBounds(r.x, r.y, r.width, r.height);
        cb.setFocusPainted(false);
        return cb;
    }

    public JCheckBox newCheckBoxDisabled(String t, @NotNull Rectangle r) {
        JCheckBox cb = new JCheckBox(t);
        cb.setBounds(r.x, r.y, r.width, r.height);
        cb.setFocusPainted(false);
        cb.setEnabled(false);
        return cb;
    }

    @NotNull
    public JLabel newLabel(String t, @NotNull Rectangle r) {
        JLabel l = new JLabel(t);
        l.setBounds(r.x, r.y, r.width, r.height);
        return l;
    }

    @NotNull
    public JTextField newTextFieldEnabled(int c, @NotNull Rectangle r) {
        JTextField tf = new JTextField();
        tf.setColumns(c);
        tf.setBounds(r.x, r.y, r.width, r.height);
        tf.setFont(new Font("Default", Font.PLAIN, 14));
        return tf;
    }

    @NotNull
    public JTextField newTextFieldBigFont(int c, @NotNull Rectangle r) {
        JTextField tf = new JTextField();
        tf.setColumns(c);
        tf.setBounds(r.x, r.y, r.width, r.height);
        tf.setFont(new Font("Default", Font.PLAIN, 14));
        return tf;
    }

    @NotNull
    public JTextField newTextFieldDisabled(int c, @NotNull Rectangle r) {
        JTextField tf = new JTextField();
        tf.setColumns(c);
        tf.setBounds(r.x, r.y, r.width, r.height);
        tf.setEnabled(false);
        return tf;
    }

    // Инициализация выпадающего списка
    @NotNull
    public JComboBox newComboBox(String[] m, @NotNull Rectangle r) {
        JComboBox cbbx = new JComboBox(new DefaultComboBoxModel(m));
        cbbx.setBounds(r.x, r.y, r.width, r.height);
        cbbx.setEnabled(false);
        return cbbx;
    }

    @NotNull
    public JPanel newPanelEtched(int x, int y, int width, int height) {
        JPanel p = new JPanel();
        p.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        p.setBounds(x, y, width, height);
        p.setLayout(null);
        return p;
    }

    @NotNull
    public JPanel newPanelBevel(int x, int y, int width, int height) {
        JPanel p = new JPanel();
        p.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        p.setBounds(x, y, width, height);
        p.setLayout(null);
        return p;
    }

    @NotNull
    public JPanel newPanelBevelTable(int x, int y, int width, int height) {
        JPanel p = new JPanel();
        p.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        p.setBounds(x, y, width, height);
        return p;
    }

    @NotNull
    public JPanel newPanelTitled(String t, @NotNull Rectangle r) {
        JPanel p = new JPanel();
        p.setBorder(new TitledBorder(null, t, TitledBorder.LEADING, TitledBorder.TOP, null, null));
        p.setBounds(r.x, r.y, r.width, r.height);
        p.setLayout(null);
        return p;
    }

    @NotNull
    public JToolBar newToolBar(int x, int y, int width, int height) {
        JToolBar tb = new JToolBar();
        tb.setFloatable(false);
        tb.setBounds(x, y, width, height);
        return tb;
    }
}
