package college.dao.impl;

import org.springframework.stereotype.Component;
import college.dao.HibernateDao;
import college.model.SubjectProfessor;

@Component
public class SubjectProfessorDao extends HibernateDao<SubjectProfessor>{

	public SubjectProfessorDao(){
		setMyClass(SubjectProfessor.class);
	}
}
