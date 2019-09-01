package college.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import college.dao.StudyProgramDao;
import college.model.Department;
import college.model.StudyProgram;
import college.service.StudyProgramService;

@Service
@Transactional
public class StudyProgramServiceImpl implements StudyProgramService {

	private StudyProgramDao studyProgramDao;

	@Autowired
	public StudyProgramServiceImpl(StudyProgramDao studyProgramDao) {
		this.studyProgramDao = studyProgramDao;
	}

	@Override
	public List<StudyProgram> findAllStudyPrograms() {
		return studyProgramDao.findAll();
	}

	@Override
	public List<StudyProgram> findAllStudyProgramsByDepartment(Department department) {
		return studyProgramDao.findAllByDepartment(department);
	}

	@Override
	public StudyProgram findStudyProgramById(Long id) {
		return studyProgramDao.findById(id);
	}

	@Override
	public void saveOrUpdateStudyProgram(StudyProgram studyProgram) {
		studyProgramDao.saveOrUpdate(studyProgram);
	}

	@Override
	public void deleteStudyProgramById(Long id) {
		studyProgramDao.deleteById(id);
	}

}
