package com.sergiomenendez.g.learningspringboot.proxy;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.sergiomenendez.g.learningspringboot.model.User;
import com.sergiomenendez.g.learningspringboot.model.User.Gender;

public interface UserResourceV1 {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<User> fetchUsers(@DefaultValue("") @QueryParam("gender") String gender);

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{userUid}")
  public User fetchUser(@PathParam("userUid") UUID userUid);

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public void insertNewUser(@RequestBody User user);

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("{userUid}")
  public void updateUser(@PathParam("userUid") UUID userUid,
      @RequestBody User user);

  @DELETE
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("{userUid}")
  public void deleteUser(@PathParam("userUid") UUID userUid);
}
