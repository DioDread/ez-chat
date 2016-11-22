package net.ezchat.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.ConfigurationException;
import net.ezchat.AppConfig;
import net.ezchat.entity.Credentials;
import net.ezchat.entity.Role;
import net.ezchat.entity.User;

public class UserDAO extends AbsctractDAO {

    private final RoleDAO roleDAO;

    public UserDAO() {
        super();
        this.roleDAO = new RoleDAO();
    }

    public User findUserByCreds(Credentials creds) {
        prepareResources();
        try {
            ResultSet rs = statement.executeQuery("select user_id, username, userpic_name, role, create_time from `" + AppConfig.getDbName() + "`.user"
                    + "where email='" + creds.getUsername() + "' and password='" + creds.getPassword() + "';");
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String username = rs.getString("username");
                String userpicName = rs.getString("userpic_name");
                Role role = roleDAO.findRoleById(rs.getInt("role"));
                Date createTime = rs.getDate("create_time");

                return new User(userId, username, userpicName, creds.getUsername(), creds.getPassword(), createTime, role);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            releaseResources();
        }

        return null;
    }

    public User findUserById(int userId) {
        prepareResources();
        User user = new User();
        try {
            ResultSet rs = statement.executeQuery("select username, userpic_name, email, role, create_time from `" + AppConfig.getDbName() + "`.user"
                    + "where user_id=" + userId + ";");
            while (rs.next()) {
                String username = rs.getString("username");
                String userPicName = rs.getString("userpic_name");
                String email = rs.getString("email");
                Role role = roleDAO.findRoleById(rs.getInt("role"));
                Date createTime = rs.getDate("create_time");
                
                user.setUsername(username);
                user.setUserpicName(userPicName);
                user.setEmail(email);
                user.setRole(role);
                user.setCreateTime(createTime);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            releaseResources();
        }

        return user;
    }

    public void insertUser(User user) throws SQLException {
        try {
            String query = "insert into `" + AppConfig.getDbName() + "`.user values(" + (getLastUserId() + 1) + ", '"
                    + user.getUsername() + "', '" + user.getUserpicName() + "', '" + user.getEmail() + "', '" 
                    + user.getPassword() + "', now(), " + user.getRole().getRoleId() + ");";
            prepareResources();
            statement.executeUpdate(query);
        } catch (IOException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConfigurationException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            releaseResources();
        }
    }

    private int getLastUserId() throws ConfigurationException, SQLException {
        prepareResources();
        int result = -1;
        try {
            String query = "select MAX(user_id) as last_id from `" + AppConfig.getDbName() + "`.user;";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                result = rs.getInt("last_id") + 1;
            }
        } catch (IOException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConfigurationException("Application configuration failed, please contact your administrator");
        } finally {
            releaseResources();
        }
        return result;
    }
}
