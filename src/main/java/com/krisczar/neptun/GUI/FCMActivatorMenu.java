package com.krisczar.neptun.GUI;

import javax.swing.*;

public class FCMActivatorMenu extends JFrame {
    // create an empty combo box with items of type String
    JPanel middlePanel = new JPanel ();

    JComboBox<String> comboLanguage = new JComboBox<String>();
    JButton startFCMBtn = new JButton("Start FCM");

    final JTextArea display = new JTextArea ( 35, 58 );

    // My code
    JFrame frame = new JFrame ();

    public FCMActivatorMenu() {
        comboLanguage.addItem("Cauchy Activator");
        comboLanguage.addItem("Gaussian Activator");
        comboLanguage.addItem("Hyperbolic Tangent Activator");
        comboLanguage.addItem("Interval Activator");
        comboLanguage.addItem("Linear Activator");
        comboLanguage.addItem("Nary Activator");
        comboLanguage.addItem("Sigmoid Activator");
        comboLanguage.addItem("Signum Activator");

//        frame.setSize(600, 600);

        display.setText("To jest zupelnie test\n\n" +
                "Taki test zeby sobie potestowac");

        display.setEditable ( false ); // set textArea non-editable
        JScrollPane scroll = new JScrollPane ( display );
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

        middlePanel.add(scroll);
        middlePanel.add(comboLanguage);
        middlePanel.add(startFCMBtn);
        frame.add(middlePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack ();
        frame.setLocationRelativeTo ( null );
        frame.setVisible ( true );
    }
}
