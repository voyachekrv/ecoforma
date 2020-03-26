package com.ecoforma.frontend.forms;

import com.ecoforma.frontend.CompanyFrame;

import java.io.IOException;

public class SaleForm {
    CompanyFrame frame;

    public SaleForm(String department) throws IOException {
        frame = new CompanyFrame(department);
    }
}