package com.sergiomenendez.g.learningspringboot.proxy;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.sergiomenendez.g.learningspringboot.model.User;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public interface UserResourceV1 {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<User> fetchUsers();

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{userUid}")
  public Response fetchUser(@PathParam("userUid") UUID userUid);

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response insertNewUser(@RequestBody User user);

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("{userUid}")
  public Response updateUser(@PathParam("userUid") UUID userUid,
      @RequestBody User user);

  @DELETE
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("{userUid}")
  public Response deleteUSer(@PathVariable("userUid") UUID userUid);
}
