package net.ezchat.entity;

import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;

public class User {

    private int userId;
    private String username;
    private String userpicName;
    private String email;
    private String password;
    private Date createTime;
    private Role role;

    public User() {

    }

    public User(int userId, String username, String userpicName, String email, String password, Date createTime, Role role) {
        this.userId = userId;
        this.username = username;
        this.userpicName = userpicName;
        this.email = email;
        this.password = password;
        this.createTime = createTime;
        this.role = role;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * @return the userpicName
     */
    public String getUserpicName() {
        return userpicName;
    }

    /**
     * @param userpicName the userpicName to set
     */
    public void setUserpicName(String userpicName) {
        this.userpicName = userpicName;
    }

    /**
     * @return JSON representation of user
     */
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("userId", getUserId())
                .add("username", getUsername())
                .add("email", getEmail())
                .add("password", getPassword())
                .add("role", getRole().toJson())
                .add("createTime", getCreateTime().toString())
                .build();
    }
}
