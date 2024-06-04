/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Notiz_App;
import java.sql.*;
/**
 *
 * @author El
 */
public class Notiz_App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         try {

            //Class.forName("com.mysql.jdbc.Driver");

            String connectionURL = "jdbc:mysql://localhost:3306/notizen";
            String user = "root";
            String password = "";

            Connection verbindung = DriverManager.getConnection(connectionURL, user, password);

            if (verbindung.isValid(2)) {
                System.out.println("Verbindung aufgebaut!");
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
            ResultSet dasResultSet = statement.executeQuery("SELECT * FROM liga;");
            
            // Meta-Daten des Resultsets auslesen, z.B. Spaltenanzahl
            ResultSetMetaData metaDaten = dasResultSet.getMetaData();
            int anzSpalten = metaDaten.getColumnCount();    
            System.out.println("Anzahl Spalten: "+anzSpalten);
            
            // Zeilenweise das ResultSet auslesen
            while(dasResultSet.next())
            {
                // Lies Inhalt der einzelnen Spalten aus
                for(int i = 1; i <= anzSpalten; i++)
                {
                   System.out.print(dasResultSet.getString(i)+", "); 
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
    
}
