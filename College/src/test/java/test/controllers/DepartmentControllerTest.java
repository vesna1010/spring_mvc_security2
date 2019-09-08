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
import college.controllers.DepartmentController;
import college.converters.DepartmentConverter;
import college.model.Department;
import college.service.DepartmentService;

public class DepartmentControllerTest extends BaseControllerTest {

	@Mock
	private DepartmentService departmentService;
	@InjectMocks
	@Autowired
	private DepartmentController departmentController;
	@InjectMocks
	@Autowired
	private DepartmentConverter departmentConverter;
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderDepartmentsPageWithAllDepartmentsWithRoleAdminTest() throws Exception {
		renderDepartmentsPageWithAllDepartments();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderDepartmentsPageWithAllDepartmentsWithRoleUserTest() throws Exception {
		renderDepartmentsPageWithAllDepartments();
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderDepartmentsPageWithAllDepartmentsWithRoleProfessorTest() throws Exception {
		renderDepartmentsPageWithAllDepartments();
	}

	private void renderDepartmentsPageWithAllDepartments() throws Exception {
		when(departmentService.findAllDepartments())
				.thenReturn(Arrays.asList(new Department(1L, "Department A"), new Department(2L, "Department B")));

		mockMvc.perform(get("/departments"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("departments", hasSize(2)))
		       .andExpect(view().name("departments/page"));

		verify(departmentService, times(1)).findAllDepartments();
	}
	
	@Test
	@WithAnonymousUser
	public void renderDepartmentsPageWithAllDepartmentsByAnonymousUserTest() throws Exception {	
		renderDepartmentsPageWithAllDepartmentsNotAuthenticated();
	}
	
	private void renderDepartmentsPageWithAllDepartmentsNotAuthenticated() throws Exception {
		mockMvc.perform(get("/departments"))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderEmptyDepartmentFormWithRoleAdminTest() throws Exception {
		renderEmptyDepartmentForm();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderEmptyDepartmentFormWithRoleUserTest() throws Exception {
		renderEmptyDepartmentForm();
	}

	private void renderEmptyDepartmentForm() throws Exception {
		mockMvc.perform(
				get("/departments/form")
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("department", is(new Department())))
		       .andExpect(view().name("departments/form"));
	}
	
	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderEmptyDepartmentFormByProfessorTest() throws Exception {
		renderEmptyDepartmentFormAccessDenied();
	}
	
	private void renderEmptyDepartmentFormAccessDenied() throws Exception {
		mockMvc.perform(
				get("/departments/form")
				)
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithAnonymousUser
	public void renderEmptyDepartmentFormByAnonymousUserTest() throws Exception {
		renderEmptyDepartmentFormNotAuthenticated();
	}
	
	private void renderEmptyDepartmentFormNotAuthenticated() throws Exception {
		mockMvc.perform(
				get("/departments/form")
				)
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void saveDepartmentAndRenderDepartmentFormValidFormTest() throws Exception {
		saveDepartmentAndRenderDepartmentFormValidForm();
	}
	
	private void saveDepartmentAndRenderDepartmentFormValidForm() throws Exception {
		Department department = new Department(1L, "Department", 
				new GregorianCalendar(2017, Calendar.JANUARY, 1).getTime());
		
		doNothing().when(departmentService).saveOrUpdateDepartment(department);

		mockMvc.perform(
				post("/departments/save")
				.param("id", "1")
				.param("title", "Department")
				.param("dateOfCreation", "01-01-2017")
				.with(csrf())
				)
		       .andExpect(model().hasNoErrors())
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/departments/form"));

		verify(departmentService, times(1)).saveOrUpdateDepartment(department);
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void saveDepartmentAndRenderDepartmentFormInvalidFormTest() throws Exception {
		saveDepartmentAndRenderDepartmentFormInvalidForm();
	}
	
	private void saveDepartmentAndRenderDepartmentFormInvalidForm() throws Exception {
		Department department = new Department(1L, "Department ???", 
				new GregorianCalendar(2017, Calendar.JANUARY, 1).getTime());

		doNothing().when(departmentService).saveOrUpdateDepartment(department);

		mockMvc.perform(
				post("/departments/save")
				.param("id", "1")
				.param("title", "Department ???")
				.param("dateOfCreation", "01-01-2017")
				.with(csrf())
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attributeHasFieldErrors("department", "title"))
		       .andExpect(model().attribute("department", is(department)))
		       .andExpect(view().name("departments/form"));

		verify(departmentService, times(0)).saveOrUpdateDepartment(department);
	}

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderDepartmentFormWithDepartmentWithRoleAdminTest() throws Exception {
		renderDepartmentFormWithDepartment();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderDepartmentFormWithDepartmentWithRoleUserTest() throws Exception {
		renderDepartmentFormWithDepartment();
	}
	
	private void renderDepartmentFormWithDepartment() throws Exception {
		Department department = new Department(1L, "Department");
		
		when(departmentService.findDepartmentById(1L)).thenReturn(department);
			
		mockMvc.perform(
				get("/departments/edit")
				.param("departmentId", "1")
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("department", is(department)))
		       .andExpect(view().name("departments/form"));

		verify(departmentService, times(1)).findDepartmentById(1L);
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderDepartmentFormWithDepartmentWithRoleProfessorTest() throws Exception {
		renderDepartmentFormWithDepartmentAccessDenied();
	}
	
	private void renderDepartmentFormWithDepartmentAccessDenied() throws Exception {
		mockMvc.perform(
				get("/departments/edit")
				.param("departmentId", "1")
				)
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithAnonymousUser
	public void renderDepartmentFormWithDepartmentWithAnonymousUserTest() throws Exception {
		renderDepartmentFormWithDepartmentNotAuthenticated();
	}
	
	private void renderDepartmentFormWithDepartmentNotAuthenticated() throws Exception {
		mockMvc.perform(
				get("/departments/edit")
				.param("departmentId", "1")
				)
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrlPattern("**/login"));
	}

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void deleteDepartmentAndRenderDepartmentsPageByAdminTest() throws Exception {
		deleteDepartmentAndRenderDepartmentsPage();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void deleteDepartmentAndRenderDepartmentsPagByUserTest() throws Exception {
		deleteDepartmentAndRenderDepartmentsPage();
	}

	private void deleteDepartmentAndRenderDepartmentsPage() throws Exception {
		doNothing().when(departmentService).deleteDepartmentById(1L);

		mockMvc.perform(
				get("/departments/delete")
				.param("departmentId", "1")
				)
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/departments"));

		verify(departmentService, times(1)).deleteDepartmentById(1L);
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void deleteDepartmentAndRenderDepartmentsPageWithRoleProfessorTest() throws Exception {
		deleteDepartmentAndRenderDepartmentsPageAccessDenied();
	}
	
	private void deleteDepartmentAndRenderDepartmentsPageAccessDenied() throws Exception {
		mockMvc.perform(
				get("/departments/delete")
				.param("departmentId", "1")
				)
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithAnonymousUser
	public void deleteDepartmentAndRenderDepartmentsPageByAnonymousUserTest() throws Exception {
		deleteDepartmentAndRenderDepartmentsPageNotAuthenticated();
	}
	
	private void deleteDepartmentAndRenderDepartmentsPageNotAuthenticated() throws Exception {
		mockMvc.perform(
				get("/departments/delete")
				.param("departmentId", "1")
				)
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrlPattern("**/login"));
	}

}

