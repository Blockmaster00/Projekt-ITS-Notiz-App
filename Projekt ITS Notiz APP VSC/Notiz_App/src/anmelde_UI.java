import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Anmelde_UI {
    static JFrame GUI = new JFrame("Anmelden");
    static JPanel eingabeFeld = new JPanel();
    public static boolean initialisiert = false;

    public static void main(String[] args) {
        init();
    }

    public static void init() {
        if (initialisiert) {
            GUI.setVisible(true);
        } else {
            GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            GUI.setLayout(new BorderLayout());
            eingabeFeld.setLayout(new BoxLayout(eingabeFeld, BoxLayout.Y_AXIS));

            GUI.add(eingabeFeld, BorderLayout.CENTER);

            // GUI Elemente

            JLabel lBenutzerName = new JLabel("Benutzername:");

            JTextField tfBenutzerName = new JTextField(10);

            JLabel lPassword = new JLabel("Password:");

            JPasswordField tfPassword = new JPasswordField(10);

            JButton btnAnmelden = new JButton("Anmelden");
            btnAnmelden.addActionListener((ActionEvent e) -> {
                char[] password = tfPassword.getPassword();
                String passwordString = new String(password);

                Notizen_UI.init(App.anmelden(tfBenutzerName.getText(), passwordString));
                GUI.setVisible(false);
            });

            JLabel lRegistrieren = new JLabel("Hier klicken um neuen Account zu registrieren.");
            Font font = new Font("Arial", Font.CENTER_BASELINE, 11);
            lRegistrieren.setFont(font);
            lRegistrieren.setCursor(new Cursor(Cursor.HAND_CURSOR));

            lRegistrieren.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    GUI.setVisible(false);
                    Registrieren_UI.init();
                }
            });

            GUI.setSize(350, 350);
            eingabeFeld.setSize(100, 100);

            eingabeFeld.setLayout(new GridBagLayout()); // Verwende ein GridBagLayout für präzise Positionierung

            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(10, 10, 10, 10); // Abstand zwischen den Komponenten

            c.gridy = 0;
            eingabeFeld.add(lBenutzerName, c);
            c.gridy = 1;
            eingabeFeld.add(tfBenutzerName, c);
            c.gridy = 2;
            eingabeFeld.add(lPassword, c);
            c.gridy = 3;
            eingabeFeld.add(tfPassword, c);
            c.gridy = 4;
            eingabeFeld.add(btnAnmelden, c);
            c.gridy = 5;
            eingabeFeld.add(lRegistrieren, c);

            GUI.setVisible(true);
            initialisiert = true;
        }
    }
}
