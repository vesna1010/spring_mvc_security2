package college.dao;

import java.util.List;
import college.dao.HibernateDao;
import college.model.Department;
import college.model.StudyProgram;

public interface StudyProgramDao extends HibernateDao<StudyProgram, Long> {

	List<StudyProgram> findAllByDepartment(Department department);
	
}

