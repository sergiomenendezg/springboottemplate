package com.sergiomenendez.g.learningspringboot.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sergiomenendez.g.learningspringboot.model.User;
import com.sergiomenendez.g.learningspringboot.service.UserService;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(method = RequestMethod.GET)
  public List<User> fetchUsers() {
    return userService.getAllUsers();
  }

  @RequestMapping(method = RequestMethod.GET, path = "{userUid}")
  public ResponseEntity<?> fetchUser(@PathVariable("userUid") UUID userUid) {
    Optional<User> user = userService.getUser(userUid);
    if (user.isPresent()) {
      return ResponseEntity.ok(user.get());
    }
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ErrorMessage("The user " + userUid + "was not found"));
  }

  @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Integer> insertNewUser(@RequestBody User user) {
    int result = userService.insertUser(user);
    if (result == 1) {
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.badRequest().build();
  }

  @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, path = "{userUid}")
  public ResponseEntity<Integer> updateUser(@PathVariable("userUid") UUID userUid,
      @RequestBody User user) {
    user.setUserUid(userUid);
    int result = userService.updateUser(user);
    if (result == 1) {
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.badRequest().build();
  }

  @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, path = "{userUid}")
  public ResponseEntity<Integer> deleteUSer(@PathVariable("userUid") UUID userUid) {
    int result = userService.removeUser(userUid);
    if (result == 1) {
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.badRequest().build();
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
