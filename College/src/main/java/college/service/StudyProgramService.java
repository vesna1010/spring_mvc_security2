package college.service;

import java.util.Set;
import college.model.StudyProgram;

public interface StudyProgramService {

	Set<StudyProgram> findAllStudyPrograms();
	
	StudyProgram findStudyProgramById(String id);
	
	void deleteStudyProgramById(String id);
	
	void saveOrUpdateStudyProgram(StudyProgram studyProgram);
	
}
