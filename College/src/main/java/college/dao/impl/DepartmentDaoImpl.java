package college.dao.impl;

import org.springframework.stereotype.Repository;
import college.dao.DepartmentDao;
import college.dao.impl.HibernateDaoImpl;
import college.model.Department;

@Repository
public class DepartmentDaoImpl extends HibernateDaoImpl<Department, Long> implements DepartmentDao {

	public DepartmentDaoImpl() {
		setEntityClass(Department.class);
	}

}
