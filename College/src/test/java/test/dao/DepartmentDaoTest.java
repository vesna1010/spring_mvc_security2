package test.dao;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import college.dao.DepartmentDao;
import college.model.Department;

public class DepartmentDaoTest extends BaseDaoTest {

	@Autowired
	private DepartmentDao departmentDao;

	@Test
	public void findAllDepartmentsTest() {
		List<Department> departments = departmentDao.findAll();

		assertThat(departments.size(), is(2));
	}

	@Test
	public void findDepartmentByIdTest() {
		Department department = departmentDao.findById(1L);

		assertThat(department.getTitle(), is("Department A"));
		assertThat(department.getStudyPrograms(), hasSize(2));
	}

	@Test
	public void saveOrUpdateDepartmentTest() {
		Department department = departmentDao.findById(1L);

		department.setTitle("Department");
		
		departmentDao.saveOrUpdate(department);

		department = departmentDao.findById(1L);

		assertThat(department.getTitle(), is("Department"));
		assertThat(department.getStudyPrograms(), hasSize(2));
	}

	@Test
	public void deleteDepartmentByIdTest() {
		departmentDao.deleteById(1L);

		assertNull(departmentDao.findById(1L));
	}

}
