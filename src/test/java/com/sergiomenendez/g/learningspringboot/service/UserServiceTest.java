package com.sergiomenendez.g.learningspringboot.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sergiomenendez.g.learningspringboot.dao.FakeDataDao;
import com.sergiomenendez.g.learningspringboot.model.User;
import com.sergiomenendez.g.learningspringboot.model.User.Gender;

public class UserServiceTest {

  @Mock
  private FakeDataDao fakeDataDao;

  private UserService userService;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
    userService = new UserService(fakeDataDao);
  }

  @Test
  void shouldGetAllUsers() {
    UUID annaUid = UUID.randomUUID();
    User user = new User(annaUid,
        "Anna", "Montana", Gender.FEMALE, 23, "amontana@gmail.com");

    ArrayList<User> users = new ArrayList<>();
    users.add(user);
    when(fakeDataDao.selectAllUsers(Optional.empty())).thenReturn(users);
    List<User> allUsers = userService.getAllUsers("");
    assertThat(allUsers).hasSize(1);

    User newUser = allUsers.get(0);
    assertUserField(newUser);

  }

  private void assertUserField(User newUser) {
    assertThat(newUser.getAge()).isEqualTo(23);
    assertThat(newUser.getFirstName()).isEqualTo("Anna");
    assertThat(newUser.getLastName()).isEqualTo("Montana");
    assertThat(newUser.getEmail()).isEqualTo("amontana@gmail.com");
    assertThat(newUser.getGender()).isEqualTo(Gender.FEMALE);
    assertThat(newUser.getUserUid()).isInstanceOf(UUID.class);
  }

  @Test
  void shouldGetUser() {
    UUID annaUid = UUID.randomUUID();
    User user = new User(annaUid,
        "Anna", "Montana", Gender.FEMALE, 23, "amontana@gmail.com");
    when(fakeDataDao.selectUserByUid(annaUid)).thenReturn(Optional.of(user));

    Optional<User> user2 = userService.getUser(annaUid);

    assertThat(user2.isPresent()).isTrue();
    User retrieveUser = user2.get();
    assertUserField(retrieveUser);
  }

  @Test
  void shouldUpdateUser() {
    UUID annaUid = UUID.randomUUID();
    User user = new User(annaUid,
        "Anna", "Montana", Gender.FEMALE, 23, "amontana@gmail.com");
    when(fakeDataDao.selectUserByUid(annaUid)).thenReturn(Optional.of(user));
    when(fakeDataDao.updateUser(user)).thenReturn(1);

    int updateResult = userService.updateUser(user);

    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

    verify(fakeDataDao).selectUserByUid(annaUid);
    verify(fakeDataDao).updateUser(captor.capture());

    User capturedUser = captor.getValue();
    assertThat(updateResult).isEqualTo(1);
    assertUserField(capturedUser);
  }

  @Test
  void shouldInsertUser() {
    User user = new User(null,
        "Anna", "Montana", Gender.FEMALE, 23, "amontana@gmail.com");

    when(fakeDataDao.insertUser(any(UUID.class), eq(user))).thenReturn(1);

    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

    int insertResult = userService.insertUser(user);

    verify(fakeDataDao).insertUser(any(UUID.class), captor.capture());

    User capturedUser = captor.getValue();

    assertUserField(capturedUser);
    assertThat(insertResult).isEqualTo(1);

  }

  @Test
  void shouldRemoveUser() {
    UUID annaUid = UUID.randomUUID();
    User user = new User(annaUid,
        "Anna", "Montana", Gender.FEMALE, 23, "amontana@gmail.com");
    when(fakeDataDao.selectUserByUid(annaUid)).thenReturn(Optional.of(user));
    when(fakeDataDao.deleteUserByUid(annaUid)).thenReturn(1);

    int deleteResult = userService.removeUser(annaUid);

    verify(fakeDataDao).selectUserByUid(annaUid);
    verify(fakeDataDao).deleteUserByUid(annaUid);

    assertThat(deleteResult).isEqualTo(1);
  }

}
