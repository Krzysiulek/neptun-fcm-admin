package com.krisczar.neptun.GUI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class FCMActivatorMenu extends JFrame {
    // create an empty combo box with items of type String
    JPanel middlePanel = new JPanel ();

    JComboBox<String> comboLanguage = new JComboBox<String>();
    JButton startFCMBtn = new JButton("Start FCM");
    JLabel deltaStatus = new JLabel("Delta: ", JLabel.CENTER);
    JTextField deltaSlider = new JTextField("0.1");

    JLabel maxEpochsStatus = new JLabel("Max Epochs: ", JLabel.CENTER);
    JTextField maxEpochsSlider = new JTextField("20000");

    final JTextArea display = new JTextArea ( 35, 58 );

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

        deltaSlider.setPreferredSize(new Dimension(100, 30));
        maxEpochsSlider.setPreferredSize(new Dimension(100, 30));

        middlePanel.add(scroll);
        middlePanel.add(comboLanguage);
        middlePanel.add(deltaStatus);
        middlePanel.add(deltaSlider);
        middlePanel.add(maxEpochsStatus);
        middlePanel.add(maxEpochsSlider);
        middlePanel.add(startFCMBtn);
        frame.add(middlePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack ();
        frame.setLocationRelativeTo ( null );
        frame.setVisible ( true );
    }
}
