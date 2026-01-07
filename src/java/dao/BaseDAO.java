/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.DatabaseUtil;

/**
 *
 * @author Devi Pratiwi
 */
public class BaseDAO {
     protected Connection connection;
    
    public BaseDAO(Connection connection) {
        this.connection = connection;
    }

    public BaseDAO() {
        try {
            this.connection = DatabaseUtil.getConnection();
            if (this.connection == null) {
                System.err.println("WARNING: Database connection is null!");
            }
        } catch (SQLException e) {
            System.err.println("Error establishing database connection in DAO: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        }
    }
    
    protected void closeResources(ResultSet rs, PreparedStatement ps) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (SQLException e) {
            System.err.println("Error closing resources");
            e.printStackTrace();
        }
    }
    
    protected void closeResources(PreparedStatement ps) {
        try {
            if (ps != null) ps.close();
        } catch (SQLException e) {
            System.err.println("Error closing statement");
            e.printStackTrace();
        }
    }

}
