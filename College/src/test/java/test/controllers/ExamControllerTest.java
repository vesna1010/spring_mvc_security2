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
import java.util.GregorianCalendar;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import college.controllers.ExamController;
import college.converters.ExamConverter;
import college.converters.ProfessorConverter;
import college.converters.StudentConverter;
import college.converters.StudyProgramConverter;
import college.converters.SubjectConverter;
import college.model.Exam;
import college.model.Professor;
import college.model.Subject;
import college.service.ExamService;
import college.service.ProfessorService;
import college.service.StudentService;
import college.service.StudyProgramService;
import college.service.SubjectService;

public class ExamControllerTest extends BaseControllerTest {

	@Mock
	private ExamService examService;
	@Mock
	private SubjectService subjectService;
	@Mock
	private ProfessorService professorService;
	@InjectMocks
	@Autowired
	private ExamController examController;
	@InjectMocks
	@Autowired
	private ExamConverter examConverter;
	@Mock
	private StudyProgramService studyProgramService;
	@InjectMocks
	@Autowired
	private StudyProgramConverter studyProgramConverter;
	@Mock
	private StudentService studentService;
	@InjectMocks
	@Autowired
	private StudentConverter studentConverter;
	@InjectMocks
	@Autowired
	private SubjectConverter subjectConverter;
	@InjectMocks
	@Autowired
	private ProfessorConverter professorConverter;

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderExamFormSubjectByAdminTest() throws Exception {
		renderExamFormSubject();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderExamFormSubjectByUserTest() throws Exception {
		renderExamFormSubject();
	}

	private void renderExamFormSubject() throws Exception {
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);

		mockMvc.perform(get("/exams/examForm").param("studyProgram", "SP1"))
		       .andExpect(status().isOk())
			   .andExpect(model().attribute("exam", is(new Exam())))
			   .andExpect(model().attribute("studyProgram", is(studyProgram1)))
			   .andExpect(view().name("examForm"));
		
		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderExamFormSubjectByProfessorTest() throws Exception {
		mockMvc.perform(get("/exams/examForm").param("studyProgramId", "SP1"))
		       .andExpect(status().isForbidden())
			   .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithMockUser
	public void saveExamAndRenderExamFormValidFormTest() throws Exception {
		 Exam exam = new Exam("E1", student1, subject1, professor1,
					new GregorianCalendar(2017, Calendar.JANUARY, 1).getTime(), 8);
		
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
		when(studentService.findStudentById("S1")).thenReturn(student1);
		when(subjectService.findSubjectById("SUB1")).thenReturn(subject1);
		when(professorService.findProfessorById("P1")).thenReturn(professor1);
		doNothing().when(examService).saveOrUpdateExam(exam);
		
		mockMvc.perform(
				post("/exams/save").with(csrf())
				.param("studyProgram", "SP1")
				.param("id", "E1")
				.param("student", "S1")
				.param("subject", "SUB1")
				.param("professor", "P1")
				.param("date", "01-01-2017")
		        .param("score", "8"))
		       .andExpect(model().hasNoErrors())
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/exams/examForm?studyProgram=SP1"));
		
		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
		verify(studentService, times(1)).findStudentById("S1");
		verify(subjectService, times(1)).findSubjectById("SUB1");
		verify(professorService, times(1)).findProfessorById("P1");
		verify(examService, times(1)).saveOrUpdateExam(exam);
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void saveExamAndRenderExamFormInvalidFormTest() throws Exception {
		Exam exam = new Exam("E1", student1, subject1, professor1,
				new GregorianCalendar(2017, 1, 1).getTime(), 0);
		
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
		when(studentService.findStudentById("S1")).thenReturn(student1);
		when(subjectService.findSubjectById("SUB1")).thenReturn(subject1);
		when(professorService.findProfessorById("P1")).thenReturn(professor1);
		doNothing().when(examService).saveOrUpdateExam(exam);
		
		mockMvc.perform(
				post("/exams/save").with(csrf())
				.param("studyProgram", "SP1")
				.param("id", "E1")
				.param("student", "S1")
				.param("professor", "P1")
				.param("subject", "SUB1")
				.param("date", "01-01-2017")
		        .param("score", "5"))
		       .andExpect(status().isOk())
		       .andExpect(model().attributeHasFieldErrors("exam", "score"))
		       .andExpect(model().attribute("exam", is(exam)))
			   .andExpect(model().attribute("studyProgram", is(studyProgram1)))
			   .andExpect(view().name("examForm"));
		
		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
		verify(studentService, times(1)).findStudentById("S1");
		verify(subjectService, times(1)).findSubjectById("SUB1");
		verify(professorService, times(1)).findProfessorById("P1");
		verify(examService, times(0)).saveOrUpdateExam(exam);
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderExamFormWithExamByAdminTest() throws Exception {
		renderExamFormWithExam();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderExamFormWithExamByUserTest() throws Exception {
		renderExamFormWithExam();
	}

	private void renderExamFormWithExam() throws Exception {
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
		when(examService.findExamById("E1")).thenReturn(exam1);

		mockMvc.perform(get("/exams/edit/E1/SP1"))
		       .andExpect(status().isOk())
			   .andExpect(model().attribute("exam", is(exam1)))
			   .andExpect(model().attribute("studyProgram", is(studyProgram1)))
			   .andExpect(view().name("examForm"));
		
		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
		verify(examService, times(1)).findExamById("E1");
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderExamFormWithExamByProfessorTest() throws Exception {
		mockMvc.perform(get("/exams/edit/E1/SP1"))
		       .andExpect(status().isForbidden())
			   .andExpect(forwardedUrl("/denied"));
	}

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void deleteExamByIdByAdminTest() throws Exception {
		deleteExamById();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void deleteExamByIdByUserTest() throws Exception {
		deleteExamById();
	}

	private void deleteExamById() throws Exception {
		when(examService.findExamById("E1")).thenReturn(exam1);
		doNothing().when(examService).deleteExam(exam1);
		when(examService.findExamsByObjects(exam1.getProfessor(), exam1.getSubject(),  
				exam1.getDate())).thenReturn(new HashSet<Exam>());
	
		mockMvc.perform(get("/exams/delete/E1"))
		       .andExpect(status().isOk())
			   .andExpect(model().attribute("exams", hasSize(0)))
			   .andExpect(view().name("examsPage"));
		
		verify(examService, times(1)).findExamById("E1");
		verify(examService, times(1)).deleteExam(exam1);
		verify(examService, times(1)).findExamsByObjects(professor1, subject1, exam1.getDate());
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void deleteExamByIdByProfessorTest() throws Exception {
		mockMvc.perform(get("/exams/delete/E1"))
		       .andExpect(status().isForbidden())
			   .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderSearchExamFormByAdminTest() throws Exception {
		renderSearchExamForm();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderSearchExamFormByUserTest() throws Exception {
		renderSearchExamForm();
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderSearchExamFormByProfessorTest() throws Exception {
		renderSearchExamForm();
	}

	private void renderSearchExamForm() throws Exception {
		when(professorService.findAllProfessors()).thenReturn(
				new HashSet<Professor>(Arrays.asList(professor1, professor2)));
		when(subjectService.findAllSubjects()).thenReturn(
				new HashSet<Subject>(Arrays.asList(subject1, subject2)));
		
		mockMvc.perform(get("/exams/search"))
		       .andExpect(status().isOk())
			   .andExpect(model().attribute("exam", is(new Exam())))
			   .andExpect(model().attribute("subjects", hasSize(2)))
			   .andExpect(model().attribute("professors", hasSize(2)))
			   .andExpect(view().name("searchExamsForm"));
		
		verify(subjectService, times(1)).findAllSubjects();
		verify(professorService, times(1)).findAllProfessors();
	}
	
	@Test
	@WithAnonymousUser
	public void renderSearchExamFormByAnonymousUserTest() throws Exception {
		mockMvc.perform(get("/exams/search"))
		       .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser
	public void renderSearchedExamsValidFormTest() throws Exception {
		when(professorService.findProfessorById("P1")).thenReturn(professor1);
		when(subjectService.findSubjectById("SUB1")).thenReturn(subject1);
		when(examService.findExamsByObjects(professor1, subject1, 
				new GregorianCalendar(2017, Calendar.JANUARY, 1).getTime())).thenReturn(
				new HashSet<Exam>(Arrays.asList(exam1)));	
		
		mockMvc.perform(
				post("/exams/search").with(csrf())
				.param("professor", "P1")
				.param("subject", "SUB1")
				.param("date", "01-01-2017")
		        .param("student", "")
		        .param("id", ""))
		       .andExpect(status().isOk())
			   .andExpect(model().attribute("exams", hasSize(1)))
			   .andExpect(view().name("examsPage"));
		
		verify(professorService, times(1)).findProfessorById("P1");
		verify(subjectService, times(1)).findSubjectById("SUB1");
		verify(examService, times(1)).findExamsByObjects(professor1, subject1, 
				new GregorianCalendar(2017, Calendar.JANUARY, 1).getTime());
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderSearchedExamsInvalidFormTest() throws Exception {	
		when(professorService.findProfessorById("")).thenReturn(null);
		when(subjectService.findSubjectById("")).thenReturn(null);
		when(examService.findExamsByObjects(null, null, 
				new GregorianCalendar(2017, Calendar.JANUARY, 1).getTime())).thenReturn(
				new HashSet<Exam>(Arrays.asList()));	
		when(professorService.findAllProfessors()).thenReturn(
				new HashSet<Professor>(Arrays.asList(professor1, professor2)));
		when(subjectService.findAllSubjects()).thenReturn(
				new HashSet<Subject>(Arrays.asList(subject1, subject2)));
		
		mockMvc.perform(
				post("/exams/search").with(csrf())
				.param("professor", "")
				.param("subject", "")
				.param("student", "")
				.param("id", "")
				.param("date", ""))
		       .andExpect(status().isOk())
		       .andExpect(model().attributeHasFieldErrors("exam", "student", "professor"))
		       .andExpect(model().attribute("subjects", hasSize(2)))
		       .andExpect(model().attribute("professors", hasSize(2)))
			   .andExpect(view().name("searchExamsForm"));
		
		verify(professorService, times(1)).findProfessorById("");
		verify(subjectService, times(1)).findSubjectById("");
		verify(examService, times(0)).findExamsByObjects(null, null, 
				new GregorianCalendar(2017, Calendar.JANUARY, 1).getTime());
		verify(subjectService, times(1)).findAllSubjects();
		verify(professorService, times(1)).findAllProfessors();
	}

}
