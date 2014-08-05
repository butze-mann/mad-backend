package entities.service;

import entities.Pob;
import java.util.List;
import java.util.TreeMap;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Michael Schroeder <michael.schroeder.1@hs-osnabrueck.de>
 */
@Stateless
@Path("pob")
public class PobREST extends AbstractFacade<Pob> {

    @PersistenceContext(unitName = "toiletPU")
    private EntityManager em;

    public PobREST() {
        super(Pob.class);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Pob find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("{id}/rate/{rating}")
    @Produces(MediaType.TEXT_PLAIN)
    public Double setRating(@PathParam("id") Integer id,
            @PathParam("rating") Integer rating,
            @DefaultValue("0000") @QueryParam("devId") String deviceId) {
        try {
            //todo nur einmal
            return super.setAndGetNewRating(id, rating);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @GET
    @Path("nearest/{lat}/{lnt}")
    @Produces(MediaType.TEXT_PLAIN)
    public TreeMap<Double, Pob> getNearest(
            @PathParam("lat") Double lat,
            @PathParam("lnt") Double lnt) {
        try {
            return super.nearestPob(lat, lnt, 500.0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pob> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pob> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @POST
    @Override
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Pob entity) {
        super.create(entity);
    }

    /**
     * *
     * wir brauchen kein update?
     *
     * @PUT
     * @Override
     * @Consumes("application/json") public void edit(Pob entity) {
     * super.edit(entity); }
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
