package net.ezchat.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.ConfigurationException;
import net.ezchat.AppConfig;
import net.ezchat.entity.Role;

public class RoleDAO extends AbsctractDAO {

    public Role findRoleById(int roleId) throws IOException {
        try {
            connection = dbc.getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select role_name from `" + AppConfig.getDbName() + "`.role"
                + "where role_id=" + roleId + ";");
            while(rs.next()) {
                return new Role(roleId, rs.getString("role_name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConfigurationException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            releaseResources();
        }
        
        return null;
    }
}
