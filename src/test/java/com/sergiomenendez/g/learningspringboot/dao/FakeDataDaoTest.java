package com.sergiomenendez.g.learningspringboot.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.protobuf.Option;
import com.sergiomenendez.g.learningspringboot.model.User;
import com.sergiomenendez.g.learningspringboot.model.User.Gender;

public class FakeDataDaoTest {

  private FakeDataDao fakeDataDao;

  @BeforeEach
  public void setUp() throws Exception {
    fakeDataDao = new FakeDataDao();
  }

  @Test
  void shouldSelectAllUsers() {
    List<User> users = fakeDataDao.selectAllUsers(Optional.empty());
    assertThat(users).hasSize(1);
    User user = users.get(0);
    assertThat(user.getAge()).isEqualTo(22);
    assertThat(user.getFirstName()).isEqualTo("Joe");
    assertThat(user.getLastName()).isEqualTo("Jones");
    assertThat(user.getEmail()).isEqualTo("jjones@gmail.com");
    assertThat(user.getGender()).isEqualTo(Gender.MALE);
    assertThat(user.getUserUid()).isNotNull();
  }

  @Test
  void shouldSelectUserByUid() {
    UUID randomUUID = UUID.randomUUID();
    User user = new User(randomUUID,
        "Anna", "Montana", Gender.FEMALE, 23, "amontana@gmail.com");
    fakeDataDao.insertUser(randomUUID, user);
    assertThat(fakeDataDao.selectAllUsers(Optional.empty())).hasSize(2);

    Optional<User> retrievedUser = fakeDataDao.selectUserByUid(randomUUID);
    assertThat(retrievedUser.isPresent()).isTrue();
    assertThat(retrievedUser.get()).isEqualToComparingFieldByField(user);
  }

  @Test
  void shouldNotSelectUserByRandomUid() {
    Optional<User> user = fakeDataDao.selectUserByUid(UUID.randomUUID());
    assertThat(user.isPresent()).isFalse();
  }

  @Test
  void shouldUpdateUser() {
    UUID joeUid = fakeDataDao.selectAllUsers(Optional.empty()).get(0).getUserUid();
    User user = new User(joeUid,
        "Anna", "Montana", Gender.FEMALE, 23, "amontana@gmail.com");
    fakeDataDao.updateUser(user);
    Optional<User> newUser = fakeDataDao.selectUserByUid(joeUid);
    assertThat(newUser.isPresent()).isTrue();
    assertThat(fakeDataDao.selectAllUsers(Optional.empty())).hasSize(1);
    assertThat(newUser.get()).isEqualToComparingFieldByField(user);

  }

  @Test
  void shouldDeleteUserByUid() {
    UUID joeUid = fakeDataDao.selectAllUsers(Optional.empty()).get(0).getUserUid();
    fakeDataDao.deleteUserByUid(joeUid);
    assertThat(fakeDataDao.selectAllUsers(Optional.empty())).hasSize(0);
    Optional<User> retrievedUser = fakeDataDao.selectUserByUid(joeUid);
    assertThat(retrievedUser.isPresent()).isFalse();
  }

  @Test
  void testInsertUser() {
    UUID randomUUID = UUID.randomUUID();
    User user = new User(
        randomUUID,
        "Anna", "Montana", Gender.FEMALE, 23, "amontana@gmail.com");

    fakeDataDao.insertUser(randomUUID, user);

    assertThat(fakeDataDao.selectAllUsers(Optional.empty())).hasSize(2);
    Optional<User> retrievedUser = fakeDataDao.selectUserByUid(randomUUID);
    assertThat(retrievedUser.isPresent()).isTrue();
    assertThat(retrievedUser.get()).isEqualToComparingFieldByField(user);
  }
}
