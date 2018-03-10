package college.service.impl;

import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import college.dao.extensions.DepartmentDao;
import college.model.Department;
import college.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Resource
	private DepartmentDao departmentDao;

	@Override
	public Set<Department> findAllDepartments() {	
		return departmentDao.findAll();
	}

	@Override
	public Department findDepartmentById(String id) {
		return departmentDao.findById(id);
	}

	@Override
	public void saveOrUpdateDepartment(Department department) {
		departmentDao.saveOrUpdate(department);
	}

	@Override
	public void deleteDepartment(Department department) {
		departmentDao.delete(department);
	}

}

