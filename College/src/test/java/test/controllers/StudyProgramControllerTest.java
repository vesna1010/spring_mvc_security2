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
import college.controllers.StudyProgramController;
import college.converters.DepartmentConverter;
import college.converters.StudyProgramConverter;
import college.model.Department;
import college.model.StudyProgram;
import college.service.StudyProgramService;
import college.service.DepartmentService;

public class StudyProgramControllerTest extends BaseControllerTest {

	@Mock
	private StudyProgramService studyProgramService;
	@Mock
	private DepartmentService departmentService;
	@InjectMocks
	@Autowired
	private StudyProgramController studyProgramController;
	@InjectMocks
	@Autowired
	private DepartmentConverter departmentConverter;
	@InjectMocks
	@Autowired
	private StudyProgramConverter studyProgramConverter;
	
	{	
		department1.addStudyProgram(studyProgram1);
		department1.addStudyProgram(studyProgram2);
	}

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderStudyProgramsPageWithAllStudyProgramsByAdminTest() throws Exception {
		renderStudyProgramsPageWithAllStudyPrograms();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderStudyProgramsPageWithAllStudyProgramsByUserTest() throws Exception {
		renderStudyProgramsPageWithAllStudyPrograms();
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderStudyProgramsPageWithAllStudyProgramsByProfessorTest() throws Exception {
		renderStudyProgramsPageWithAllStudyPrograms();
	}

	private void renderStudyProgramsPageWithAllStudyPrograms() throws Exception {
		when(studyProgramService.findAllStudyPrograms()).thenReturn(
				new HashSet<StudyProgram>(Arrays.asList(studyProgram1, studyProgram2)));

		mockMvc.perform(get("/studyPrograms"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("title", is("All Study Programs")))
		       .andExpect(model().attribute("studyPrograms", hasSize(2)))
		       .andExpect(view().name("studyProgramsPage"));

		verify(studyProgramService, times(1)).findAllStudyPrograms();
	}
	
	@Test
	@WithAnonymousUser
	public void renderStudentsPageWithAllStudentsByAnonymousUserTest() throws Exception {
		mockMvc.perform(get("/studyPrograms"))
		       .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderStudyProgramsPageWithStudyProgramsByDepartmentByAdminTest() throws Exception {
		renderStudyProgramsPageWithStudyProgramsByDepartment();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderStudyProgramsPageWithStudyProgramsByDepartmentByUserTest() throws Exception {
		renderStudyProgramsPageWithStudyProgramsByDepartment();
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderStudyProgramsPageWithStudyProgramsByDepartmentByProfessorTest() throws Exception {
		renderStudyProgramsPageWithStudyProgramsByDepartment();
	}

	private void renderStudyProgramsPageWithStudyProgramsByDepartment() throws Exception {
		when(departmentService.findDepartmentById("D1")).thenReturn(department1);

		mockMvc.perform(get("/studyPrograms").param("department", "D1"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("title", is("Study Programs at Department 1")))
		       .andExpect(model().attribute("studyPrograms", hasSize(2)))
		       .andExpect(view().name("studyProgramsPage"));

		verify(departmentService, times(1)).findDepartmentById("D1");
	}
	
	@Test
	@WithAnonymousUser
	public void renderStudyProgramsPageWithStudyProgramsByDepartmentByAnonymousUserTest() throws Exception {
		mockMvc.perform(get("/studyPrograms").param("department", "D1"))
		       .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
	}

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderEmptyStudyProgramFormByAdminTest() throws Exception {
		renderEmptyStudyProgramForm();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderEmptyStudyProgramFormByUserTest() throws Exception {
		renderEmptyStudyProgramForm();
	}

	private void renderEmptyStudyProgramForm() throws Exception {
		when(departmentService.findAllDepartments()).thenReturn(
				new HashSet<Department>(Arrays.asList(department1, department2)));

		mockMvc.perform(get("/studyPrograms/studyProgramForm"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("studyProgram", is(new StudyProgram())))
		       .andExpect(model().attribute("departments", hasSize(2)))
		       .andExpect(view().name("studyProgramForm"));

		verify(departmentService, times(1)).findAllDepartments();
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderEmptyStudyProgramFormByProfessorTest() throws Exception {
		mockMvc.perform(get("/studyPrograms/studyProgramForm"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithMockUser
	public void saveStudyProgramAndRenderStudyProgramFormValidFormTest() throws Exception {
		StudyProgram studyProgram = new StudyProgram("SP1", "Study Program",
				new GregorianCalendar(2017, Calendar.JANUARY, 1).getTime(), 3, department1);
		
		when(departmentService.findDepartmentById("D1")).thenReturn(department1);
		doNothing().when(studyProgramService).saveOrUpdateStudyProgram(studyProgram);
		when(departmentService.findAllDepartments()).thenReturn(
				new HashSet<Department>(Arrays.asList(department1, department2)));

		mockMvc.perform(post("/studyPrograms/save").with(csrf())
				.param("id", "SP1")
				.param("title", "Study Program")
				.param("dateOfCreation", "01-01-2017")
				.param("durationOfStudy", "3")
				.param("department", "D1"))
		       .andExpect(model().hasNoErrors())
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/studyPrograms/studyProgramForm"));

		verify(departmentService, times(1)).findDepartmentById("D1");
		verify(studyProgramService, times(1)).saveOrUpdateStudyProgram(studyProgram);
		verify(departmentService, times(0)).findAllDepartments();
	} 

	@Test
	@WithMockUser
	public void saveStudyProgramAndRenderStudyProgramFormInvalidFormTest() throws Exception {
		StudyProgram studyProgram = new StudyProgram("SP1", "Study Program?????",
				new GregorianCalendar(2016, 4, 1).getTime(), 3, department1);
		
		when(departmentService.findDepartmentById("D1")).thenReturn(department1);
		doNothing().when(studyProgramService).saveOrUpdateStudyProgram(studyProgram);
		when(departmentService.findAllDepartments()).thenReturn(
				new HashSet<Department>(Arrays.asList(department1, department2)));

		mockMvc.perform(post("/studyPrograms/save").with(csrf())
				.param("id", "SP1")
				.param("title", "Study Program?????")
				.param("durationOfStudy", "3")
				.param("dateOfCreation", "01-05-2016")
				.param("department", "D1"))
		       .andExpect(status().isOk())
		       .andExpect(model().attributeHasFieldErrors("studyProgram", "title"))
		       .andExpect(model().attribute("studyProgram", is(studyProgram)))
		       .andExpect(model().attribute("departments", hasSize(2)))
		       .andExpect(view().name("studyProgramForm"));

		verify(departmentService, times(1)).findDepartmentById("D1");
		verify(studyProgramService, times(0)).saveOrUpdateStudyProgram(studyProgram);
		verify(departmentService, times(1)).findAllDepartments();
	}

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderFormWithStudyProgramByAdminTest() throws Exception {
		renderFormWithStudyProgram();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderFormWithStudyProgramByUserTest() throws Exception {
		renderFormWithStudyProgram();
	}

	private void renderFormWithStudyProgram() throws Exception {
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
		when(departmentService.findAllDepartments()).thenReturn(
				new HashSet<Department>(Arrays.asList(department1, department2)));

		mockMvc.perform(get("/studyPrograms/edit/SP1"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("studyProgram", is(studyProgram1)))
		       .andExpect(model().attribute("departments", hasSize(2)))
		       .andExpect(view().name("studyProgramForm"));

		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
		verify(departmentService, times(1)).findAllDepartments();
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderFormWithStudyProgramByProfessorTest() throws Exception {
		mockMvc.perform(get("/studyPrograms/edit/SP1"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void deleteStudyProgramAndRenderStudyProgramsPageByAdminTest() throws Exception {
		deleteStudyProgramAndRenderStudyProgramsPage();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void deleteStudyProgramAndRenderStudyProgramsPageByUserTest() throws Exception {
		deleteStudyProgramAndRenderStudyProgramsPage();
	}

	private void deleteStudyProgramAndRenderStudyProgramsPage() throws Exception {
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
		doNothing().when(studyProgramService).deleteStudyProgram(studyProgram1);

		mockMvc.perform(get("/studyPrograms/delete/SP1"))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/studyPrograms"));

		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
		verify(studyProgramService, times(1)).deleteStudyProgram(studyProgram1);
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void deleteStudyProgramAndRenderStudyProgramsPageByProfessorTest() throws Exception {
		mockMvc.perform(get("/studyPrograms/delete/SP1"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}

}


