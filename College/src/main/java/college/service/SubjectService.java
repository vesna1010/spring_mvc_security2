package college.service;

import java.util.Set;
import college.model.Subject;

public interface SubjectService {

	Set<Subject> findAllSubjects();
	
	Subject findSubjectById(String id);
	
	void deleteSubjectById(String id);
	
	void saveOrUpdateSubject(Subject subject);
	
}
