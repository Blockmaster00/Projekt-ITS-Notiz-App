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
    static JFrame frame = new JFrame("Notizen");
    static JPanel panelContainer = new JPanel();

    public static  String connectionURL = "jdbc:mysql://localhost:3306/notizen";
    public static  String user = "root";
    public static  String password = "";

    public static void main(String[] args) {
        
        }

    public static void init(){
    
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        
        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));
/*
        JPanel eingabePanel = new JPanel();
        eingabePanel.add(new javax.swing.JTextField(TextFeld));
        TextFeld.setText("jTextField1");
*/

        JButton button = new JButton("Neue Notiz hinzufügen");

        button.addActionListener(new ActionListener() {
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
                        frame.pack();
                    }
                });
                newPanel.add(deleteButton);
                newPanel.add(editButton);
                panelContainer.add(newPanel);
                frame.pack();
            }
        });

        frame.add(button, BorderLayout.NORTH);
        frame.add(new JScrollPane(panelContainer), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        
        getNotizen();
    }

    public static void getNotizen(){
                try{
                    ArrayList<String> notizID = new ArrayList<String>();

                    Connection verbindung = DriverManager.getConnection(connectionURL, user, password);
                    Statement statement = verbindung.createStatement();

                    ResultSet ergebnisse = statement.executeQuery("SELECT Notiz_ID FROM notiz;");

                    ResultSetMetaData metaData = ergebnisse.getMetaData();

                    int anzSpalten = 0;
                    
                    
                    while(ergebnisse.next()){
                    anzSpalten++;
                    
                    System.out.println(anzSpalten);

                    
                        notizID.add(ergebnisse.getString(anzSpalten-1));
                        System.out.println(notizID.get(anzSpalten-1));
                    }
                        
                    
                    
                    for(int i = 0; i < anzSpalten; i++){
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
                        frame.pack();
                    }
                });
                newPanel.add(deleteButton);
                newPanel.add(editButton);
                panelContainer.add(newPanel);
                frame.pack();
                }
                verbindung.close();
                }catch(SQLException ex){System.out.println("SQLException beim Initialisieren der Notizen");}
        }
}   

