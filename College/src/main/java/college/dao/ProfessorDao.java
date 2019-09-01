package college.dao;

import java.util.List;
import college.dao.HibernateDao;
import college.model.Professor;
import college.model.StudyProgram;

public interface ProfessorDao extends HibernateDao<Professor, Long> {

	List<Professor> findAllByStudyProgram(StudyProgram studyProgram);

}

