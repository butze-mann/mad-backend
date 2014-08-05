/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.service;

import entities.Comment;
import java.util.Collection;
import java.util.List;
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
@Path("comment")
public class CommentREST extends AbstractFacade<Comment> {

    @PersistenceContext(unitName = "toiletPU")
    private EntityManager em;

    public CommentREST() {
        super(Comment.class);
    }

    @POST
    @Path("/pob/{pobId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Comment entity,
            @PathParam("pobId") Integer pobId,
            @DefaultValue("0000") @QueryParam("devId") String deviceId) {
        //TODO ueberpruefung ob erlaubt
        try {
            System.out.println("Comment geadded" + entity.toString() + "by User: " + deviceId);
            super.addCommentToPob(entity, pobId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @GET
    @Path("/pob/{pobId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Comment> getCommentForPob(@PathParam("pobId") Integer pobId) {
        try {
            return super.getCommentsToPob(pobId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    public Boolean hasDataFromDevice(String deviceId, Integer id) {
        Comment c = super.find(id);
        return true;
    }

    /**
     * @PUT @Path("{id}")
     * @Consumes({"application/xml", "application/json"}) public void
     * edit(@PathParam("id") Integer id, Comment entity) { super.edit(entity); }
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id
    ) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Comment find(@PathParam("id") Integer id
    ) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Comment> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Comment> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to
    ) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
