package Presentation;

import Business.PerformancesBLL;
import Persistence.GenericDAO;
import Persistence.PerformancesDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
/**
 *  Contains the GUI elements for the Performance operations' window, including Action Listeners for
 *  the buttons.
 * @author Mihai Curac
 */
public class PerformancesGUI extends JFrame {

    private JButton insertBtn;
    private JButton updateBtn;
    private JButton deleteBtn;
    private JButton backBtn;
    private JButton listBtn;

    private JLabel pidLabel;
    private JTextField pidField;

    private JLabel nameLabel;
    private JTextField nameField;

    private JLabel genreLabel;
    private JTextField genreField;

    private JLabel sidLabel;
    private JTextField sidField;

    public PerformancesGUI(AdminView mainGUI, Connection con) {
        PerformancesGUI p = this;
        PerformancesDAO performanceDAO = new PerformancesDAO(con);
        GenericDAO genericDAO = new GenericDAO();
        this.setTitle("Performances Management");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(600, 200, 900, 700);
        this.getContentPane().setLayout(null);
        Font visibleFont = new Font("Arial", Font.PLAIN, 20);
        Font bigFont = new Font("Arial", Font.PLAIN, 36);

        pidLabel = new JLabel("Performance ID: ");
        pidLabel.setFont(visibleFont);
        pidLabel.setBounds(30, 30, 160, 30);
        getContentPane().add(pidLabel);

        pidField = new JTextField();
        pidField.setBounds(200, 30, 130, 30);
        getContentPane().add(pidField);

        nameLabel = new JLabel("Name: ");
        nameLabel.setFont(visibleFont);
        nameLabel.setBounds(30, 70, 130, 30);
        getContentPane().add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(120, 70, 130, 30);
        getContentPane().add(nameField);

        genreLabel = new JLabel("Genre: ");
        genreLabel.setFont(visibleFont);
        genreLabel.setBounds(30, 110, 130, 30);
        getContentPane().add(genreLabel);

        genreField = new JTextField();
        genreField.setBounds(120, 110, 130, 30);
        getContentPane().add(genreField);

        sidLabel = new JLabel("Show ID: ");
        sidLabel.setFont(visibleFont);
        sidLabel.setBounds(30, 150, 130, 30);
        getContentPane().add(sidLabel);

        sidField = new JTextField();
        sidField.setBounds(120, 150, 130, 30);
        getContentPane().add(sidField);

        insertBtn = new JButton("Insert");
        insertBtn.setFont(visibleFont);
        insertBtn.setBounds(30, 200, 100, 30);
        getContentPane().add(insertBtn);

        updateBtn = new JButton("Update");
        updateBtn.setFont(visibleFont);
        updateBtn.setBounds(30, 250, 100, 30);
        getContentPane().add(updateBtn);

        deleteBtn = new JButton("Delete");
        deleteBtn.setFont(visibleFont);
        deleteBtn.setBounds(30, 300, 100, 30);
        getContentPane().add(deleteBtn);

        listBtn = new JButton("List");
        listBtn.setFont(visibleFont);
        listBtn.setBounds(30, 350, 100, 30);
        getContentPane().add(listBtn);

        backBtn = new JButton("Back");
        backBtn.setFont(visibleFont);
        backBtn.setBounds(30, 400, 100, 30);
        getContentPane().add(backBtn);

        this.setLocationRelativeTo(null);

        insertBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new PerformancesBLL(p, performanceDAO, genericDAO).insertValidate(getPidField(), getNameField(), getGenreField(), getSidField());
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        updateBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new PerformancesBLL(p, performanceDAO, genericDAO).updateValidate(getPidField(), getNameField(), getGenreField(), getSidField());
            }
        });

        deleteBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new PerformancesBLL(p, performanceDAO, genericDAO).deleteValidate(getPidField());
            }
        });

        listBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new PerformancesBLL(p, performanceDAO, genericDAO).listPerformances();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setVisible(false);
                mainGUI.setVisible(true);

            }
        });
    }
        public String getPidField() {
            return pidField.getText();
        }

        public String getNameField() {
            return nameField.getText();
        }

        public String getGenreField() {
            return genreField.getText();
        }

        public String getSidField() {
            return sidField.getText();
        }
}
