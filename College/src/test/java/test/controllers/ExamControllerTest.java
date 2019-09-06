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
import java.util.Date;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import college.controllers.ExamController;
import college.converters.ProfessorConverter;
import college.converters.StudentConverter;
import college.converters.StudyProgramConverter;
import college.converters.SubjectConverter;
import college.model.Exam;
import college.model.Professor;
import college.model.Student;
import college.model.StudentSubjectId;
import college.model.StudyProgram;
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
	@Mock
	private StudentService studentService;
	@InjectMocks
	@Autowired
	private ExamController examController;
	@InjectMocks
	@Autowired
	private StudentConverter studentConverter;
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
	public void renderPageWithExamsByStudyProgramWithRoleAdminTest() throws Exception {
		renderPageWithExamsByStudyProgram();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderPageWithExamsByStudyProgramWithRoleUserTest() throws Exception {
		renderPageWithExamsByStudyProgram();
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderPageWithExamsByStudyProgramWithRoleProfessorTest() throws Exception {
		renderPageWithExamsByStudyProgram();
	}
	
	private void renderPageWithExamsByStudyProgram() throws Exception {
		StudyProgram studyProgram = new StudyProgram(1L, "Study Program");
		
		when(studyProgramService.findStudyProgramById(1L)).thenReturn(studyProgram);
		when(examService.findAllExamsByStudyProgram(studyProgram))
				.thenReturn(Arrays.asList(new Exam(new Student(1L, "Student"), new Subject(1L, "Subject"),
						new Professor(1L, "Professor"), new Date(), 10)));	
		
		mockMvc.perform(
				get("/exams")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("exams", hasSize(1)))
		       .andExpect(view().name("exams/page"));
		
		verify(studyProgramService, times(4)).findStudyProgramById(1L);
		verify(examService, times(1)).findAllExamsByStudyProgram(studyProgram);
	}
	
	@Test
	@WithAnonymousUser
	public void renderPageWithExamsByStudyProgramWithAnonymousUserTest() throws Exception {
		renderPageWithExamsByStudyProgramNotAuthenticated();
	}
	
	private void renderPageWithExamsByStudyProgramNotAuthenticated() throws Exception {
		mockMvc.perform(
				get("/exams")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().is3xxRedirection())
                       .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderEmptyExamFormWithRoleAdminTest() throws Exception {
		renderEmptyExamForm();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderEmptyExamFormWithRoleUserTest() throws Exception {
		renderEmptyExamForm();
	}

	private void renderEmptyExamForm() throws Exception {
		StudyProgram studyProgram = new StudyProgram(1L, "Study Program");
		
		when(studyProgramService.findStudyProgramById(1L)).thenReturn(studyProgram);
		when(studentService.findAllStudentsByStudyProgram(studyProgram)).thenReturn(Arrays.asList(new Student(1L, "Student")));
		when(subjectService.findAllSubjectsByStudyProgram(studyProgram)).thenReturn(Arrays.asList(new Subject(1L, "Subject")));
		when(professorService.findAllProfessorsByStudyProgram(studyProgram))
				.thenReturn(Arrays.asList(new Professor(1L, "Professor")));

		mockMvc.perform(
				get("/exams/form")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("exam", is(new Exam())))
		       .andExpect(model().attribute("students", hasSize(1)))
		       .andExpect(model().attribute("subjects", hasSize(1)))
		       .andExpect(model().attribute("professors", hasSize(1)))
		       .andExpect(view().name("exams/form"));
		
		verify(studyProgramService, times(3)).findStudyProgramById(1L);
		verify(studentService, times(1)).findAllStudentsByStudyProgram(studyProgram);
		verify(subjectService, times(1)).findAllSubjectsByStudyProgram(studyProgram);
		verify(professorService, times(1)).findAllProfessorsByStudyProgram(studyProgram);
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderEmptyExamFormWithRoleProfessorTest() throws Exception {
		renderEmptyExamFormAccessDenied();
	}
	
	private void renderEmptyExamFormAccessDenied() throws Exception {
		mockMvc.perform(
				get("/exams/form")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithAnonymousUser
	public void renderExamFormSubjectWithAnonymousUserTest() throws Exception {
		renderEmptyExamFormNotAuthenticated();
	}
	
	private void renderEmptyExamFormNotAuthenticated() throws Exception {
		mockMvc.perform(
				get("/exams/form")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().is3xxRedirection())
                       .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void saveExamAndRenderExamFormValidFormTest() throws Exception {
		saveExamAndRenderExamFormValidForm();
	}
	
	private void saveExamAndRenderExamFormValidForm() throws Exception {
		Student student = new Student(1L, "Student");
		Subject subject = new Subject(1L, "Subject");
		Professor professor = new Professor(1L, "Professor");
		Exam exam = new Exam(student, subject, professor,
				new GregorianCalendar(2017, Calendar.JANUARY, 1).getTime(), 8);
	
		when(studentService.findStudentById(1L)).thenReturn(student);
		when(subjectService.findSubjectById(1L)).thenReturn(subject);
		when(professorService.findProfessorById(1L)).thenReturn(professor);
		doNothing().when(examService).saveOrUpdateExam(exam);
		
		mockMvc.perform(
				post("/exams/save")
				.param("studyProgramId", "1")
				.param("student", "1")
				.param("subject", "1")
				.param("professor", "1")
				.param("date", "01-01-2017")
		                .param("score", "8")
		                .with(csrf())
		                )
		       .andExpect(model().hasNoErrors())
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/exams/form?studyProgramId=1"));
		
		verify(studentService, times(1)).findStudentById(1L);
		verify(subjectService, times(1)).findSubjectById(1L);
		verify(professorService, times(1)).findProfessorById(1L);
		verify(examService, times(1)).saveOrUpdateExam(exam);
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void saveExamAndRenderExamFormInvalidFormTest() throws Exception {
		saveExamAndRenderExamFormInvalidForm();
	}
	
	private void saveExamAndRenderExamFormInvalidForm() throws Exception {
		Student student = new Student(1L, "Student");
		Subject subject = new Subject(1L, "Subject");
		Professor professor = new Professor(1L, "Professor");
		Exam exam = new Exam(student, subject, professor, new GregorianCalendar(2017, 1, 1).getTime(), 0);
		StudyProgram studyProgram = new StudyProgram(1L, "Study Program");
		
		when(studyProgramService.findStudyProgramById(1L)).thenReturn(studyProgram);
		when(studentService.findStudentById(1L)).thenReturn(student);
		when(subjectService.findSubjectById(1L)).thenReturn(subject);
		when(professorService.findProfessorById(1L)).thenReturn(professor);
		doNothing().when(examService).saveOrUpdateExam(exam);
		when(studentService.findAllStudentsByStudyProgram(studyProgram)).thenReturn(Arrays.asList(student));
		when(subjectService.findAllSubjectsByStudyProgram(studyProgram)).thenReturn(Arrays.asList(subject));
		when(professorService.findAllProfessorsByStudyProgram(studyProgram)).thenReturn(Arrays.asList(professor));
		
		mockMvc.perform(
				post("/exams/save")
				.param("studyProgramId", "1")
				.param("student", "1")
				.param("professor", "1")
				.param("subject", "1")
				.param("date", "01-01-2017")
		                .param("score", "5")
		                .with(csrf())
		                )
		       .andExpect(status().isOk())
		       .andExpect(model().attributeHasFieldErrors("exam", "score"))
		       .andExpect(model().attribute("exam", is(exam)))
		       .andExpect(model().attribute("students", hasSize(1)))
		       .andExpect(model().attribute("subjects", hasSize(1)))
		       .andExpect(model().attribute("professors", hasSize(1)))
	               .andExpect(view().name("exams/form"));
		
		verify(studyProgramService, times(3)).findStudyProgramById(1L);
		verify(studentService, times(1)).findStudentById(1L);
		verify(subjectService, times(1)).findSubjectById(1L);
		verify(professorService, times(1)).findProfessorById(1L);
		verify(examService, times(0)).saveOrUpdateExam(exam);
		verify(studentService, times(1)).findAllStudentsByStudyProgram(studyProgram);
		verify(subjectService, times(1)).findAllSubjectsByStudyProgram(studyProgram);
		verify(professorService, times(1)).findAllProfessorsByStudyProgram(studyProgram);
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void deleteExamAndRenderExamsPageWithRoleAdminTest() throws Exception {
		deleteExamAndRenderExamsPage();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void deleteExamAndRenderExamsPageWithRoleUserTest() throws Exception {
		deleteExamAndRenderExamsPage();
	}

	private void deleteExamAndRenderExamsPage() throws Exception {
		StudentSubjectId id = new StudentSubjectId(1L, 1L);
		
		doNothing().when(examService).deleteExamById(id);
		
		mockMvc.perform(
				get("/exams/delete")
				.param("studentId", "1")
				.param("subjectId", "1")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/exams?studyProgramId=1"));
		
		verify(examService, times(1)).deleteExamById(id);
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void deleteExamAndRenderExamsPageWithRoleProfessorTest() throws Exception {
		deleteExamAndRenderExamsPageAccessDenied();
	}
	
	private void deleteExamAndRenderExamsPageAccessDenied() throws Exception {
		mockMvc.perform(
				get("/exams/delete")
				.param("studentId", "1")
				.param("subjectId", "1")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithAnonymousUser
	public void deleteExamAndRenderExamsPageWithAnonymousUserTest() throws Exception {
		deleteExamAndRenderExamsPageNotAuthenticated();
	}
	
	private void deleteExamAndRenderExamsPageNotAuthenticated() throws Exception {
		mockMvc.perform(
				get("/exams/delete")
				.param("studentId", "1")
				.param("subjectId", "1")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().is3xxRedirection())
                       .andExpect(redirectedUrlPattern("**/login"));
	}
	
}
