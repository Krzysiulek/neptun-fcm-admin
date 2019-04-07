package com.krisczar.neptun.GUI;

import lombok.Data;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@Data
public class ActivatorOptions {
    JFrame frame;
    private JPanel contentPane;
    private JTextField txtWidthvalue;
    private JTextField txtZerovalue;
    private JTextField txtAmplitude;
    private JTextField txtMin;
    private JTextField txtMax;
    private JTextField txtFactor;
    private JTextField txtN;
    private JTextField txtK;

    public ActivatorOptions() {
        frame = new JFrame("Activator Preferences");

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(50,50);
        frame.setVisible ( true );
        frame.setTitle("FCM Activator Options");

        frame.setBounds(100, 100, 450, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame.setContentPane(contentPane);
        contentPane.setLayout(null);

        FomrExaml();
        frame.repaint();
    }

    public void FomrExaml() {
        JLabel lblWidth = new JLabel("Width:");
        lblWidth.setBounds(31, 31, 114, 16);
        contentPane.add(lblWidth);

        JLabel lblZeroValue = new JLabel("Zero Value:");
        lblZeroValue.setBounds(31, 61, 114, 16);
        contentPane.add(lblZeroValue);

        JLabel lblAmplitude = new JLabel("Amplitude:");
        lblAmplitude.setBounds(31, 89, 114, 16);
        contentPane.add(lblAmplitude);

        JLabel lblMin = new JLabel("Min: ");
        lblMin.setBounds(31, 117, 114, 16);
        contentPane.add(lblMin);

        JLabel lblMax = new JLabel("Max: ");
        lblMax.setBounds(31, 145, 114, 16);
        contentPane.add(lblMax);

        JLabel lblFactor = new JLabel("Factor:");
        lblFactor.setBounds(31, 173, 114, 16);
        contentPane.add(lblFactor);

        JLabel lblN = new JLabel("N: ");
        lblN.setBounds(31, 201, 114, 16);
        contentPane.add(lblN);

        JLabel lblK = new JLabel("K:");
        lblK.setBounds(31, 229, 114, 16);
        contentPane.add(lblK);

        txtWidthvalue = new JTextField();
        txtWidthvalue.setText("widthValue");
        txtWidthvalue.setBounds(157, 26, 220, 26);
        contentPane.add(txtWidthvalue);
        txtWidthvalue.setColumns(10);

        txtZerovalue = new JTextField();
        txtZerovalue.setText("zeroValue");
        txtZerovalue.setBounds(157, 56, 220, 26);
        contentPane.add(txtZerovalue);
        txtZerovalue.setColumns(10);

        txtAmplitude = new JTextField();
        txtAmplitude.setText("amplitude");
        txtAmplitude.setBounds(157, 84, 220, 26);
        contentPane.add(txtAmplitude);
        txtAmplitude.setColumns(10);

        txtMin = new JTextField();
        txtMin.setText("min");
        txtMin.setBounds(157, 112, 220, 26);
        contentPane.add(txtMin);
        txtMin.setColumns(10);

        txtMax = new JTextField();
        txtMax.setText("max");
        txtMax.setBounds(157, 140, 220, 26);
        contentPane.add(txtMax);
        txtMax.setColumns(10);

        txtFactor = new JTextField();
        txtFactor.setText("factor");
        txtFactor.setBounds(157, 168, 220, 26);
        contentPane.add(txtFactor);
        txtFactor.setColumns(10);

        txtN = new JTextField();
        txtN.setText("n");
        txtN.setBounds(157, 196, 220, 26);
        contentPane.add(txtN);
        txtN.setColumns(10);

        txtK = new JTextField();
        txtK.setEnabled(false);
        txtK.setText("k");
        txtK.setBounds(157, 224, 220, 26);
        contentPane.add(txtK);
        txtK.setColumns(10);

        JButton btnApply = new JButton("Apply");
        btnApply.setBounds(260, 316, 117, 29);
        contentPane.add(btnApply);
    }

}
