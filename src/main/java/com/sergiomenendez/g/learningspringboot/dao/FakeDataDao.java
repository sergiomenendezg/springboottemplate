package com.sergiomenendez.g.learningspringboot.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.sergiomenendez.g.learningspringboot.model.User;
import com.sergiomenendez.g.learningspringboot.model.User.Gender;

@Repository
@ConditionalOnProperty(name = "data.source", havingValue = "variable")
public class FakeDataDao implements UserDao {

  private Map<UUID, User> database;

  public FakeDataDao() {
    database = new HashMap<>();
    UUID joeUserUid = UUID.randomUUID();
    database.put(
        joeUserUid,
        new User(joeUserUid, "Joe", "Jones", User.Gender.MALE, 22, "jjones@gmail.com"));
  }

  @Override
  public List<User> selectAllUsers(Optional<Gender> gender) {
    if (gender.isPresent()) {
      return new ArrayList<>(database.entrySet()
          .stream()
          .filter(ent -> ent.getValue().getGender().equals(gender.get()))
          .map(Map.Entry::getValue)
          .collect(Collectors.toList()));
    }
    return new ArrayList<>(database.values());
  }

  @Override
  public Optional<User> selectUserByUid(UUID userUid) {
    return Optional.ofNullable(database.get(userUid));
  }

  @Override
  public int updateUser(User user) {
    database.put(user.getUserUid(), user);
    return 1;
  }

  @Override
  public int deleteUserByUid(UUID userUid) {
    database.remove(userUid);
    return 1;
  }

  @Override
  public int insertUser(UUID userUid, User user) {
    database.put(userUid, user);
    return 1;
  }

}
