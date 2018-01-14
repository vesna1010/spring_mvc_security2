package test.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import college.controllers.DepartmentController;
import college.model.Department;
import college.service.DepartmentService;

public class DepartmentControllerTest extends BaseControllerTest {

	@Mock
	private DepartmentService departmentService;
	@InjectMocks
	@Autowired
	private DepartmentController departmentController;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wax).apply(springSecurity()).build();
	}

	@Test
	public void test() {
		assertNotNull(departmentService);
		assertNotNull(departmentController);
	}

	@Test
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD1", roles = "ADMIN")
	public void renderDepartmentsPageWithAllDepartmentsTest_By_Admin() throws Exception {
		renderDepartmentsPageWithAllDepartments_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void renderDepartmentsPageWithAllDepartmentsTest_By_User() throws Exception {
		renderDepartmentsPageWithAllDepartments_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_PROFESSOR", password = "PASSWORD3", roles = "PROFESSOR")
	public void renderDepartmentsPageWithAllDepartmentsTest_By_Professor() throws Exception {
		renderDepartmentsPageWithAllDepartments_Enabled();
	}

	private void renderDepartmentsPageWithAllDepartments_Enabled() throws Exception {
		Set<Department> departments = new HashSet<Department>(Arrays.asList(department1, department2));

		when(departmentService.findAllDepartments()).thenReturn(departments);

		mockMvc.perform(get("/departments"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("departments", is(departments)))
		       .andExpect(view().name("departmentsPage"));

		verify(departmentService, times(1)).findAllDepartments();
	}

	@Test
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD1", roles = "ADMIN")
	public void renderEmptyDepartmentForm_By_Admin() throws Exception {
		renderEmptyDepartmentForm_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void renderEmptyDepartmentForm_By_User() throws Exception {
		renderEmptyDepartmentForm_Enabled();
	}

	private void renderEmptyDepartmentForm_Enabled() throws Exception {
		mockMvc.perform(get("/departments/departmentForm"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("department", is(new Department())))
		       .andExpect(view().name("departmentForm"));
	}

	@Test
	@WithMockUser(username = "USERNAME_PROFESSOR", password = "PASSWORD3", roles = "PROFESSOR")
	public void renderEmptyDepartmentForm_By_Professor() throws Exception {
		renderEmptyDepartmentForm_Disabled();
	}

	private void renderEmptyDepartmentForm_Disabled() throws Exception {
		mockMvc.perform(get("/departments/departmentForm"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void saveDepartmentAndRenderDepartmentFormTest_ValidForm() throws Exception {
		doNothing().when(departmentService).saveOrUpdateDepartment(department1);

		mockMvc.perform(post("/departments/save").with(csrf())
				.param("id", "D1")
				.param("title", "Department 1")
				.param("dateOfCreation", "01-01-2017"))
		        .andExpect(model().hasNoErrors())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/departments/departmentForm"));

		verify(departmentService, times(1)).saveOrUpdateDepartment(department1);
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void saveDepartmentAndRenderDepartmentFormTest_InvalidForm() throws Exception {
		Department department = new Department("D3", "Department ???", 
				new GregorianCalendar(2017, Calendar.JANUARY, 1).getTime());

		doNothing().when(departmentService).saveOrUpdateDepartment(department);

		mockMvc.perform(post("/departments/save").with(csrf())
				.param("id", "D3")
				.param("title", "Department ???")
				.param("dateOfCreation", "01-01-2017"))
		       .andExpect(status().isOk())
		       .andExpect(model().attributeHasFieldErrors("department", "title"))
		       .andExpect(model().attribute("department", is(department)))
		       .andExpect(view().name("departmentForm"));

		verify(departmentService, times(0)).saveOrUpdateDepartment(department);
	}

	@Test
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD1", roles = "ADMIN")
	public void renderDepartmentFormWithDepartmentTest_By_Admin() throws Exception {
		renderDepartmentFormWithDepartment_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void renderDepartmentFormWithDepartmentTest_By_User() throws Exception {
		renderDepartmentFormWithDepartment_Enabled();
	}

	private void renderDepartmentFormWithDepartment_Enabled() throws Exception {
		when(departmentService.findDepartmentById("D1")).thenReturn(department1);

		mockMvc.perform(get("/departments/edit/" + "D1"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("department", is(department1)))
		       .andExpect(view().name("departmentForm"));

		verify(departmentService, times(1)).findDepartmentById("D1");
	}

	@Test
	@WithMockUser(username = "USERNAME_PROFESSOR", password = "PASSWORD3", roles = "PROFESSOR")
	public void renderDepartmentFormWithDepartmentTest_By_Professor() throws Exception {
		renderDepartmentFormWithDepartment_Disabled();
	}

	private void renderDepartmentFormWithDepartment_Disabled() throws Exception {
		when(departmentService.findDepartmentById("D1")).thenReturn(department1);

		mockMvc.perform(get("/departments/edit/" + "D1"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));

		verify(departmentService, times(0)).findDepartmentById("D1");
	}

	@Test
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD1", roles = "ADMIN")
	public void deleteDepartmentAndRenderDepartmentsPageTest_By_Admin() throws Exception {
		deleteDepartmentAndRenderDepartmentsPage_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void deleteDepartmentAndRenderDepartmentsPagTeste_By_User() throws Exception {
		deleteDepartmentAndRenderDepartmentsPage_Enabled();
	}

	private void deleteDepartmentAndRenderDepartmentsPage_Enabled() throws Exception {
		doNothing().when(departmentService).deleteDepartmentById("D1");

		mockMvc.perform(get("/departments/delete/" + "D1"))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/departments"));

		verify(departmentService, times(1)).deleteDepartmentById("D1");
	}

	@Test
	@WithMockUser(username = "USERNAME_PROFESSOR", password = "PASSWORD3", roles = "PROFESSOR")
	public void deleteDepartmentAndRenderDepartmentsPage_By_Professor() throws Exception {
		deleteDepartmentAndRenderDepartmentsPage_Disabled();
	}

	private void deleteDepartmentAndRenderDepartmentsPage_Disabled() throws Exception {
		doNothing().when(departmentService).deleteDepartmentById("D1");

		mockMvc.perform(get("/departments/delete/" + "D1"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));

		verify(departmentService, times(0)).deleteDepartmentById("D1");
	}

}

