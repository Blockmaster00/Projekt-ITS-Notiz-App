
import java.sql.*;

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

    public static int addNotiz(String ueberschrift, String beschreibung) {

        try {
            try (Connection verbindung = DriverManager.getConnection(connectionURL, user, password)) {
                String sql = "INSERT INTO notiz (Ueberschrift, Beschreibung) VALUES (?, ?)";
                PreparedStatement preparedStatement = verbindung.prepareStatement(sql);
                
                preparedStatement.setString(1, ueberschrift);
                preparedStatement.setString(2, beschreibung);
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

    public static void updateNotiz(String ueberschrift, String beschreibung, int id) {

        try {
            try (Connection verbindung = DriverManager.getConnection(connectionURL, user, password)) {
                String sql = "UPDATE notiz SET Ueberschrift = ?, Beschreibung = ? WHERE Notiz_ID = ?";
                PreparedStatement preparedStatement = verbindung.prepareStatement(sql);
                
                preparedStatement.setString(1, ueberschrift);
                preparedStatement.setString(2, beschreibung);
                preparedStatement.setString(3, String.valueOf(id));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("SQLException beim Bearbeiten der Notiz");
        }
    }
}
