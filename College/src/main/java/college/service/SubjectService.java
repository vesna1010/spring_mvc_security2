package college.service;

import java.util.List;
import college.model.StudyProgram;
import college.model.Subject;

public interface SubjectService {

	List<Subject> findAllSubjects();

	List<Subject> findAllSubjectsByStudyProgram(StudyProgram studyProgram);

	Subject findSubjectById(Long id);

	void saveOrUpdateSubject(Subject subject);

	void deleteSubjectById(Long id);

}



