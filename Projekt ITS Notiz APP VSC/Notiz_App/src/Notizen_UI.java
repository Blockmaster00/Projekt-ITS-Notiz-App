
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
public class Notizen_UI extends App {
    static JFrame GUI = new JFrame("Notizen");
    static JPanel panelContainer = new JPanel();
    static JPanel eingabefeld = new JPanel();
    static JTextField tfUeberschrift; // Instanzvariable
    static JTextArea taBeschreibung; // Instanzvariable

    public static void main(String[] args) {

    }

    public static void init() { // Initialisierung der GUI, erstes erstellen des Eingabefeldes und Buttons

        GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUI.setLayout(new BorderLayout());

        eingabefeld.setLayout(new BoxLayout(eingabefeld, BoxLayout.Y_AXIS));

        JButton btnaddNotiz = new JButton("Neue Notiz hinzufügen");
        JLabel lUeberschrift = new JLabel("Überschrift:");
        tfUeberschrift = new JTextField("", 1); // Verwendung der Instanzvariable
        JLabel lBeschreibung = new JLabel("Beschreibung:");
        taBeschreibung = new JTextArea(3, 1); // Verwendung der Instanzvariable

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
                JPanel newNotiz = new JPanel(); // neues Feld für Notiz wird erstellt
                newNotiz.setLayout(new BoxLayout(newNotiz, BoxLayout.Y_AXIS)); // Feld bekommt Box Layout zugewiesen,
                                                                               // damit alles ordentlich angezeigt wird

                newNotiz.setName(String.valueOf(App.addNotiz(tfUeberschrift.getText(), taBeschreibung.getText())));
                // Neue Notiz in der Datenbank erstellen mit der Überschrift und Beschreibung ^

                JLabel lIdN = new JLabel(newNotiz.getName());
                JLabel lUeberschriftN = new JLabel(tfUeberschrift.getText());
                JLabel lBeschreibungN = new JLabel(taBeschreibung.getText());
                newNotiz.add(lIdN);
                newNotiz.add(lUeberschriftN);
                newNotiz.add(lBeschreibungN);

                JButton btnEdit = new JButton("Bearbeiten");
                btnEdit.addActionListener(new ActionListener() { // Button Bearbeiten
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        App.updateNotiz(tfUeberschrift.getText(), taBeschreibung.getText(),
                                Integer.parseInt(newNotiz.getName()));
                        lUeberschriftN.setText(App.getUeberschrift(Integer.parseInt(newNotiz.getName())));
                        lBeschreibungN.setText(App.getBeschreibung(Integer.parseInt(newNotiz.getName())));
                        GUI.pack();
                    }
                });
                JButton btnDelete = new JButton("Löschen");
                btnDelete.addActionListener(new ActionListener() { // Button Löschen
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        App.deleteNotiz(Integer.parseInt(newNotiz.getName()));
                        panelContainer.remove(newNotiz);
                        GUI.pack();
                    }
                });
                newNotiz.add(btnDelete);
                newNotiz.add(btnEdit);
                newNotiz.setBorder(BorderFactory.createLineBorder(Color.black, 3));

                panelContainer.add(newNotiz);
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
            ArrayList<String> notizID = new ArrayList<>();
            ArrayList<String> ueberschrift = new ArrayList<>();
            ArrayList<String> beschreibung = new ArrayList<>();

            try (Connection verbindung = DriverManager.getConnection(connectionURL, user, password)) {
                Statement statement = verbindung.createStatement();

                ResultSet ergebnisse = statement
                        .executeQuery("SELECT Ueberschrift, Beschreibung, Notiz_ID FROM notiz;");

                int anzReihen = 0;
                while (ergebnisse.next()) {
                    anzReihen++;
                    ueberschrift.add(ergebnisse.getString(1));
                    beschreibung.add(ergebnisse.getString(2));
                    notizID.add(ergebnisse.getString(3));
                }

                for (int i = 0; i < anzReihen; i++) {
                    JPanel newNotiz = new JPanel();
                    newNotiz.setLayout(new BoxLayout(newNotiz, BoxLayout.Y_AXIS));

                    newNotiz.setName(notizID.get(i));

                    JLabel lIdN = new JLabel(newNotiz.getName());
                    JLabel lUeberschriftN = new JLabel(ueberschrift.get(i));
                    JLabel lBeschreibungN = new JLabel(beschreibung.get(i));
                    newNotiz.add(lIdN);
                    newNotiz.add(lUeberschriftN);
                    newNotiz.add(lBeschreibungN);

                    JButton btnEdit = new JButton("Bearbeiten");
                    btnEdit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            App.updateNotiz(lUeberschriftN.getText(),
                                    lBeschreibungN.getText(), Integer.parseInt(newNotiz.getName()));
                            lUeberschriftN.setText(App.getUeberschrift(Integer.parseInt(newNotiz.getName())));
                            lBeschreibungN.setText(App.getBeschreibung(Integer.parseInt(newNotiz.getName())));
                            GUI.pack();
                        }
                    });
                    JButton btnDelete = new JButton("Löschen");
                    btnDelete.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            panelContainer.remove(newNotiz);
                            App.deleteNotiz(Integer.parseInt(newNotiz.getName()));
                            GUI.pack();
                        }
                    });
                    newNotiz.add(btnDelete);
                    newNotiz.add(btnEdit);
                    newNotiz.setBorder(BorderFactory.createLineBorder(Color.black, 3));

                    panelContainer.add(newNotiz);
                    GUI.pack();
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException beim Initialisieren der Notizen");
        }
    }

}
