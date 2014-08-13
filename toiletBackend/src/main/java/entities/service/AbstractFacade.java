package entities.service;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import de.hs.os.toiletbackend.NearestGeoPop;
import entities.Comment;
import entities.Pob;
import entities.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.EJBException;
import javax.persistence.EntityManager;

/**
 *
 * @author Michael Schroeder <michael.schroeder.1@hs-osnabrueck.de>
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public Integer createPobAndGetId(Pob entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
        return entity.getPobId();
    }

    public void addCommentToPob(Comment c, Integer pobId) throws Exception {
        //schauen ob passendes pob gefunden wird        
        Pob p = getEntityManager().find(Pob.class, pobId);

        if (p == null) {
            throw new EJBException("noPob");
        }

        User user = getEntityManager().find(User.class, c.getUser().getUserId());

        if (user != null) {
            getEntityManager().merge(c.getUser());
        } else {
            getEntityManager().persist(c.getUser());
        }

        //comment persisten
        getEntityManager().persist(c);

        //dann zur Pob-commentliste hinzufuegen
        p.getCommentCollection().add(c);
        System.out.println("Kommentar hinzugefuegt von User: " + c.getUser().getUserId());

        getEntityManager().persist(p);
    }

    public Collection<Comment> getCommentsToPob(Integer pobId) throws Exception {
        Pob p = getEntityManager().find(Pob.class, pobId);
        if (p == null) {
            throw new EJBException("noPob");
        }
        return p.getCommentCollection();
    }

    public Double setAndGetNewRating(Integer pobId, Float rating) throws Exception {
        Pob p = getEntityManager().find(Pob.class, pobId);
        if (p == null) {
            throw new Exception("noPob");
        }

        //berechne neues Rating
        Integer rateCount = p.getRateCount();
        Double sumRating = p.getRatingSum();

        rateCount++;

        System.out.println("setNewRating: rechne " + sumRating + " + " + rating + " / " + rateCount);
        Double newRating = (sumRating + rating) / rateCount;

        p.setRatingSum(sumRating + rating);
        p.setRating(newRating);
        p.setRateCount(rateCount);

        getEntityManager().merge(p);

        return newRating;
    }

    public List<NearestGeoPop> nearestPob(Double fromLat, Double fromLng, Double maxKm) {
        TreeMap<Double, Pob> sortedList = new TreeMap<>();

        LatLng fromPoint = new LatLng(fromLat, fromLng);

        //hole alle Pobs
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        List<Pob> listPob = getEntityManager().createQuery(cq).getResultList();

        //vergleiche alle distances
        for (Pob p : listPob) {
            LatLng tmpPoint = new LatLng(p.getLat(), p.getLnt());
            Double distance = LatLngTool.distance(fromPoint, tmpPoint, LengthUnit.KILOMETER);
            if (distance <= maxKm) {
                sortedList.put(distance, p);
            }
        }

        List<NearestGeoPop> geoList = new ArrayList<>();

        for (Map.Entry<Double, Pob> entry : sortedList.entrySet()) {
            Double key = entry.getKey();
            Pob value = entry.getValue();
            geoList.add(new NearestGeoPop(key, value));
        }

        return geoList;
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
