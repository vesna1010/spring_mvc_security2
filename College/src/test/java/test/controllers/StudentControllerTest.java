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
import java.util.HashSet;
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
	@Mock
	private StudyProgramService studyProgramService;
	@InjectMocks
	@Autowired
	private StudentController studentController;
	@InjectMocks
	@Autowired
	private StudentConverter studentConverter;
	@InjectMocks
	@Autowired
	private StudyProgramConverter studyProgramConverter;
	
	{	
		studyProgram1.addStudent(student1);
		studyProgram1.addStudent(student2);
	}

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderStudentsPageWithAllStudentsByAdminTest() throws Exception {
		renderStudentsPageWithAllStudents();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderStudentsPageWithAllStudentsByUserTest() throws Exception {
		renderStudentsPageWithAllStudents();
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderStudentsPageWithAllStudentsByProfessorTest() throws Exception {
		renderStudentsPageWithAllStudents();
	}
	
	private void renderStudentsPageWithAllStudents() throws Exception {
		when(studentService.findAllStudents()).thenReturn(
				new HashSet<Student>(Arrays.asList(student1, student2)));
		
		mockMvc.perform(get("/students"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("title", is("All Students")))
		       .andExpect(model().attribute("students", hasSize(2)))
		       .andExpect(view().name("studentsPage"));
		
		verify(studentService, times(1)).findAllStudents();
	}
	
	@Test
	@WithAnonymousUser
	public void renderStudentsPageWithAllStudentsByAnonymousUserTest() throws Exception {
		mockMvc.perform(get("/students"))
		       .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderStudentsPageWithStudentsByStudyProgramByAdminTest() throws Exception {
		renderStudentsPageWithStudentsByStudyProgram();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderStudentsPageWithStudentsByStudyProgramByUserTest() throws Exception {
		renderStudentsPageWithStudentsByStudyProgram();
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderStudentsPageWithStudentsByStudyProgramByProfessorTest() throws Exception {
		renderStudentsPageWithStudentsByStudyProgram();
	}
	
	private void renderStudentsPageWithStudentsByStudyProgram() throws Exception {
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
		
		mockMvc.perform(get("/students").param("studyProgram", "SP1"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("title", is("Students at Study Program 1")))
		       .andExpect(model().attribute("students", hasSize(2)))
		       .andExpect(view().name("studentsPage"));
		
		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
	}
	
	@Test
	@WithAnonymousUser
	public void renderStudentsPageWithStudentsByStudyProgramByAnonymousUserTest() throws Exception {
		mockMvc.perform(get("/students").param("studyProgram", "SP1"))
		       .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderEmptyStudentFormByAdminTest() throws Exception {
		renderEmptyStudentForm();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderEmptyStudentFormByUserTest() throws Exception {
		renderEmptyStudentForm();
	}
	
	private void renderEmptyStudentForm() throws Exception {	
		when(studyProgramService.findAllStudyPrograms()).thenReturn(
				new HashSet<StudyProgram>(Arrays.asList(studyProgram1, studyProgram2)));
		
		mockMvc.perform(get("/students/studentForm"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("student", is(new Student())))
		       .andExpect(model().attribute("studyPrograms", hasSize(2)))
               .andExpect(view().name("studentForm"));
		    	
		verify(studyProgramService, times(1)).findAllStudyPrograms();
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderEmptyStudentFormByProfessorTest() throws Exception {
		mockMvc.perform(get("/students/studentForm"))
		       .andExpect(status().isForbidden())
               .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithMockUser
	public void saveStudentAndRenderStudentFormValidFormTest() throws Exception {	
		Student student = new Student("S1", "First Student", "First Father Name",
				new GregorianCalendar(1995, Calendar.MAY, 5).getTime(), "email@gmail.com", 
				"065-123-456", Gender.MALE, new Address("City", "Street", "State"), 
				new GregorianCalendar(2016, Calendar.SEPTEMBER, 1).getTime(), 1, studyProgram1);
		
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
		doNothing().when(studentService).saveOrUpdateStudent(student);
		when(studyProgramService.findAllStudyPrograms()).thenReturn(
				new HashSet<StudyProgram>(Arrays.asList(studyProgram1, studyProgram2)));
			
		mockMvc.perform(
				post("/students/save").with(csrf())
				.param("id", "S1")
				.param("fullName", "First Student")
				.param("fatherName", "First Father Name")
				.param("dateOfBirth", "05-05-1995")
				.param("email", "email@gmail.com")
				.param("telephone", "065-123-456")
				.param("gender", "MALE")
				.param("image", "")
				.param("address", "City - Street - State")
				.param("dateOfEntry", "01-09-2016")
				.param("yearOfStudy", "1")
				.param("studyProgram", "SP1"))
		       .andExpect(model().hasNoErrors())
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/students/studentForm"));
		
		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
		verify(studentService, times(1)).saveOrUpdateStudent(student);
		verify(studyProgramService, times(0)).findAllStudyPrograms();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void saveStudentAndRenderStudentFormInvalidFormTest() throws Exception {
		Student student = new Student("S1", "First Student", "First Father Name", 
				new GregorianCalendar(5, Calendar.MAY, 1995).getTime(), "invalidEmail", 
				"065-123-456", Gender.MALE, new Address("City", "Street", "State"), 
				new GregorianCalendar(2016, Calendar.SEPTEMBER, 1).getTime(), 1, studyProgram1);
		
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
		doNothing().when(studentService).saveOrUpdateStudent(student);
		when(studyProgramService.findAllStudyPrograms()).thenReturn(
				new HashSet<StudyProgram>(Arrays.asList(studyProgram1, studyProgram2)));
		
		mockMvc.perform(
				post("/students/save").with(csrf())
				.param("id", "S1")
				.param("fullName", "First Student")
				.param("fatherName", "First Father Name")
				.param("dateOfBirth", "05-05-1995")
				.param("email", "invalidEmail")
				.param("telephone", "065-123-456")
				.param("gender", "MALE")
				.param("image", "")
				.param("address", "City - Street - State")
				.param("dateOfEntry", "01-09-2016")
				.param("yearOfStudy", "1")
				.param("studyProgram", "SP1"))
		       .andExpect(model().attributeHasFieldErrors("student", "email"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("student", is(student)))
		       .andExpect(model().attribute("studyPrograms", hasSize(2)))
               .andExpect(view().name("studentForm"));
		
		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
		verify(studentService, times(0)).saveOrUpdateStudent(student1);
		verify(studyProgramService, times(1)).findAllStudyPrograms();
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderStudentFormWithStudentByAdminTest() throws Exception {
		renderStudentFormWithStudent();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderStudentFormWithStudentByUserTest() throws Exception {
		renderStudentFormWithStudent();
	}

	private void renderStudentFormWithStudent() throws Exception {
		when(studentService.findStudentById("S1")).thenReturn(student1);
	    when(studyProgramService.findAllStudyPrograms()).thenReturn(
	    		new HashSet<StudyProgram>(Arrays.asList(studyProgram1, studyProgram2)));
		
		mockMvc.perform(get("/students/edit/S1"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("student", is(student1)))
		       .andExpect(model().attribute("studyPrograms", hasSize(2)))
		       .andExpect(view().name("studentForm"));
		
		verify(studentService, times(1)).findStudentById("S1");
		verify(studyProgramService, times(1)).findAllStudyPrograms();
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderStudentFormWithStudentByProfessorTest() throws Exception {
		mockMvc.perform(get("/students/edit/S1"))
		       .andExpect(status().isForbidden())
               .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void deleteStudentAndRenderStudentsPageByAdminTest() throws Exception {
		deleteStudentAndRenderStudentsPage();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void deleteStudentAndRenderStudentsPageByUserTest() throws Exception {
		deleteStudentAndRenderStudentsPage();
	}
	
	private void deleteStudentAndRenderStudentsPage() throws Exception {
		when(studentService.findStudentById("S1")).thenReturn(student1);
		doNothing().when(studentService).deleteStudent(student1);
		
		mockMvc.perform(get("/students/delete/S1"))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/students"));
		
		verify(studentService, times(1)).findStudentById("S1");
		verify(studentService, times(1)).deleteStudent(student1);
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void deleteStudentAndRenderStudentsPageByProfessorTest() throws Exception {
		mockMvc.perform(get("/students/delete/S1"))
		       .andExpect(status().isForbidden())
               .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderStudentExamsPageWithStudentByAdminTest() throws Exception {
		renderStudentExamsPageWithStudent();
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderStudentExamsPageWithStudentByUserTest() throws Exception {
		renderStudentExamsPageWithStudent();
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderStudentExamsPageWithStudentByProfessorTest() throws Exception {
		renderStudentExamsPageWithStudent();
	}
	
	private void renderStudentExamsPageWithStudent() throws Exception {
		when(studentService.findStudentById("S1")).thenReturn(student1);
		
		mockMvc.perform(get("/students/exams/S1"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("student", is(student1)))
		       .andExpect(view().name("studentExamsPage"));
		
		verify(studentService, times(1)).findStudentById("S1");
	}
	
	@Test
	@WithAnonymousUser
	public void renderStudentExamsPageWithStudentByAnonymousUserTest() throws Exception {
		mockMvc.perform(get("/students/exams/S1"))
		       .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}
	
}
