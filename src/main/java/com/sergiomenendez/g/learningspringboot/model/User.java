package com.sergiomenendez.g.learningspringboot.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

  public enum Gender {
    MALE, FEMALE
  }

  private UUID userUid;
  @NotNull
  private String firstName;
  @NotNull
  private String lastName;
  @NotNull
  private Gender gender;
  @NotNull
  @Max(value = 112)
  @Min(value = 0)
  private Integer age;
  @NotNull
  @Email
  private String email;

  public User(UUID userUid, String firstName, String lastName, Gender gender, Integer age, String email) {
    this.userUid = userUid;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.age = age;
    this.email = email;
  }

  public User() {
  }

  public UUID getUserUid() {
    return userUid;
  }

  public void setUserUid(UUID userUid) {
    this.userUid = userUid;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public Gender getGender() {
    return gender;
  }

  public Integer getAge() {
    return age;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String toString() {
    return "User [userUid=" + userUid + ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender
        + ", age=" + age + ", email=" + email + "]";
  }

}
