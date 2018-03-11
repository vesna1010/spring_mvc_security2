package test.dao;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import college.dao.extensions.DepartmentDao;
import college.model.Department;

public class DepartmentDaoTest extends BaseDaoTest {

	@Autowired
	private DepartmentDao departmentDao;

	@Before
	public void setUp() {
		departmentDao.deleteAll();
		department1.addStudyProgram(studyProgram1);
		department1.addStudyProgram(studyProgram2);
		departmentDao.saveOrUpdate(department1);
	}

	@Test
	public void findAllDepartmentsTest() {
		Set<Department> departments = departmentDao.findAll();

		assertThat(departments.size(), is(1));
		assertTrue(departments.contains(department1));
		assertFalse(departments.contains(department2));
	}

	@Test
	public void findDepartmentByIdTest() {
		Department department = departmentDao.findById("D1");

		assertThat(department.getTitle(), is("Department 1"));
		assertThat(department.getStudyPrograms(), hasSize(2));
	}

	@Test
	public void saveDepartmentTest() {
		departmentDao.saveOrUpdate(department2);

		assertNotNull(departmentDao.findById("D2"));
	}

	@Test
	public void updateDepartmentTest() {
		department1.setTitle("Department");

		departmentDao.saveOrUpdate(department1);

		Department department = departmentDao.findById("D1");

		assertThat(department.getTitle(), is("Department"));
		assertThat(department.getStudyPrograms(), hasSize(2));
	}

	@Test
	public void deleteDepartmentByIdTest() {
		departmentDao.deleteById("D1");

		assertNull(departmentDao.findById("D1"));
	}
	
	@Test
	public void deleteDepartmentTest() {
		departmentDao.delete(department1);

		assertNull(departmentDao.findById("D1"));
	}
	

}
