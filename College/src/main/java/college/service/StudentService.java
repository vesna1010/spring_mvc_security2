package college.service;

import java.util.Set;
import college.model.Student;

public interface StudentService {

	Set<Student> findAllStudents();
	
	Student findStudentById(String id);
	
	void deleteStudentById(String id);
	
	void saveOrUpdateStudent(Student student);
	
}

