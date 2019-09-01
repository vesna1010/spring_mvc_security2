package college.service;

import java.util.List;
import college.model.Department;
import college.model.StudyProgram;

public interface StudyProgramService {

	List<StudyProgram> findAllStudyPrograms();

	List<StudyProgram> findAllStudyProgramsByDepartment(Department department);

	StudyProgram findStudyProgramById(Long id);

	void saveOrUpdateStudyProgram(StudyProgram studyProgram);

	void deleteStudyProgramById(Long id);

}


