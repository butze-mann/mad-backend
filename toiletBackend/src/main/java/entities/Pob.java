/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Michael Schroeder <michael.schroeder.1@hs-osnabrueck.de>
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pob.findAll", query = "SELECT p FROM Pob p")})
public class Pob implements Serializable {

    @Id
    @GeneratedValue
    private int pobId;

    private Double lat = 0.0;
    private Double lnt = 0.0;
    private String creationDate = "0000-00-00";
    private String creatorId = "0000";
    private String description = "";
    private Double rating = 0.0;
    private Double ratingSum = 0.0;
    private Integer rateCount = 0;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="pobId")
    private List<Comment> commentCollection = new ArrayList<Comment>();

    @XmlElement
    public int getPobId() {
        return pobId;
    }

    @XmlElement
    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    @XmlElement
    public Double getRating() {
        return rating;
    }

    @XmlTransient
    public void setRating(Double rating) {
        this.rating = rating;
    }

    @XmlElement
    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @XmlElement
    public Double getLnt() {
        return lnt;
    }

    public void setLnt(Double lnt) {
        this.lnt = lnt;
    }

    @XmlElement
    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @XmlTransient
    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    @XmlTransient
    public void setRateCount(Integer rateCount) {
        this.rateCount = rateCount;
    }

    @XmlElement
    public Integer getRateCount() {
        return rateCount;
    }        

    @XmlTransient
    public Double getRatingSum() {
        return ratingSum;
    }

    @XmlTransient
    public void setRatingSum(Double ratingSum) {
        this.ratingSum = ratingSum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }        
       
}
