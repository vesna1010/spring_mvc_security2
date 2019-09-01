package college.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import college.dao.SubjectDao;
import college.model.StudyProgram;
import college.model.Subject;
import college.service.SubjectService;

@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

	private SubjectDao subjectDao;

	@Autowired
	public SubjectServiceImpl(SubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}

	@Override
	public List<Subject> findAllSubjects() {
		return subjectDao.findAll();
	}

	@Override
	public List<Subject> findAllSubjectsByStudyProgram(StudyProgram studyProgram) {
		return subjectDao.findAllByStudyProgram(studyProgram);
	}

	@Override
	public Subject findSubjectById(Long id) {
		return subjectDao.findById(id);
	}

	@Override
	public void saveOrUpdateSubject(Subject subject) {
		subjectDao.saveOrUpdate(subject);
	}

	@Override
	public void deleteSubjectById(Long id) {
		subjectDao.deleteById(id);
	}

}
