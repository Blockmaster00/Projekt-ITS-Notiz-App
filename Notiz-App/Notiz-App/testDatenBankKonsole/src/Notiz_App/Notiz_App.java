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
 * @author Benjamin, Florian
 */
public class Notiz_App {
    
        public static  String connectionURL = "jdbc:mysql://localhost:3306/notizen";
        public static  String user = "root";
        public static  String password = "";
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GUI.init();
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
                    int Notiz_ID = 0;
                    Connection verbindung = DriverManager.getConnection(connectionURL, user, password);
                    Statement statement = verbindung.createStatement();
                
                    ResultSet ergebnisse = statement.executeQuery("SELECT MAX(Notiz_ID) FROM notiz;");  
                    while(ergebnisse.next()){
                    Notiz_ID = ergebnisse.getInt(1);
                    }
                    verbindung.close();
                
                    return Notiz_ID;
                }catch(SQLException ex){System.out.println("SQLException beim getten der ID");}
                
            return -1;
            
        }  
        
        public static void deleteNotiz(int notiz_ID) {
            try{
                Connection verbindung = DriverManager.getConnection(connectionURL, user, password);

                String sql = "DELETE FROM notiz Where notiz_ID = (?)";
                PreparedStatement preparedStatement = verbindung.prepareStatement(sql);

                preparedStatement.setInt(1, notiz_ID);
                preparedStatement.executeUpdate();
                verbindung.close();
            }catch(SQLException ex){System.out.println("SQLException beim Löschen der Notizen");}

          
}
/*
        public static void init(){
                try{
                int anzahlNotizen;
                    Connection verbindung = DriverManager.getConnection(connectionURL, user, password);
                    Statement statement = verbindung.createStatement();

                    ResultSet ergebnisse = statement.executeQuery("SELECT Count(Notiz_ID) FROM notiz;");
                    while(ergebnisse.next()){
                    anzahlNotizen = ergebnisse.getInt(1);
                    }
                    
                    for(int i = 0; i < anzahlNotizen; i++){
                JPanel newPanel = new JPanel();
                newPanel.setName(String.valueOf(addNotiz("Test", "hilfe")));
                newPanel.add(new JLabel(newPanel.getName()));
                
                JButton editButton = new JButton("Bearbeiten");
                JButton deleteButton = new JButton("Löschen");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        panelContainer.remove(newPanel);
                        deleteNotiz(Integer.valueOf(newPanel.getName()));
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
*/
    
}
