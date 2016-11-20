package net.ezchat.entity;

import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;

public class Message {

    private int messageId;
    private User author;
    private String text;
    private Date dateCreated;

    public Message(int messageId, User author, String text, Date dateCreated) {
        this.messageId = messageId;
        this.author = author;
        this.text = text;
        this.dateCreated = dateCreated;
    }

    /**
     * @return the messageId
     */
    public int getMessageId() {
        return messageId;
    }

    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    /**
     * @return the author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    /**
     * @return JSON representation of chat message.
     */
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("messageId", messageId)
                .add("author", author.toJson())
                .add("text", text)
                .add("dateCreated", dateCreated.toString())
                .build();
    }
}
