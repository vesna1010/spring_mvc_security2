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
import college.controllers.LectureController;
import college.converters.ProfessorConverter;
import college.converters.StudyProgramConverter;
import college.converters.SubjectConverter;
import college.model.Lecture;
import college.model.Professor;
import college.model.ProfessorSubjectId;
import college.model.StudyProgram;
import college.model.Subject;
import college.service.LectureService;
import college.service.ProfessorService;
import college.service.StudyProgramService;
import college.service.SubjectService;

public class LectureControllerTest extends BaseControllerTest {

	@Mock
	private LectureService lectureService;
	@Mock
	private ProfessorService professorService;
	@Mock
	private SubjectService subjectService;
	@InjectMocks
	@Autowired
	private LectureController lectureController;
	@InjectMocks
	@Autowired
	private SubjectConverter subjectConverter;
	@InjectMocks
	@Autowired
	private ProfessorConverter professorConverter;
	@Mock
	private StudyProgramService studyProgramService;
	@InjectMocks
	@Autowired
	private StudyProgramConverter studyProgramConverter;
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderLecturesByStudyProgramWithRoleAdminTest() throws Exception {
		renderLecturesByStudyProgram();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderLecturesByStudyProgramWithRoleUserTest() throws Exception {
		renderLecturesByStudyProgram();
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD3", roles = "PROFESSOR")
	public void renderLecturesByStudyProgramWithRoleProfessorTest() throws Exception {
		renderLecturesByStudyProgram();
	}

	private void renderLecturesByStudyProgram() throws Exception {
		StudyProgram studyProgram = new StudyProgram(1L, "Study Program");
		
		when(studyProgramService.findStudyProgramById(1L)).thenReturn(studyProgram);
		when(lectureService.findAllLecturesByStudyProgram(studyProgram)).thenReturn(
				Arrays.asList(new Lecture(new Professor(1L, "Professor"), new Subject(1L, "Subject"), 2)));
	
		mockMvc.perform(
				get("/lectures")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("lectures", hasSize(1)))
		       .andExpect(view().name("lectures/page"));
		
		verify(studyProgramService, times(2)).findStudyProgramById(1L);
		verify(lectureService, times(1)).findAllLecturesByStudyProgram(studyProgram);
	}
	
	@Test
	@WithAnonymousUser
	public void renderLecturesByStudyProgramWithAnonymousUserTest() throws Exception {
		renderLecturesByStudyProgramNotAuthenticated();
	}
	
	private void renderLecturesByStudyProgramNotAuthenticated() throws Exception {
		mockMvc.perform(
				get("/lectures")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().is3xxRedirection())
                       .andExpect(redirectedUrlPattern("**/login"));
	}

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderLectureFormWithRoleAdminTest() throws Exception {
		renderLectureForm();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderLectureFormWithRoleUserTest() throws Exception {
		renderLectureForm();
	}
	
	private void renderLectureForm() throws Exception {	
		StudyProgram studyProgram = new StudyProgram(1L, "Study Program");
		
		when(studyProgramService.findStudyProgramById(1L)).thenReturn(studyProgram);
		when(subjectService.findAllSubjectsByStudyProgram(studyProgram))
				.thenReturn(Arrays.asList(new Subject(1L, "Subject A"), new Subject(2L, "Subject B")));
		when(professorService.findAllProfessors())
				.thenReturn(Arrays.asList(new Professor(1L, "Professor A"), new Professor(2L, "Professor B")));
	
		mockMvc.perform(
				get("/lectures/form")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("lecture", is(new Lecture())))
		       .andExpect(model().attribute("subjects", hasSize(2)))
		       .andExpect(model().attribute("professors", hasSize(2)))
		       .andExpect(view().name("lectures/form"));
		
		verify(studyProgramService, times(1)).findStudyProgramById(1L);
		verify(professorService, times(1)).findAllProfessors();
		verify(subjectService, times(1)).findAllSubjectsByStudyProgram(studyProgram);
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderLectureFormWithRoleProfessorTest() throws Exception {	
		renderLectureFormAccessDenied();
	}
	
	private void renderLectureFormAccessDenied() throws Exception {	
		mockMvc.perform(
				get("/lectures/form")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithAnonymousUser
	public void renderLectureFormWithAnonymousUserTest() throws Exception {	
		renderLectureFormNotAuthenticated();
	}
	
	private void renderLectureFormNotAuthenticated() throws Exception {	
		mockMvc.perform(
				get("/lectures/form")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().is3xxRedirection())
                       .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void saveLectureAndRenderFormValidFormTest() throws Exception {
		saveLectureAndRenderFormValidForm();
	}
	
	private void saveLectureAndRenderFormValidForm() throws Exception {
		Professor professor = new Professor(1L, "Professor");
		Subject subject = new Subject(1L, "Subject");
		Lecture lecture = new Lecture(professor, subject, 2);
		
		when(professorService.findProfessorById(1L)).thenReturn(professor);
		when(subjectService.findSubjectById(1L)).thenReturn(subject);
		doNothing().when(lectureService).saveOrUpdateLecture(lecture);
		
		mockMvc.perform(
				post("/lectures/save")
				.param("studyProgramId", "1")
				.param("professor", "1")
				.param("subject", "1")
				.param("hours", "2")
				.with(csrf())
				)
		       .andExpect(model().hasNoErrors())
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/lectures/form?studyProgramId=1"));
		
		verify(professorService, times(1)).findProfessorById(1L);
		verify(subjectService, times(1)).findSubjectById(1L);
		verify(lectureService, times(1)).saveOrUpdateLecture(lecture);
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void saveLectureAndRenderFormInvalidFormTest() throws Exception {
		saveLectureAndRenderFormInvalidForm();
	}
	
	private void saveLectureAndRenderFormInvalidForm() throws Exception {
		Professor professor = new Professor(1L, "Professor");
		Subject subject = new Subject(1L, "Subject");
		Lecture lecture = new Lecture(professor, subject, 0);
		StudyProgram studyProgram = new StudyProgram(1L, "Study Program");
		
		when(studyProgramService.findStudyProgramById(1L)).thenReturn(studyProgram);
		when(professorService.findProfessorById(1L)).thenReturn(professor);
		when(subjectService.findSubjectById(1L)).thenReturn(subject);
		doNothing().when(lectureService).saveOrUpdateLecture(lecture);
		when(professorService.findAllProfessors()).thenReturn(Arrays.asList(professor));
		when(subjectService.findAllSubjectsByStudyProgram(studyProgram)).thenReturn(Arrays.asList(subject));
	
		mockMvc.perform(
				post("/lectures/save")
				.param("studyProgramId", "1")
				.param("professor", "1")
				.param("subject", "1")
				.param("hours", "0")
				.with(csrf())
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attributeHasFieldErrors("lecture", "hours"))
		       .andExpect(model().attribute("lecture", is(lecture)))
		       .andExpect(model().attribute("subjects", hasSize(1)))
		       .andExpect(model().attribute("professors", hasSize(1)))
		       .andExpect(view().name("lectures/form"));
		
		verify(studyProgramService, times(1)).findStudyProgramById(1L);
		verify(professorService, times(1)).findProfessorById(1L);
		verify(subjectService, times(1)).findSubjectById(1L);
		verify(lectureService, times(0)).saveOrUpdateLecture(lecture);
		verify(professorService, times(1)).findAllProfessors();
		verify(subjectService, times(1)).findAllSubjectsByStudyProgram(studyProgram);
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void deleteLectureAndRenderLecturesPageWithRoleAdminTest() throws Exception {
		deleteLectureAndRenderLecturesPage();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void deleteLectureAndRenderLecturesPageWithRoleUserTest() throws Exception {
		deleteLectureAndRenderLecturesPage();
	}
	
	private void deleteLectureAndRenderLecturesPage() throws Exception {
		ProfessorSubjectId id = new ProfessorSubjectId(1L, 1L);
		
		doNothing().when(lectureService).deleteLectureById(id);
		
		mockMvc.perform(
				get("/lectures/delete")
				.param("studyProgramId", "1")
				.param("professorId", "1")
				.param("subjectId", "1")
				)
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/lectures?studyProgramId=1"));
		
		verify(lectureService, times(1)).deleteLectureById(id);
	}
    
    @Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWOR", roles = "PROFESSOR")
	public void deleteLectureAndRenderLecturesPageWithRoleProfessorTest() throws Exception {
    	deleteLectureAndRenderLecturesPageAccessDenied();
	}
    
    private void deleteLectureAndRenderLecturesPageAccessDenied() throws Exception {
	        mockMvc.perform(
		                get("/lectures/delete")
			        .param("studyProgramId", "1")
			        .param("professorId", "1")
				.param("subjectId", "1")
				)
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
    }
    
    @Test
	@WithAnonymousUser
	public void deleteLectureAndRenderLecturesPageWithAnonymousUserTest() throws Exception {
    	deleteLectureAndRenderLecturesPageNotAuthenticated();
	}
    
    private void deleteLectureAndRenderLecturesPageNotAuthenticated() throws Exception {
    	        mockMvc.perform(
				get("/lectures/delete")
				.param("studyProgramId", "1")
				.param("professorId", "1")
				.param("subjectId", "1")
				)
		       .andExpect(status().is3xxRedirection())
                       .andExpect(redirectedUrlPattern("**/login"));
    }
	
}
