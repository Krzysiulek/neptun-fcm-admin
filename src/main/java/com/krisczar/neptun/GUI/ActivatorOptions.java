package com.krisczar.neptun.GUI;

import lombok.Data;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Data
public class ActivatorOptions {
    JFrame frame;
    private JPanel contentPane;
    private JTextField txtWidthValue;
    private JTextField txtZeroValue;
    private JTextField txtAmplitude;
    private JTextField txtMin;
    private JTextField txtMax;
    private JTextField txtFactor;
    private JTextField txtN;
    private JTextField txtK;

    private JLabel lblWidth;
    private JLabel lblZeroValue;
    private JLabel lblAmplitude;
    private JLabel lblMin;
    private JLabel lblMax;
    private JLabel lblFactor;
    private JLabel lblN;
    private JLabel lblK;

    JButton btnApply;

    public static double activatorWidth = 1.0;
    public static double activatorZeroValue = 0.0;
    public static double activatorAmplitude = 1.0;
    public static double activatorMin = Double.parseDouble("-Infinity");
    public static double activatorMax = Double.parseDouble("Infinity");
    public static double activatorFactor = 1.0;
    public static int activatorN = 2;
    public static double activatorK = 1.0;

    public ActivatorOptions(String activator) {
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

        formCreation();
        enableTextFields(activator);
        applyBtnListener();
        frame.repaint();
    }

    @SuppressWarnings("Duplicates")
    private void formCreation() {
        lblWidth = new JLabel("Width:");
        lblWidth.setBounds(31, 31, 114, 16);
        contentPane.add(lblWidth);

        lblZeroValue = new JLabel("Zero Value:");
        lblZeroValue.setBounds(31, 61, 114, 16);
        contentPane.add(lblZeroValue);

        lblAmplitude = new JLabel("Amplitude:");
        lblAmplitude.setBounds(31, 89, 114, 16);
        contentPane.add(lblAmplitude);

        lblMin = new JLabel("Min: ");
        lblMin.setBounds(31, 117, 114, 16);
        contentPane.add(lblMin);

        lblMax = new JLabel("Max: ");
        lblMax.setBounds(31, 145, 114, 16);
        contentPane.add(lblMax);

        lblFactor = new JLabel("Factor:");
        lblFactor.setBounds(31, 173, 114, 16);
        contentPane.add(lblFactor);

        lblN = new JLabel("N: ");
        lblN.setBounds(31, 201, 114, 16);
        contentPane.add(lblN);

        lblK = new JLabel("K:");
        lblK.setBounds(31, 229, 114, 16);
        contentPane.add(lblK);

        txtWidthValue = new JTextField();
        txtWidthValue.setEnabled(false);
        txtWidthValue.setText(String.valueOf(activatorWidth));
        txtWidthValue.setBounds(157, 26, 220, 26);
        contentPane.add(txtWidthValue);
        txtWidthValue.setColumns(10);

        txtZeroValue = new JTextField();
        txtZeroValue.setEnabled(false);
        txtZeroValue.setText(String.valueOf(activatorZeroValue));
        txtZeroValue.setBounds(157, 56, 220, 26);
        contentPane.add(txtZeroValue);
        txtZeroValue.setColumns(10);

        txtAmplitude = new JTextField();
        txtAmplitude.setEnabled(false);
        txtAmplitude.setText(String.valueOf(activatorAmplitude));
        txtAmplitude.setBounds(157, 84, 220, 26);
        contentPane.add(txtAmplitude);
        txtAmplitude.setColumns(10);

        txtMin = new JTextField();
        txtMin.setEnabled(false);
        txtMin.setText(String.valueOf(activatorMin));
        txtMin.setBounds(157, 112, 220, 26);
        contentPane.add(txtMin);
        txtMin.setColumns(10);

        txtMax = new JTextField();
        txtMax.setEnabled(false);
        txtMax.setText(String.valueOf(activatorMax));
        txtMax.setBounds(157, 140, 220, 26);
        contentPane.add(txtMax);
        txtMax.setColumns(10);

        txtFactor = new JTextField();
        txtFactor.setEnabled(false);
        txtFactor.setText(String.valueOf(activatorFactor));
        txtFactor.setBounds(157, 168, 220, 26);
        contentPane.add(txtFactor);
        txtFactor.setColumns(10);

        txtN = new JTextField();
        txtN.setText(String.valueOf(activatorN));
        txtN.setEnabled(false);
        txtN.setBounds(157, 196, 220, 26);
        contentPane.add(txtN);
        txtN.setColumns(10);

        txtK = new JTextField();
        txtK.setEnabled(false);
        txtK.setText(String.valueOf(activatorK));
        txtK.setBounds(157, 224, 220, 26);
        contentPane.add(txtK);
        txtK.setColumns(10);

        btnApply = new JButton("Apply");
        btnApply.setBounds(260, 316, 117, 29);
        contentPane.add(btnApply);
    }

    private void enableTextFields(String activator){

        switch (activator){
            case "Cauchy Activator":
                break;

            case "Gaussian Activator":
                txtWidthValue.setEnabled(true);
                break;

            case "Hyperbolic Tangent Activator":
                break;

            case "Interval Activator":
                txtZeroValue.setEnabled(true);
                txtAmplitude.setEnabled(true);
                break;

            case "Linear Activator":
                txtMin.setEnabled(true);
                txtMax.setEnabled(true);
                txtFactor.setEnabled(true);
                break;

            case "Nary Activator":
                txtN.setEnabled(true);
                break;

            case "Sigmoid Activator":
                txtK.setEnabled(true);
                break;

            case "Signum Activator":
                txtZeroValue.setEnabled(true);
                break;
        }

    }

    private void applyBtnListener(){
        btnApply.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    activatorWidth = Double.parseDouble(txtWidthValue.getText());
                    activatorZeroValue = Double.parseDouble(txtZeroValue.getText());
                    activatorAmplitude = Double.parseDouble(txtAmplitude.getText());
                    activatorMin = Double.parseDouble(txtMin.getText());
                    activatorMax = Double.parseDouble(txtMax.getText());
                    activatorFactor = Double.parseDouble(txtFactor.getText());
                    activatorN = Integer.parseInt(txtN.getText());
                    activatorK = Double.parseDouble(txtK.getText());
                    frame.dispose();
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Incorrect value.");
                    ex.printStackTrace();
                }
            }
        });
    }
}
