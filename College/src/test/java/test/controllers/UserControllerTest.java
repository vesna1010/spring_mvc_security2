package test.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.Arrays;
import java.util.HashSet;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import college.controllers.UserController;
import college.enums.Role;
import college.model.User;
import college.service.UserService;
import college.validation.UserValidator;

public class UserControllerTest extends BaseControllerTest {
	
	@Mock
	private UserService userService;
	@InjectMocks
	@Autowired
	private UserController userController;
	@InjectMocks
	@Autowired
	private UserValidator userValidator;

	@Test
	@WithMockUser(username = "USERNAME", password = "PASSWORD", roles = { "USER", "PROFESSOR" })
	public void renderUsersPageWithAllUsersWithRoleUserAndProfessorTest() throws Exception {
		renderUsersPageWithAllUsersAccessDenied();
	}
	
	private void renderUsersPageWithAllUsersAccessDenied() throws Exception {
		mockMvc.perform(get("/users"))
	           .andExpect(status().isForbidden())
	           .andExpect(forwardedUrl("/denied"));
	}

	@Test
	@WithMockUser(username = "USERNAME", password = "PASSWORD", roles = "ADMIN")
	public void renderUsersPageWithAllUsersWithRoleAdminTest() throws Exception {
		renderUsersPageWithAllUsers();
	}
	
