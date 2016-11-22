package net.ezchat.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.ezchat.dao.UserDAO;
import net.ezchat.entity.User;
import net.ezchat.utils.JsonUtils;

public class UserServlet extends HttpServlet {

    private final JsonUtils jsonUtils = new JsonUtils();
    private final UserDAO userDAO;
    
    public UserServlet() {
        this.userDAO = new UserDAO();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletInputStream bodyData = req.getInputStream();

        JsonObject userJson = jsonUtils.deserialize(inputStreaToBytes(bodyData));
        
        try {
            userDAO.insertUser(User.fromJson(userJson));
        } catch (SQLException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private byte[] inputStreaToBytes(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();

        return buffer.toByteArray();
    }

}
