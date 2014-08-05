/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    private String creatorId = "";
    private String msg = "";
    private String creationDate = "";
    /*
     @ManyToOne(optional = false)
     @JoinColumn(name = "POB_ID")
     private Pob pob;
     */

    public int getCommentId() {
        return commentId;
    }

    /*    public Pob getPob() {
     return pob;
     }

     public void setPob(Pob pob) {
     this.pob = pob;
     }*/
    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
