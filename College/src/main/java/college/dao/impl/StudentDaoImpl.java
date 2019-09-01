package college.dao.impl;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import college.dao.StudentDao;
import college.dao.impl.HibernateDaoImpl;
import college.model.Student;
import college.model.StudyProgram;

@SuppressWarnings("unchecked")
@Repository
public class StudentDaoImpl extends HibernateDaoImpl<Student, Long> implements StudentDao {

	public StudentDaoImpl() {
		setEntityClass(Student.class);
	}

	@Override
	public List<Student> findAllByStudyProgram(StudyProgram studyProgram) {
		Query query = getSession().createQuery("select s from Student s where s.studyProgram=:studyProgram");

		query.setEntity("studyProgram", studyProgram);

		return query.list();
	}

}

