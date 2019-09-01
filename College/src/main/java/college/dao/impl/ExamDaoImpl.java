package college.dao.impl;

import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import college.dao.ExamDao;
import college.dao.impl.HibernateDaoImpl;
import college.model.Exam;
import college.model.Professor;
import college.model.Student;
import college.model.StudentSubjectId;
import college.model.StudyProgram;
import college.model.Subject;

@SuppressWarnings("unchecked")
@Repository
public class ExamDaoImpl extends HibernateDaoImpl<Exam, StudentSubjectId> implements ExamDao {

	public ExamDaoImpl() {
		setEntityClass(Exam.class);
	}

	@Override
	public List<Exam> findAllByStudyProgram(StudyProgram studyProgram) {
		Query query = getSession().createQuery("select e from Exam e where e.subject.studyProgram=:studyProgram");

		query.setEntity("studyProgram", studyProgram);

		return query.list();
	}

	@Override
	public List<Exam> findAllByStudent(Student student) {
		Query query = getSession().createQuery("select e from Exam e where e.student=:student");

		query.setEntity("student", student);

		return query.list();
	}

	@Override
	public List<Exam> findAllByProfessorAndSubjectAndDate(Professor professor, Subject subject, Date date) {
		Query query = getSession().createQuery(
				"select e from Exam e where e.subject=:subject and e.professor=:professor and e.date=:date");

		query.setEntity("subject", subject);
		query.setEntity("professor", professor);
		query.setDate("date", date);

		return query.list();
	}

	@Override
	public void saveOrUpdate(Exam exam) {
		Session session = getSession();
		Subject subject = exam.getSubject();
		Professor professor = exam.getProfessor();
		Student student = exam.getStudent();

		subject = (Subject) session.merge(subject);
		professor = (Professor) session.merge(professor);
		student = (Student) session.merge(student);

		exam.setSubject(subject);
		exam.setProfessor(professor);
		exam.setStudent(student);

		session.merge(exam);
	}

}
