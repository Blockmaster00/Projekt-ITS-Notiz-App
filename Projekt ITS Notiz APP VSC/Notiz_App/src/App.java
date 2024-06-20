
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Benjamin, Florian
 */
public class App {

    public static String connectionURL = "jdbc:mysql://localhost:3306/notizen";
    public static String user = "root";
    public static String password = "";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Notizen_UI.init();
    }

    public static String registrieren(String Name, String Password) {
        String status = "error";
        try {
            Connection verbindung = DriverManager.getConnection(connectionURL, user, password);

            String sql = ("SELECT BenutzerName FROM benutzer WHERE BenutzerName = ?");
            PreparedStatement preparedStatement = verbindung.prepareStatement(sql);

            preparedStatement.setString(1, Name);
            ResultSet ergebniss = preparedStatement.executeQuery();

            int i = 0;
            while (ergebniss.next()) {
                i++;
            }

            if (i > 0) {
                status = "Nutzer bereits vorhanden";
                System.out.println("Nutzer bereits vorhanden");
            } else {
                String sql_insert = ("INSERT INTO benutzer (BenutzerName, Password) VALUES (?, ?)");
                PreparedStatement insertStatement = verbindung.prepareStatement(sql_insert);

                insertStatement.setString(1, Name);
                insertStatement.setString(2, Password);
                insertStatement.executeUpdate();

                status = "Nutzer erfolgreich erstellt";
            }

        } catch (SQLException ex) {
            System.out.println("SQLException beim registrieren eines Benutzers");
        }
        return status;
    }

    public static String anmelden(String Name, String Password) {
        String status = "error";
        try {
            Connection verbindung = DriverManager.getConnection(connectionURL, user, password);

        } catch (SQLException ex) {
            System.out.println("SQLException beim anmelden eines Benutzers");
        }
        return status;
    }

    public static String getKategorie(int Kategorie_ID) {
        try {
            String Kategorie = "error";

            Connection verbindung = DriverManager.getConnection(connectionURL, user, password);

            String sql = ("SELECT Name FROM kategorie WHERE Kategorie_ID = ?");
            PreparedStatement preparedStatement = verbindung.prepareStatement(sql);

            preparedStatement.setString(1, String.valueOf(Kategorie_ID));
            ResultSet ergebniss = preparedStatement.executeQuery();

            while (ergebniss.next()) {
                Kategorie = ergebniss.getString(1);
            }

            return (Kategorie);
        } catch (SQLException ex) {
            System.out.println("SQLException beim getten der Überschrift");
        }
        return ("error2");

    }

    public static ArrayList<String> getKategorien() {
        ArrayList<String> kategorien = new ArrayList<>();
        try {
            Connection verbindung = DriverManager.getConnection(connectionURL, user, password);
            Statement statement = verbindung.createStatement();
            ResultSet ergebnisse = statement.executeQuery("SELECT Name FROM kategorie;");

            while (ergebnisse.next()) {
                kategorien.add(ergebnisse.getString(1));
            }
            return kategorien;
        } catch (SQLException ex) {
            System.out.println("SQLException beim getten der Kategorien");
        }
        return kategorien;
    }

    public static String getUeberschrift(int Notiz_ID) {
        try {
            String ueberschrift = "error";

            Connection verbindung = DriverManager.getConnection(connectionURL, user, password);

            String sql = ("SELECT Ueberschrift FROM notiz WHERE notiz_ID = ?");
            PreparedStatement preparedStatement = verbindung.prepareStatement(sql);

            preparedStatement.setString(1, String.valueOf(Notiz_ID));
            ResultSet ergebniss = preparedStatement.executeQuery();

            while (ergebniss.next()) {
                ueberschrift = ergebniss.getString(1);
            }

            return (ueberschrift);
        } catch (SQLException ex) {
            System.out.println("SQLException beim getten der Überschrift");
        }
        return ("error2");

    }

    public static String getBeschreibung(int Notiz_ID) {
        try {
            String beschreibung = "error";

            Connection verbindung = DriverManager.getConnection(connectionURL, user, password);

            String sql = ("SELECT Beschreibung FROM notiz WHERE notiz_ID = ?");
            PreparedStatement preparedStatement = verbindung.prepareStatement(sql);

            preparedStatement.setString(1, String.valueOf(Notiz_ID));
            ResultSet ergebniss = preparedStatement.executeQuery();

            while (ergebniss.next()) {
                beschreibung = ergebniss.getString(1);
            }

            return (beschreibung);
        } catch (SQLException ex) {
            System.out.println("SQLException beim getten der Beschreibung");
        }
        return ("error2");

    }

    public static int addNotiz(String ueberschrift, String beschreibung, Integer Kategorie_ID) {

        try {
            try (Connection verbindung = DriverManager.getConnection(connectionURL, user, password)) {
                String sql = "INSERT INTO notiz (Ueberschrift, Beschreibung, Kategorie_ID) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = verbindung.prepareStatement(sql);

                preparedStatement.setString(1, ueberschrift);
                preparedStatement.setString(2, beschreibung);
                preparedStatement.setString(3, String.valueOf(Kategorie_ID));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("SQLException beim Adden der Notiz");
        }

        try {
            int Notiz_ID = 0;
            try (Connection verbindung = DriverManager.getConnection(connectionURL, user, password)) {
                Statement statement = verbindung.createStatement();

                ResultSet ergebnisse = statement.executeQuery("SELECT MAX(Notiz_ID) FROM notiz;");
                while (ergebnisse.next()) {
                    Notiz_ID = ergebnisse.getInt(1);
                }
            }

            return Notiz_ID;
        } catch (SQLException ex) {
            System.out.println("SQLException beim getten der ID");
        }
        return -1;
    }

    public static void deleteNotiz(int notiz_ID) {
        try {
            try (Connection verbindung = DriverManager.getConnection(connectionURL, user, password)) {
                String sql = "DELETE FROM notiz Where Notiz_ID = (?)";
                PreparedStatement preparedStatement = verbindung.prepareStatement(sql);

                preparedStatement.setInt(1, notiz_ID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("SQLException beim Löschen der Notizen");
        }

    }

    public static void updateNotiz(String ueberschrift, String beschreibung, int kategorie_ID, int notiz_id) {

        try {
            try (Connection verbindung = DriverManager.getConnection(connectionURL, user, password)) {
                String sql = "UPDATE notiz SET Ueberschrift = ?, Beschreibung = ?, Kategorie_ID = ? WHERE Notiz_ID = ?";
                PreparedStatement preparedStatement = verbindung.prepareStatement(sql);

                preparedStatement.setString(1, ueberschrift);
                preparedStatement.setString(2, beschreibung);
                preparedStatement.setString(3, String.valueOf(kategorie_ID));
                preparedStatement.setString(4, String.valueOf(notiz_id));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("SQLException beim Bearbeiten der Notiz");
        }
    }
}
