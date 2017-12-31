package college.service.impl;

import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import college.dao.extensions.ProfessorDao;
import college.model.Professor;
import college.service.ProfessorService;

@Service
public class ProfessorServiceImpl implements ProfessorService {

	@Resource
	private ProfessorDao professorDao;

	@Override
	public Set<Professor> findAllProfessors() {
		return professorDao.findAll();
	}

	@Override
	public Professor findProfessorById(String id) {
		return professorDao.findOneById(id);
	}

	@Override
	public void deleteProfessorById(String id) {
		professorDao.deleteById(id);
	}

	@Override
	public void saveOrUpdateProfessor(Professor professor) {
		professorDao.saveOrUpdate(professor);
	}

}
