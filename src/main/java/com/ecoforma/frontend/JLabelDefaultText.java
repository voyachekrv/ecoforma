package com.ecoforma.frontend;

import javax.swing.*;
import java.awt.*;

public class JLabelDefaultText extends JLabel {
    private String defaultText;

    public JLabelDefaultText(String defaultText, Rectangle r) {
        this.defaultText = defaultText;
        this.setText(defaultText);
        this.setBounds(r);
        this.setFont(new Font("Dialog", Font.BOLD, 13));
    }

    public void appendText(String text) {
        this.setText(defaultText + text);
        this.setToolTipText(this.getText());
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void resetText() {
        this.setText(defaultText);
        this.setToolTipText(null);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}

