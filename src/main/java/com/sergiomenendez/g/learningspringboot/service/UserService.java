package com.sergiomenendez.g.learningspringboot.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sergiomenendez.g.learningspringboot.dao.UserDao;
import com.sergiomenendez.g.learningspringboot.model.User;

@Service
public class UserService {

  private UserDao userDao;

  @Autowired
  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public List<User> getAllUsers(Optional<String> gender) {
    return userDao.selectAllUsers();
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
    UUID uid = UUID.randomUUID();
    user.setUserUid(uid);
    return userDao.insertUser(uid, user);
  }
}
