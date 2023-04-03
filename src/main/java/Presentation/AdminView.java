package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author Mihai Curac
 * Contains the GUI elements for the main window of the application.
 */
public class AdminView extends JFrame {
    private JLabel mainLabel;
    private JButton performanceTableBtn;
    private JButton showTableBtn;
    private JButton userTableBtn;

    public AdminView() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(600, 200, 900, 700);
        this.getContentPane().setLayout(null);
        Font visibleFont = new Font("Arial", Font.PLAIN, 16);

        Font bigFont = new Font("Arial",Font.PLAIN,36);

        mainLabel = new JLabel("Administrator Panel");
        mainLabel.setFont(bigFont);
        mainLabel.setBounds(280,150,500,50);
        getContentPane().add(mainLabel);

        performanceTableBtn = new JButton("Manage Performances");
        performanceTableBtn.setFont(visibleFont);
        performanceTableBtn.setBounds(130,350,200,50);
        getContentPane().add(performanceTableBtn);

        showTableBtn = new JButton("Manage Shows");
        showTableBtn.setFont(visibleFont);
        showTableBtn.setBounds(355,350,200,50);
        getContentPane().add(showTableBtn);

        userTableBtn = new JButton("Manage Users");
        userTableBtn.setFont(visibleFont);
        userTableBtn.setBounds(580,350,200,50);
        getContentPane().add(userTableBtn);

        this.setLocationRelativeTo(null);
    }

    public void performanceTableBtnActionListener(ActionListener actionListener)
    {
        performanceTableBtn.addActionListener(actionListener);
    }

    public void showTableBtnActionListener(ActionListener actionListener)
    {
        showTableBtn.addActionListener(actionListener);
    }

    public void userTableBtnActionListener(ActionListener actionListener)
    {
        userTableBtn.addActionListener(actionListener);
    }
}