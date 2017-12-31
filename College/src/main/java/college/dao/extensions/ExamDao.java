package college.dao.extensions;

import java.util.Date;
import java.util.Set;
import college.dao.HibernateDao;
import college.model.Exam;
import college.model.Professor;
import college.model.Subject;

public interface ExamDao extends HibernateDao<Long, Exam> {

	Set<Exam> findExamsByObjects(Professor professor, Subject subject, Date date);
}
