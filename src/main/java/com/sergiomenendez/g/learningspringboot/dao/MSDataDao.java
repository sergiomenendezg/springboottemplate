package com.sergiomenendez.g.learningspringboot.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.ResultSet;
import com.sergiomenendez.g.learningspringboot.model.User;
import com.sergiomenendez.g.learningspringboot.model.User.Gender;

@Repository
public class MSDataDao implements UserDao {

  private JdbcTemplate jdbcTemplate;

  @Autowired
  public MSDataDao(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public List<User> selectAllUsers() {
    return jdbcTemplate.query("select * from users",
        (rs, rowNum) -> new User(UUID.fromString(rs.getString("userUid")),
            rs.getString("firstName"),
            rs.getString("lastName"),
            Gender.valueOf(rs.getString("gender")),
            (Integer) rs.getInt("age"),
            rs.getString("email")));
  }

  @Override
  public Optional<User> selectUserByUid(UUID userUid) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'selectUserByUid'");
  }

  @Override
  public int updateUser(User user) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
  }

  @Override
  public int deleteUserByUid(UUID userUid) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteUserByUid'");
  }

  @Override
  public int insertUser(UUID userUid, User user) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertUser'");
  }

}
