package college.service.impl;

import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import college.dao.extensions.StudyProgramDao;
import college.model.StudyProgram;
import college.service.StudyProgramService;

@Service
public class StudyProgramServiceImpl implements StudyProgramService {

	@Resource
	private StudyProgramDao studyProgramDao;

	@Override
	public Set<StudyProgram> findAllStudyPrograms() {
		return studyProgramDao.findAll();
	}

	@Override
	public StudyProgram findStudyProgramById(String id) {
		return studyProgramDao.findById(id);
	}

	@Override
	public void deleteStudyProgram(StudyProgram studyProgram) {
		studyProgramDao.delete(studyProgram);
	}

	@Override
	public void saveOrUpdateStudyProgram(StudyProgram studyProgram) {
		studyProgramDao.saveOrUpdate(studyProgram);
	}

}

