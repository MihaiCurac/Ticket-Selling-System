package Persistence;

import Model.Performances;
import Model.Shows;
import Presentation.PerformancesGUI;
import Presentation.ShowsGUI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ShowsDAO extends GenericDAO {

    private static Connection showConnection;
    private Statement showStatement;

    public ShowsDAO(Connection con) {
        showConnection = con;
        try {
            this.showStatement = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertShow(Shows myNewShow) throws SQLException {
        Insert(myNewShow);
    }

    public void updateShow(Shows myShow) {
        try {
            Shows myNewShow = searchID(myShow.getSid());
            myNewShow.setTitle(myShow.getTitle());
            myNewShow.setDate_time(myShow.getDate_time());
            myNewShow.setMax_tickets(myShow.getMax_tickets());
            myNewShow.setPrice(myShow.getPrice());
            Update(myNewShow);
        } catch (Exception e) {
            System.out.println("Show not found.");
        }
    }

    public void deleteShow(Shows myShow) {
        try {
            Shows myNewShow = searchID(myShow.getSid());
            if (myNewShow != null)
                Delete(myNewShow);
        } catch (Exception e) {
            System.out.println("Delete error!");
        }
    }

    public int getLastID() throws SQLException {
        ResultSet rs = showStatement.executeQuery("SELECT max(sid) FROM shows");
        int lastID = 0;
        while (rs.next()) {
            lastID = rs.getInt(1);
        }
        return lastID + 1;
    }

    public ArrayList<Shows> getShowsList() throws SQLException {
        ResultSet rs = showStatement.executeQuery("SELECT * FROM untold.shows");
        ArrayList<Shows> showsList = new ArrayList<Shows>();
        while (rs.next()) {
            Shows newShow = new Shows(rs.getInt("sid"), rs.getString("title"),
                    rs.getString("date_time"), rs.getInt("max_tickets"), rs.getDouble("price"));
            showsList.add(newShow);
        }
        return showsList;
    }

    public Shows searchID(int ID) throws SQLException {
        Shows searchedShow = null;
        ArrayList<Shows> showsList = getShowsList();
        for (Shows currentShow : showsList) {
            if (currentShow.getSid() == ID) {
                searchedShow = currentShow;
                break;
            }
        }
        return searchedShow;
    }

}
