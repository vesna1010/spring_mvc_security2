package college.dao.impl;

import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Component;
import college.dao.HibernateDao;
import college.model.Exam;
import college.model.Professor;
import college.model.Subject;

@Component
public class ExamDao extends HibernateDao<Exam> {

	
	public ExamDao(){
		setMyClass(Exam.class);
	}
	
	//returns exams for professor, subject and date
		@SuppressWarnings("unchecked")
		public List<Exam> findByObjects(Professor professor, Subject subject, Date date) {
			Query query= session().createQuery("from Exam where professor=:professor and date=:date and subject=:subject");
			query.setEntity("professor", professor);
			query.setEntity("subject", subject);
			query.setDate("date", date);
			return query.list();
		}
		
}
