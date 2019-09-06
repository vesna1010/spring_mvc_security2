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
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import college.controllers.SubjectsController;
import college.converters.StudyProgramConverter;
import college.converters.SubjectConverter;
import college.model.StudyProgram;
import college.model.Subject;
import college.service.SubjectService;
import college.service.StudyProgramService;

public class SubjectControllerTest extends BaseControllerTest {

	@Mock
	private SubjectService subjectService;
	@InjectMocks
	@Autowired
	private SubjectsController subjectController;
	@InjectMocks
	@Autowired
	private SubjectConverter subjectConverter;
	@Mock
	private StudyProgramService studyProgramService;
	@InjectMocks
	@Autowired
	private StudyProgramConverter studyProgramConverter;
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderSubjectsPageWithAllSubjectsWithRoleAdminTest() throws Exception {
		renderSubjectsPageWithAllSubjects();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderSubjectsPageWithAllSubjectsWithRoleUserTest() throws Exception {
		renderSubjectsPageWithAllSubjects();
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderSubjectsPageWithAllSubjectsWithRoleProfessorTest() throws Exception {
		renderSubjectsPageWithAllSubjects();
	}
	
	private void renderSubjectsPageWithAllSubjects() throws Exception {	
		when(subjectService.findAllSubjects())
				.thenReturn(Arrays.asList(new Subject(1L, "Subject A"), new Subject(2L, "Subject B")));
		
		mockMvc.perform(get("/subjects"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("subjects", hasSize(2)))
		       .andExpect(view().name("subjects/page"));
		
		verify(subjectService, times(1)).findAllSubjects();
	}
	
	@Test
	@WithAnonymousUser
	public void renderSubjectsPageWithAllSubjectsWithAnonymousUserTest() throws Exception {
		renderSubjectsPageWithAllSubjectsNotAuthenticated();
	}
	
	private void renderSubjectsPageWithAllSubjectsNotAuthenticated() throws Exception {
		mockMvc.perform(get("/subjects"))
	               .andExpect(status().is3xxRedirection())
                       .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderSubjectsPageWithSubjectsByStudyProgramWithRoleAdminTest() throws Exception {
		renderSubjectsPageWithSubjectsByStudyProgram();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderSubjectsPageWithSubjectsByStudyProgramWithRoleUserTest() throws Exception {
		renderSubjectsPageWithSubjectsByStudyProgram();
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderSubjectsPageWithSubjectsByStudyProgramWithRoleProfessorTest() throws Exception {
		renderSubjectsPageWithSubjectsByStudyProgram();
	}
	
	private void renderSubjectsPageWithSubjectsByStudyProgram() throws Exception {
		StudyProgram studyProgram = new StudyProgram(1L, "Study Program");
		
		when(studyProgramService.findStudyProgramById(1L)).thenReturn(studyProgram);
		when(subjectService.findAllSubjectsByStudyProgram(studyProgram))
				.thenReturn(Arrays.asList(new Subject(1L, "Subject A"), new Subject(2L, "Subject B")));
	
		mockMvc.perform(
				get("/subjects")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("subjects", hasSize(2)))
		       .andExpect(view().name("subjects/page"));
	
		verify(studyProgramService, times(1)).findStudyProgramById(1L);
		verify(subjectService, times(1)).findAllSubjectsByStudyProgram(studyProgram);
	}
	
	@Test
	@WithAnonymousUser
	public void renderSubjectsPageWithSubjectsByStudyProgramWithAnonymousUserTest() throws Exception {
		renderSubjectsPageWithSubjectsByStudyProgramNotAuthenticated();
	}
	
	private void renderSubjectsPageWithSubjectsByStudyProgramNotAuthenticated() throws Exception {
		mockMvc.perform(
				get("/subjects")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().is3xxRedirection())
                       .andExpect(redirectedUrlPattern("**/login"));
	}

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderEmptySubjectFormWithRoleAdminTest() throws Exception {
		renderEmptySubjectForm();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderEmptySubjectFormWithRoleUserTest() throws Exception {
		renderEmptySubjectForm();
	}
	
	private void renderEmptySubjectForm() throws Exception {
		when(studyProgramService.findAllStudyPrograms()).thenReturn(
				Arrays.asList(new StudyProgram(1L, "Study Program A"), new StudyProgram(2L, "Study Program B")));
		
		mockMvc.perform(get("/subjects/form"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("subject", is(new Subject())))
		       .andExpect(model().attribute("studyPrograms", hasSize(2)))
		       .andExpect(view().name("subjects/form"));
		
		verify(studyProgramService, times(1)).findAllStudyPrograms();
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderEmptySubjectFormWithRoleProfessorTest() throws Exception {
		renderEmptySubjectFormAccessDenied();
	}
	
	private void renderEmptySubjectFormAccessDenied() throws Exception {
		mockMvc.perform(get("/subjects/form"))
	               .andExpect(status().isForbidden())
	               .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithAnonymousUser
	public void renderEmptySubjectFormWithAnonymousUserTest() throws Exception {
		renderEmptySubjectFormNotAuthenticated();
	}

	private void renderEmptySubjectFormNotAuthenticated() throws Exception {
		mockMvc.perform(get("/subjects/form"))
	               .andExpect(status().is3xxRedirection())
                       .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void saveSubjectAndRenderSubjectFormValidFormTest() throws Exception {
		saveSubjectAndRenderSubjectFormValidForm();
	}
	
	private void saveSubjectAndRenderSubjectFormValidForm() throws Exception {
		StudyProgram studyProgram = new StudyProgram(1L, "Study Program");
		Subject subject = new Subject(1L, "Subject", studyProgram);
		
		when(studyProgramService.findStudyProgramById(1L)).thenReturn(studyProgram);
		doNothing().when(subjectService).saveOrUpdateSubject(subject);
		
		mockMvc.perform(
				post("/subjects/save")
				.param("id", "1")
				.param("title", "Subject")
				.param("studyProgram", "1")
				.with(csrf())
				)
		       .andExpect(model().hasNoErrors())
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/subjects/form"));
		
		verify(studyProgramService, times(1)).findStudyProgramById(1L);
		verify(subjectService, times(1)).saveOrUpdateSubject(subject);
	}
	
	@Test
	@WithMockUser
	public void saveSubjectAndRenderSubjectFormInvalidFormTest() throws Exception {
		saveSubjectAndRenderSubjectFormInvalidForm();
	}
	
	private void saveSubjectAndRenderSubjectFormInvalidForm() throws Exception {
		StudyProgram studyProgram = new StudyProgram(1L, "Study Program");
		Subject subject = new Subject(1L, "Subject :::", studyProgram);
		
		when(studyProgramService.findStudyProgramById(1L)).thenReturn(studyProgram);
		doNothing().when(subjectService).saveOrUpdateSubject(subject);
		when(studyProgramService.findAllStudyPrograms()).thenReturn(Arrays.asList(studyProgram));
		
		mockMvc.perform(
				post("/subjects/save")
				.param("id", "1")
				.param("title", "Subject :::")
				.param("studyProgram", "1")
				.with(csrf())
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attributeHasFieldErrors("subject", "title"))
		       .andExpect(model().attribute("subject", is(subject)))
		       .andExpect(model().attribute("studyPrograms", hasSize(1)))
		       .andExpect(view().name("subjects/form"));
		
		verify(studyProgramService, times(1)).findStudyProgramById(1L);
		verify(subjectService, times(0)).saveOrUpdateSubject(subject);
		verify(studyProgramService, times(1)).findAllStudyPrograms();
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderSubjectFormWithSubjectWithRoleAdminTest() throws Exception {
		renderSubjectFormWithSubject();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderSubjectFormWithSubjectWithRoleUserTest() throws Exception {
		renderSubjectFormWithSubject();
	}

	private void renderSubjectFormWithSubject() throws Exception {
		StudyProgram studyProgram = new StudyProgram(1L, "Study Program");
		Subject subject = new Subject(1L, "Subject", studyProgram);
		
		when(subjectService.findSubjectById(1L)).thenReturn(subject);
		when(studyProgramService.findAllStudyPrograms()).thenReturn(Arrays.asList(studyProgram));
		
		mockMvc.perform(
				get("/subjects/edit")
				.param("subjectId", "1")
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("subject", is(subject)))
		       .andExpect(model().attribute("studyPrograms", hasSize(1)))
		       .andExpect(view().name("subjects/form"));
		
		verify(studyProgramService, times(1)).findAllStudyPrograms();
		verify(subjectService, times(1)).findSubjectById(1L);
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderSubjectFormWithSubjectWithRoleProfessorTest() throws Exception {
		renderSubjectFormWithSubjectAccessDenied();
	}
	
	private void renderSubjectFormWithSubjectAccessDenied() throws Exception {
		mockMvc.perform(
				get("/subjects/edit")
				.param("subjectId", "1")
				)
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithAnonymousUser
	public void renderSubjectFormWithSubjectWithAnonymousUserTest() throws Exception {
		renderSubjectFormWithSubjectNotAuthenticated();
	}

	private void renderSubjectFormWithSubjectNotAuthenticated() throws Exception {
		mockMvc.perform(
				get("/subjects/edit")
				.param("subjectId", "1")
				)
		       .andExpect(status().is3xxRedirection())
                       .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void deleteSubjectAndRenderSubjectsPageWithRoleAdminTest() throws Exception {
		deleteSubjectAndRenderSubjectsPage();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void deleteSubjectAndRenderSubjectsPageWithRoleUserTest() throws Exception {
		deleteSubjectAndRenderSubjectsPage();
	}
	
	private void deleteSubjectAndRenderSubjectsPage() throws Exception {
		doNothing().when(subjectService).deleteSubjectById(1L);
		
		mockMvc.perform(
				get("/subjects/delete")
				.param("subjectId", "1")
				)
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/subjects"));
		
		verify(subjectService, times(1)).deleteSubjectById(1L);
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void deleteSubjectAndRenderSubjectsPageByProfessorTest() throws Exception {
		deleteSubjectAndRenderSubjectsPageAccessDenied();
	}

	private void deleteSubjectAndRenderSubjectsPageAccessDenied() throws Exception {
		mockMvc.perform(
				get("/subjects/delete")
				.param("subjectId", "1")
				)
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithAnonymousUser
	public void deleteSubjectAndRenderSubjectsPageByAnonymousUserTest() throws Exception {
		deleteSubjectAndRenderSubjectsPageNotAuthenticated();
	}
	
	private void deleteSubjectAndRenderSubjectsPageNotAuthenticated() throws Exception {
		mockMvc.perform(
				get("/subjects/delete")
				.param("subjectId", "1")
				)
		       .andExpect(status().is3xxRedirection())
                       .andExpect(redirectedUrlPattern("**/login"));
	}
}
