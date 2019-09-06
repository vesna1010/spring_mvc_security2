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
	@InjectMocks
	@Autowired
	private StudyProgramController studyProgramController;
	@InjectMocks
	@Autowired
	private StudyProgramConverter studyProgramConverter;
	@Mock
	private DepartmentService departmentService;
	@InjectMocks
	@Autowired
	private DepartmentConverter departmentConverter;	
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderStudyProgramsPageWithAllStudyProgramsWithRoleAdminTest() throws Exception {
		renderStudyProgramsPageWithAllStudyPrograms();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderStudyProgramsPageWithAllStudyProgramsWithRoleUserTest() throws Exception {
		renderStudyProgramsPageWithAllStudyPrograms();
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderStudyProgramsPageWithAllStudyProgramsWithRoleProfessorTest() throws Exception {
		renderStudyProgramsPageWithAllStudyPrograms();
	}

	private void renderStudyProgramsPageWithAllStudyPrograms() throws Exception {
		when(studyProgramService.findAllStudyPrograms()).thenReturn(
				Arrays.asList(new StudyProgram(1L, "Study Program A"), new StudyProgram(2L, "Study Program B")));

		mockMvc.perform(get("/studyPrograms"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("studyPrograms", hasSize(2)))
		       .andExpect(view().name("studyPrograms/page"));

		verify(studyProgramService, times(1)).findAllStudyPrograms();
	}
	
	@Test
	@WithAnonymousUser
	public void renderStudentsPageWithAllStudentsWithAnonymousUserTest() throws Exception {
		renderStudyProgramsPageWithAllStudyProgramsNotAuthenticated();
	}
	
	private void renderStudyProgramsPageWithAllStudyProgramsNotAuthenticated() throws Exception {
		mockMvc.perform(get("/studyPrograms"))
	               .andExpect(status().is3xxRedirection())
                       .andExpect(redirectedUrlPattern("**/login"));
	}

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderStudyProgramsPageWithStudyProgramsByDepartmentWithRoleAdminTest() throws Exception {
		renderStudyProgramsPageWithStudyProgramsByDepartment();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderStudyProgramsPageWithStudyProgramsByDepartmentWithRoleUserTest() throws Exception {
		renderStudyProgramsPageWithStudyProgramsByDepartment();
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderStudyProgramsPageWithStudyProgramsByDepartmentWithRoleProfessorTest() throws Exception {
		renderStudyProgramsPageWithStudyProgramsByDepartment();
	}

	private void renderStudyProgramsPageWithStudyProgramsByDepartment() throws Exception {
		Department department = new Department(1L, "Department");
		
		when(departmentService.findDepartmentById(1L)).thenReturn(department);
		when(studyProgramService.findAllStudyProgramsByDepartment(department)).thenReturn(
				Arrays.asList(new StudyProgram(1L, "Study Program A"), new StudyProgram(2L, "Study Program B")));

		mockMvc.perform(
				get("/studyPrograms")
				.param("departmentId", "1")
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("studyPrograms", hasSize(2)))
		       .andExpect(view().name("studyPrograms/page"));

		verify(departmentService, times(1)).findDepartmentById(1L);
		verify(studyProgramService, times(1)).findAllStudyProgramsByDepartment(department);
	}
	
	@Test
	@WithAnonymousUser
	public void renderStudyProgramsPageWithStudyProgramsByDepartmentWithAnonymousUserTest() throws Exception {
		renderStudyProgramsPageWithStudyProgramsByDepartmentNotAuthenticated();
	}
	
	private void renderStudyProgramsPageWithStudyProgramsByDepartmentNotAuthenticated() throws Exception {
		mockMvc.perform(
				get("/studyPrograms")
				.param("departmentId", "1")
				)
		       .andExpect(status().is3xxRedirection())
                       .andExpect(redirectedUrlPattern("**/login"));
	}

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderEmptyStudyProgramFormWithRoleAdminTest() throws Exception {
		renderEmptyStudyProgramForm();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderEmptyStudyProgramFormWithRoleUserTest() throws Exception {
		renderEmptyStudyProgramForm();
	}

	private void renderEmptyStudyProgramForm() throws Exception {
		when(departmentService.findAllDepartments())
				.thenReturn(Arrays.asList(new Department(1L, "Department A"), new Department(2L, "Department B")));

		mockMvc.perform(get("/studyPrograms/form"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("studyProgram", is(new StudyProgram())))
		       .andExpect(model().attribute("departments", hasSize(2)))
		       .andExpect(view().name("studyPrograms/form"));

		verify(departmentService, times(1)).findAllDepartments();
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderEmptyStudyProgramFormWithRoleProfessorTest() throws Exception {
		renderEmptyStudyProgramFormAccessDenied();
	}
	
	private void renderEmptyStudyProgramFormAccessDenied() throws Exception {
		mockMvc.perform(get("/studyPrograms/form"))
	               .andExpect(status().isForbidden())
	               .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithAnonymousUser
	public void renderEmptyStudyProgramFormWithAnonymousUserTest() throws Exception {
		renderEmptyStudyProgramFormNotAuthenticated();
	}
	
	private void renderEmptyStudyProgramFormNotAuthenticated() throws Exception {
		mockMvc.perform(get("/studyPrograms/form"))
	               .andExpect(status().is3xxRedirection())
                       .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void saveStudyProgramAndRenderStudyProgramFormValidFormTest() throws Exception {
		saveStudyProgramAndRenderStudyProgramFormValidForm();
	} 
	
	private void saveStudyProgramAndRenderStudyProgramFormValidForm() throws Exception {
		Department department = new Department(1L, "Department");
		StudyProgram studyProgram = new StudyProgram(1L, "Study Program",
				new GregorianCalendar(2017, Calendar.JANUARY, 1).getTime(), 3, department);
		
		when(departmentService.findDepartmentById(1L)).thenReturn(department);
		doNothing().when(studyProgramService).saveOrUpdateStudyProgram(studyProgram);

		mockMvc.perform(
				post("/studyPrograms/save")
				.param("id", "1")
				.param("title", "Study Program")
				.param("dateOfCreation", "01-01-2017")
				.param("durationOfStudy", "3")
				.param("department", "1")
				.with(csrf())
				)
		       .andExpect(model().hasNoErrors())
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/studyPrograms/form"));

		verify(departmentService, times(1)).findDepartmentById(1L);
		verify(studyProgramService, times(1)).saveOrUpdateStudyProgram(studyProgram);
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void saveStudyProgramAndRenderStudyProgramFormInvalidFormTest() throws Exception {
		saveStudyProgramAndRenderStudyProgramFormInvalidForm();
	}
	
	private void saveStudyProgramAndRenderStudyProgramFormInvalidForm() throws Exception {
		Department department = new Department(1L, "Department");
		StudyProgram studyProgram = new StudyProgram(1L, "Study Program?????",
				new GregorianCalendar(2016, 4, 1).getTime(), 3, department);
		
		when(departmentService.findDepartmentById(1L)).thenReturn(department);
		doNothing().when(studyProgramService).saveOrUpdateStudyProgram(studyProgram);
		when(departmentService.findAllDepartments()).thenReturn(Arrays.asList(department));

		mockMvc.perform(
				post("/studyPrograms/save")
				.param("id", "1")
				.param("title", "Study Program?????")
				.param("durationOfStudy", "3")
				.param("dateOfCreation", "01-05-2016")
				.param("department", "1")
				.with(csrf())
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attributeHasFieldErrors("studyProgram", "title"))
		       .andExpect(model().attribute("studyProgram", is(studyProgram)))
		       .andExpect(model().attribute("departments", hasSize(1)))
		       .andExpect(view().name("studyPrograms/form"));

		verify(departmentService, times(1)).findDepartmentById(1L);
		verify(studyProgramService, times(0)).saveOrUpdateStudyProgram(studyProgram);
		verify(departmentService, times(1)).findAllDepartments();
	}

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderFormWithStudyProgramWithRoleAdminTest() throws Exception {
		renderFormWithStudyProgram();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderFormWithStudyProgramWithRoleUserTest() throws Exception {
		renderFormWithStudyProgram();
	}

	private void renderFormWithStudyProgram() throws Exception {
		StudyProgram studyProgram = new StudyProgram(1L, "Study Program");
		
		when(studyProgramService.findStudyProgramById(1L)).thenReturn(studyProgram);
		when(departmentService.findAllDepartments())
				.thenReturn(Arrays.asList(new Department(1L, "Department A"), new Department(2L, "Department B")));

		mockMvc.perform(
				get("/studyPrograms/edit")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("studyProgram", is(studyProgram)))
		       .andExpect(model().attribute("departments", hasSize(2)))
		       .andExpect(view().name("studyPrograms/form"));

		verify(studyProgramService, times(1)).findStudyProgramById(1L);
		verify(departmentService, times(1)).findAllDepartments();
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderFormWithStudyProgramWithRoleProfessorTest() throws Exception {
		renderFormWithStudyProgramAccessDenied();
	}
	
	private void renderFormWithStudyProgramAccessDenied() throws Exception {
		mockMvc.perform(
				get("/studyPrograms/edit")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithAnonymousUser
	public void renderFormWithStudyProgramWithAnonymousUserTest() throws Exception {
		renderFormWithStudyProgramNotAuthenitcated();
	}
	
	private void renderFormWithStudyProgramNotAuthenitcated() throws Exception {
		mockMvc.perform(
				get("/studyPrograms/edit")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().is3xxRedirection())
                       .andExpect(redirectedUrlPattern("**/login"));
	}

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void deleteStudyProgramAndRenderStudyProgramsPageWithRoleAdminTest() throws Exception {
		deleteStudyProgramAndRenderStudyProgramsPage();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void deleteStudyProgramAndRenderStudyProgramsPageWithRoleUserTest() throws Exception {
		deleteStudyProgramAndRenderStudyProgramsPage();
	}

	private void deleteStudyProgramAndRenderStudyProgramsPage() throws Exception {
		doNothing().when(studyProgramService).deleteStudyProgramById(1L);

		mockMvc.perform(
				get("/studyPrograms/delete")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/studyPrograms"));

		verify(studyProgramService, times(1)).deleteStudyProgramById(1L);
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void deleteStudyProgramAndRenderStudyProgramsPageWithRoleProfessorTest() throws Exception {
		deleteStudyProgramAndRenderStudyProgramsPageAccessDenied();
	}
	
	private void deleteStudyProgramAndRenderStudyProgramsPageAccessDenied() throws Exception {
		mockMvc.perform(
				get("/studyPrograms/delete")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithAnonymousUser
	public void deleteStudyProgramAndRenderStudyProgramsPageWithAnonymousUserTest() throws Exception {
		deleteStudyProgramAndRenderStudyProgramsPageNotAuthenticated();
	}
	
	private void deleteStudyProgramAndRenderStudyProgramsPageNotAuthenticated() throws Exception {
		mockMvc.perform(
				get("/studyPrograms/delete")
				.param("studyProgramId", "1")
				)
		       .andExpect(status().is3xxRedirection())
                       .andExpect(redirectedUrlPattern("**/login"));
	}

}


