package com.sergiomenendez.g.learningspringboot.it;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.sergiomenendez.g.learningspringboot.model.User;
import com.sergiomenendez.g.learningspringboot.proxy.UserResourceV1;

import jakarta.ws.rs.NotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, properties = "../../../../../../resources/application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@PropertySource("classpath:application.properties")
public class UserIT {

	@Autowired
	private UserResourceV1 userResourceV1;

	@Order(1)
	@Test
	void itShouldFecthAllUsers() {
		List<User> users = userResourceV1.fetchUsers("");
		assertThat(users).hasSize(1);
		User joe = new User(null, "Joe", "Jones", User.Gender.MALE, 22, "jjones@gmail.com");
		assertThat(users.get(0))
				.usingRecursiveComparison()
				.ignoringFields("userUid")
				.isEqualTo(joe);
		assertThat(users.get(0)).isInstanceOf(User.class);
		assertThat(users.get(0)).isNotNull();
	}

	@Order(2)
	@Test
	public void shouldInsertUser() throws Exception {
		// GIVEN
		UUID userUuid = UUID.randomUUID();
		User user = new User(userUuid, "Joe", "Jones", User.Gender.MALE, 22, "jjones@gmail.com");
		// WHEN
		userResourceV1.insertNewUser(user);
		// THEN
		User joe = userResourceV1.fetchUser(userUuid);
		assertThat(joe)
				.usingRecursiveComparison()
				.isEqualTo(user);
	}

	@Order(3)
	@Test
	public void shouldDeleteUser() throws Exception {
		// GIVEN
		UUID userUuid = UUID.randomUUID();
		User user = new User(userUuid, "Joe", "Jones", User.Gender.MALE, 22, "jjones@gmail.com");
		// WHEN
		userResourceV1.insertNewUser(user);
		// THEN
		User joe = userResourceV1.fetchUser(userUuid);
		assertThat(joe)
				.usingRecursiveComparison()
				.isEqualTo(user);

		// WHEN
		userResourceV1.deleteUser(userUuid);
		// THEN
		assertThatThrownBy(() -> userResourceV1.fetchUser(userUuid))
				.isInstanceOf(Exception.class);

	}

	@Order(4)
	@Test
	public void shouldUpdateUser() {
		// GIVEN
		UUID userUuid = UUID.randomUUID();
		User user = new User(userUuid, "Joe", "Jones", User.Gender.MALE, 22, "jjones@gmail.com");
		// WHEN
		userResourceV1.insertNewUser(user);

		User updatedUSer = new User(userUuid, "Alex", "Jones", User.Gender.MALE, 55, "alexjones@gmail.com");
		userResourceV1.updateUser(userUuid, updatedUSer);

		// THEN
		user = userResourceV1.fetchUser(userUuid);
		assertThat(user)
				.usingRecursiveComparison()
				.isEqualTo(updatedUSer);
	}

	@Order(5)
	@Test
	public void shouldFetchUsersByGender() {
		// GIVEN
		UUID userUuid = UUID.randomUUID();
		User user = new User(userUuid, "Joe", "Jones", User.Gender.MALE, 22, "jjones@gmail.com");
		// WHEN
		userResourceV1.insertNewUser(user);

		List<User> females = userResourceV1.fetchUsers("FEMALE");
		assertThat(females).extracting("userUid").doesNotContain(user.getUserUid());
		assertThat(females).extracting("firstName").doesNotContain(user.getFirstName());
		assertThat(females).extracting("lastName").doesNotContain(user.getLastName());
		assertThat(females).extracting("gender").doesNotContain(user.getGender());
		assertThat(females).extracting("age").doesNotContain(user.getAge());
		assertThat(females).extracting("email").doesNotContain(user.getEmail());

		List<User> males = userResourceV1.fetchUsers("MALE");
		assertThat(males).extracting("userUid").contains(user.getUserUid());
		assertThat(males).extracting("firstName").contains(user.getFirstName());
		assertThat(males).extracting("lastName").contains(user.getLastName());
		assertThat(males).extracting("gender").contains(user.getGender());
		assertThat(males).extracting("age").contains(user.getAge());
		assertThat(males).extracting("email").contains(user.getEmail());
	}

}
