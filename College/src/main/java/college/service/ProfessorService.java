package college.service;

import java.util.List;
import college.model.Professor;
import college.model.StudyProgram;

public interface ProfessorService {

	List<Professor> findAllProfessors();

	List<Professor> findAllProfessorsByStudyProgram(StudyProgram studyProgram);

	Professor findProfessorById(Long id);

	void deleteProfessorById(Long id);

	void saveOrUpdateProfessor(Professor professor);

}


