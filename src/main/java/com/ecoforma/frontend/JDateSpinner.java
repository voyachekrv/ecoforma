package com.ecoforma.frontend;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JDateSpinner extends JSpinner {
    private final SimpleDateFormat spinnerFormat = new SimpleDateFormat("dd.MM.yyyy");
    private final SimpleDateFormat databaseFormat = new SimpleDateFormat("yyyy-MM-dd");

    public JDateSpinner(int x, int y, int width, int height, boolean isEnable) {
        this.setModel(new SpinnerDateModel());
        this.setEditor(new DateEditor(this, spinnerFormat.toPattern()));
        this.setBounds(x, y, width, height);
        this.setEnabled(isEnable);
    }

    public void setDateToSpinner(String date) {
        try {
            this.setValue(spinnerFormat.parse(spinnerFormat.format(databaseFormat.parse(date))));
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getDatabaseFormatDate() {
        return databaseFormat.format(this.getValue());
    }

    public void setCurrentDate() {
        try {
            this.setValue(spinnerFormat.parse(spinnerFormat.format(new Date())));
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
