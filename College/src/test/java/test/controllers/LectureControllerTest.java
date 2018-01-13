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
import college.controllers.LectureController;
import college.model.Lecture;
import college.model.Professor;
import college.service.LectureService;
import college.service.ProfessorService;
import college.service.StudyProgramService;

public class LectureControllerTest extends BaseControllerTest {

	@Mock
	private LectureService lectureService;
	@Mock
	private ProfessorService professorService;
	@Mock
	private StudyProgramService studyProgramService;
	@InjectMocks
	@Autowired
	private LectureController lectureController;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wax).apply(springSecurity()).build();
	}

	@Test
	public void test() {
		assertNotNull(lectureService);
		assertNotNull(professorService);
		assertNotNull(studyProgramService);
		assertNotNull(lectureController);
	}

	@Test
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD1", roles = "ADMIN")
	public void renderLecturesByStudyProgramTest_By_Admin() throws Exception {
		renderLecturesByStudyProgram_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void renderLecturesByStudyProgramTest_By_User() throws Exception {
		renderLecturesByStudyProgram_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_PROFESSOR", password = "PASSWORD3", roles = "PROFESSOR")
	public void renderLecturesByStudyProgramTest_By_Professor() throws Exception {
		renderLecturesByStudyProgram_Enabled();
	}

	private void renderLecturesByStudyProgram_Enabled() throws Exception {
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
		
		mockMvc.perform(get("/lectures").param("studyProgramId", "SP1"))
                       .andExpect(status().isOk())
                       .andExpect(model().attribute("studyProgram", is(studyProgram1)))
                       .andExpect(view().name("lectures"));
		
		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
	}

	@Test
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD1", roles = "ADMIN")
	public void renderLectureFormTest_By_Admin() throws Exception {
		renderLectureForm_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void renderLectureFormTest_By_User() throws Exception {
		renderLectureForm_Enabled();
	}
	
	private void renderLectureForm_Enabled() throws Exception {	
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
		when(professorService.findAllProfessors()).thenReturn(
				new HashSet<Professor>(Arrays.asList(professor1, professor2)));
		
		mockMvc.perform(get("/lectures/lectureForm").param("studyProgramId", "SP1"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("lecture", is(new Lecture())))
		       .andExpect(model().attribute("studyProgram", is(studyProgram1)))
		       .andExpect(model().attribute("professors", hasSize(2)))
		       .andExpect(view().name("lectureForm"));
		
		verify(professorService, times(1)).findAllProfessors();
		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
	}

	@Test
	@WithMockUser(username = "USERNAME_PROFESSOR", password = "PASSWORD3", roles = "PROFESSOR")
	public void renderLectureFormTest_By_Professor() throws Exception {
		renderLectureForm_Disabled();
	}

	private void renderLectureForm_Disabled() throws Exception {
		mockMvc.perform(get("/lectures/lectureForm").param("studyProgramId", "SP1"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void saveLecture_ValidForm() throws Exception {
		doNothing().when(lectureService).saveOrUpdateLecture(lecture1);
		
		mockMvc.perform(post("/lectures/save").with(csrf())
				.param("studyProgramId", "SP1")
				.param("id", "L1")
				.param("professor", "P1")
				.param("subject", "SUB1")
				.param("hours", "2"))
		       .andExpect(model().hasNoErrors())
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/lectures/lectureForm?studyProgramId=SP1"));
		
		verify(lectureService, times(1)).saveOrUpdateLecture(lecture1);
	}
	
	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void saveLecture_InvalidForm() throws Exception {
		Lecture lecture = new Lecture("L1", professor1, subject1, 0);
	
		when(professorService.findAllProfessors()).thenReturn(
				new HashSet<Professor>(Arrays.asList(professor1, professor2)));
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
		doNothing().when(lectureService).saveOrUpdateLecture(lecture1);
		
		mockMvc.perform(post("/lectures/save").with(csrf())
				.param("studyProgramId", "SP1")
				.param("id", "L1")
				.param("professor", "P1")
				.param("subject", "SUB1")
				.param("hours", "0"))
		       .andExpect(model().attributeHasFieldErrors("lecture", "hours"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("lecture", is(lecture)))
		       .andExpect(model().attribute("studyProgram", is(studyProgram1)))
		       .andExpect(model().attribute("professors", hasSize(2)))
		       .andExpect(view().name("lectureForm"));
		       
		verify(lectureService, times(0)).saveOrUpdateLecture(lecture);
		verify(professorService, times(1)).findAllProfessors();
		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
	}
	
	@Test
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD1", roles = "ADMIN")
	public void renderFormWithLectureTest_By_Admin() throws Exception {
		renderFormWithLecture_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void renderFormWithLectureTest_By_User() throws Exception {
		renderFormWithLecture_Enabled();
	}
	
	private void renderFormWithLecture_Enabled() throws Exception {
		when(lectureService.findLectureById("L1")).thenReturn(lecture1);
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
		when(professorService.findAllProfessors()).thenReturn(
				new HashSet<Professor>(Arrays.asList(professor1, professor2)));
		
		mockMvc.perform(get("/lectures/edit/" + "L1/SP1"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("lecture", is(lecture1)))
		       .andExpect(model().attribute("studyProgram", is(studyProgram1)))
		       .andExpect(model().attribute("professors", hasSize(2)))
		       .andExpect(view().name("lectureForm"));
		       
		verify(lectureService, times(1)).findLectureById("L1");
		verify(professorService, times(1)).findAllProfessors();
		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
	}

	@Test
	@WithMockUser(username = "USERNAME_PROFESSOR", password = "PASSWORD3", roles = "PROFESSOR")
	public void renderFormWithLectureTest_By_Professor() throws Exception {
		renderFormWithLecture_Disabled();
	}
	
	private void renderFormWithLecture_Disabled() throws Exception {
		mockMvc.perform(get("/lectures/edit/" + "L1/SP1"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD1", roles = "ADMIN")
	public void deleteLectureAndRenderLecturesPageTest_By_Admin() throws Exception {
		deleteLectureAndRenderLecturesPage_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void deleteLectureAndRenderLecturesPageTest_By_User() throws Exception {
		deleteLectureAndRenderLecturesPage_Enabled();
	}
	
	private void deleteLectureAndRenderLecturesPage_Enabled() throws Exception {
		doNothing().when(lectureService).deleteLectureById("L1");
		
		mockMvc.perform(get("/lectures/delete/" + "L1/SP1"))
    	               .andExpect(status().is3xxRedirection())
    	               .andExpect(redirectedUrl("/lectures?studyProgramId=SP1"));
		
		verify(lectureService, times(1)).deleteLectureById("L1");
	}
    
        @Test
	@WithMockUser(username = "USERNAME_PROFESSOR", password = "PASSWORD3", roles = "PROFESSOR")
	public void deleteLectureAndRenderLecturesPageTest_By_Professor() throws Exception {
		deleteLectureAndRenderLecturesPage_Disabled();
	}
    
	private void deleteLectureAndRenderLecturesPage_Disabled() throws Exception {
		doNothing().when(lectureService).deleteLectureById("L1");
		
		mockMvc.perform(get("/lectures/delete/" + "L1/SP1"))
    	               .andExpect(status().isForbidden())
                       .andExpect(forwardedUrl("/denied"));
		
		verify(lectureService, times(0)).deleteLectureById("L1");
	}
	
}

