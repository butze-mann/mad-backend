/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hs.os.toiletbackend;

import entities.Pob;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Michael Schroeder <michael.schroeder.1@hs-osnabrueck.de>
 */
@XmlRootElement
public class NearestGeoPop implements Serializable {

    public Double distance = 0.0;
    public Pob pob = null;

    public NearestGeoPop(Double dist, Pob p) {
        this.distance = dist;
        this.pob = p;
    }

    @XmlElement
    public Double getDistance() {
        return distance;
    }

    @XmlElement
    public Pob getPob() {
        return pob;
    }
}
