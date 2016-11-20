package net.ezchat.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.ConfigurationException;

public class AbsctractDAO {

    protected Connection connection;
    protected Statement statement;
    protected final DBConnect dbc;

    public AbsctractDAO() {
        dbc = new DBConnect();
    }

    protected void prepareResources() {
        try {
            connection = dbc.getConnection();
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(AbsctractDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConfigurationException ex) {
            Logger.getLogger(AbsctractDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void releaseResources() {
        try {
            connection.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(AbsctractDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
