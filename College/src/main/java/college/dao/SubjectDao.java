package college.dao;

import java.util.List;
import college.dao.HibernateDao;
import college.model.StudyProgram;
import college.model.Subject;

public interface SubjectDao extends HibernateDao<Subject, Long> {

	List<Subject> findAllByStudyProgram(StudyProgram studyProgram);

}

