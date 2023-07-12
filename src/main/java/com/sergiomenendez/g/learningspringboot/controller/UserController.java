package com.sergiomenendez.g.learningspringboot.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sergiomenendez.g.learningspringboot.model.User;
import com.sergiomenendez.g.learningspringboot.service.UserService;

import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

@Validated
@RestController
@ConditionalOnProperty(name = "data.controller", havingValue = "springboot")
@RequestMapping(path = "/api/v1/users")
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(method = RequestMethod.GET)
  public List<User> fetchUsers(@RequestParam(required = false) String gender) {
    return userService.getAllUsers(gender);
  }

  @RequestMapping(method = RequestMethod.GET, path = "{userUid}")
  public User fetchUser(@PathVariable("userUid") UUID userUid) throws NotFoundException {
    User user = userService.getUser(userUid)
        .orElseThrow(() -> {
          return new NotFoundException("User " + userUid + " not found");
        });
    return user;
  }

  @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public void insertNewUser(@Valid @RequestBody User user) {
    userService.insertUser(user);
  }

  @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, path = "{userUid}")
  public void updateUser(@PathVariable("userUid") UUID userUid,
      @RequestBody User user) {
    user.setUserUid(userUid);
    userService.updateUser(user);

  }

  @RequestMapping(method = RequestMethod.DELETE, path = "{userUid}")
  public void deleteUser(@PathVariable("userUid") UUID userUid) throws NotFoundException {
    userService.removeUser(userUid);
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
