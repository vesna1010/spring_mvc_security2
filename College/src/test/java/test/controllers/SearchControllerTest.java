package test.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import college.controllers.SearchExamsController;
import college.converters.ProfessorConverter;
import college.converters.StudentConverter;
import college.converters.SubjectConverter;
import college.model.Exam;
import college.model.Professor;
import college.model.Student;
import college.model.Subject;
import college.service.ExamService;
import college.service.ProfessorService;
import college.service.StudentService;
import college.service.SubjectService;

public class SearchControllerTest extends BaseControllerTest {
	
	@Mock
	private ExamService examService;
	@Mock
	private SubjectService subjectService;
	@Mock
	private ProfessorService professorService;
	@InjectMocks
	@Autowired
	private SearchExamsController searchExamController;
	@InjectMocks
	@Autowired
	private SubjectConverter subjectConverter;
	@InjectMocks
	@Autowired
	private ProfessorConverter professorConverter;
	@Mock
	private StudentService studentService;
	@InjectMocks
	@Autowired
	private StudentConverter studentConverter;
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderStudentExamsPageWithStudentWithRoleAdminTest() throws Exception {
		renderStudentExamsPageWithStudent();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderStudentExamsPageWithStudentWithRoleUserTest() throws Exception {
		renderStudentExamsPageWithStudent();
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderStudentExamsPageWithStudentWithRoleProfessorTest() throws Exception {
		renderStudentExamsPageWithStudent();
	}
	
	private void renderStudentExamsPageWithStudent() throws Exception {	
		Student student = new Student(1L, "Student");
		
		when(studentService.findStudentById(1L)).thenReturn(student);
		when(examService.findAllExamsByStudent(student)).thenReturn(Arrays.asList(new Exam(new Student(1L, "Student"),
				new Subject(1L, "Subject"), new Professor(1L, "Professor"), new Date(), 10)));
	
		mockMvc.perform(
				get("/exams/student")
				.param("studentId", "1")
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("exams", hasSize(1)))
		       .andExpect(view().name("student/exams/page"));
		
		verify(studentService, times(1)).findStudentById(1L);
		verify(examService, times(1)).findAllExamsByStudent(student);
	}
	
	@Test
	@WithAnonymousUser
	public void renderStudentExamsPageWithStudentWithAnonymousUserTest() throws Exception {
		renderStudentExamsPageWithStudentNotAuthenticated();
	}
	
	private void renderStudentExamsPageWithStudentNotAuthenticated() throws Exception {
		mockMvc.perform(
				get("/exams/student")
				.param("studentId", "1")
				)
		       .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderSearchExamFormWithRoleAdminTest() throws Exception {
		renderSearchExamForm();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderSearchExamFormWithRoleUserTest() throws Exception {
		renderSearchExamForm();
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderSearchExamFormWithRoleProfessorTest() throws Exception {
		renderSearchExamForm();
	}

	private void renderSearchExamForm() throws Exception {
		when(professorService.findAllProfessors()).thenReturn(Arrays.asList(new Professor(1L, "Professor")));
		when(subjectService.findAllSubjects()).thenReturn(Arrays.asList(new Subject(1L, "Subject")));
	
		mockMvc.perform(get("/exams/search"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("exam", is(new Exam())))
		       .andExpect(model().attribute("subjects", hasSize(1)))
		       .andExpect(model().attribute("professors", hasSize(1)))
		       .andExpect(view().name("exams/search"));
		
		verify(subjectService, times(1)).findAllSubjects();
		verify(professorService, times(1)).findAllProfessors();
	}
	
	@Test
	@WithAnonymousUser
	public void renderSearchExamFormWithAnonymousUserTest() throws Exception {
		renderSearchExamFormNotAuthenticated();
	}
	
	private void renderSearchExamFormNotAuthenticated() throws Exception {
		mockMvc.perform(get("/exams/search"))
	           .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderSearchedExamsValidFormTest() throws Exception {
		renderSearchedExamsValidForm();
	}
	
	private void renderSearchedExamsValidForm() throws Exception {
		Student student = new Student(1L, "Student");
		Subject subject = new Subject(1L, "Subject");
		Professor professor = new Professor(1L, "Professor");
		Exam exam = new Exam(student, subject, professor, new GregorianCalendar(2017, 1, 1).getTime(), 0);

		when(professorService.findProfessorById(1L)).thenReturn(professor);
		when(subjectService.findSubjectById(1L)).thenReturn(subject);
		when(examService.findAllExamsByProfessorAndSubjectAndDate(professor, subject,
				new GregorianCalendar(2017, Calendar.JANUARY, 1).getTime())).thenReturn(Arrays.asList(exam));
	
		mockMvc.perform(
				post("/exams/search")
				.param("professor", "1")
				.param("subject", "1")
				.param("date", "01-01-2017")
		        .param("student", "")
		        .param("id", "")
		        .with(csrf())
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("exams", hasSize(1)))
		       .andExpect(view().name("searched/exams/page"));
		
		verify(professorService, times(1)).findProfessorById(1L);
		verify(subjectService, times(1)).findSubjectById(1L);
		verify(examService, times(1)).findAllExamsByProfessorAndSubjectAndDate(professor, subject,
				new GregorianCalendar(2017, Calendar.JANUARY, 1).getTime());
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderSearchedExamsInvalidFormTest() throws Exception {	
		renderSearchedExamsInvalidForm();
	}
	
	private void renderSearchedExamsInvalidForm() throws Exception {
		Subject subject = new Subject(1L, "Subject");
		Professor professor = new Professor(1L, "Professor");

		when(professorService.findProfessorById(1L)).thenReturn(professor);
		when(subjectService.findSubjectById(1L)).thenReturn(subject);
		when(examService.findAllExamsByProfessorAndSubjectAndDate(professor, subject, null))
				.thenReturn(Arrays.asList());
		when(professorService.findAllProfessors()).thenReturn(Arrays.asList(professor));
		when(subjectService.findAllSubjects()).thenReturn(Arrays.asList(subject));
	
		mockMvc.perform(
				post("/exams/search")
				.param("professor", "1")
				.param("subject", "1")
				.param("student", "")
				.param("id", "")
				.param("date", "")
				.with(csrf())
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attributeHasFieldErrors("exam", "date"))
		       .andExpect(model().attribute("subjects", hasSize(1)))
		       .andExpect(model().attribute("professors", hasSize(1)))
		       .andExpect(view().name("exams/search"));
		
		verify(professorService, times(1)).findProfessorById(1L);
		verify(subjectService, times(1)).findSubjectById(1L);
		verify(examService, times(0)).findAllExamsByProfessorAndSubjectAndDate(professor, subject, null);
		verify(subjectService, times(1)).findAllSubjects();
		verify(professorService, times(1)).findAllProfessors();
	}
}
