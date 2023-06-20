package com.sergiomenendez.g.learningspringboot.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sergiomenendez.g.learningspringboot.model.User;
import com.sergiomenendez.g.learningspringboot.model.User.Gender;

import jakarta.ws.rs.GET;

@Repository
@ConditionalOnProperty(name = "data.source", havingValue = "mysql")
public class MSDataDao implements UserDao {

  private final String TABLE_NAME = "users";

  private JdbcTemplate jdbcTemplate;

  @Autowired
  public MSDataDao(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }

  private User mapToUser(ResultSet rs) throws SQLException {
    return new User(UUID.fromString(rs.getString("userUid")),
        rs.getString("firstName"),
        rs.getString("lastName"),
        Gender.valueOf(rs.getString("gender")),
        (Integer) rs.getInt("age"),
        rs.getString("email"));
  }

  @Override
  public List<User> selectAllUsers(Optional<Gender> gender) {
    return jdbcTemplate.query("select * from " + TABLE_NAME,
        (rs, rowNum) -> this.mapToUser(rs));
  }

  @Override
  public Optional<User> selectUserByUid(UUID userUid) {
    List<User> users = jdbcTemplate.query("select * from " + TABLE_NAME + " where userUid = ?",
        (rs, rowNum) -> this.mapToUser(rs), userUid.toString());
    return Optional.of(users.get(0));
  }

  @Override
  public int updateUser(User user) {
    String setStatement = "";
    if (user.getFirstName() != null && !user.getFirstName().isEmpty()) {
      setStatement += "firstName = " + user.getFirstName() + " AND";
    }
    if (user.getLastName() != null && !user.getLastName().isEmpty()) {
      setStatement += "lastName = " + user.getLastName() + " AND";
    }
    if (user.getGender() != null && user.getGender().name().isEmpty()) {
      setStatement += "gender = " + user.getGender() + " AND";
    }
    if (user.getAge() != null) {
      setStatement += "age = " + user.getAge() + " AND";
    }
    if (user.getEmail() != null && user.getEmail().isEmpty()) {
      setStatement += "email = " + user.getEmail() + " AND";
    }

    String updateStatement = setStatement.substring(0, setStatement.length() - 4);
    return jdbcTemplate.update("UPDATE " + TABLE_NAME + " SET " + updateStatement + " WHERE userUid = ?",
        user.getUserUid().toString());
  }

  @Override
  public int deleteUserByUid(UUID userUid) {
    return jdbcTemplate
        .update("DELETE FROM " + TABLE_NAME + " WHERE userUid = ?", userUid.toString());
  }

  @Override
  public int insertUser(UUID userUid, User user) {
    return jdbcTemplate.update("INSERT INTO " + TABLE_NAME +
        "(userUid, firstName, lastName, gender, age, email) VALUES (?,?,?,?,?,?)",
        userUid.toString(), user.getFirstName(), user.getLastName(), user.getGender().toString(), user.getAge(),
        user.getEmail());
  }

}
