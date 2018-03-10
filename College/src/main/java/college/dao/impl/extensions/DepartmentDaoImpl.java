package college.dao.impl.extensions;

import org.springframework.stereotype.Component;
import college.dao.extensions.DepartmentDao;
import college.dao.impl.HibernateDaoImpl;
import college.model.Department;

@Component("departmentDao")
public class DepartmentDaoImpl extends HibernateDaoImpl<String, Department> implements DepartmentDao {

	public DepartmentDaoImpl() {
		setEntityClass(Department.class);
	}

}


