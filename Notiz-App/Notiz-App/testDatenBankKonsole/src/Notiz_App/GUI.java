/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Notiz_App;

import java.awt.*;
import java.awt.Color;
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
        JLabel lUeberschrift = new JLabel("Überschrift:");
        JTextField tfUeberschrift = new JTextField("", 1);
        JLabel lBeschreibung = new JLabel("Beschreibung:");
        JTextArea taBeschreibung = new JTextArea(3, 1);

        eingabefeld.add(btnaddNotiz);
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
                newPanel.setLayout(new BoxLayout(newPanel,BoxLayout.Y_AXIS));
                
                newPanel.setName(String.valueOf(Notiz_App.addNotiz(tfUeberschrift.getText(), taBeschreibung.getText()))); //Neue Notiz erstellen mit Überschrift und Beschreibung von den Eingabefeldern
                newPanel.add(new JLabel(newPanel.getName(),JLabel.RIGHT));
                newPanel.add(new JLabel(tfUeberschrift.getText()));
                newPanel.add(new JLabel(taBeschreibung.getText()));

                JButton btnEdit = new JButton("Bearbeiten");
                btnEdit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        Notiz_App.updateNotiz(tfUeberschrift.getText(), taBeschreibung.getText(),Integer.valueOf(newPanel.getName()));
                        GUI.pack();
                    }
                });
                JButton btnDelete = new JButton("Löschen");
                btnDelete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        panelContainer.remove(newPanel);
                        Notiz_App.deleteNotiz(Integer.valueOf(newPanel.getName()));
                        GUI.pack();
                    }
                });
                newPanel.add(btnDelete);
                newPanel.add(btnEdit);
                newPanel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
                
                
                panelContainer.add(newPanel);
                GUI.pack();
            }
        });

        GUI.add(eingabefeld, BorderLayout.NORTH);
        GUI.add(new JScrollPane(panelContainer), BorderLayout.CENTER);
        GUI.pack();
        GUI.setVisible(true);

        getNotizen();
    }

    public static void getNotizen() {
        try {
            ArrayList<String> notizID = new ArrayList<String>();
            ArrayList<String> ueberschrift = new ArrayList<String>();
            ArrayList<String> beschreibung = new ArrayList<String>();

            Connection verbindung = DriverManager.getConnection(connectionURL, user, password);
            Statement statement = verbindung.createStatement();

            ResultSet ergebnisse = statement.executeQuery("SELECT Ueberschrift, Beschreibung, Notiz_ID FROM notiz;");

            int anzReihen = 0;
            while (ergebnisse.next()) {
                anzReihen++;
                ueberschrift.add(ergebnisse.getString(1));
                beschreibung.add(ergebnisse.getString(2));
                notizID.add(ergebnisse.getString(3));
            }

            for (int i = 0; i < anzReihen; i++) {
                JPanel newPanel = new JPanel();
                newPanel.setLayout(new BoxLayout(newPanel,BoxLayout.Y_AXIS));
                
                newPanel.setName(notizID.get(i));
                newPanel.add(new JLabel(newPanel.getName()));
                
                newPanel.add(new JLabel(ueberschrift.get(i)));
                newPanel.add(new JLabel(beschreibung.get(i)));

                JButton btnEdit = new JButton("Bearbeiten");
                btnEdit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //Notiz_App.updateNotiz(tfUeberschrift.getText(), taBeschreibung.getText(),Integer.valueOf(newPanel.getName()));
                        GUI.pack();
                    }
                });
                JButton btnDelete = new JButton("Löschen");
                btnDelete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        panelContainer.remove(newPanel);
                        Notiz_App.deleteNotiz(Integer.valueOf(newPanel.getName()));
                        GUI.pack();
                    }
                });
                newPanel.add(btnDelete);
                newPanel.add(btnEdit);
                newPanel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
                
                panelContainer.add(newPanel);
                GUI.pack();
            }
            verbindung.close();
        } catch (SQLException ex) {
            System.out.println("SQLException beim Initialisieren der Notizen");
        }
    }

}
