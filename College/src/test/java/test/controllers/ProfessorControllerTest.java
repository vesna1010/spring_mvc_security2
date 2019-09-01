package test.controllers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.CoreMatchers.is;
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
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import college.controllers.ProfessorController;
import college.converters.ProfessorConverter;
import college.converters.StudyProgramConverter;
import college.enums.Gender;
import college.model.Address;
import college.model.Professor;
import college.model.StudyProgram;
import college.service.ProfessorService;
import college.service.StudyProgramService;

public class ProfessorControllerTest extends BaseControllerTest {

	@Mock
	private ProfessorService professorService;
	@InjectMocks
	@Autowired
	private ProfessorController professorController;
	@InjectMocks
	@Autowired
	ProfessorConverter professorConverter;
	@Mock
	private StudyProgramService studyProgramService;
	@InjectMocks
	@Autowired
	private StudyProgramConverter studyProgramConverter;

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderProfessorsPageWithAllProfessorsWithRoleAdminTest() throws Exception {
		renderProfessorsPageWithAllProfessors();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderProfessorsPageWithAllProfessorsWithRoleUserTest() throws Exception {
		renderProfessorsPageWithAllProfessors();
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderProfessorsPageWithAllProfessorsWithRoleProfessorTest() throws Exception {
		renderProfessorsPageWithAllProfessors();
	}
	
	private void renderProfessorsPageWithAllProfessors() throws Exception {
		when(professorService.findAllProfessors())
				.thenReturn(Arrays.asList(new Professor(1L, "Professor A"), new Professor(2L, "Professor B")));

		mockMvc.perform(get("/professors"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("professors", hasSize(2)))
		       .andExpect(view().name("professors/page"));
		
		verify(professorService, times(1)).findAllProfessors();
	}
	
	@Test
	@WithAnonymousUser
	public void renderProfessorsPageWithAllProfessorsWithAnonymousUserTest() throws Exception {
		renderProfessorsPageWithAllProfessorsNotAuthenticated();
	}
	
	private void renderProfessorsPageWithAllProfessorsNotAuthenticated() throws Exception {
		mockMvc.perform(get("/professors"))
	           .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderProfessorsPageWithProfessorsByStudyProgramWithRoleAdminTest() throws Exception {
		renderProfessorsPageWithProfessorsByStudyProgram();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderProfessorsPageWithProfessorsByStudyProgramWithRoleUserTest() throws Exception {
		renderProfessorsPageWithProfessorsByStudyProgram();
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderProfessorsPageWithProfessorsByStudyProgramWithRoleProfessorTest() throws Exception {
		renderProfessorsPageWithProfessorsByStudyProgram();
	}
	
	private void renderProfessorsPageWithProfessorsByStudyProgram() throws Exception {
		StudyProgram studyProgram = new StudyProgram(1L, "Study Program");
		
		when(studyProgramService.findStudyProgramById(1L)).thenReturn(studyProgram);
		when(professorService.findAllProfessorsByStudyProgram(studyProgram))
				.thenReturn(Arrays.asList(new Professor(1L, "Professor A"), new Professor(2L, "Professor B")));
	
		mockMvc.perform(
				get("/professors")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("professors", hasSize(2)))
		       .andExpect(view().name("professors/page"));
		
		verify(studyProgramService, times(1)).findStudyProgramById(1L);
		verify(professorService, times(1)).findAllProfessorsByStudyProgram(studyProgram);
	}
	
	@Test
	@WithAnonymousUser
	public void renderProfessorsPageWithProfessorsByStudyProgramWithAnonymousUserTest() throws Exception {
		renderProfessorsPageWithProfessorsByStudyProgramNotAuthenticated();
	}
	
	private void renderProfessorsPageWithProfessorsByStudyProgramNotAuthenticated() throws Exception {
		mockMvc.perform(
				get("/professors")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderEmptyProfessorFormWithRoleAdminTest() throws Exception {
		renderEmptyProfessorForm();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderEmptyProfessorFormWithRoleUserTest() throws Exception {
		renderEmptyProfessorForm();
	}
	
	public void renderEmptyProfessorForm() throws Exception {
		mockMvc.perform(get("/professors/form"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("professor", is(new Professor())))
		       .andExpect(view().name("professors/form"));
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderEmptyProfessorFormWithRoleProfessorTest() throws Exception {
		renderEmptyProfessorFormAccessDenied();
	}
	
	public void renderEmptyProfessorFormAccessDenied() throws Exception {
		mockMvc.perform(get("/professors/form"))
	           .andExpect(status().isForbidden())
               .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithAnonymousUser
	public void renderEmptyProfessorFormWithAnonymousUserTest() throws Exception {
		renderEmptyProfessorFormNotAuthenticated();
	}
	
	public void renderEmptyProfessorFormNotAuthenticated() throws Exception {
		mockMvc.perform(get("/professors/form"))
	           .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void saveProfessorAndRenderProfessorFormValidFormTest() throws Exception {
		saveProfessorAndRenderProfessorFormValidForm();
	}
	
	private void saveProfessorAndRenderProfessorFormValidForm() throws Exception {
		Professor professor = new Professor(1L, "First Professor", "Father Name",
				new GregorianCalendar(1985, Calendar.APRIL, 2).getTime(), "professor@gmail.com", "065-333-212", Gender.MALE,
				new Address("City", "74000", "Street", "State"),
				new GregorianCalendar(2017, Calendar.AUGUST, 1).getTime(), "Title");

		doNothing().when(professorService).saveOrUpdateProfessor(professor);
		
		mockMvc.perform(
				post("/professors/save")
				.param("id", "1")
				.param("fullName", "First Professor")
				.param("fatherName", "Father Name")
				.param("dateOfBirth", "02-04-1985")
				.param("email", "professor@gmail.com")
				.param("telephon", "065-333-212")
				.param("gender", "MALE")
				.param("image", "")
				.param("address.city", "City")
				.param("address.state", "State")
				.param("address.street", "Street")
				.param("address.zipCode", "74000")
				.param("dateOfEmployment", "01-08-2017" )
				.param("titleOfProfessor", "Title")
				.with(csrf())
				)
		       .andExpect(model().hasNoErrors())
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/professors/form"));
		
		verify(professorService, times(1)).saveOrUpdateProfessor(professor);
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void saveProfessorAndRenderProfessorFormInvalidFormTest() throws Exception {
		saveProfessorAndRenderProfessorFormInvalidForm();
	}
	
	private void saveProfessorAndRenderProfessorFormInvalidForm() throws Exception {
		Professor professor = new Professor(1L, "First Professor", "Father Name",
				new GregorianCalendar(1995, Calendar.MAY, 5).getTime(), "invalidEmail", "065-333-212", 
				Gender.MALE, new Address("City", "74000", "Street", "State"), 
				new GregorianCalendar(2017, Calendar.AUGUST, 1).getTime(), "Title");
			
		doNothing().when(professorService).saveOrUpdateProfessor(professor);
		
		mockMvc.perform(
				post("/professors/save")
				.param("id", "1")
				.param("fullName", "First Professor")
				.param("fatherName", "Father Name")
				.param("dateOfBirth", "05-05-1995")
				.param("email", "invalidEmail")
				.param("telephon", "065-333-212")
				.param("gender", "MALE")
				.param("image", "")
				.param("address.city", "City")
				.param("address.state", "State")
				.param("address.street", "Street")
				.param("address.zipCode", "74000")
				.param("dateOfEmployment", "01-08-2017")
				.param("titleOfProfessor", "Title")
				.with(csrf())
				)
		       .andExpect(model().attributeHasFieldErrors("professor", "email"))
		       .andExpect(model().attribute("professor", is(professor)))
		       .andExpect(view().name("professors/form"));
		
		verify(professorService, times(0)).saveOrUpdateProfessor(professor);
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderFormWithProfessorWithAdminTest() throws Exception {
		renderFormWithProfessor();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderFormWithProfessorWithUserTest() throws Exception {
		renderFormWithProfessor();
	}
	
	private void renderFormWithProfessor() throws Exception {
		Professor professor = new Professor(1L, "Professor");
		
		when(professorService.findProfessorById(1L)).thenReturn(professor);
		
		mockMvc.perform(
				get("/professors/edit")
				.param("professorId", "1")
				)
	           .andExpect(status().isOk())
	           .andExpect(model().attribute("professor", is(professor)))
	           .andExpect(view().name("professors/form"));
		
		verify(professorService, times(1)).findProfessorById(1L);	
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderFormWithProfessorWithRoleProfessorTest() throws Exception {
		renderFormWithProfessorAccessDenied();
	}
	
	private void renderFormWithProfessorAccessDenied() throws Exception {
		mockMvc.perform(
				get("/professors/edit")
				.param("professorId", "1")
				)
		       .andExpect(status().isForbidden())
               .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithAnonymousUser
	public void renderFormWithProfessorByAnonymousUserTest() throws Exception {
		renderFormWithProfessorNotAuthenticated();
	}
	
	private void renderFormWithProfessorNotAuthenticated() throws Exception {
		mockMvc.perform(
				get("/professors/edit")
				.param("professorId", "1")
				)
		       .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void deleteProfessorAndRenderProfessorsPageWithRoleAdminTest() throws Exception {
		deleteProfessorAndRenderProfessorsPage();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void deleteProfessorAndRenderProfessorsPageWithRoleUserTest() throws Exception {
		deleteProfessorAndRenderProfessorsPage();
	}
	
	private void deleteProfessorAndRenderProfessorsPage() throws Exception {
		doNothing().when(professorService).deleteProfessorById(1L);
		
		mockMvc.perform(
				get("/professors/delete")
				.param("professorId", "1")
				)
	           .andExpect(status().is3xxRedirection())
	           .andExpect(redirectedUrl("/professors"));
		
		verify(professorService, times(1)).deleteProfessorById(1L);
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void deleteProfessorAndRenderProfessorsPageWithRoleProfessorTest() throws Exception {	
		deleteProfessorAndRenderProfessorsPageAccessDenied();
	}
	
	private void deleteProfessorAndRenderProfessorsPageAccessDenied() throws Exception {
		mockMvc.perform(
				get("/professors/delete")
				.param("professorId", "1")
				)
		       .andExpect(status().isForbidden())
               .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithAnonymousUser
	public void deleteProfessorAndRenderProfessorsPageByAnonymousUserTest() throws Exception {	
		deleteProfessorAndRenderProfessorsPageNotAuthenticated();
	}
	
	private void deleteProfessorAndRenderProfessorsPageNotAuthenticated() throws Exception {
		mockMvc.perform(
				get("/professors/delete")
				.param("professorId", "1")
				)
		       .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}
	
}
