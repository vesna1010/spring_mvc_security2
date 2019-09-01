package college.dao.impl;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import college.dao.ProfessorDao;
import college.dao.impl.HibernateDaoImpl;
import college.model.Professor;
import college.model.StudyProgram;

@SuppressWarnings("unchecked")
@Repository
public class ProfessorDaoImpl extends HibernateDaoImpl<Professor, Long> implements ProfessorDao {

	public ProfessorDaoImpl() {
		setEntityClass(Professor.class);
	}

	@Override
	public List<Professor> findAllByStudyProgram(StudyProgram studyProgram) {
		Query query = getSession()
				.createQuery("select distinct l.professor from Lecture l where l.subject.studyProgram=:studyProgram");

		query.setEntity("studyProgram", studyProgram);

		return query.list();
	}

}

