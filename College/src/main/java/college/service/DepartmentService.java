package college.service;

import java.util.List;
import college.model.Department;

public interface DepartmentService {

	List<Department> findAllDepartments();

	Department findDepartmentById(Long id);

	void saveOrUpdateDepartment(Department department);

	void deleteDepartmentById(Long id);

}



