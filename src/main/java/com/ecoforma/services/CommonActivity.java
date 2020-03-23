package com.ecoforma.services;

import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;

import static com.ecoforma.App.signInForm;

public class CommonActivity {

    public void signOut(@NotNull JFrame frame) {
        frame.setVisible(false);
        signInForm.frame.setVisible(true);
    }

}
