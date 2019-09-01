package college.dao.impl;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import college.dao.LectureDao;
import college.dao.impl.HibernateDaoImpl;
import college.model.Lecture;
import college.model.Professor;
import college.model.ProfessorSubjectId;
import college.model.StudyProgram;
import college.model.Subject;

@SuppressWarnings("unchecked")
@Repository
public class LectureDaoImpl extends HibernateDaoImpl<Lecture, ProfessorSubjectId> implements LectureDao {

	public LectureDaoImpl() {
		setEntityClass(Lecture.class);
	}

	@Override
	public List<Lecture> findAllByStudyProgram(StudyProgram studyProgram) {
		Query query = getSession().createQuery("select l from Lecture l where l.subject.studyProgram=:studyProgram");

		query.setEntity("studyProgram", studyProgram);

		return query.list();
	}

	@Override
	public void saveOrUpdate(Lecture lecture) {
		Session session = getSession();
		Professor professor = lecture.getProfessor();
		Subject subject = lecture.getSubject();

		professor = (Professor) session.merge(professor);
		subject = (Subject) session.merge(subject);

		lecture.setProfessor(professor);
		lecture.setSubject(subject);

		session.merge(lecture);
	}

}
