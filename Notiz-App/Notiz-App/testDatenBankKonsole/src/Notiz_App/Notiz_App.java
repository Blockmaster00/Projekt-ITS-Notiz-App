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
 * @author El
 */
public class Notiz_App {
    
        public static  String connectionURL = "jdbc:mysql://localhost:3306/notizen";
        public static  String user = "root";
        public static  String password = "";
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Notizen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panelContainer = new JPanel();
        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));

        JButton button = new JButton("Neue Notiz hinzufügen");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel newPanel = new JPanel();
                newPanel.setName("Notiz" + addNotiz("Test", "hilfe"));
                newPanel.add(new JLabel(newPanel.getName()));

                JButton deleteButton = new JButton("Löschen");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        panelContainer.remove(newPanel);
                        frame.pack();
                    }
                });
                newPanel.add(deleteButton);

                panelContainer.add(newPanel);
                frame.pack();
            }
        });

        frame.add(button, BorderLayout.NORTH);
        frame.add(new JScrollPane(panelContainer), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
    
    public static ArrayList<String> getData(){
            ArrayList<String> daten = new ArrayList<String>();
        try{
            Connection verbindung = DriverManager.getConnection(connectionURL, user, password);
            Statement statement = verbindung.createStatement();
            
            ResultSet ergebnisse = statement.executeQuery("SELECT * FROM notiz;");  //Alle Spalten von notiz auslesen und in "ergebnisse" speichern
            ResultSetMetaData metaData = ergebnisse.getMetaData();
            int anzSpalten = metaData.getColumnCount();
            
            while(ergebnisse.next())
            {
                // Lies Inhalt der einzelnen Spalten aus und speicher sie in Daten um sie zu übergeben
                for(int i = 1; i <= anzSpalten; i++)
                {
                   daten.add(ergebnisse.getString(i)); 
                }
            }
            verbindung.close();
        }catch(SQLException ex) {System.out.println("SQLException beim Aufbau der Verbindung!");}
        
        
        return daten;
    }
    
    public static int addNotiz(String ueberschrift, String beschreibung){
            
                try{
                    Connection verbindung = DriverManager.getConnection(connectionURL, user, password);
                    Statement statement = verbindung.createStatement();
            
                    String sql = "INSERT INTO notiz (Ueberschrift, Beschreibung) VALUES (?, ?)";
                    PreparedStatement preparedStatement = verbindung.prepareStatement(sql);
            
                    preparedStatement.setString(1, ueberschrift);
                    preparedStatement.setString(2, beschreibung);
                    preparedStatement.executeUpdate();
                
                    verbindung.close();
                }catch(SQLException ex){System.out.println("SQLException beim Aufbau der Verbindung!");}
                
                try{
                    int anzahlNotizen = 0;
                    Connection verbindung = DriverManager.getConnection(connectionURL, user, password);
                    Statement statement = verbindung.createStatement();
                
                    ResultSet ergebnisse = statement.executeQuery("SELECT Count(Notiz_ID) FROM notiz;");  
                    while(ergebnisse.next()){
                    anzahlNotizen = ergebnisse.getInt(1);
                    }
                    verbindung.close();
                
                    return anzahlNotizen;
                }catch(SQLException ex){System.out.println("SQLException beim getten der ID's");}
                
            return -1;
            
        }  
        
        public static void deleteNotiz(int notiz_ID) {
 try{
                Connection verbindung = DriverManager.getConnection(connectionURL, user, password);
                Statement statement = verbindung.createStatement();

                String sql = "DELETE FROM Notizen Where notiz_ID = (?)";
                PreparedStatement preparedStatement = verbindung.prepareStatement(sql);

                preparedStatement.setInt(1, notiz_ID);
                preparedStatement.executeUpdate();
                verbindung.close();
}catch(SQLException ex){System.out.println("SQLException beim Löschen der Notizen");}

          
}

    
}
