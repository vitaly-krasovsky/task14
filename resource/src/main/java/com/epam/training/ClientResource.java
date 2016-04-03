package com.epam.training;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * @author vkrasovsky
 */
@Path("clients")
@Singleton//TODO: One instance for all queries
public class ClientResource {
    private ClientService clientService = new ClientServiceImpl();

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(@QueryParam("format") String format, Client client) {
        return Response.ok(clientService.create(client), processFormat(format)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") int id, @QueryParam("format") String format, Client client) {
        client.setId(id);
        return Response.ok(clientService.update(client), processFormat(format)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") int id, @QueryParam("format") String format) {
        clientService.delete(id);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response get(@PathParam("id") int id, @QueryParam("format") String format) {
        return Response.ok(clientService.get(id)).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAll(@QueryParam("format") String format) {
        //TODO: for xml add @XmlRootElement, default constructor, generic entity
        return Response.ok(new GenericEntity<Collection<Client>>(clientService.getAll()) {
        }, processFormat(format)).build();
    }

    private String processFormat(String format) {
        return "xml".equals(format) ? MediaType.APPLICATION_XML : MediaType.APPLICATION_JSON;
    }
}