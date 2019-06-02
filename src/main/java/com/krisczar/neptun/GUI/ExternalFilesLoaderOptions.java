package com.krisczar.neptun.GUI;

import com.krisczar.neptun.TOs.FCMPreferences;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExternalFilesLoaderOptions extends JFrame {
    private JPanel contentPane;

    JButton btnApply = new JButton("Apply");
    private JCheckBox chckbxLoadExternalFiles = new JCheckBox("Load external files to create map");
    private JCheckBox chckbxLoadConnectionsWeight = new JCheckBox("Load connections weight from file");
    private JCheckBox chckbxLoadConceptsWeight = new JCheckBox("Load concepts weight from file");

    public ExternalFilesLoaderOptions() {
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        btnApply.setBounds(260, 316, 117, 29);
        contentPane.add(btnApply);

        chckbxLoadExternalFiles.setBounds(31, 21, 346, 23);
        contentPane.add(chckbxLoadExternalFiles);

        chckbxLoadConnectionsWeight.setBounds(31, 56, 346, 23);
        contentPane.add(chckbxLoadConnectionsWeight);

        chckbxLoadConceptsWeight.setBounds(31, 91, 346, 23);
        contentPane.add(chckbxLoadConceptsWeight);

        updateCheckBoxes();
        addListeners();
    }

    private void updateCheckBoxes(){
        chckbxLoadExternalFiles.setSelected(FCMPreferences.isExternalFilesLoading);
        chckbxLoadConceptsWeight.setSelected(FCMPreferences.isLoadingConceptWeightsFromFile);
        chckbxLoadConnectionsWeight.setSelected(FCMPreferences.isLoadingConnectionsWeightsFromFile);
    }

    private void addListeners(){
        btnApply.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                FCMPreferences.isExternalFilesLoading = chckbxLoadExternalFiles.isSelected();
                FCMPreferences.isLoadingConceptWeightsFromFile = chckbxLoadConceptsWeight.isSelected();
                FCMPreferences.isLoadingConnectionsWeightsFromFile = chckbxLoadConnectionsWeight.isSelected();

                dispose();
            }
        });
    }
}
