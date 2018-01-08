package college.dao.impl.extensions;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import org.hibernate.Query;
import org.springframework.stereotype.Component;
import college.dao.extensions.ExamDao;
import college.dao.impl.HibernateDaoImpl;
import college.model.Exam;
import college.model.Professor;
import college.model.Subject;

@Component("examDao")
public class ExamDaoImpl extends HibernateDaoImpl<Exam> implements ExamDao {

	public ExamDaoImpl() {
		setEntityClass(Exam.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Exam> findExamsByObjects(Professor professor, Subject subject, Date date) {
		Query query = getSession().createQuery(
				"from Exam where professor=:professor and date=:date and subject=:subject");
		
		query.setEntity("professor", professor);
		query.setEntity("subject", subject);
		query.setDate("date", date);
		
		return new HashSet<Exam>(query.list());
	}
	
}
