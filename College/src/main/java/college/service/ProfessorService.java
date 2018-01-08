package college.service;

import java.util.Set;
import college.model.Professor;

public interface ProfessorService {

	Set<Professor> findAllProfessors();
		
	Professor findProfessorById(String id);
	
	void deleteProfessorById(String id);
	
	void saveOrUpdateProfessor(Professor professor);
	
}

