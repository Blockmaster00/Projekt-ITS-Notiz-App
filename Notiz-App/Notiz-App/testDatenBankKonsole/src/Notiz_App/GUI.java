/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Notiz_App;

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
public class GUI {
    public static void main(String[] args) {
        
        }

    public static void init(){
    JFrame frame = new JFrame("Notizen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panelContainer = new JPanel();
        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));
/*
        JPanel eingabePanel = new JPanel();
        eingabePanel.add(new javax.swing.JTextField(TextFeld));
        TextFeld.setText("jTextField1");
*/

        JButton button = new JButton("Neue Notiz hinzufügen");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel newPanel = new JPanel();
                newPanel.setName(String.valueOf(Notiz_App.addNotiz("Test", "hilfe")));
                newPanel.add(new JLabel(newPanel.getName()));
                
                JButton editButton = new JButton("Bearbeiten");
                JButton deleteButton = new JButton("Löschen");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        panelContainer.remove(newPanel);
                        Notiz_App.deleteNotiz(Integer.valueOf(newPanel.getName()));
                        frame.pack();
                    }
                });
                newPanel.add(deleteButton);
                newPanel.add(editButton);
                panelContainer.add(newPanel);
                frame.pack();
            }
        });

        frame.add(button, BorderLayout.NORTH);
        frame.add(new JScrollPane(panelContainer), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}   

