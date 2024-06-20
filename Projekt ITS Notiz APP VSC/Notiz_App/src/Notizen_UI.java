
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

    @SuppressWarnings("unchecked")
    public static void init(Integer Benutzer_ID) { // Initialisierung der GUI, erstes erstellen des Eingabefeldes und
                                                   // Buttons
        GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUI.setLayout(new BorderLayout());

        eingabefeld.setLayout(new BoxLayout(eingabefeld, BoxLayout.Y_AXIS));

        JButton btnRefreshNotizen = new JButton("Refresh");
        JButton btnaddNotiz = new JButton("Neue Notiz hinzufügen");
        JLabel lUeberschrift = new JLabel("Überschrift:");
        tfUeberschrift = new JTextField("", 1); // Verwendung der Instanzvariable
        JLabel lBeschreibung = new JLabel("Beschreibung:");
        taBeschreibung = new JTextArea(3, 1); // Verwendung der Instanzvariable

        ArrayList<String> KategorieListe = App.getKategorien(); // getten der Kategorien
        String[] KategorieArray = new String[KategorieListe.size()];

        for (int i = 0; i < KategorieListe.size(); i++)
            KategorieArray[i] = KategorieListe.get(i);

        @SuppressWarnings("rawtypes")
        JComboBox cbKategorieAuswahl = new JComboBox(KategorieArray);

        // Deklarierung der Elemente
        eingabefeld.add(btnRefreshNotizen);
        eingabefeld.add(btnaddNotiz);
        eingabefeld.add(lUeberschrift);
        eingabefeld.add(tfUeberschrift);
        eingabefeld.add(lBeschreibung);
        eingabefeld.add(taBeschreibung);
        JScrollPane scrollpane = new JScrollPane(taBeschreibung);
        eingabefeld.add(scrollpane);
        eingabefeld.add(cbKategorieAuswahl);

        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));

        btnRefreshNotizen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getNotizen(Benutzer_ID);
            }
        });

        btnaddNotiz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel newNotiz = new JPanel(); // neues Feld für Notiz wird erstellt
                JPanel beschreibungsFeld = new JPanel(); // Felder um Notiz besser zu strukturieren
                JPanel knoepfeFeld = new JPanel(); // ""

                knoepfeFeld.setLayout(new BoxLayout(knoepfeFeld, BoxLayout.Y_AXIS));
                beschreibungsFeld.setLayout(new BoxLayout(beschreibungsFeld, BoxLayout.Y_AXIS));
                newNotiz.setLayout(new BorderLayout()); // Feld bekommt Box Layout zugewiesen,
                                                        // damit alles ordentlich angezeigt wird

                newNotiz.setName(String.valueOf(App.addNotiz(tfUeberschrift.getText(), taBeschreibung.getText(),
                        cbKategorieAuswahl.getSelectedIndex() + 1, Benutzer_ID)));
                // Neue Notiz in der Datenbank erstellen mit der Überschrift, Beschreibung und
                // Kategorie ^

                JLabel lIdN = new JLabel(newNotiz.getName());
                JLabel lUeberschriftN = new JLabel(tfUeberschrift.getText());
                Font font = new Font("Arial", Font.BOLD, 15);
                lUeberschriftN.setFont(font);
                JLabel lBeschreibungN = new JLabel(taBeschreibung.getText());
                JLabel lKategorieN = new JLabel((String) cbKategorieAuswahl.getSelectedItem(), JLabel.CENTER);
                newNotiz.add(lIdN, BorderLayout.NORTH);
                beschreibungsFeld.add(lUeberschriftN);
                beschreibungsFeld.add(lBeschreibungN);
                newNotiz.add(lKategorieN, BorderLayout.SOUTH);

                JButton btnEdit = new JButton("Bearbeiten");
                btnEdit.addActionListener(new ActionListener() { // Button Bearbeiten
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        App.updateNotiz(tfUeberschrift.getText(), taBeschreibung.getText(),
                                cbKategorieAuswahl.getSelectedIndex() + 1,
                                Integer.parseInt(newNotiz.getName()));
                        lUeberschriftN.setText(App.getUeberschrift(Integer.parseInt(newNotiz.getName())));
                        lBeschreibungN.setText(App.getBeschreibung(Integer.parseInt(newNotiz.getName())));
                        lKategorieN.setText((String) cbKategorieAuswahl.getSelectedItem());
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
                knoepfeFeld.add(btnDelete, BorderLayout.EAST);
                knoepfeFeld.add(btnEdit, BorderLayout.EAST);

                newNotiz.setBorder(BorderFactory.createLineBorder(Color.black, 3));

                panelContainer.add(newNotiz);
                newNotiz.add(beschreibungsFeld, BorderLayout.CENTER);
                newNotiz.add(knoepfeFeld, BorderLayout.EAST);

                GUI.pack();

            }

        });

        GUI.add(eingabefeld, BorderLayout.NORTH);
        GUI.add(new JScrollPane(panelContainer), BorderLayout.CENTER);
        GUI.pack();
        GUI.setVisible(true);

    }

    public static void getNotizen(int Benutzer_ID) {
        try {
            ArrayList<String> notizID = new ArrayList<>();
            ArrayList<String> ueberschrift = new ArrayList<>();
            ArrayList<String> beschreibung = new ArrayList<>();
            ArrayList<Integer> kategorieID = new ArrayList<>();

            try (Connection verbindung = DriverManager.getConnection(connectionURL, user, password)) {
                String sql = "SELECT Ueberschrift, Beschreibung, Notiz_ID, Kategorie_ID FROM notiz WHERE Benutzer_ID = ?;";
                PreparedStatement preparedStatement = verbindung.prepareStatement(sql);

                preparedStatement.setString(1, String.valueOf(Benutzer_ID));
                ResultSet ergebnisse = preparedStatement.executeQuery();

                int anzReihen = 0;
                while (ergebnisse.next()) {
                    anzReihen++;
                    ueberschrift.add(ergebnisse.getString(1));
                    beschreibung.add(ergebnisse.getString(2));
                    notizID.add(ergebnisse.getString(3));
                    kategorieID.add(ergebnisse.getInt(4));
                }

                for (int i = 0; i < anzReihen; i++) {
                    JPanel newNotiz = new JPanel();
                    JPanel beschreibungsFeld = new JPanel(); // Felder um Notiz besser zu strukturieren
                    JPanel knoepfeFeld = new JPanel(); // ""

                    knoepfeFeld.setLayout(new BoxLayout(knoepfeFeld, BoxLayout.Y_AXIS));
                    beschreibungsFeld.setLayout(new BoxLayout(beschreibungsFeld, BoxLayout.Y_AXIS));
                    newNotiz.setLayout(new BorderLayout());

                    newNotiz.setName(notizID.get(i));

                    JLabel lIdN = new JLabel(newNotiz.getName());
                    JLabel lUeberschriftN = new JLabel(ueberschrift.get(i));
                    JLabel lBeschreibungN = new JLabel(beschreibung.get(i));

                    Font font = new Font("Arial", Font.BOLD, 15);
                    lUeberschriftN.setFont(font);

                    JLabel lKategorieN = new JLabel((String) App.getKategorie(kategorieID.get(i)), JLabel.CENTER);

                    newNotiz.add(lIdN, BorderLayout.NORTH);
                    beschreibungsFeld.add(lUeberschriftN);
                    beschreibungsFeld.add(lBeschreibungN);
                    newNotiz.add(lKategorieN, BorderLayout.SOUTH);

                    JButton btnEdit = new JButton("Bearbeiten");
                    btnEdit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            App.updateNotiz(lUeberschriftN.getText(),
                                    lBeschreibungN.getText(), kategorieID.get(Integer.parseInt(newNotiz.getName())),
                                    Integer.parseInt(newNotiz.getName()));
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
                    knoepfeFeld.add(btnDelete);
                    knoepfeFeld.add(btnEdit);

                    newNotiz.setBorder(BorderFactory.createLineBorder(Color.black, 3));

                    panelContainer.add(newNotiz);
                    newNotiz.add(beschreibungsFeld, BorderLayout.CENTER);
                    newNotiz.add(knoepfeFeld, BorderLayout.EAST);
                    GUI.pack();
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException beim Initialisieren der Notizen");
        }
    }

}
