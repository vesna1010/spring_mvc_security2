package college.dao.impl.extensions;

import org.springframework.stereotype.Component;
import college.dao.extensions.StudentDao;
import college.dao.impl.HibernateDaoImpl;
import college.model.Student;

@Component("studentDao")
public class StudentDaoImpl extends HibernateDaoImpl<String, Student> implements StudentDao {

	public StudentDaoImpl() {
		setEntityClass(Student.class);
	}

}
