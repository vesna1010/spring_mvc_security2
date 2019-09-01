package college.dao;

import java.util.List;
import college.dao.HibernateDao;
import college.model.Student;
import college.model.StudyProgram;

public interface StudentDao extends HibernateDao<Student, Long> {

	List<Student> findAllByStudyProgram(StudyProgram studyProgram);

}

