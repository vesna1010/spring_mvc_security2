package college.service;

import java.util.Set;
import college.model.Student;

public interface StudentService {

	Set<Student> findAllStudents();
	
	Student findStudentById(String id);
	
	void deleteStudent(Student student);
	
	void saveOrUpdateStudent(Student student);
	
}

