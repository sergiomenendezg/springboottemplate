package com.sergiomenendez.g.learningspringboot.service;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sergiomenendez.g.learningspringboot.dao.UserDao;
import com.sergiomenendez.g.learningspringboot.model.User;
import com.sergiomenendez.g.learningspringboot.model.User.Gender;

import jakarta.ws.rs.NotFoundException;

@Service
public class UserService {

  private UserDao userDao;

  @Autowired
  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public List<User> getAllUsers(String gender) {
    if (gender != null && gender != "") {
      Gender g = Gender.valueOf(gender);
      Optional<Gender> value = Optional.of(g);
      return userDao.selectAllUsers(value);
    }
    return userDao.selectAllUsers(Optional.empty());
  }

  public Optional<User> getUser(UUID userUid) {
    Optional<User> user = userDao.selectUserByUid(userUid);
    return user;
  }

  public int updateUser(User user) {
    Optional<User> optionalUser = userDao.selectUserByUid(user.getUserUid());
    if (optionalUser.isPresent())
      return userDao.updateUser(user);
    throw new NotFoundException("User " + user.getUserUid() + " not found");
  }

  public int removeUser(UUID userUid) throws NotFoundException {
    UUID uuid = userDao.selectUserByUid(userUid)
        .map(User::getUserUid)
        .orElseThrow(() -> new NotFoundException("User " + userUid + " not found"));
    return userDao.deleteUserByUid(uuid);
  }

  private void validateUser(User user) {
    requireNonNull(user.getFirstName(), "First name required");
    requireNonNull(user.getLastName(), "Last name required");
    requireNonNull(user.getAge(), "Age required");
    requireNonNull(user.getEmail(), "Email required");
    // validate email
    requireNonNull(user.getGender(), "Gender required");
  }

  public int insertUser(User user) {
    UUID uid = user.getUserUid() == null ? UUID.randomUUID() : user.getUserUid();
    user.setUserUid(uid);
    return userDao.insertUser(uid, user);
  }
}
