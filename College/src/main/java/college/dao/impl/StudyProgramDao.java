package college.dao.impl;

import org.springframework.stereotype.Component;
import college.dao.HibernateDao;
import college.model.StudyProgram;

@Component
public class StudyProgramDao extends HibernateDao<StudyProgram> {
	
	public StudyProgramDao(){
		setMyClass(StudyProgram.class);
	}

}
