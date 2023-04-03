package Business;

import Model.Performances;
import Persistence.GenericDAO;
import Persistence.PerformancesDAO;
import Presentation.PerformancesGUI;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PerformancesBLL {

    private PerformancesGUI performanceGUI;
    private PerformancesDAO performanceDAO;
    private GenericDAO genericDAO;

    public PerformancesBLL(PerformancesGUI performanceGUI, PerformancesDAO performanceDAO, GenericDAO genericDAO) {
        this.performanceGUI = performanceGUI;
        this.performanceDAO = performanceDAO;
        this.genericDAO = genericDAO;
    }

    public void insertValidate(String pid, String name, String genre, String sid) throws SQLException {
        boolean correctPid = true;
        int pfID = 0;
        if (pid.equals(""))
            pfID = performanceDAO.getLastID();
        else {
            try {
                pfID = Integer.parseInt(pid);
            } catch (Exception e) {
                correctPid = false;
                JOptionPane.showMessageDialog(null, "Incorrect ID.");
            }
        }
        boolean correctSid = true;
        int sID = 0;
        if (sid.equals(""))
            correctSid = false;
        else {
            try {
                sID = Integer.parseInt(sid);
            } catch (Exception e) {
                correctSid = false;
                JOptionPane.showMessageDialog(null, "Incorrect Show ID.");
            }
        }
        if (correctPid & correctSid) {
            if (ValidatePerformance(genre)) {
                Performances myNewPerformance = new Performances(pfID, name, genre, sID);
                performanceDAO.insertPerformance(myNewPerformance);
            } else
                JOptionPane.showMessageDialog(null, "Incorrect input.");
        }
    }

    public void updateValidate(String pid, String name, String genre, String sid) {
        boolean correctPid = true;
        int pfID = 0;
        try {
            pfID = Integer.parseInt(pid);
        } catch (Exception e) {
            correctPid = false;
            JOptionPane.showMessageDialog(null, "Incorrect ID.");
        }
        boolean correctSid = true;
        int sID = 0;
        if (sid.equals(""))
            correctSid = false;
        else {
            try {
                sID = Integer.parseInt(sid);
            } catch (Exception e) {
                correctSid = false;
                JOptionPane.showMessageDialog(null, "Incorrect Show ID.");
            }
        }
        if (correctPid & correctSid) {
            if (ValidatePerformance(genre)) {
                Performances myPerformance = new Performances(pfID, name, genre, sID);
                performanceDAO.updatePerformance(myPerformance);
            } else
                JOptionPane.showMessageDialog(null, "Incorrect input.");
        }
    }

    public void deleteValidate(String pid) {
        int pfID = 0;
        boolean correctPid = true;
        try {
            pfID = Integer.parseInt(pid);
        } catch (Exception e) {
            correctPid = false;
            JOptionPane.showMessageDialog(null, "Incorrect ID.");
        }
        if (correctPid) {
            Performances myPerformance = new Performances(pfID, "", "", 0);
            performanceDAO.deletePerformance(myPerformance);
        }
    }

    public void listPerformances() throws SQLException {

        ArrayList<Performances> performancesList = performanceDAO.getPerformancesList();
        JScrollPane scrlPane = new JScrollPane();
        scrlPane.setBounds(275, 70, 600, 400);
        JTable performanceTable = new JTable();
        performanceTable = genericDAO.createTable(performancesList);
        performanceTable.setEnabled(true);
        performanceTable.setVisible(true);
        scrlPane.setViewportView(performanceTable);
        performanceGUI.getContentPane().add(scrlPane);
    }

    public boolean ValidateGenre(String genre) {
        if (genre.equals(""))
            return false;
        if (!genre.matches("[ a-zA-Z&]+"))
            return false;
        return true;
    }

    public boolean ValidatePerformance(String genre) {
        return ValidateGenre(genre);
    }
}
