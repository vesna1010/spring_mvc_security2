package college.service;

import java.util.Set;
import college.model.StudyProgram;

public interface StudyProgramService {

	Set<StudyProgram> findAllStudyPrograms();
	
	StudyProgram findStudyProgramById(String id);
	
	void deleteStudyProgram(StudyProgram studyProgram);
	
	void saveOrUpdateStudyProgram(StudyProgram studyProgram);
	
}


