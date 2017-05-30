package college.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import college.dao.HibernateDao;
import college.model.Professor;

@Component
public class ProfessorDao extends HibernateDao<Professor>{

	public ProfessorDao(){
		setMyClass(Professor.class);
	}
	
	@Override
	public void save(Professor professor){
		Query query=session().createQuery("delete from SubjectProfessor where professor=:professor");
		query.setEntity("professor", professor);
		query.executeUpdate();
		session().saveOrUpdate(professor);
	}
}
