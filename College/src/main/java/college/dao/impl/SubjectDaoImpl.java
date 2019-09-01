package college.dao.impl;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import college.dao.SubjectDao;
import college.dao.impl.HibernateDaoImpl;
import college.model.StudyProgram;
import college.model.Subject;

@SuppressWarnings("unchecked")
@Repository
public class SubjectDaoImpl extends HibernateDaoImpl<Subject, Long> implements SubjectDao {

	public SubjectDaoImpl() {
		setEntityClass(Subject.class);
	}

	@Override
	public List<Subject> findAllByStudyProgram(StudyProgram studyProgram) {
		Query query = getSession().createQuery("select s from Subject s where s.studyProgram=:studyProgram");

		query.setEntity("studyProgram", studyProgram);

		return query.list();
	}

}

