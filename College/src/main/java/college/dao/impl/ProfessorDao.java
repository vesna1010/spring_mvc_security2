package college.dao.impl;

import org.springframework.stereotype.Component;
import college.dao.HibernateDao;
import college.model.Professor;

@Component
public class ProfessorDao extends HibernateDao<Professor>{

	public ProfessorDao(){
		setMyClass(Professor.class);
	}
}
