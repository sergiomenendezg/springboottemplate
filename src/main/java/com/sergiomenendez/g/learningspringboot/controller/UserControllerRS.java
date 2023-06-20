package com.sergiomenendez.g.learningspringboot.controller;

import java.util.List;
import java.util.Map;
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
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sergiomenendez.g.learningspringboot.model.User;
import com.sergiomenendez.g.learningspringboot.model.User.Gender;
import com.sergiomenendez.g.learningspringboot.service.UserService;

@RestController
@ConditionalOnProperty(name = "data.controller", havingValue = "reasteasy")
@Path("/api/v1/users")
public class UserControllerRS {

  @Autowired
  private UserService userService;

  public UserControllerRS(UserService userService) {
    this.userService = userService;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<User> fetchUsers(@DefaultValue("") @QueryParam("gender") String gender) {
    return userService.getAllUsers(gender);
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{userUid}")
  public Response fetchUser(@PathParam("userUid") UUID userUid) {
    Optional<User> user = userService.getUser(userUid);
    if (user.isPresent()) {
      return Response.ok(user.get()).build();
    }
    return Response
        .status(Status.NOT_FOUND)
        .entity(new ErrorMessage("The user " + userUid + "was not found"))
        .build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response insertNewUser(@RequestBody User user) {
    int result = userService.insertUser(user);
    if (result == 1) {
      return Response.ok().build();
    }
    return Response.status(Status.BAD_REQUEST).build();
  }

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("{userUid}")
  public Response updateUser(@PathParam("userUid") UUID userUid,
      @RequestBody User user) {
    user.setUserUid(userUid);
    int result = userService.updateUser(user);
    if (result == 1) {
      return Response.ok().build();
    }
    return Response.status(Status.BAD_REQUEST).build();
  }

  @DELETE
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("{userUid}")
  public Response deleteUser(@PathVariable("userUid") UUID userUid) {
    int result = userService.removeUser(userUid);
    if (result == 1) {
      return Response.ok().build();
    }
    return Response.status(Status.BAD_REQUEST).build();
  }

  class ErrorMessage {
    String message;

    public ErrorMessage(String message) {
      this.message = message;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    @Override
    public String toString() {
      return "ErrorMessage [message=" + message + "]";
    }

  }

}
