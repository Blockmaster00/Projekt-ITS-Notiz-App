import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class anmelde_UI {
    static JFrame GUI = new JFrame("Anmelden");
    static JPanel eingabeFeld = new JPanel();

    public static void main(String[] args){
        GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GUI.setLayout(new BorderLayout());
        eingabeFeld.setLayout(new BoxLayout(eingabeFeld, BoxLayout.Y_AXIS));
        
        GUI.add(eingabeFeld, BorderLayout.CENTER);

        JLabel lBenutzerName = new JLabel("Benutzername:");
        eingabeFeld.add(lBenutzerName);

        JTextField tfBenutzerName = new JTextField(15);
        eingabeFeld.add(tfBenutzerName);

        JLabel lPassword = new JLabel("Password:");
        eingabeFeld.add(lPassword);

        JPasswordField tfPassword = new JPasswordField(15);
        eingabeFeld.add(tfPassword);

        JButton btnAnmelden = new JButton("Anmelden");
        eingabeFeld.add(btnAnmelden);

            btnAnmelden.addActionListener(new ActionListener() {
                
                public void actionPerformed(ActionEvent e){
                    //App.anmelden(tfBenutzerName.getText(), tfPassword.getAccessibleContext());
                }
            });


        GUI.setSize(250, 250);
        eingabeFeld.setSize(100,100);

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

        GUI.setVisible(true);
    }
}
