package college.dao.impl.extensions;

import org.springframework.stereotype.Component;
import college.dao.extensions.SubjectDao;
import college.dao.impl.HibernateDaoImpl;
import college.model.Subject;

@Component("subjectDao")
public class SubjectDaoImpl extends HibernateDaoImpl<String, Subject> implements SubjectDao {

	public SubjectDaoImpl() {
		setEntityClass(Subject.class);
	}

}

