package test.controllers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.Arrays;
import java.util.HashSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import college.controllers.SubjectsController;
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

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wax).apply(springSecurity()).build();
		studyProgram1.addSubject(subject1);
		studyProgram1.addSubject(subject2);
	}

	@Test
	public void mocksTest() {
		assertNotNull(subjectService);
		assertNotNull(studyProgramService);
		assertNotNull(subjectController);
	}

	@Test
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD1", roles = "ADMIN")
	public void renderSubjectsPageWithAllSubjectsTest_By_Admin() throws Exception {
		renderSubjectsPageWithAllSubjects_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void renderSubjectsPageWithAllSubjectsTest_By_User() throws Exception {
		renderSubjectsPageWithAllSubjects_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_PROFESSOR", password = "PASSWORD3", roles = "PROFESSOR")
	public void renderSubjectsPageWithAllSubjectsTest_By_Professor() throws Exception {
		renderSubjectsPageWithAllSubjects_Enabled();
	}
	
	private void renderSubjectsPageWithAllSubjects_Enabled() throws Exception {	
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
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD1", roles = "ADMIN")
	public void renderSubjectsPageWithSubjectsByStudyProgramTest_By_Admin() throws Exception {
		renderSubjectsPageWithSubjectsByStudyProgram_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void renderSubjectsPageWithSubjectsByStudyProgramTest_By_User() throws Exception {
		renderSubjectsPageWithSubjectsByStudyProgram_Enabled();
	}
	
	@Test
	@WithMockUser(username = "USERNAME_PROFESSOR", password = "PASSWORD3", roles = "PROFESSOR")
	public void renderSubjectsPageWithSubjectsByStudyProgramTest_By_Professor() throws Exception {
		renderSubjectsPageWithSubjectsByStudyProgram_Enabled();
	}
	
	private void renderSubjectsPageWithSubjectsByStudyProgram_Enabled() throws Exception {
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
	
		mockMvc.perform(get("/subjects").param("studyProgramId", "SP1"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("subjects", hasSize(2)))
	               .andExpect(model().attribute("title", is("Subjects at Study Program 1")))
	               .andExpect(view().name("subjectsPage"));
	
		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
	}

	@Test
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD1", roles = "ADMIN")
	public void renderEmptySubjectFormTest_By_Admin() throws Exception {
		renderEmptySubjectForm_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void renderEmptySubjectFormTest_By_User() throws Exception {
		renderEmptySubjectForm_Enabled();
	}
	
	private void renderEmptySubjectForm_Enabled() throws Exception {
		when(studyProgramService.findAllStudyPrograms()).thenReturn(
				new HashSet<StudyProgram>(Arrays.asList(studyProgram1, studyProgram2)));
		
		mockMvc.perform(get("/subjects/subjectForm"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("studyPrograms", hasSize(2)))
		       .andExpect(model().attribute("subject", is(new Subject())))
		       .andExpect(view().name("subjectForm"));
		
		verify(studyProgramService, times(1)).findAllStudyPrograms();
	}

	@Test
	@WithMockUser(username = "USERNAME_PROFESSOR", password = "PASSWORD3", roles = "PROFESSOR")
	public void renderEmptySubjectForm_By_Professor() throws Exception {
		renderEmptySubjectForm_Disabled();
	}

	private void renderEmptySubjectForm_Disabled() throws Exception {
		mockMvc.perform(get("/subjects/subjectForm"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void saveSubjectAndRenderSubjectForm_ValidForm() throws Exception {
		doNothing().when(subjectService).saveOrUpdateSubject(subject1);
		
		mockMvc.perform(post("/subjects/save").with(csrf())
				.param("id", "SUB1")
				.param("title", "Subject 1")
				.param("studyProgram", "SP1"))
		       .andExpect(model().hasNoErrors())
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/subjects/subjectForm"));
		
		verify(subjectService, times(1)).saveOrUpdateSubject(subject1);
	}
	
	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void saveSubjectAndRenderSubjectForm_InvalidForm() throws Exception {
		Subject subject = new Subject("SUB1", "Subject :::", studyProgram1);
		
		doNothing().when(subjectService).saveOrUpdateSubject(subject);
		when(studyProgramService.findAllStudyPrograms()).thenReturn(
				new HashSet<StudyProgram>(Arrays.asList(studyProgram1, studyProgram2)));
		
		mockMvc.perform(post("/subjects/save").with(csrf())
				.param("id", "SUB1")
				.param("title", "Subject :::")
				.param("studyProgram", "SP1"))
		       .andExpect(model().attributeHasFieldErrors("subject", "title"))
		       .andExpect(model().attribute("subject", is(subject)))
		       .andExpect(model().attribute("studyPrograms", hasSize(2)))
		       .andExpect(view().name("subjectForm"));
		
		verify(subjectService, times(0)).saveOrUpdateSubject(subject);
		verify(studyProgramService, times(1)).findAllStudyPrograms();
	}
	
	@Test
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD1", roles = "ADMIN")
	public void renderSubjectFormWithSubjectTest_By_Admin() throws Exception {
		renderSubjectFormWithSubject_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void renderSubjectFormWithSubjectTest_By_User() throws Exception {
		renderSubjectFormWithSubject_Enabled();
	}

	private void renderSubjectFormWithSubject_Enabled() throws Exception {
		when(subjectService.findSubjectById("SUB1")).thenReturn(subject1);
		when(studyProgramService.findAllStudyPrograms()).thenReturn(
				new HashSet<StudyProgram>(Arrays.asList(studyProgram1, studyProgram2)));
		
		mockMvc.perform(get("/subjects/edit/" + "SUB1"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("subject", is(subject1)))
		       .andExpect(model().attribute("studyPrograms", hasSize(2)))
		       .andExpect(view().name("subjectForm"));
		
		verify(studyProgramService, times(1)).findAllStudyPrograms();
		verify(subjectService, times(1)).findSubjectById("SUB1");
	}
	
	@Test
	@WithMockUser(username = "USERNAME_PROFESSOR", password = "PASSWORD3", roles = "PROFESSOR")
	public void renderSubjectFormWithSubject_By_Professor() throws Exception {
		renderSubjectFormWithSubject_Disabled();
	}
	
	private void renderSubjectFormWithSubject_Disabled() throws Exception {	
		mockMvc.perform(get("/subjects/edit/" + "SUB1"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}

	@Test
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD1", roles = "ADMIN")
	public void deleteSubjectAndRenderSubjectsPageTest_By_Admin() throws Exception {
		deleteSubjectAndRenderSubjectsPage_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void deleteSubjectAndRenderSubjectsPageTest_By_User() throws Exception {
		deleteSubjectAndRenderSubjectsPage_Enabled();
	}
	
	private void deleteSubjectAndRenderSubjectsPage_Enabled() throws Exception {
		doNothing().when(subjectService).deleteSubjectById("SUB1");
		
		mockMvc.perform(get("/subjects/delete/" + "SUB1"))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/subjects"));
		
		verify(subjectService, times(1)).deleteSubjectById("SUB1");
	}

	@Test
	@WithMockUser(username = "USERNAME_PROFESSOR", password = "PASSWORD3", roles = "PROFESSOR")
	public void deleteSubjectAndRenderSubjectsPageTest_By_Professor() throws Exception {
		deleteSubjectAndRenderSubjectsPage_Disabled();
	}

	private void deleteSubjectAndRenderSubjectsPage_Disabled() throws Exception {
		mockMvc.perform(get("/subjects/delete/" + "SUB1"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}

}

