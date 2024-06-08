/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Notiz_App;
import java.sql.*;
import java.util.ArrayList;
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
        new GUI().setVisible(true);
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
    
    public static void addNotiz(String ueberschrift, String beschreibung){
            
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
            
            
        }
    
}
