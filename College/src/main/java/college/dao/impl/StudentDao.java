package college.dao.impl;

import org.springframework.stereotype.Component;
import college.dao.HibernateDao;
import college.model.Student;

@Component
public class StudentDao extends HibernateDao<Student> {

	public StudentDao(){
		setMyClass(Student.class);
	}
}
