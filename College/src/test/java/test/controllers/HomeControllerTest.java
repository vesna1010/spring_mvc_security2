package test.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.Test;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

public class HomeControllerTest extends BaseControllerTest {

	@Test
	@WithAnonymousUser
	public void renderHomePageByAnonymousUserTest() throws Exception {
		mockMvc.perform(get("/"))
		       .andExpect(status().isOk())
		       .andExpect(view().name("home"));      
	}
	
	@Test
	@WithAnonymousUser
	public void renderLoginPageByAnonymousUserTest() throws Exception {
		mockMvc.perform(get("/login"))
	           .andExpect(status().isOk())
	           .andExpect(view().name("loginForm"));   
	}
	
	@Test
	@WithMockUser
	public void renderLoginPageByAuthenticatedUserTest() throws Exception {
		mockMvc.perform(get("/login"))
		       .andExpect(status().isForbidden())
	         .andExpect(forwardedUrl("/denied")); 
	} 
	
}
