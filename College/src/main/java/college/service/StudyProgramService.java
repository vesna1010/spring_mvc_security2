package college.service;

import java.util.List;
import college.model.Student;
import college.model.StudyProgram;

public interface StudentService {

	List<Student> findAllStudents();
	
	List<Student> findAllStudentsByStudyProgram(StudyProgram studyProgram);

	Student findStudentById(Long id);

	void saveOrUpdateStudent(Student student);

	void deleteStudentById(Long id);

}


