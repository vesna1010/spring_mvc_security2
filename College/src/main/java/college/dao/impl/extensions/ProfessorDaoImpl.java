package college.dao.impl.extensions;

import org.springframework.stereotype.Component;
import college.dao.extensions.ProfessorDao;
import college.dao.impl.HibernateDaoImpl;
import college.model.Professor;

@Component("professorDao")
public class ProfessorDaoImpl extends HibernateDaoImpl<Professor> implements ProfessorDao {

	public ProfessorDaoImpl() {
		setEntityClass(Professor.class);
	}

}
