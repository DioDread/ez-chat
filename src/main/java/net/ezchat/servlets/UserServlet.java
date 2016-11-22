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
import javax.servlet.http.HttpSession;
import net.ezchat.AppConfig;
import net.ezchat.dao.UserDAO;
import net.ezchat.entity.Credentials;
import net.ezchat.entity.User;
import net.ezchat.utils.CryptoUtils;
import net.ezchat.utils.JsonUtils;

public class UserServlet extends HttpServlet {

    private final JsonUtils jsonUtils = new JsonUtils();
    private final CryptoUtils cryptoUtils = new CryptoUtils();
    private final UserDAO userDAO;

    public UserServlet() {
        this.userDAO = new UserDAO();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean isAuthSessionCheck = req.getParameter("check") != null;
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        if (isAuthSessionCheck && !checkUserInSession(username, req)) {
            resp.setStatus(401);
            return;
        }

        Credentials creds = new Credentials(username, password);
        User loggedInUser = userDAO.findUserByCreds(creds);
        if (loggedInUser != null) {
            resp.setStatus(200);

            loggedInUser.setPassword("");
            JsonObject userToDisplay = loggedInUser.toJson();

            resp.setContentType("application/json;");
            resp.getWriter().write(userToDisplay.toString());
            resp.getWriter().flush();
        } else {
            resp.setStatus(401);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletInputStream bodyData = req.getInputStream();

        JsonObject userJson = jsonUtils.deserialize(inputStreamToBytes(bodyData));

        try {
            User userToCreate = User.fromJson(userJson);
            userToCreate.setPassword(cryptoUtils.encrypt(userToCreate.getPassword() + AppConfig.getSalt()));
            userDAO.insertUser(userToCreate);
        } catch (SQLException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            resp.setStatus(400);
        } catch (ParseException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        resp.setStatus(201);
    }

    private boolean checkUserInSession(String username, HttpServletRequest req) {
        HttpSession session = req.getSession();
        return username.equals((String) session.getAttribute("username"));
    }

    private byte[] inputStreamToBytes(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();

        return buffer.toByteArray();
    }

    private boolean validateUser(User user) {
        return false;
    }

}
