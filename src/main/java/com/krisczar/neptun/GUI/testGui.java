package com.krisczar.neptun.GUI;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class testGui {
    public static void main ( String[] args )
    {
        JPanel middlePanel = new JPanel ();
        middlePanel.setBorder ( new TitledBorder( new EtchedBorder(), "Display Area" ) );

        // create the middle panel components

        JTextArea display = new JTextArea ( 16, 58 );
        display.setText("duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa duuuuupaaaa ");
        display.setEditable ( false ); // set textArea non-editable
        JScrollPane scroll = new JScrollPane ( display );
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

        //Add Textarea in to middle panel
        middlePanel.add ( scroll );

        // My code
        JFrame frame = new JFrame ();
        frame.add ( middlePanel );
        frame.pack ();
        frame.setLocationRelativeTo ( null );
        frame.setVisible ( true );
    }
}
