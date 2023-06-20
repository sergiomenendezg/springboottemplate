package com.sergiomenendez.g.learningspringboot.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.protobuf.Option;
import com.sergiomenendez.g.learningspringboot.dao.UserDao;
import com.sergiomenendez.g.learningspringboot.model.User;
import com.sergiomenendez.g.learningspringboot.model.User.Gender;

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
    return userDao.selectUserByUid(userUid);
  }

  public int updateUser(User user) {
    Optional<User> optionalUser = userDao.selectUserByUid(user.getUserUid());
    if (optionalUser.isPresent())
      return userDao.updateUser(user);
    return -1;
  }

  public int removeUser(UUID userUid) {
    Optional<User> optionalUser = userDao.selectUserByUid(userUid);
    if (optionalUser.isPresent())
      return userDao.deleteUserByUid(userUid);
    return -1;
  }

  public int insertUser(User user) {
    UUID uid = user.getUserUid() == null ? UUID.randomUUID() : user.getUserUid();
    user.setUserUid(uid);
    return userDao.insertUser(uid, user);
  }
}
