package com.sergiomenendez.g.learningspringboot;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.sergiomenendez.g.learningspringboot.model.User;
import com.sergiomenendez.g.learningspringboot.proxy.UserResourceV1;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)

class LearningSpringBootApplicationTests {

	@Autowired
	private UserResourceV1 userResourceV1;

	@Test
	void itShouldFecthAllUsers() {
		List<User> users = userResourceV1.fetchUsers();
		assertThat(users).hasSize(1);
	}

}
