package com.sergiomenendez.g.learningspringboot.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import com.sergiomenendez.g.learningspringboot.model.User;
import com.sergiomenendez.g.learningspringboot.service.UserService;

@ConditionalOnProperty(name = "data.controller", havingValue = "resteasy")
@Path("api/v1/users")
public class UserControllerRS {

  private UserService userService;

  @Autowired
  public UserControllerRS(UserService userService) {
    System.out.println("UserControllerRS");
    this.userService = userService;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<User> fetchUsers() {
    return userService.getAllUsers(null);
  }

}
