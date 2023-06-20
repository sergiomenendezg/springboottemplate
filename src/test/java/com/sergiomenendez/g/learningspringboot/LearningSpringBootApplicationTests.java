package com.sergiomenendez.g.learningspringboot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.sergiomenendez.g.learningspringboot.model.User;
import com.sergiomenendez.g.learningspringboot.model.User.Gender;
import com.sergiomenendez.g.learningspringboot.proxy.UserResourceV1;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)

class LearningSpringBootApplicationTests {

	@Autowired
	private UserResourceV1 userResourceV1;

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
		assertThatThrownBy(() -> {
			userResourceV1.fetchUser(userUuid);
		})
				.isInstanceOf(NotFoundException.class);

	}

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

	}

}
