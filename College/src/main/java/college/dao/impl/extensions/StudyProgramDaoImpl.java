package college.dao.impl.extensions;

import org.springframework.stereotype.Component;
import college.dao.extensions.StudyProgramDao;
import college.dao.impl.HibernateDaoImpl;
import college.model.StudyProgram;

@Component("studyProgramDao")
public class StudyProgramDaoImpl extends HibernateDaoImpl<String, StudyProgram> implements StudyProgramDao {

	public StudyProgramDaoImpl() {
		setEntityClass(StudyProgram.class);
	}

}
