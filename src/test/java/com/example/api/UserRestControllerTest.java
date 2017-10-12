package com.example.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.Application;
import com.example.domain.User;
import com.example.domain.UserJson;
import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(Enclosed.class)
public class UserRestControllerTest {

	@RunWith(SpringRunner.class)
	@SpringBootTest
	@AutoConfigureMockMvc
	public static class NGCase {
		
		/**
		 * ConfigをオーバライドしスタブのUserServiceがBeanに登録されるようにする.
		 * @see <a href="https://stackoverflow.com/questions/28605833/overriding-an-autowired-bean-in-unit-tests">Stack overflow</a>
		 * 
		 * @author taijimiyabe
		 */
		@Configuration
		@Import(Application.class)
		static class Config {
			@Bean
			public UserService userService() {
				return new UserService() {
					@Override
					public Optional<User> findByFistName(String firstName) {
						return Optional.of(new User());
					}
				};
			}
		}

		@Autowired
		private MockMvc mockMvc;

		@Test
		public void test() throws Exception {
			UserJson userJson = new UserJson();
			userJson.setFirstName("du");
			userJson.setLastName("dada");
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userJson);
			mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(jsonStr.getBytes()))
					.andDo(print()).andExpect(status().isBadRequest());
		}
	}

	@RunWith(SpringRunner.class)
	@SpringBootTest
	@AutoConfigureMockMvc
	public static class OKCase {
		@Configuration
		@Import(Application.class)
		static class Config {
			@Bean
			public UserService userService() {
				return new UserService() {
					@Override
					public Optional<User> findByFistName(String firstName) {
						return Optional.empty();
					}
				};
			}
		}

		@Autowired
		private MockMvc mockMvc;

		@Test
		public void test() throws Exception {
			UserJson userJson = new UserJson();
			userJson.setFirstName("du");
			userJson.setLastName("dada");
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userJson);
			mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(jsonStr.getBytes()))
					.andDo(print()).andExpect(status().is2xxSuccessful());
		}
	}
}
