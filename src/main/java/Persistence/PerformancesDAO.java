package Persistence;

import Model.Performances;
import Presentation.PerformancesGUI;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Extends the GenericDAO class and uses reflection to do the common operations for accessing
 * the Performances table.
 * @author Mihai Curac
 */
public class PerformancesDAO extends GenericDAO {

    private static Connection performanceConnection;
    private Statement performanceStatement;

    public PerformancesDAO(Connection con) {
        performanceConnection = con;
        try {
            this.performanceStatement = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertPerformance(Performances myNewPerformance) throws SQLException {
        Insert(myNewPerformance);
    }

    public void updatePerformance(Performances myPerformance) {
        try {
            Performances myNewPerformance = searchID(myPerformance.getPid());
            myNewPerformance.setName(myPerformance.getName());
            myNewPerformance.setGenre(myPerformance.getGenre());
            myNewPerformance.setSid(myPerformance.getSid());
            Update(myNewPerformance);
        } catch (Exception e) {
            System.out.println("Performance not found.");
        }
    }
    public void deletePerformance(Performances myPerformance) {
        try {
            Performances myNewPerformance = searchID(myPerformance.getPid());
            if (myNewPerformance != null)
                Delete(myNewPerformance);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Delete error!");
        }
    }

    public int getLastID() throws SQLException {
        ResultSet rs = performanceStatement.executeQuery("SELECT max(pid) FROM performances");
        int lastID = 0;
        while (rs.next()) {
            lastID = rs.getInt(1);
        }
        return lastID + 1;
    }

    public ArrayList<Performances> getPerformancesList() throws SQLException {
        ResultSet rs = performanceStatement.executeQuery("SELECT * FROM untold.performances");
        ArrayList<Performances> performancesList = new ArrayList<Performances>();
        while (rs.next()) {
            Performances newPerformance = new Performances(rs.getInt("pid"), rs.getString("name"),
                    rs.getString("genre"), rs.getInt("sid"));
            performancesList.add(newPerformance);
        }
        return performancesList;
    }

    public Performances searchID(int ID) throws SQLException {
        Performances searchedPerformance = null;
        ArrayList<Performances> performancesList = getPerformancesList();
        for (Performances currentPerformance : performancesList) {
            if (currentPerformance.getPid() == ID) {
                searchedPerformance = currentPerformance;
                break;
            }
        }
        return searchedPerformance;
    }

    //search for performances with given sID and return an ArrayList of their names
    public String searchPerformances(int sID) throws SQLException {
        String performancesList = "";
        ResultSet rs = performanceStatement.executeQuery("SELECT * FROM untold.performances");
        while (rs.next()) {
            if (rs.getInt("sid") == sID) {
                performancesList = performancesList.concat(rs.getString("name") + ", ");
            }
        }
        if (performancesList.length() > 2) {
            performancesList = performancesList.substring(0, performancesList.length() - 2);
        }
        return performancesList;
    }
}