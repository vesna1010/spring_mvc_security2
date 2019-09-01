package college.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import college.dao.DepartmentDao;
import college.model.Department;
import college.service.DepartmentService;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

	private DepartmentDao departmentDao;

	@Autowired
	public DepartmentServiceImpl(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	@Override
	public List<Department> findAllDepartments() {
		return departmentDao.findAll();
	}

	@Override
	public Department findDepartmentById(Long id) {
		return departmentDao.findById(id);
	}

	@Override
	public void saveOrUpdateDepartment(Department department) {
		departmentDao.saveOrUpdate(department);
	}

	@Override
	public void deleteDepartmentById(Long id) {
		departmentDao.deleteById(id);
	}

}
