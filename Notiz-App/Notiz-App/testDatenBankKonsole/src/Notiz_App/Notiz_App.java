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
        // TODO code application logic here
         try {

            //Class.forName("com.mysql.jdbc.Driver");

            Connection verbindung = DriverManager.getConnection(connectionURL, user, password);

            if (verbindung.isValid(2)) {
                System.out.println("Verbindung aufgebaut!");
                new GUI().setVisible(true);
            } else {
                System.out.println("Fehler beim Aufbau der Verbindung!");
            }
       
             if (verbindung.isClosed()){
                System.out.println("Verbindung ist geschlossen");
            } else {
                System.out.println("Verbindung ist offen");
            }
             // SQL Statement erstellen
            Statement statement = verbindung.createStatement();
            
            // Statement an die DB schicken, ResultSet speichern
            ResultSet ergebnisse = statement.executeQuery("SELECT * FROM notiz;");
            
            
            
            // Meta-Daten des Resultsets auslesen, z.B. Spaltenanzahl
            ResultSetMetaData metaDaten = ergebnisse.getMetaData();
            int anzSpalten = metaDaten.getColumnCount();    
            System.out.println("Anzahl Spalten: "+anzSpalten);
            
            // Zeilenweise das ResultSet auslesen
            while(ergebnisse.next())
            {
                // Lies Inhalt der einzelnen Spalten aus
                for(int i = 1; i <= anzSpalten; i++)
                {
                   System.out.print(ergebnisse.getString(i)+", "); 
                }
                System.out.print("\n");
            }
             verbindung.close();
             
              if (verbindung.isClosed()){
                System.out.println("Verbindung ist geschlossen");
            } else {
                System.out.println("Verbindung ist offen");
            }
        } catch (SQLException ex) {            
            System.out.println("SQLException beim Aufbau der Verbindung!");
        }
    
    }
        public static ArrayList<String> getData(){
            ArrayList<String> daten = new ArrayList<String>();
        try{
            Connection verbindung = DriverManager.getConnection(connectionURL, user, password);
            Statement statement = verbindung.createStatement();
            
            ResultSet ergebnisse = statement.executeQuery("SELECT * FROM notiz;");  //Alle Spalten von notiz auslesen und in "ergebnisse speichern
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
            
        }catch(SQLException ex) {System.out.println("SQLException beim Aufbau der Verbindung!");}
        
        
        return daten;
    }
    
}
