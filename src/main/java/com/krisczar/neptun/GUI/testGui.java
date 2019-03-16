package com.krisczar.neptun.GUI;

import com.krisczar.neptun.ModelResolver;
import com.krisczar.neptun.Resolver;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class testGui {
    static String resolvedText = "";

    public static void main ( String[] args )
    {
        ModelResolver.loadAllModels();


        JPanel middlePanel = new JPanel ();
        JButton button1 = new JButton("Get All Users");
        JButton button2 = new JButton("Resolve User");
        JButton button3 = new JButton("Copy");
        final JTextField textField = new JTextField("User_ID");
        textField.setPreferredSize(new Dimension(100, 30));


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
        middlePanel.add(button1);
        middlePanel.add(button2);
        middlePanel.add(button3);
        middlePanel.add(textField);



        // My code
        JFrame frame = new JFrame ();
        frame.add ( middlePanel );
        frame.pack ();
        frame.setLocationRelativeTo ( null );
        frame.setVisible ( true );

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                display.setText(Resolver.getUsersIDs());
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int userID = Integer.parseInt(textField.getText());
//                    System.out.println(userID);
                    Resolver resolver = new Resolver(userID);

                    resolver.resolveSection_1();
                    resolver.resolveSection_2();
                    resolver.resolveSection_3();
                    resolver.resolveSection_2_2();

                    resolvedText = resolver.getAllQAs();
                    display.setText(resolvedText);

                    resolver.printWWT();

                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Incorrect userID: " + ex.getMessage());
                    ex.printStackTrace();
                }



            }
        });

        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringSelection stringSelection = new StringSelection(resolvedText);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });
    }
}
