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
import java.util.HashSet;
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
	public void renderProfessorsPageWithAllProfessorsByAdminTest() throws Exception {
		renderProfessorsPageWithAllProfessors();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderProfessorsPageWithAllProfessorsByUserTest() throws Exception {
		renderProfessorsPageWithAllProfessors();
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderProfessorsPageWithAllProfessorsByProfessorTest() throws Exception {
		renderProfessorsPageWithAllProfessors();
	}
	
	private void renderProfessorsPageWithAllProfessors() throws Exception {
		when(professorService.findAllProfessors()).thenReturn(
				new HashSet<Professor>(Arrays.asList(professor1, professor2)));
		
		mockMvc.perform(get("/professors"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("title", is("All Professors")))
		       .andExpect(model().attribute("professors", hasSize(2)))
		       .andExpect(view().name("professorsPage"));
		
		verify(professorService, times(1)).findAllProfessors();
	}
	
	@Test
	@WithAnonymousUser
	public void renderProfessorsPageWithAllProfessorsByAnonymousUserTest() throws Exception {
		mockMvc.perform(get("/professors"))
		       .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderProfessorsPageWithProfessorsByStudyProgramByAdminTest() throws Exception {
		renderProfessorsPageWithProfessorsByStudyProgram();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderProfessorsPageWithProfessorsByStudyProgramByUserTest() throws Exception {
		renderProfessorsPageWithProfessorsByStudyProgram();
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderProfessorsPageWithProfessorsByStudyProgramByProfessorTest() throws Exception {
		renderProfessorsPageWithProfessorsByStudyProgram();
	}
	
	private void renderProfessorsPageWithProfessorsByStudyProgram() throws Exception {
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
		
		mockMvc.perform(get("/professors").param("studyProgram", "SP1"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("title", is("Professors at Study Program 1")))
		       .andExpect(model().attribute("professors", hasSize(0)))
		       .andExpect(view().name("professorsPage"));
		
		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
	}
	
	@Test
	@WithAnonymousUser
	public void renderProfessorsPageWithProfessorsByStudyProgramByAnonymousUserTest() throws Exception {
		mockMvc.perform(get("/professors").param("studyProgram", "SP1"))
		       .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderEmptyProfessorFormByAdminTest() throws Exception {
		renderEmptyProfessorForm();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderEmptyProfessorFormByUserTest() throws Exception {
		renderEmptyProfessorForm();
	}
	
	public void renderEmptyProfessorForm() throws Exception {
		mockMvc.perform(get("/professors/professorForm"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("professor", is(new Professor())))
		       .andExpect(view().name("professorForm"));
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderEmptyProfessorFormByProfessorTest() throws Exception {
		mockMvc.perform(get("/professors/professorForm"))
		       .andExpect(status().isForbidden())
               .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithMockUser
	public void saveProfessorAndRenderProfessorFormValidFormTest() throws Exception {
		Professor professor = new Professor("P1", "First Professor", "First Father Name",
				new GregorianCalendar(1985, Calendar.APRIL, 2).getTime(), 
				"email@gmail.com", "065-333-212",  Gender.MALE, new Address("City", "Street", "State"), 
				new GregorianCalendar(2017, Calendar.AUGUST, 1).getTime(), "Title");
	
		doNothing().when(professorService).saveOrUpdateProfessor(professor);
		
		mockMvc.perform(post("/professors/save").with(csrf())
				.param("id", "P1")
				.param("fullName", "First Professor")
				.param("fatherName", "First Father Name")
				.param("dateOfBirth", "02-04-1985")
				.param("email", "email@gmail.com")
				.param("telephon", "065-333-212")
				.param("gender", "MALE")
				.param("image", "")
				.param("address", "City - Street - State")
				.param("dateOfEmployment", "01-08-2017" )
				.param("titleOfProfessor", "Title"))
		        .andExpect(model().hasNoErrors())
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/professors/professorForm"));
		
		verify(professorService, times(1)).saveOrUpdateProfessor(professor);
	}
	
	@Test
	@WithMockUser
	public void saveProfessorAndRenderProfessorFormInvalidFormTest() throws Exception {
		Professor professor = new Professor("P1", "First Professor", "First Father Name",
				new GregorianCalendar(1995, Calendar.MAY, 5).getTime(), "invalidEmail", "065-333-212", 
				Gender.MALE, new Address("City", "Street", "State"), 
				new GregorianCalendar(2017, Calendar.AUGUST, 1).getTime(), "Title");
			
		doNothing().when(professorService).saveOrUpdateProfessor(professor);
		
		mockMvc.perform(post("/professors/save").with(csrf())
				.param("id", "P1")
				.param("fullName", "First Professor")
				.param("fatherName", "First Father Name")
				.param("dateOfBirth", "05-05-1995")
				.param("email", "invalidEmail")
				.param("telephon", "065-333-212")
				.param("gender", "MALE")
				.param("image", "")
				.param("address", "City - Street - State")
				.param("dateOfEmployment", "01-08-2017")
				.param("titleOfProfessor", "Title"))
		       .andExpect(model().attributeHasFieldErrors("professor", "email"))
		       .andExpect(model().attribute("professor", is(professor)))
		       .andExpect(view().name("professorForm"));
		
		verify(professorService, times(0)).saveOrUpdateProfessor(professor);
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderFormWithProfessorByAdminTest() throws Exception {
		renderFormWithProfessor();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderFormWithProfessorByUserTest() throws Exception {
		renderFormWithProfessor();
	}
	
	private void renderFormWithProfessor() throws Exception {
		when(professorService.findProfessorById("P1")).thenReturn(professor1);
		
		mockMvc.perform(get("/professors/edit/P1"))
	       .andExpect(status().isOk())
	       .andExpect(model().attribute("professor", is(professor1)))
	       .andExpect(view().name("professorForm"));
		
		verify(professorService, times(1)).findProfessorById("P1");	
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderFormWithProfessorByProfessorTest() throws Exception {
		mockMvc.perform(get("/professors/edit/P1"))
		       .andExpect(status().isForbidden())
               .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void deleteProfessorAndRenderProfessorsPageByAdminTest() throws Exception {
		deleteProfessorAndRenderProfessorsPage();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void deleteProfessorAndRenderProfessorsPageByUserTest() throws Exception {
		deleteProfessorAndRenderProfessorsPage();
	}
	
	private void deleteProfessorAndRenderProfessorsPage() throws Exception {
		when(professorService.findProfessorById("P1")).thenReturn(professor1);
		doNothing().when(professorService).deleteProfessor(professor1);
		
		mockMvc.perform(get("/professors/delete/P1"))
	           .andExpect(status().is3xxRedirection())
	           .andExpect(redirectedUrl("/professors"));
		
		verify(professorService, times(1)).findProfessorById("P1");
		verify(professorService, times(1)).deleteProfessor(professor1);
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void deleteProfessorAndRenderProfessorsPageByProfessorTest() throws Exception {	
		mockMvc.perform(get("/professors/delete/P1"))
		       .andExpect(status().isForbidden())
               .andExpect(forwardedUrl("/denied"));
	}
	
}
