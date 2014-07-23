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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Michael Schroeder <michael.schroeder.1@hs-osnabrueck.de>
 */
@Entity
@XmlRootElement

public class Rating implements Serializable {

    @Id
    @GeneratedValue
    private int ratingId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID")
    private Rater user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "POB_ID")
    private Pob pob;

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public Pob getPob() {
        return pob;
    }

    public void setPob(Pob pob) {
        this.pob = pob;
    }

}