	private void renderUsersPageWithAllUsers() throws Exception {
		when(userService.findAllUsers())
				.thenReturn(Arrays.asList(new User(1L, "userA@gmail.com"), new User(2L, "userB@gmail.com")));

		mockMvc.perform(get("/users"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("users", hasSize(2)))
		       .andExpect(view().name("users/page"));

		verify(userService, times(1)).findAllUsers();
	}
	
	@WithAnonymousUser
	public void renderUsersPageWithAllUsersWithAnonymousUserTest() throws Exception {
		renderUsersPageWithAllUsersNotAuthenticated();
	}
	
	private void renderUsersPageWithAllUsersNotAuthenticated() throws Exception {
		mockMvc.perform(get("/users"))
	           .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "USERNAME", password = "PASSWORD", roles = { "USER", "PROFESSOR" })
	public void renderEmptyUserFormWithRoleUserOrProfessorTest() throws Exception {
		renderEmptyUserFormAccessDenied();
	}
	
	private void renderEmptyUserFormAccessDenied() throws Exception {
		mockMvc.perform(get("/users/form"))
	           .andExpect(status().isForbidden())
	           .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithMockUser(username = "USERNAME", password = "PASSWORD", roles = "ADMIN")
	public void renderEmptyUserFormWithRoleAdminTest() throws Exception {
		renderEmptyUserForm();
	}
	
	private void renderEmptyUserForm() throws Exception {
		mockMvc.perform(get("/users/form"))
	           .andExpect(status().isOk())
	           .andExpect(model().attribute("user", is(new User())))
	           .andExpect(view().name("users/form"));
	}
	
	@Test
	@WithAnonymousUser
	public void renderEmptyUserFormWithAnonymousUserTest() throws Exception {
		renderEmptyUserFormNotAuthenticated();
	}
	
	private void renderEmptyUserFormNotAuthenticated() throws Exception {
		mockMvc.perform(get("/users/form"))
	           .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "USERNAME", password = "PASSWORD", roles = "ADMIN")
	public void saveUserAndRenderUserFormValidFormTest() throws Exception {
		saveUserAndRenderUserFormValidForm();
	}
	
	private void saveUserAndRenderUserFormValidForm() throws Exception {
		User user = new User(1L, "First Name", "user@gmail.com", "Password", "Password", true,
				new HashSet<Role>(Arrays.asList(Role.PROFESSOR)));

		when(userService.findUserByEmail("user@gmail.com")).thenReturn(null);
		doNothing().when(userService).saveOrUpdateUser(user);

		mockMvc.perform(
				post("/users/save")
				.param("id", "1")
				.param("name", "First Name")
				.param("email", "user@gmail.com")
				.param("password", "Password")
				.param("confirmPassword", "Password")
				.param("roles", "PROFESSOR")
		        .param("enabled", "true")
		        .with(csrf())
		        )
		       .andExpect(model().hasNoErrors())
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/users/form"));

		verify(userService, times(1)).findUserByEmail("user@gmail.com");
		verify(userService, times(1)).saveOrUpdateUser(user);
	}
	
	@Test
	@WithMockUser(username = "USERNAME", password = "PASSWORD", roles = "ADMIN")
	public void saveUserAndRenderUserFormInvalidFormTest() throws Exception {
		saveUserAndRenderUserFormInvalidForm();
	}
	
	private void saveUserAndRenderUserFormInvalidForm() throws Exception {
		User user = new User(1L, "First Name", "userA@gmail.com", "Password", "Password", true,
				new HashSet<Role>(Arrays.asList(Role.USER)));

		when(userService.findUserByEmail("userA@gmail.com")).thenReturn(user);
		doNothing().when(userService).saveOrUpdateUser(user);

		mockMvc.perform(
				post("/users/save")
				.param("id", "1")
				.param("name", "First Name")
				.param("email", "userA@gmail.com")
				.param("password", "Password")
				.param("confirmPassword", "Password")
				.param("roles", "USER")
				.param("enabled", "true")
				.with(csrf())
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attributeHasFieldErrors("user", "email"))
		       .andExpect(model().attribute("user", is(user)))
		       .andExpect(view().name("users/form"));

		verify(userService, times(1)).findUserByEmail("userA@gmail.com");
		verify(userService, times(0)).saveOrUpdateUser(user);
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void updateUserPasswordAndRenderPasswordFormValidFormTest() throws Exception {
		updateUserPasswordAndRenderPasswordFormValidForm();
	}
	
	private void updateUserPasswordAndRenderPasswordFormValidForm() throws Exception {
		User user = new User(1L, "First Name", "user@gmail.com", "Password", "Password", true,
				new HashSet<Role>(Arrays.asList(Role.PROFESSOR)));

		when(userService.findUserByEmail("user@gmail.com")).thenReturn(null);
		doNothing().when(userService).saveOrUpdateUser(user);

		mockMvc.perform(
				post("/users/update")
				.param("id", "1")
				.param("name", "First Name")
				.param("email", "user@gmail.com")
				.param("password", "Password")
				.param("confirmPassword", "Password")
				.param("roles", "PROFESSOR")
		        .param("enabled", "true")
		        .with(csrf())
		        )
		       .andExpect(model().hasNoErrors())
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("user", is(user)))
		       .andExpect(view().name("users/update/form"));

		verify(userService, times(1)).findUserByEmail("user@gmail.com");
		verify(userService, times(1)).saveOrUpdateUser(user);
	}
	
	@Test
	@WithMockUser(username = "USERNAME", password = "PASSWORD", roles = "ADMIN")
	public void updateUserPasswordAndRenderPasswordInvalidFormTest() throws Exception {
		updateUserPasswordAndRenderPasswordInvalidForm();
	}
	
	private void updateUserPasswordAndRenderPasswordInvalidForm() throws Exception {
		User user = new User(1L, "First Name", "userA@gmail.com", "Password", "Password", true,
				new HashSet<Role>(Arrays.asList(Role.USER)));

		when(userService.findUserByEmail("userA@gmail.com")).thenReturn(null);
		doNothing().when(userService).saveOrUpdateUser(user);

		mockMvc.perform(
				post("/users/update")
				.param("id", "1")
				.param("name", "First Name")
				.param("email", "userA@gmail.com")
				.param("password", "Password")
				.param("confirmPassword", "Password1")
				.param("roles", "USER")
				.param("enabled", "true")
				.with(csrf())
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attributeHasFieldErrors("user", "confirmPassword"))
		       .andExpect(model().attribute("user", is(user)))
		       .andExpect(view().name("users/update/form"));

		verify(userService, times(1)).findUserByEmail("userA@gmail.com");
		verify(userService, times(0)).saveOrUpdateUser(user);
	}
	
	
	@Test
	@WithMockUser(username = "USERNAME", password = "PASSWORD", roles = { "USER", "PROFESSOR" })
	public void deleteUserAndRenderUsersPageWithRoleUserOrPasswordTest() throws Exception {
		deleteUserAndRenderUsersPageAccessDenied();
	}
	
	private void deleteUserAndRenderUsersPageAccessDenied() throws Exception {
		mockMvc.perform(
				get("/users/delete")
				.param("userId", "1")
				)
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithMockUser(username = "USERNAME", password = "PASSWORD", roles = "ADMIN")
	public void deleteUserAndRenderUsersPageWithRoleAdminTest() throws Exception {
		deleteUserAndRenderUsersPage();
	}
	
	private void deleteUserAndRenderUsersPage() throws Exception {
		doNothing().when(userService).deleteUserById(1L);

		mockMvc.perform(
				get("/users/delete")
				.param("userId", "1")
				)
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/users"));

		verify(userService, times(1)).deleteUserById(1L);
	}
	
	@Test
	@WithAnonymousUser
	public void deleteUserAndRenderUsersPageByAnonymousUserTest() throws Exception {
		deleteUserAndRenderUsersPageNotAuthenticated();
	}
	
	private void deleteUserAndRenderUsersPageNotAuthenticated() throws Exception {
		mockMvc.perform(
				get("/users/delete")
				.param("userId", "1")
				)
		       .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "USERNAME", password = "PASSWORD", roles = { "USER", "PROFESSOR" })
	public void disableUserAndRenderUsersPageWithRoleUserOrPasswordTest() throws Exception {
		disableUserAndRenderUsersPageAccessDenied();
	}
	
	private void disableUserAndRenderUsersPageAccessDenied() throws Exception {
		mockMvc.perform(
				get("/users/disable")
				.param("userId", "1")
				)
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithMockUser(username = "USERNAME", password = "PASSWORD", roles = "ADMIN")
	public void disableUserAndRenderUsersPageWithRoleAdminTest() throws Exception {
		disableUserAndRenderUsersPage();
	}
	
	private void disableUserAndRenderUsersPage() throws Exception {
		doNothing().when(userService).disableUserById(1L);

		mockMvc.perform(
				get("/users/disable")
				.param("userId", "1")
				)
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/users"));

		verify(userService, times(1)).disableUserById(1L);
	}
	
	@Test
	@WithAnonymousUser
	public void disableUserAndRenderUsersPageWithAnonymousUserTest() throws Exception {
		disableUserAndRenderUsersPageNotAuthenticated();
	}
	
	private void disableUserAndRenderUsersPageNotAuthenticated() throws Exception {
		mockMvc.perform(
				get("/users/disable")
				.param("userId", "1")
				)
		       .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "userA@gmail.com", password = "Password", roles = "PROFESSOR")
	public void renderUserFormWithRoleProfessorTest() throws Exception {
		renderUserFormWithUser();
	}
	
	private void renderUserFormWithUser() throws Exception {
		User user = new User(1L, "userA@gmail.com");
		
		when(userService.findUserByEmail("userA@gmail.com")).thenReturn(user);
		
		mockMvc.perform(get("/users/edit"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("user", is(user)))
		       .andExpect(view().name("users/update/form"));
		
		verify(userService, times(1)).findUserByEmail("userA@gmail.com");
	}
	
	@Test
	@WithAnonymousUser
	public void renderUserFormWithAnonymousUserTest() throws Exception {
		renderUserFormWithUserNotAuthenticated();
	}
	
	private void renderUserFormWithUserNotAuthenticated() throws Exception {
		mockMvc.perform(get("/users/edit"))
	           .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}
	
}


