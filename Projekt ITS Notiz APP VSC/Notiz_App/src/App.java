
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

    public static int addNotiz(String ueberschrift, String beschreibung) {

        try {
            Connection verbindung = DriverManager.getConnection(connectionURL, user, password);

            String sql = "INSERT INTO notiz (Ueberschrift, Beschreibung) VALUES (?, ?)";
            PreparedStatement preparedStatement = verbindung.prepareStatement(sql);

            preparedStatement.setString(1, ueberschrift);
            preparedStatement.setString(2, beschreibung);
            preparedStatement.executeUpdate();

            verbindung.close();
        } catch (SQLException ex) {
            System.out.println("SQLException beim Adden der Notiz");
        }

        try {
            int Notiz_ID = 0;
            Connection verbindung = DriverManager.getConnection(connectionURL, user, password);
            Statement statement = verbindung.createStatement();

            ResultSet ergebnisse = statement.executeQuery("SELECT MAX(Notiz_ID) FROM notiz;");
            while (ergebnisse.next()) {
                Notiz_ID = ergebnisse.getInt(1);
            }
            verbindung.close();

            return Notiz_ID;
        } catch (SQLException ex) {
            System.out.println("SQLException beim getten der ID");
        }

        return -1;

    }

    public static void deleteNotiz(int notiz_ID) {
        try {
            Connection verbindung = DriverManager.getConnection(connectionURL, user, password);

            String sql = "DELETE FROM notiz Where Notiz_ID = (?)";
            PreparedStatement preparedStatement = verbindung.prepareStatement(sql);

            preparedStatement.setInt(1, notiz_ID);
            preparedStatement.executeUpdate();
            verbindung.close();
        } catch (SQLException ex) {
            System.out.println("SQLException beim Löschen der Notizen");
        }

    }

    public static void updateNotiz(String ueberschrift, String beschreibung, int id) {

        try {
            Connection verbindung = DriverManager.getConnection(connectionURL, user, password);

            String sql = "UPDATE notiz SET Ueberschrift = ?, Beschreibung = ? WHERE Notiz_ID = ?";
            PreparedStatement preparedStatement = verbindung.prepareStatement(sql);

            preparedStatement.setString(1, ueberschrift);
            preparedStatement.setString(2, beschreibung);
            preparedStatement.setString(3, String.valueOf(id));
            preparedStatement.executeUpdate();

            verbindung.close();
        } catch (SQLException ex) {
            System.out.println("SQLException beim Bearbeiten der Notiz");
        }
    }
}
