package college.dao.impl;

import org.springframework.stereotype.Component;
import college.dao.HibernateDao;
import college.model.Department;

@Component
public class DepartmentDao extends HibernateDao<Department> {
	
	public DepartmentDao(){
		setMyClass(Department.class);
	}
	
}