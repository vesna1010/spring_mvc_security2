package college.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import college.dao.ProfessorDao;
import college.model.Professor;
import college.model.StudyProgram;
import college.service.ProfessorService;

@Service
@Transactional
public class ProfessorServiceImpl implements ProfessorService {

	private ProfessorDao professorDao;

	@Autowired
	public ProfessorServiceImpl(ProfessorDao professorDao) {
		this.professorDao = professorDao;
	}

	@Override
	public List<Professor> findAllProfessors() {
		return professorDao.findAll();
	}

	@Override
	public List<Professor> findAllProfessorsByStudyProgram(StudyProgram studyProgram) {
		return professorDao.findAllByStudyProgram(studyProgram);
	}

	@Override
	public Professor findProfessorById(Long id) {
		return professorDao.findById(id);
	}

	@Override
	public void saveOrUpdateProfessor(Professor professor) {
		professorDao.saveOrUpdate(professor);
	}

	@Override
	public void deleteProfessorById(Long id) {
		professorDao.deleteById(id);
	}

}
