/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import helper.GlobalFunc;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Michael Schroeder <michael.schroeder.1@hs-osnabrueck.de>
 */
@Entity
@XmlRootElement
//@NamedQueries({     @NamedQuery(name = "Comment.hasAlreadyPosted", query = "SELECT c FROM Comment c WHERE c.creatorId = :creatorId"),")})

public class Comment implements Serializable {

    @Id
    @GeneratedValue
    private int commentId;

    private String msg = "";
    private String creationDate = "";
    private Float rating = 0f;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    public Comment() {
        this.creationDate = GlobalFunc.getDateWithTime();
    }

    public int getCommentId() {
        return commentId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
