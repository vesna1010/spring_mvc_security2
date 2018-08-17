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
import java.util.HashSet;
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
	@Mock
	private StudyProgramService studyProgramService;
	@InjectMocks
	@Autowired
	private SubjectsController subjectController;
	@InjectMocks
	@Autowired
	private SubjectConverter subjectConverter;
	@InjectMocks
	@Autowired
	private StudyProgramConverter studyProgramConverter;
	
	{
		studyProgram1.addSubject(subject1);
		studyProgram1.addSubject(subject2);
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderSubjectsPageWithAllSubjectsByAdminTest() throws Exception {
		renderSubjectsPageWithAllSubjects();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderSubjectsPageWithAllSubjectsByUserTest() throws Exception {
		renderSubjectsPageWithAllSubjects();
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderSubjectsPageWithAllSubjectsByProfessorTest() throws Exception {
		renderSubjectsPageWithAllSubjects();
	}
	
	private void renderSubjectsPageWithAllSubjects() throws Exception {	
		when(subjectService.findAllSubjects()).thenReturn(
				new HashSet<Subject>(Arrays.asList(subject1, subject2)));
		
		mockMvc.perform(get("/subjects"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("title", is("All Subjects")))
		       .andExpect(model().attribute("subjects", hasSize(2)))
		       .andExpect(view().name("subjectsPage"));
		
		verify(subjectService, times(1)).findAllSubjects();
	}
	
	@Test
	@WithAnonymousUser
	public void renderSubjectsPageWithAllSubjectsByAnonymousUserTest() throws Exception {
		mockMvc.perform(get("/subjects"))
		       .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderSubjectsPageWithSubjectsByStudyProgramByAdminTest() throws Exception {
		renderSubjectsPageWithSubjectsByStudyProgram();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderSubjectsPageWithSubjectsByStudyProgramByUserTest() throws Exception {
		renderSubjectsPageWithSubjectsByStudyProgram();
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderSubjectsPageWithSubjectsByStudyProgramByProfessorTest() throws Exception {
		renderSubjectsPageWithSubjectsByStudyProgram();
	}
	
	private void renderSubjectsPageWithSubjectsByStudyProgram() throws Exception {
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
	
		mockMvc.perform(get("/subjects").param("studyProgram", "SP1"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("title", is("Subjects at Study Program 1")))
		       .andExpect(model().attribute("subjects", hasSize(2)))
		       .andExpect(view().name("subjectsPage"));
	
		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
	}
	
	@Test
	@WithAnonymousUser
	public void renderSubjectsPageWithSubjectsByStudyProgramByAnonymousUserTest() throws Exception {
		mockMvc.perform(get("/subjects").param("studyProgram", "SP1"))
		       .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderEmptySubjectFormByAdminTest() throws Exception {
		renderEmptySubjectForm();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderEmptySubjectFormByUserTest() throws Exception {
		renderEmptySubjectForm();
	}
	
	private void renderEmptySubjectForm() throws Exception {
		when(studyProgramService.findAllStudyPrograms()).thenReturn(
				new HashSet<StudyProgram>(Arrays.asList(studyProgram1, studyProgram2)));
		
		mockMvc.perform(get("/subjects/subjectForm"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("subject", is(new Subject())))
		       .andExpect(model().attribute("studyPrograms", hasSize(2)))
		       .andExpect(view().name("subjectForm"));
		
		verify(studyProgramService, times(1)).findAllStudyPrograms();
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderEmptySubjectFormByProfessorTest() throws Exception {
		mockMvc.perform(get("/subjects/subjectForm"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}

	@Test
	@WithMockUser
	public void saveSubjectAndRenderSubjectFormValidFormTest() throws Exception {
		Subject subject = new Subject("SUB1", "Subject", studyProgram1);
		
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
		doNothing().when(subjectService).saveOrUpdateSubject(subject);
		when(studyProgramService.findAllStudyPrograms()).thenReturn(
				new HashSet<StudyProgram>(Arrays.asList(studyProgram1, studyProgram2)));
		
		mockMvc.perform(post("/subjects/save").with(csrf())
				.param("id", "SUB1")
				.param("title", "Subject")
				.param("studyProgram", "SP1"))
		       .andExpect(model().hasNoErrors())
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/subjects/subjectForm"));
		
		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
		verify(subjectService, times(1)).saveOrUpdateSubject(subject);
		verify(studyProgramService, times(0)).findAllStudyPrograms();
	}
	
	@Test
	@WithMockUser
	public void saveSubjectAndRenderSubjectFormInvalidForm() throws Exception {
		Subject subject = new Subject("SUB1", "Subject :::", studyProgram1);
		
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
		doNothing().when(subjectService).saveOrUpdateSubject(subject);
		when(studyProgramService.findAllStudyPrograms()).thenReturn(
				new HashSet<StudyProgram>(Arrays.asList(studyProgram1, studyProgram2)));
		
		mockMvc.perform(post("/subjects/save").with(csrf())
				.param("id", "SUB1")
				.param("title", "Subject :::")
				.param("studyProgram", "SP1"))
		       .andExpect(status().isOk())
		       .andExpect(model().attributeHasFieldErrors("subject", "title"))
		       .andExpect(model().attribute("subject", is(subject)))
		       .andExpect(model().attribute("studyPrograms", hasSize(2)))
		       .andExpect(view().name("subjectForm"));
		
		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
		verify(subjectService, times(0)).saveOrUpdateSubject(subject);
		verify(studyProgramService, times(1)).findAllStudyPrograms();
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderSubjectFormWithSubjectByAdminTest() throws Exception {
		renderSubjectFormWithSubject();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderSubjectFormWithSubjectByUserTest() throws Exception {
		renderSubjectFormWithSubject();
	}

	private void renderSubjectFormWithSubject() throws Exception {
		when(subjectService.findSubjectById("SUB1")).thenReturn(subject1);
		when(studyProgramService.findAllStudyPrograms()).thenReturn(
				new HashSet<StudyProgram>(Arrays.asList(studyProgram1, studyProgram2)));
		
		mockMvc.perform(get("/subjects/edit/SUB1"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("subject", is(subject1)))
		       .andExpect(model().attribute("studyPrograms", hasSize(2)))
		       .andExpect(view().name("subjectForm"));
		
		verify(studyProgramService, times(1)).findAllStudyPrograms();
		verify(subjectService, times(1)).findSubjectById("SUB1");
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderSubjectFormWithSubjectByProfessorTest() throws Exception {
		mockMvc.perform(get("/subjects/edit/SUB1"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void deleteSubjectAndRenderSubjectsPageByAdminTest() throws Exception {
		deleteSubjectAndRenderSubjectsPage();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void deleteSubjectAndRenderSubjectsPageByUserTest() throws Exception {
		deleteSubjectAndRenderSubjectsPage();
	}
	
	private void deleteSubjectAndRenderSubjectsPage() throws Exception {
		when(subjectService.findSubjectById("SUB1")).thenReturn(subject1);
		doNothing().when(subjectService).deleteSubject(subject1);
		
		mockMvc.perform(get("/subjects/delete/SUB1"))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/subjects"));
		
		verify(subjectService, times(1)).findSubjectById("SUB1");
		verify(subjectService, times(1)).deleteSubject(subject1);
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void deleteSubjectAndRenderSubjectsPageByProfessorTest() throws Exception {
		mockMvc.perform(get("/subjects/delete/SUB1"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}
	
}
