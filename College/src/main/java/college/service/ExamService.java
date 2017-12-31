package college.service;

import java.util.Date;
import java.util.Set;
import college.model.Exam;
import college.model.Professor;
import college.model.Subject;

public interface ExamService {
	
	Exam findExamById(Long id);
	
	Set<Exam> findExamsByObjects(Professor professor, Subject subject, Date date );

	void saveOrUpdateExam(Exam exam);
	
	void deleteExamById(Long id);
	
}