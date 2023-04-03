package Persistence;

import Database.ConnectionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * Defines the common operations for accessing a table: Insert, Update, Delete.
 * @author Mihai Curac
 */
public class GenericDAO {

    /**
     * @param genericObject inserts a new entry into the database.
     */
    public void Insert(Object genericObject) {
        String tableTitle = genericObject.getClass().getSimpleName();
        StringBuilder values = new StringBuilder();
        StringBuilder query = new StringBuilder();
        query.append("Insert into untold.").append(tableTitle).append(" (");
        Field[] fields = genericObject.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < fields.length - 1; i++)
            {
                fields[i].setAccessible(true);
                query.append(fields[i].getName()).append(", ");
                Object fieldValue = fields[i].get(genericObject);
                String fieldType = fields[i].getType().getSimpleName();
                if (fieldType.equals("String"))
                    values.append("\"").append(fieldValue).append("\"");
                else
                    values.append(fieldValue);
                values.append(", ");
            }
            int lastIndex = fields.length - 1;
            fields[lastIndex].setAccessible(true);
            query.append(fields[lastIndex].getName());
            Object fieldValue = fields[lastIndex].get(genericObject);
            String fieldType = fields[lastIndex].getType().getSimpleName();
            if (fieldType.equals("String"))
                values.append("\"").append(fieldValue).append("\"");
            else
                values.append(fieldValue);
            query.append(") values (").append(values).append(" )");
            System.out.println(query);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was an INSERT error.");
        }
        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmInsert = con.prepareStatement(query.toString());
            stmInsert.executeUpdate();
            con.close();
            stmInsert.close();
            JOptionPane.showMessageDialog(null, "Successfully created record!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "INSERT query execution failed.");
        }
    }

    public void Update(Object genericObject) {
        String tableTitle = genericObject.getClass().getSimpleName();
        StringBuilder query = new StringBuilder();
        query.append("update untold.").append(tableTitle).append(" set ");
        Field[] fields = genericObject.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < fields.length - 1; i++) {
                fields[i].setAccessible(true);
                query.append(fields[i].getName());
                query.append(" = ");
                Object fieldValue = fields[i].get(genericObject);
                String fieldType = fields[i].getType().getSimpleName();
                if (fieldType.equals("String"))
                    query.append("\"").append(fieldValue).append("\"");
                else
                    query.append(fieldValue);
                query.append(", ");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was an UPDATE error.");
        }
        int lastIndex = fields.length - 1;
        fields[lastIndex].setAccessible(true);
        query.append(fields[lastIndex].getName());
        query.append(" = ");
        try {
            Object fieldValue = fields[lastIndex].get(genericObject);
            String fieldType = fields[lastIndex].getType().getSimpleName();
            if (fieldType.equals("String"))
                query.append("\"").append(fieldValue).append("\"");
            else
                query.append(fieldValue);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was an UPDATE error.");
        }
        query.append(" where ");
        Field firstField = fields[0];
        firstField.setAccessible(true);
        String fieldName = firstField.getName();
        query.append(fieldName).append(" = ");
        try {
            Object value = firstField.get(genericObject);
            query.append(value);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Obtaining ID value failed.");
        }
        try {
            System.out.println(query);
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmUpdate = con.prepareStatement(query.toString());
            stmUpdate.executeUpdate();
            con.close();
            stmUpdate.close();
            JOptionPane.showMessageDialog(null, "Successfully updated record!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "UPDATE query execution failed.");
        }
    }

    public void Delete(Object genericObject) {
        String tableTitle = genericObject.getClass().getSimpleName();
        StringBuilder query = new StringBuilder();
        query.append("Delete from untold.").append(tableTitle).append(" where ");
        Field[] fields = genericObject.getClass().getDeclaredFields();
        Field firstField = fields[0];
        firstField.setAccessible(true);
        String fieldName = firstField.getName();
        query.append(fieldName).append(" = ");
        try {
            Object value = firstField.get(genericObject);
            query.append(value);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Obtaining ID value failed.");
        }
        System.out.println(query);
        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmDelete = con.prepareStatement(query.toString());
            stmDelete.executeUpdate();
            con.close();
            stmDelete.close();
            JOptionPane.showMessageDialog(null, "Successfully deleted record!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "DELETE query execution failed.");
        }
    }

    public JTable createTable(ArrayList<?> tableList) {
        int tableSize = tableList.get(0).getClass().getDeclaredFields().length;
        String[] columnNames = new String[tableSize];
        int columnIndex = 0;
        for (java.lang.reflect.Field currentField : tableList.get(0).getClass().getDeclaredFields()) {
            currentField.setAccessible(true);
            try {
                columnNames[columnIndex] = currentField.getName();
                columnIndex++;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.setColumnIdentifiers(columnNames);
        for (Object obj : tableList) {
            Object[] object = new Object[tableSize];
            int col = 0;
            for (java.lang.reflect.Field currentField : obj.getClass().getDeclaredFields()) {
                currentField.setAccessible(true);
                try {
                    object[col] = currentField.get(obj);
                    col++;
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            tableModel.addRow(object);
        }
        return new JTable(tableModel);
    }
}
