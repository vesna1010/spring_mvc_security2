package college.service;

import java.util.Set;
import college.model.Department;

public interface DepartmentService {

	Set<Department> findAllDepartments();

	Department findDepartmentById(String id);
	
	void saveOrUpdateDepartment(Department department);

	void deleteDepartment(Department department);

}


