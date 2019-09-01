package college.service;

import java.util.Date;
import java.util.List;
import college.model.Exam;
import college.model.Professor;
import college.model.Student;
import college.model.StudentSubjectId;
import college.model.StudyProgram;
import college.model.Subject;

public interface ExamService {

	List<Exam> findAllExamsByStudyProgram(StudyProgram studyProgram);

	List<Exam> findAllExamsByStudent(Student student);

	List<Exam> findAllExamsByProfessorAndSubjectAndDate(Professor professor, Subject subject, Date date);

	void saveOrUpdateExam(Exam exam);

	void deleteExamById(StudentSubjectId id);

}



