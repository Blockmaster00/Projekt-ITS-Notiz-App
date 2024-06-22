
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author benjamin.heinold
 */
public class Notizen_UI extends App {
    static JFrame GUI = new JFrame("Notizen");
    static JToolBar tbar = new JToolBar();
    static JPanel panelContainer = new JPanel();
    static JPanel eingabeFeld = new JPanel();
    static JPanel kategorieFeld = new JPanel();
    static JTextField tfUeberschrift; // Instanzvariable
    static JTextArea taBeschreibung; // Instanzvariable

    public static void main(String[] args) {

    }

    @SuppressWarnings("unchecked")
    public static void init(Integer Benutzer_ID) { // Initialisierung der GUI, erstes erstellen des Eingabefeldes und
                                                   // Buttons
        GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUI.setLayout(new BorderLayout());

        eingabeFeld.setLayout(new BoxLayout(eingabeFeld, BoxLayout.Y_AXIS));

        // Deklarierung der Elemente
        JButton btnRefreshNotizen = new JButton("Refresh");
        JButton btnaddNotiz = new JButton("Neue Notiz hinzufügen");
        JLabel lUeberschrift = new JLabel("Überschrift:");
        tfUeberschrift = new JTextField("", 1); // Verwendung der Instanzvariable
        JLabel lBeschreibung = new JLabel("Beschreibung:");
        taBeschreibung = new JTextArea(3, 1); // Verwendung der Instanzvariable

        JTextField tfKategorie = new JTextField("", 10);
        JButton btnKategorieAdd = new JButton("Kategorie hinzufügen");
        ArrayList<String> KategorieListe = App.getKategorien(); // getten der Kategorien
        String[] KategorieArray = new String[KategorieListe.size()];

        for (int i = 0; i < KategorieListe.size(); i++) {
            KategorieArray[i] = KategorieListe.get(i);
        }

        @SuppressWarnings("rawtypes")
        JComboBox cbKategorieAuswahl = new JComboBox(KategorieArray);

        eingabeFeld.add(tbar);
        tbar.setFloatable(false);

        kategorieFeld.add(tfKategorie);
        kategorieFeld.add(btnKategorieAdd);
        tbar.add(btnRefreshNotizen);
        tbar.add(btnaddNotiz);
        eingabeFeld.add(lUeberschrift);
        eingabeFeld.add(tfUeberschrift);
        eingabeFeld.add(lBeschreibung);
        eingabeFeld.add(taBeschreibung);
        JScrollPane scrollpane = new JScrollPane(taBeschreibung);
        eingabeFeld.add(scrollpane);
        eingabeFeld.add(cbKategorieAuswahl);

        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));

        btnKategorieAdd.addActionListener((ActionEvent e) -> {
            if (!"".equals(tfKategorie.getText())) {
                App.addKategorie(tfKategorie.getText());
                cbKategorieAuswahl.removeAllItems();
                ArrayList<String> KategorieListe1 = App.getKategorien(); // getten der Kategorien
                for (int i = 0; i < KategorieListe1.size(); i++) {
                    cbKategorieAuswahl.addItem(KategorieListe1.get(i));
                }
            }
        });

        btnRefreshNotizen.addActionListener((ActionEvent e) -> {
            panelContainer.removeAll();
            getNotizen(Benutzer_ID);
        });

        btnaddNotiz.addActionListener((ActionEvent e) -> {
            if (!"".equals(tfUeberschrift.getText())) {
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
                btnEdit.addActionListener((ActionEvent e1) -> {
                    App.updateNotiz(tfUeberschrift.getText(), taBeschreibung.getText(),
                            cbKategorieAuswahl.getSelectedIndex() + 1,
                            Integer.parseInt(newNotiz.getName()));
                    lUeberschriftN.setText(App.getUeberschrift(Integer.parseInt(newNotiz.getName())));
                    lBeschreibungN.setText(App.getBeschreibung(Integer.parseInt(newNotiz.getName())));
                    lKategorieN.setText((String) cbKategorieAuswahl.getSelectedItem());
                    GUI.pack();
                } // Button Bearbeiten
                );
                JButton btnDelete = new JButton("Löschen");
                btnDelete.addActionListener((ActionEvent e1) -> {
                    App.deleteNotiz(Integer.parseInt(newNotiz.getName()));
                    panelContainer.remove(newNotiz);
                    GUI.pack();
                } // Button LÃ¶schen
                );
                knoepfeFeld.add(btnDelete, BorderLayout.EAST);
                knoepfeFeld.add(btnEdit, BorderLayout.EAST);

                newNotiz.setBorder(BorderFactory.createLineBorder(Color.black, 3));

                panelContainer.add(newNotiz);
                newNotiz.add(beschreibungsFeld, BorderLayout.CENTER);
                newNotiz.add(knoepfeFeld, BorderLayout.EAST);

                GUI.pack();
            }
        });

        GUI.add(eingabeFeld, BorderLayout.NORTH);
        eingabeFeld.add(kategorieFeld);
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
                    btnEdit.addActionListener((ActionEvent e) -> {
                        App.updateNotiz(lUeberschriftN.getText(),
                                lBeschreibungN.getText(), kategorieID.get(Integer.parseInt(newNotiz.getName())),
                                Integer.parseInt(newNotiz.getName()));
                        lUeberschriftN.setText(App.getUeberschrift(Integer.parseInt(newNotiz.getName())));
                        lBeschreibungN.setText(App.getBeschreibung(Integer.parseInt(newNotiz.getName())));
                        GUI.pack();
                    });
                    JButton btnDelete = new JButton("Löschen");
                    btnDelete.addActionListener((ActionEvent e) -> {
                        panelContainer.remove(newNotiz);
                        App.deleteNotiz(Integer.parseInt(newNotiz.getName()));
                        GUI.pack();
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
