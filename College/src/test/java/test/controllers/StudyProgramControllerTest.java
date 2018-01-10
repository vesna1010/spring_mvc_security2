package test.controllers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
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
import java.util.HashSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import college.controllers.StudyProgramController;
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

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wax).apply(springSecurity()).build();
		department1.addStudyProgram(studyProgram1);
		department1.addStudyProgram(studyProgram2);
	}

	@Test
	public void mocksTest() {
		assertNotNull(studyProgramService);
		assertNotNull(departmentService);
		assertNotNull(studyProgramController);
	}

	@Test
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD1", roles = "ADMIN")
	public void renderStudyProgramsPageWithAllStudyProgramsTest_By_Admin() throws Exception {
		renderStudyProgramsPageWithAllStudyPrograms_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void renderStudyProgramsPageWithAllStudyProgramsTest_By_User() throws Exception {
		renderStudyProgramsPageWithAllStudyPrograms_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_PROFESSOR", password = "PASSWORD3", roles = "PROFESSOR")
	public void renderStudyProgramsPageWithAllStudyProgramsTest_By_Professor() throws Exception {
		renderStudyProgramsPageWithAllStudyPrograms_Enabled();
	}

	private void renderStudyProgramsPageWithAllStudyPrograms_Enabled() throws Exception {
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
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD1", roles = "ADMIN")
	public void renderStudyProgramsPageWithStudyProgramsByDepartmentTest_By_Admin() throws Exception {
		renderStudyProgramsPageWithStudyProgramsByDepartment_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void renderStudyProgramsPageWithStudyProgramsByDepartmentTest_By_User() throws Exception {
		renderStudyProgramsPageWithStudyProgramsByDepartment_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_PROFESSOR", password = "PASSWORD3", roles = "PROFESSOR")
	public void renderStudyProgramsPageWithStudyProgramsByDepartmentTest_By_Professor() throws Exception {
		renderStudyProgramsPageWithStudyProgramsByDepartment_Enabled();
	}

	private void renderStudyProgramsPageWithStudyProgramsByDepartment_Enabled() throws Exception {
		when(departmentService.findDepartmentById("D1")).thenReturn(department1);

		mockMvc.perform(get("/studyPrograms").param("departmentId", "D1"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("title", is("Study Programs at Department 1")))
		       .andExpect(model().attribute("studyPrograms", hasSize(2)))
		       .andExpect(view().name("studyProgramsPage"));

		verify(departmentService, times(1)).findDepartmentById("D1");
	}

	@Test
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD1", roles = "ADMIN")
	public void renderEmptyStudyProgramFormTest_By_Admin() throws Exception {
		renderEmptyStudyProgramForm_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void renderEmptyStudyProgramFormTest_By_User() throws Exception {
		renderEmptyStudyProgramForm_Enabled();
	}

	private void renderEmptyStudyProgramForm_Enabled() throws Exception {
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
	@WithMockUser(username = "USERNAME_PROFESSOR", password = "PASSWORD3", roles = "PROFESSOR")
	public void renderEmptyStudyProgramFormTest_By_Professor() throws Exception {
		renderEmptyStudyProgramForm_Disabled();
	}

	private void renderEmptyStudyProgramForm_Disabled() throws Exception {
		mockMvc.perform(get("/studyPrograms/studyProgramForm"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void saveStudyProgramAndRenderStudyProgramFormTest_ValidForm() throws Exception {
		doNothing().when(studyProgramService).saveOrUpdateStudyProgram(studyProgram1);

		mockMvc.perform(post("/studyPrograms/save").with(csrf())
				.param("id", "SP1").param("title", "Study Program 1")
				.param("durationOfStudy", "3").param("department", "D1"))
		       .andExpect(model().hasNoErrors())
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/studyPrograms/studyProgramForm"));

		verify(studyProgramService, times(1)).saveOrUpdateStudyProgram(studyProgram1);
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void saveStudyProgramAndRenderStudyProgramFormTest_InvalidForm() throws Exception {
		StudyProgram studyProgram = new StudyProgram("SP1", "Study Program?????", department1);

		doNothing().when(studyProgramService).saveOrUpdateStudyProgram(studyProgram);
		when(departmentService.findAllDepartments()).thenReturn(
				new HashSet<Department>(Arrays.asList(department1, department2)));

		mockMvc.perform(post("/studyPrograms/save").with(csrf())
				.param("id", "SP1").param("title", "Study Program?????")
				.param("durationOfStudy", "3")
				.param("department", "D1"))
		       .andExpect(model().attributeHasFieldErrors("studyProgram", "title"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("studyProgram", is(studyProgram)))
		       .andExpect(model().attribute("departments", hasSize(2)))
		       .andExpect(view().name("studyProgramForm"));

		verify(studyProgramService, times(0)).saveOrUpdateStudyProgram(studyProgram);
		verify(departmentService, times(1)).findAllDepartments();
	}

	@Test
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD1", roles = "ADMIN")
	public void renderFormWithStudyProgramTest_By_Admin() throws Exception {
		renderFormWithStudyProgram_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void renderFormWithStudyProgramTest_By_User() throws Exception {
		renderFormWithStudyProgram_Enabled();
	}

	private void renderFormWithStudyProgram_Enabled() throws Exception {
		when(studyProgramService.findStudyProgramById("SP1")).thenReturn(studyProgram1);
		when(departmentService.findAllDepartments()).thenReturn(
				new HashSet<Department>(Arrays.asList(department1, department2)));

		mockMvc.perform(get("/studyPrograms/edit/" + "SP1")).andExpect(status().isOk())
		       .andExpect(model().attribute("studyProgram", is(studyProgram1)))
		       .andExpect(model().attribute("departments", hasSize(2)))
		       .andExpect(view().name("studyProgramForm"));

		verify(studyProgramService, times(1)).findStudyProgramById("SP1");
		verify(departmentService, times(1)).findAllDepartments();
	}

	@Test
	@WithMockUser(username = "USERNAME_PROFESSOR", password = "PASSWORD3", roles = "PROFESSOR")
	public void renderFormWithStudyProgramTest_By_Professor() throws Exception {
		renderFormWithStudyProgram_Disabled();
	}

	public void renderFormWithStudyProgram_Disabled() throws Exception {
		mockMvc.perform(get("/studyPrograms/edit/" + "SP1"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}

	@Test
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD1", roles = "ADMIN")
	public void deleteStudyProgramAndRenderStudyProgramsPageTest_By_Admin() throws Exception {
		deleteStudyProgramAndRenderStudyProgramsPage_Enabled();
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD2", roles = "USER")
	public void deleteStudyProgramAndRenderStudyProgramsPageTest_By_User() throws Exception {
		deleteStudyProgramAndRenderStudyProgramsPage_Enabled();
	}

	private void deleteStudyProgramAndRenderStudyProgramsPage_Enabled() throws Exception {
		doNothing().when(studyProgramService).deleteStudyProgramById("SP1");

		mockMvc.perform(get("/studyPrograms/delete/" + "SP1"))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/studyPrograms"));

		verify(studyProgramService, times(1)).deleteStudyProgramById("SP1");
	}

	@Test
	@WithMockUser(username = "USERNAME_PROFESSOR", password = "PASSWORD3", roles = "PROFESSOR")
	public void deleteStudyProgramAndRenderStudyProgramsPageTest_By_Professor() throws Exception {
		deleteStudyProgramAndRenderStudyProgramsPage_Disabled();
	}

	private void deleteStudyProgramAndRenderStudyProgramsPage_Disabled() throws Exception {
		mockMvc.perform(get("/studyPrograms/delete/" + "SP1"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}

}


