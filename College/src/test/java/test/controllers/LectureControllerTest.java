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
import college.controllers.LectureController;
import college.converters.LectureConverter;
import college.converters.ProfessorConverter;
import college.converters.StudyProgramConverter;
import college.converters.SubjectConverter;
import college.model.Lecture;
import college.model.Professor;
import college.service.LectureService;
import college.service.ProfessorService;
import college.service.StudyProgramService;
import college.service.SubjectService;

public class LectureControllerTest extends BaseControllerTest {

	@Mock
	private LectureService lectureService;
	@Mock
	private ProfessorService professorService;
	@InjectMocks
	@Autowired
	private LectureController lectureController;
	@InjectMocks
	@Autowired
	private LectureConverter lectureConverter;
	@InjectMocks
	@Autowired
	private ProfessorConverter professorConverter;
	@Mock
	private StudyProgramService studyProgramService;
	@InjectMocks
	@Autowired
	private StudyProgramConverter studyProgramConverter;
	@Mock
	private SubjectService subjectService;
	@InjectMocks
	@Autowired
	private SubjectConverter subjectConverter;

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderLecturesByStudyProgramByAdminTest() throws Exception {
		renderLecturesByStudyProgram();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderLecturesByStudyProgramByUserTest() throws Exception {
		renderLecturesByStudyProgram();
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD3", roles = "PROFESSOR")
	public void renderLecturesByStudyProgramByProfessorTest() throws Exception {
		renderLecturesByStudyProgram();
	}

	private void renderLecturesByStudyProgram() throws Exception {
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
		
		mockMvc.perform(get("/lectures").param("studyProgram", "SP1"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("studyProgram", is(studyProgram1)))
		       .andExpect(view().name("lecturesPage"));
		
		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
	}
	
	@Test
	@WithAnonymousUser
	public void renderLecturesByStudyProgramByAnonymousUserTest() throws Exception {
		mockMvc.perform(get("/lectures").param("studyProgram", "SP1"))
		       .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderLectureFormByAdminTest() throws Exception {
		renderLectureForm();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderLectureFormByUserTest() throws Exception {
		renderLectureForm();
	}
	
	private void renderLectureForm() throws Exception {	
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
		when(professorService.findAllProfessors()).thenReturn(
				new HashSet<Professor>(Arrays.asList(professor1, professor2)));
		
		mockMvc.perform(get("/lectures/lectureForm").param("studyProgram", "SP1"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("lecture", is(new Lecture())))
		       .andExpect(model().attribute("studyProgram", is(studyProgram1)))
		       .andExpect(model().attribute("professors", hasSize(2)))
		       .andExpect(view().name("lectureForm"));
		
		verify(professorService, times(1)).findAllProfessors();
		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderLectureFormByProfessor() throws Exception {	
		mockMvc.perform(get("/lectures/lectureForm").param("studyProgram", "SP1"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithMockUser
	public void saveLectureAndRenderFormValidFormTest() throws Exception {
		Lecture lecture = new Lecture(professor1, subject1, 2);
		
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
		when(professorService.findProfessorById("P1")).thenReturn(professor1);
		when(subjectService.findSubjectById("SUB1")).thenReturn(subject1);
		doNothing().when(lectureService).saveOrUpdateLecture(lecture);
		
		mockMvc.perform(
				post("/lectures/save").with(csrf())
				.param("studyProgram", "SP1")
				.param("professor", "P1")
				.param("subject", "SUB1")
				.param("hours", "2"))
		       .andExpect(model().hasNoErrors())
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/lectures/lectureForm?studyProgram=SP1"));
		
		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
		verify(professorService, times(1)).findProfessorById("P1");
		verify(subjectService, times(1)).findSubjectById("SUB1");
		verify(lectureService, times(1)).saveOrUpdateLecture(lecture);
	}
	
	@Test
	@WithMockUser
	public void saveLectureAndRenderFormInvalidFormTest() throws Exception {
		Lecture lecture = new Lecture(professor1, subject1, 0);
		
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
		when(professorService.findProfessorById("P1")).thenReturn(professor1);
		when(subjectService.findSubjectById("SUB1")).thenReturn(subject1);
		doNothing().when(lectureService).saveOrUpdateLecture(lecture);
		when(professorService.findAllProfessors()).thenReturn(
				new HashSet<Professor>(Arrays.asList(professor1, professor2)));
		
		mockMvc.perform(
				post("/lectures/save").with(csrf())
				.param("studyProgram", "SP1")
				.param("professor", "P1")
				.param("subject", "SUB1")
				.param("hours", "0"))
		       .andExpect(model().attributeHasFieldErrors("lecture", "hours"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("lecture", is(lecture)))
		       .andExpect(model().attribute("studyProgram", is(studyProgram1)))
		       .andExpect(model().attribute("professors", hasSize(2)))
		       .andExpect(view().name("lectureForm"));
		
		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
		verify(professorService, times(1)).findProfessorById("P1");
		verify(subjectService, times(1)).findSubjectById("SUB1");
		verify(lectureService, times(0)).saveOrUpdateLecture(lecture);
		verify(professorService, times(1)).findAllProfessors();
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderFormWithLectureByAdminTest() throws Exception {
		renderFormWithLecture();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderFormWithLectureByUserTest() throws Exception {
		renderFormWithLecture();
	}
	
	private void renderFormWithLecture() throws Exception {
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
		when(lectureService.findLectureById(1L)).thenReturn(lecture1);
		when(professorService.findAllProfessors()).thenReturn(
				new HashSet<Professor>(Arrays.asList(professor1, professor2)));
		
		mockMvc.perform(get("/lectures/edit/1/SP1"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("lecture", is(lecture1)))
		       .andExpect(model().attribute("studyProgram", is(studyProgram1)))
		       .andExpect(model().attribute("professors", hasSize(2)))
		       .andExpect(view().name("lectureForm"));
		       
		verify(lectureService, times(1)).findLectureById(1L);
		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
		verify(professorService, times(1)).findAllProfessors();
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderFormWithLectureByProfessorTest() throws Exception {	
		mockMvc.perform(get("/lectures/edit/1/SP1"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void deleteLectureAndRenderLecturesPageByAdminTest() throws Exception {
		deleteLectureAndRenderLecturesPage();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void deleteLectureAndRenderLecturesPageByUserTest() throws Exception {
		deleteLectureAndRenderLecturesPage();
	}
	
	private void deleteLectureAndRenderLecturesPage() throws Exception {
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
		when(lectureService.findLectureById(1L)).thenReturn(lecture1);
		doNothing().when(lectureService).deleteLecture(lecture1);
		
		mockMvc.perform(get("/lectures/delete/1/SP1"))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/lectures?studyProgram=SP1"));
		
		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
		verify(lectureService, times(1)).findLectureById(1L);
		verify(lectureService, times(1)).deleteLecture(lecture1);
	}
    
    @Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWOR", roles = "PROFESSOR")
	public void deleteLectureAndRenderLecturesPageByProfessorTest() throws Exception {
		mockMvc.perform(get("/lectures/delete/1/SP1"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}
	
}
