package com.sergiomenendez.g.learningspringboot.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sergiomenendez.g.learningspringboot.model.User;
import com.sergiomenendez.g.learningspringboot.model.User.Gender;

public interface UserDao {
  List<User> selectAllUsers(Optional<Gender> gender);

  Optional<User> selectUserByUid(UUID userUid);

  int updateUser(User user);

  int deleteUserByUid(UUID userUid);

  int insertUser(UUID userUid, User user);

}
