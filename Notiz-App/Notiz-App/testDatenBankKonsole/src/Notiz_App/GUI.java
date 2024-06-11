/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Notiz_App;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author benjamin.heinold
 */
public class GUI {
    static JFrame GUI = new JFrame("Notizen");
    static JPanel panelContainer = new JPanel();
    static JPanel eingabefeld = new JPanel();

    public static String connectionURL = "jdbc:mysql://localhost:3306/notizen";
    public static String user = "root";
    public static String password = "";

    public static void main(String[] args) {

    }

    public static void init() {

        GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUI.setLayout(new BorderLayout());
        
        eingabefeld.setLayout(new BoxLayout(eingabefeld, BoxLayout.Y_AXIS));
        
        JButton btnaddNotiz = new JButton("Neue Notiz hinzufügen");
        //btnaddNotiz.setHorizontalAlignment(JButton.CENTER);
        JLabel lUeberschrift = new JLabel("Überschrift:");
        JTextField tfUeberschrift = new JTextField("Überschrift",1);
        JLabel lBeschreibung = new JLabel("Beschreibung:");
        JTextArea taBeschreibung = new JTextArea(3,1);
        
        eingabefeld.add(btnaddNotiz);
        btnaddNotiz.setHorizontalAlignment(JButton.LEFT);
        eingabefeld.add(lUeberschrift);
        eingabefeld.add(tfUeberschrift);
        eingabefeld.add(lBeschreibung);
        eingabefeld.add(taBeschreibung);
        JScrollPane scrollpane = new JScrollPane(taBeschreibung);
        eingabefeld.add(scrollpane);

        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));
        

        btnaddNotiz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel newPanel = new JPanel();
                newPanel.setName(String.valueOf(Notiz_App.addNotiz("Test", "hilfe")));
                newPanel.add(new JLabel(newPanel.getName()));

                JButton editButton = new JButton("Bearbeiten");
                JButton deleteButton = new JButton("Löschen");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        panelContainer.remove(newPanel);
                        Notiz_App.deleteNotiz(Integer.valueOf(newPanel.getName()));
                        GUI.pack();
                    }
                });
                newPanel.add(deleteButton);
                newPanel.add(editButton);
                panelContainer.add(newPanel);
                GUI.pack();
            }
        });

        GUI.add(eingabefeld,BorderLayout.NORTH);
        GUI.add(new JScrollPane(panelContainer), BorderLayout.CENTER);
        GUI.pack();
        GUI.setVisible(true);

        getNotizen();
    }

    public static void getNotizen() {
        try {
            ArrayList<String> notizID = new ArrayList<String>();

            Connection verbindung = DriverManager.getConnection(connectionURL, user, password);
            Statement statement = verbindung.createStatement();

            ResultSet ergebnisse = statement.executeQuery("SELECT Notiz_ID FROM notiz;");


            int anzReihen = 0;
            while (ergebnisse.next()) {
                anzReihen++;
                notizID.add(ergebnisse.getString(1));
            }

            for (int i = 0; i < anzReihen; i++) {
                JPanel newPanel = new JPanel();
                newPanel.setName(notizID.get(i));
                newPanel.add(new JLabel(newPanel.getName()));

                JButton editButton = new JButton("Bearbeiten");
                JButton deleteButton = new JButton("Löschen");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        panelContainer.remove(newPanel);
                        Notiz_App.deleteNotiz(Integer.valueOf(newPanel.getName()));
                        GUI.pack();
                    }
                });
                newPanel.add(deleteButton);
                newPanel.add(editButton);
                panelContainer.add(newPanel);
                GUI.pack();
            }
            verbindung.close();
        } catch (SQLException ex) {
            System.out.println("SQLException beim Initialisieren der Notizen");
        }
    }

}
