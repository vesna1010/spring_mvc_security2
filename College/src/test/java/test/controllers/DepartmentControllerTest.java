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
import college.controllers.DepartmentController;
import college.converters.DepartmentConverter;
import college.model.Department;
import college.service.DepartmentService;

public class DepartmentControllerTest extends BaseControllerTest {

	@Mock
	private DepartmentService service;
	@InjectMocks
	@Autowired
	private DepartmentController controller;
	@InjectMocks
	@Autowired
	private DepartmentConverter converter;

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderDepartmentsPageWithAllDepartmentsByAdminTest() throws Exception {
		renderDepartmentsPageWithAllDepartments();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderDepartmentsPageWithAllDepartmentsByUserTest() throws Exception {
		renderDepartmentsPageWithAllDepartments();
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderDepartmentsPageWithAllDepartmentsByProfessorTest() throws Exception {
		renderDepartmentsPageWithAllDepartments();
	}

	private void renderDepartmentsPageWithAllDepartments() throws Exception {
		when(service.findAllDepartments()).thenReturn(
				new HashSet<Department>(Arrays.asList(department1, department2)));

		mockMvc.perform(get("/departments"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("departments", hasSize(2)))
		       .andExpect(view().name("departmentsPage"));

		verify(service, times(1)).findAllDepartments();
	}
	
	@Test
	@WithAnonymousUser
	public void renderDepartmentsPageWithAllDepartmentsByAnonymousUserTest() throws Exception {	
		mockMvc.perform(get("/departments"))
		       .andExpect(status().is3xxRedirection())
	         .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderEmptyDepartmentFormByAdminTest() throws Exception {
		renderEmptyDepartmentForm();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderEmptyDepartmentFormByUserTest() throws Exception {
		renderEmptyDepartmentForm();
	}

	private void renderEmptyDepartmentForm() throws Exception {
		mockMvc.perform(get("/departments/departmentForm"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("department", is(new Department())))
		       .andExpect(view().name("departmentForm"));
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderEmptyDepartmentFormByProfessorTest() throws Exception {
		mockMvc.perform(get("/departments/departmentForm"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithMockUser
	public void saveDepartmentAndRenderDepartmentFormValidFormTest() throws Exception {
		Department department = new Department("D1", "Department", 
				new GregorianCalendar(2017, Calendar.JANUARY, 1).getTime());
		
		doNothing().when(service).saveOrUpdateDepartment(department);

		mockMvc.perform(post("/departments/save").with(csrf())
				.param("id", "D1")
				.param("title", "Department")
				.param("dateOfCreation", "01-01-2017"))
		       .andExpect(model().hasNoErrors())
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/departments/departmentForm"));

		verify(service, times(1)).saveOrUpdateDepartment(department);
	}

	@Test
	@WithMockUser
	public void saveDepartmentAndRenderDepartmentFormInvalidFormTest() throws Exception {
		Department department = new Department("D1", "Department ???", 
				new GregorianCalendar(2017, Calendar.JANUARY, 1).getTime());

		doNothing().when(service).saveOrUpdateDepartment(department);

		mockMvc.perform(post("/departments/save").with(csrf())
				.param("id", "D1")
				.param("title", "Department ???")
				.param("dateOfCreation", "01-01-2017"))
		       .andExpect(status().isOk())
		       .andExpect(model().attributeHasFieldErrors("department", "title"))
		       .andExpect(model().attribute("department", is(department)))
		       .andExpect(view().name("departmentForm"));

		verify(service, times(0)).saveOrUpdateDepartment(department);
	}

	@Test
	@WithMockUser(username = "ADMIN", password = "PASSWORD", roles = "ADMIN")
	public void renderDepartmentFormWithDepartmentByAdminTest() throws Exception {
		renderDepartmentFormWithDepartment();
	}

	@Test
	@WithMockUser(username = "USER", password = "PASSWORD", roles = "USER")
	public void renderDepartmentFormWithDepartmentByUserTest() throws Exception {
		renderDepartmentFormWithDepartment();
	}
	
	private void renderDepartmentFormWithDepartment() throws Exception {
		when(service.findDepartmentById("D1")).thenReturn(department1);
			
		mockMvc.perform(get("/departments/edit/D1"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("department", is(department1)))
		       .andExpect(view().name("departmentForm"));

		verify(service, times(1)).findDepartmentById("D1");
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void renderDepartmentFormWithDepartmentByProfessorTest() throws Exception {
		mockMvc.perform(get("/departments/edit/D1"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
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
		when(service.findDepartmentById("D1")).thenReturn(department1);
		doNothing().when(service).deleteDepartment(department1);

		mockMvc.perform(get("/departments/delete/D1"))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/departments"));

		verify(service, times(1)).findDepartmentById("D1");
		verify(service, times(1)).deleteDepartment(department1);
	}

	@Test
	@WithMockUser(username = "PROFESSOR", password = "PASSWORD", roles = "PROFESSOR")
	public void deleteDepartmentAndRenderDepartmentsPageByProfessorTest() throws Exception {
		mockMvc.perform(get("/departments/delete/D1"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}

}

