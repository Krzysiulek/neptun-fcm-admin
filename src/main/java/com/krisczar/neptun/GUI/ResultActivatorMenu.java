package com.krisczar.neptun.GUI;

import com.krisczar.neptun.SupportServices.Connections;
import com.krisczar.neptun.RulesResolver.*;
import com.krisczar.neptun.ModelResolver;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultActivatorMenu {
    static String resolvedText = "";

    public static void main ( String[] args )
    {
        ModelResolver.loadAllModels();


        JPanel middlePanel = new JPanel ();
        JButton getAllUsersBtn = new JButton("Get All Users");
        JButton resolveUserBtn = new JButton("Resolve User");
        JButton copyBtn = new JButton("Copy");
        JButton deleteBtn = new JButton("Delete user");
        final JTextField userIdField = new JTextField("User_ID");
        userIdField.setPreferredSize(new Dimension(100, 30));


        middlePanel.setBorder ( new TitledBorder( new EtchedBorder(), "Display Area" ) );

        // create the middle panel components

        final JTextArea display = new JTextArea ( 35, 58 );
        display.setText("To get informations about registred users press 'Get All Users' button\n\n" +
                "To resolve user answers type user ID into 'User_ID' field and press 'Resolve User' button");

        display.setEditable ( false ); // set textArea non-editable
        JScrollPane scroll = new JScrollPane ( display );
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

        //Add Textarea in to middle panel
        middlePanel.add ( scroll );
        middlePanel.add(getAllUsersBtn);
        middlePanel.add(resolveUserBtn);
        middlePanel.add(copyBtn);
        middlePanel.add(userIdField);
        middlePanel.add(deleteBtn);



        // My code
        JFrame frame = new JFrame ();
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.add ( middlePanel );
        frame.pack ();
        frame.setLocationRelativeTo ( null );
        frame.setVisible ( true );

        getAllUsersBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                display.setText(Connections.getUsersIDs());
            }
        });

        resolveUserBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                try {
                int userID = Integer.parseInt(userIdField.getText());
//                    System.out.println(userID);
                ResolverNew resolver = new ResolverNew(userID);

                resolver.resolveSection1();
                resolver.resolveSection2();
                resolver.resolveSection3();

                resolver.saveToFile();

                resolvedText = resolver.toString();
                display.setText(resolvedText);

 
              //if (resolver.isFCMneeded())
                if (true) { 
                    if (JOptionPane.showConfirmDialog(frame,
                            "FCM is needed. Do you want to run FCM?", "Run FCM?",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

                        FCMActivatorMenu fcmActivatorMenu = new FCMActivatorMenu();
                    }
                }
            }
        });

        copyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringSelection stringSelection = new StringSelection(display.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    int userId = Integer.parseInt(userIdField.getText());

                    if (JOptionPane.showConfirmDialog(frame,
                            "Are you sure you want to delete user " + userId + "?", "Close Window?",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

                        Connections.deleteUser(userId);
                        JOptionPane.showMessageDialog(null, "User " + userId + " deleted");
                        display.setText(Connections.getUsersIDs());
                    }

                }
                catch (Exception exc){
                    JOptionPane.showMessageDialog(null, "User don't exists");
                }
            }
        });

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to close this application?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
    }
}
