package com.krisczar.neptun.GUI;

import com.krisczar.neptun.FCM.FCMCreator;
import com.krisczar.neptun.RulesResolver.ResolverNew;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FCMActivatorMenu extends JFrame {
    // create an empty combo box with items of type String
    JPanel middlePanel = new JPanel ();
    private JMenuBar menuBar = new JMenuBar(); // Window menu bar
    JMenu preferences = new JMenu("Preferences");

    JComboBox<String> comboLanguage = new JComboBox<String>();
    JButton startFCMBtn = new JButton("Start FCM");
    JLabel deltaLabel = new JLabel("Delta: ", JLabel.CENTER);
    JTextField deltaTextField = new JTextField("0.1");

    JLabel maxEpochsLabel = new JLabel("Max Epochs: ", JLabel.CENTER);
    JTextField maxEpochsTextField = new JTextField("10");

    final JTextArea display = new JTextArea ( 35, 58 );

    JFrame frame = new JFrame ();

    public FCMActivatorMenu() {
        addActivators();

        frame.setJMenuBar(menuBar);
        menuBar.add(preferences);

        addMenuActivators();
        addMenuFileLoaders();

        display.setText("It's Fuzzy Cognitive Map display Area.\n" +
                "You can modify all the parameters to achieve best results.\n" +
                "Good luck!");

        display.setEditable ( false ); // set textArea non-editable
        JScrollPane scroll = new JScrollPane ( display );
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

        deltaTextField.setPreferredSize(new Dimension(100, 30));
        maxEpochsTextField.setPreferredSize(new Dimension(100, 30));

        middlePanel.add(scroll);
        middlePanel.add(comboLanguage);
        middlePanel.add(deltaLabel);
        middlePanel.add(deltaTextField);
        middlePanel.add(maxEpochsLabel);
        middlePanel.add(maxEpochsTextField);
        middlePanel.add(startFCMBtn);

        frame.add(middlePanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.pack ();
        frame.setLocationRelativeTo ( null );
        frame.setVisible ( true );

        addListeners();
    }

    private void addActivators(){
        comboLanguage.addItem("Cauchy Activator");
        comboLanguage.addItem("Gaussian Activator");
        comboLanguage.addItem("Hyperbolic Tangent Activator");
        comboLanguage.addItem("Interval Activator");
        comboLanguage.addItem("Linear Activator");
        comboLanguage.addItem("Nary Activator");
        comboLanguage.addItem("Sigmoid Activator");
        comboLanguage.addItem("Signum Activator");
    }

    private void addListeners(){
        closeAppliactionListener();
        runFCMListener();
    }

    private void closeAppliactionListener(){
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

    private void runFCMListener(){
        startFCMBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runFCM();
            }
        });
    }

    private void runFCM(){
        String FCMActivator = comboLanguage.getSelectedItem().toString();
        long userID = ResolverNew.getUserId();

        FCMCreator fcmCreator = new FCMCreator(userID, FCMActivator);

        double maxDelta = 0.1;
        int maxEpochs = 1;
        try{
            maxDelta = Double.parseDouble(deltaTextField.getText());
            maxEpochs = Integer.parseInt(maxEpochsTextField.getText());

            fcmCreator.run(maxDelta, maxEpochs);

            display.setText(fcmCreator.toString());
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Incorrect maxDelta or maxEpochs format.");
            e.printStackTrace();
        }
    }

    private void addMenuActivators(){
        JMenuItem openActivatorOptions = new JMenuItem("Activators");
        preferences.add(openActivatorOptions);

        openActivatorOptions.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println(comboLanguage.getSelectedItem().toString());
                new ActivatorOptions(comboLanguage.getSelectedItem().toString());
            }
        });
    }

    private void addMenuFileLoaders(){
        JMenuItem openExternalFilesOptions = new JMenuItem("External files");
        preferences.add(openExternalFilesOptions);

        openExternalFilesOptions.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new ExternalFilesLoaderOptions();
            }
        });
    }
}
