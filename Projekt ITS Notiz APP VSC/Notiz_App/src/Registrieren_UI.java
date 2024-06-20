import java.awt.*;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Registrieren_UI {
    static JFrame GUI = new JFrame("Registrieren");
    static JPanel eingabeFeld = new JPanel();

    public static void main(String[] args) {
        init();
    }

    public static void init() {
        GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GUI.setLayout(new BorderLayout());
        eingabeFeld.setLayout(new BoxLayout(eingabeFeld, BoxLayout.Y_AXIS));

        GUI.add(eingabeFeld, BorderLayout.CENTER);

        // GUI Elemente
        JLabel lFeedback = new JLabel("");

        JLabel lBenutzerName = new JLabel("Benutzername:");

        JTextField tfBenutzerName = new JTextField(10);

        JLabel lPassword = new JLabel("Password:");

        JPasswordField tfPassword = new JPasswordField(10);

        JButton btnRegistrieren = new JButton("Registrieren");
        btnRegistrieren.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                char[] password = tfPassword.getPassword();
                String passwordString = new String(password);

                lFeedback.setText(App.registrieren(tfBenutzerName.getText(), passwordString));

            }
        });

        JLabel lAnmelden = new JLabel("Hier klicken zum Anmelden.");
        Font font = new Font("Arial", Font.CENTER_BASELINE, 11);
        lAnmelden.setFont(font);
        lAnmelden.setCursor(new Cursor(Cursor.HAND_CURSOR));

        lAnmelden.addMouseListener(new MouseAdapter() {
            public void mouseclicked(MouseEvent e) {
                GUI.dispose();
                Anmelde_UI.init();
            }
        });

        GUI.setSize(350, 350);
        eingabeFeld.setSize(100, 100);

        eingabeFeld.setLayout(new GridBagLayout()); // Verwende ein GridBagLayout für präzise Positionierung

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10); // Abstand zwischen den Komponenten

        c.gridy = 0;
        eingabeFeld.add(lFeedback, c);
        c.gridy = 1;
        eingabeFeld.add(lBenutzerName, c);
        c.gridy = 2;
        eingabeFeld.add(tfBenutzerName, c);
        c.gridy = 3;
        eingabeFeld.add(lPassword, c);
        c.gridy = 4;
        eingabeFeld.add(tfPassword, c);
        c.gridy = 5;
        eingabeFeld.add(btnRegistrieren, c);
        c.gridy = 6;
        eingabeFeld.add(lAnmelden, c);

        GUI.setVisible(true);
    }
}
