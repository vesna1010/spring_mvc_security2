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
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import college.controllers.StudentController;
import college.converters.StudentConverter;
import college.converters.StudyProgramConverter;
import college.enums.Gender;
import college.service.StudentService;
import college.service.StudyProgramService;
import college.model.Address;
import college.model.Student;
import college.model.StudyProgram;

public class StudentControllerTest extends BaseControllerTest {

	@Mock
	private StudentService studentService;
	@InjectMocks
	@Autowired
	private StudentController studentController;
	@InjectMocks
	@Autowired
	private StudentConverter studentConverter;
	@Mock
	private StudyProgramService studyProgramService;
	@InjectMocks
	@Autowired
	private StudyProgramConverter studyProgramConverter;
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderStudentsPageWithAllStudentsWithRoleAdminTest() throws Exception {
		renderStudentsPageWithAllStudents();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderStudentsPageWithAllStudentsWithRoleUserTest() throws Exception {
		renderStudentsPageWithAllStudents();
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderStudentsPageWithAllStudentsWithRoleProfessorTest() throws Exception {
		renderStudentsPageWithAllStudents();
	}
	
	private void renderStudentsPageWithAllStudents() throws Exception {
		when(studentService.findAllStudents())
				.thenReturn(Arrays.asList(new Student(1L, "Student A"), new Student(2L, "Student B")));
		
		mockMvc.perform(get("/students"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("students", hasSize(2)))
		       .andExpect(view().name("students/page"));
		
		verify(studentService, times(1)).findAllStudents();
	}
	
	@Test
	@WithAnonymousUser
	public void renderStudentsPageWithAllStudentsWithAnonymousUserTest() throws Exception {
		renderStudentsPageWithAllStudentsNotAuthenticated();
	}
	
	private void renderStudentsPageWithAllStudentsNotAuthenticated() throws Exception {
		mockMvc.perform(get("/students"))
	               .andExpect(status().is3xxRedirection())
                       .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderStudentsPageWithStudentsByStudyProgramWithRoleAdminTest() throws Exception {
		renderStudentsPageWithStudentsByStudyProgram();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderStudentsPageWithStudentsByStudyProgramWithRoleUserTest() throws Exception {
		renderStudentsPageWithStudentsByStudyProgram();
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderStudentsPageWithStudentsByStudyProgramWithRoleProfessorTest() throws Exception {
		renderStudentsPageWithStudentsByStudyProgram();
	}
	
	private void renderStudentsPageWithStudentsByStudyProgram() throws Exception {
		StudyProgram studyProgram = new StudyProgram(1L, "Study Program");
		
		when(studyProgramService.findStudyProgramById(1L)).thenReturn(studyProgram);
		when(studentService.findAllStudentsByStudyProgram(studyProgram))
				.thenReturn(Arrays.asList(new Student(1L, "Student A"), new Student(2L, "Student B")));
	
		mockMvc.perform(
				get("/students")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("students", hasSize(2)))
		       .andExpect(view().name("students/page"));
		
		verify(studyProgramService, times(1)).findStudyProgramById(1L);
		verify(studentService, times(1)).findAllStudentsByStudyProgram(studyProgram);
	}
	
	@Test
	@WithAnonymousUser
	public void renderStudentsPageWithStudentsByStudyProgramWithAnonymousUserTest() throws Exception {
		renderStudentsPageWithStudentsByStudyProgramNotAuthenticated();
	}
	
	private void renderStudentsPageWithStudentsByStudyProgramNotAuthenticated() throws Exception {
		mockMvc.perform(
				get("/students")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderEmptyStudentFormwithRoleAdminTest() throws Exception {
		renderEmptyStudentForm();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderEmptyStudentFormWithRoleUserTest() throws Exception {
		renderEmptyStudentForm();
	}
	
	private void renderEmptyStudentForm() throws Exception {	
		when(studyProgramService.findAllStudyPrograms()).thenReturn(
				Arrays.asList(new StudyProgram(1L, "Study Program A"), new StudyProgram(2L, "Study Program B")));
		
		mockMvc.perform(get("/students/form"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("student", is(new Student())))
		       .andExpect(model().attribute("studyPrograms", hasSize(2)))
                       .andExpect(view().name("students/form"));
		    	
		verify(studyProgramService, times(1)).findAllStudyPrograms();
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderEmptyStudentFormWithRoleProfessorTest() throws Exception {
		renderEmptyStudentFormAccessDenied();
	}
	
	private void renderEmptyStudentFormAccessDenied() throws Exception {	
		mockMvc.perform(get("/students/form"))
	               .andExpect(status().isForbidden())
                       .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithAnonymousUser
	public void renderEmptyStudentFormWithAnonymousUserTest() throws Exception {
		renderEmptyStudentFormNotAuthenticated();
	}
	
	private void renderEmptyStudentFormNotAuthenticated() throws Exception {
		mockMvc.perform(get("/students/form"))
	               .andExpect(status().is3xxRedirection())
                       .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void saveStudentAndRenderStudentFormValidFormTest() throws Exception {	
		saveStudentAndRenderStudentFormValidForm();
	}
	
	private void saveStudentAndRenderStudentFormValidForm() throws Exception {
		StudyProgram studyProgram = new StudyProgram(1L, "Study Program");
		Student student = new Student(1L, "Student Name", "Father Name",
				new GregorianCalendar(1995, Calendar.MAY, 5).getTime(), "student@gmail.com", "065-123-456", Gender.MALE,
				new Address("City", "74000", "Street", "State"),
				new GregorianCalendar(2016, Calendar.SEPTEMBER, 1).getTime(), 1, studyProgram);

		when(studyProgramService.findStudyProgramById(1L)).thenReturn(studyProgram);
		doNothing().when(studentService).saveOrUpdateStudent(student);
			
		mockMvc.perform(
				post("/students/save")
				.param("id", "1")
				.param("fullName", "Student Name")
				.param("fatherName", "Father Name")
				.param("dateOfBirth", "05-05-1995")
				.param("email", "student@gmail.com")
				.param("telephone", "065-123-456")
				.param("gender", "MALE")
				.param("image", "")
				.param("address.city", "City")
				.param("address.state", "State")
				.param("address.street", "Street")
				.param("address.zipCode", "74000")
				.param("dateOfEntry", "01-09-2016")
				.param("yearOfStudy", "1")
				.param("studyProgram", "1")
				.with(csrf())
				)
		       .andExpect(model().hasNoErrors())
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/students/form"));
		
		verify(studyProgramService, times(1)).findStudyProgramById(1L);
		verify(studentService, times(1)).saveOrUpdateStudent(student);
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void saveStudentAndRenderStudentFormInvalidFormTest() throws Exception {
		saveStudentAndRenderStudentFormInvalidForm();
	}
	
	private void saveStudentAndRenderStudentFormInvalidForm() throws Exception {
		StudyProgram studyProgram = new StudyProgram(1L, "Study Program");
		Student student = new Student(1L, "Student Name", "Father Name",
				new GregorianCalendar(1995, Calendar.MAY, 5).getTime(), "invalidEmail", "065-123-456", Gender.MALE,
				new Address("City", "74000", "Street", "State"),
				new GregorianCalendar(2016, Calendar.SEPTEMBER, 1).getTime(), 1, studyProgram);
		
		when(studyProgramService.findStudyProgramById(1L)).thenReturn(studyProgram);
		doNothing().when(studentService).saveOrUpdateStudent(student);
		when(studyProgramService.findAllStudyPrograms())
				.thenReturn(Arrays.asList(studyProgram));
		
		mockMvc.perform(
				post("/students/save")
				.param("id", "1")
				.param("fullName", "Student Name")
				.param("fatherName", "Father Name")
				.param("dateOfBirth", "05-05-1995")
				.param("email", "invalidEmail")
				.param("telephone", "065-123-456")
				.param("gender", "MALE")
				.param("image", "")
				.param("address.city", "City")
				.param("address.state", "State")
				.param("address.street", "Street")
				.param("address.zipCode", "74000")
				.param("dateOfEntry", "01-09-2016")
				.param("yearOfStudy", "1")
				.param("studyProgram", "1")
				.with(csrf())
				)
		       .andExpect(model().attributeHasFieldErrors("student", "email"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("student", is(student)))
		       .andExpect(model().attribute("studyPrograms", hasSize(1)))
                       .andExpect(view().name("students/form"));
		
		verify(studyProgramService, times(1)).findStudyProgramById(1L);
		verify(studentService, times(0)).saveOrUpdateStudent(student);
		verify(studyProgramService, times(1)).findAllStudyPrograms();
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderStudentFormWithStudentWithRoleAdminTest() throws Exception {
		renderStudentFormWithStudent();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderStudentFormWithStudentWithRoleUserTest() throws Exception {
		renderStudentFormWithStudent();
	}

	private void renderStudentFormWithStudent() throws Exception {
		Student student = new Student(1L, "Student");
		
		when(studentService.findStudentById(1L)).thenReturn(student);
		when(studyProgramService.findAllStudyPrograms()).thenReturn(
				Arrays.asList(new StudyProgram(1L, "Study Program A"), new StudyProgram(2L, "Study Program B")));
	
		mockMvc.perform(
				get("/students/edit")
				.param("studentId", "1")
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("student", is(student)))
		       .andExpect(model().attribute("studyPrograms", hasSize(2)))
		       .andExpect(view().name("students/form"));
		
		verify(studentService, times(1)).findStudentById(1L);
		verify(studyProgramService, times(1)).findAllStudyPrograms();
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderStudentFormWithStudentWithRoleProfessorTest() throws Exception {
		renderStudentFormWithStudentAccessDenied();
	}
	
	private void renderStudentFormWithStudentAccessDenied() throws Exception {
		mockMvc.perform(
				get("/students/edit")
				.param("studentId", "1")
				)
		       .andExpect(status().isForbidden())
                       .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithAnonymousUser
	public void renderStudentFormWithStudentWithAnonymousUserTest() throws Exception {
		renderStudentFormWithStudentNotAuthenticated();
	}
	
	private void renderStudentFormWithStudentNotAuthenticated() throws Exception {
		mockMvc.perform(
				get("/students/edit")
				.param("studentId", "1")
				)
		       .andExpect(status().is3xxRedirection())
                       .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void deleteStudentAndRenderStudentsPageWithRoleAdminTest() throws Exception {
		deleteStudentAndRenderStudentsPage();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void deleteStudentAndRenderStudentsPageWithRoleUserTest() throws Exception {
		deleteStudentAndRenderStudentsPage();
	}
	
	private void deleteStudentAndRenderStudentsPage() throws Exception {
		doNothing().when(studentService).deleteStudentById(1L);
		
		mockMvc.perform(
				get("/students/delete")
				.param("studentId", "1")
				)
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/students"));
		
		verify(studentService, times(1)).deleteStudentById(1L);
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void deleteStudentAndRenderStudentsPageWithRoleProfessorTest() throws Exception {
		deleteStudentAndRenderStudentsPageAccessDenied();
	}
	
	private void deleteStudentAndRenderStudentsPageAccessDenied() throws Exception {
		mockMvc.perform(
				get("/students/delete")
				.param("studentId", "1")
				)
		       .andExpect(status().isForbidden())
                       .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithAnonymousUser
	public void deleteStudentAndRenderStudentsPageByAnonymousUserTest() throws Exception {
		deleteStudentAndRenderStudentsPageNotAuthenticated();
	}
	
	private void deleteStudentAndRenderStudentsPageNotAuthenticated() throws Exception {
		mockMvc.perform(
				get("/students/delete")
				.param("studentId", "1")
				)
		       .andExpect(status().is3xxRedirection())
                       .andExpect(redirectedUrlPattern("**/login"));
	}
	
	
}
