package college.dao.impl;

import org.springframework.stereotype.Component;
import college.dao.HibernateDao;
import college.model.Subject;

@Component
public class SubjectDao extends HibernateDao<Subject> {
	
	public SubjectDao(){
		setMyClass(Subject.class);
	}
}
