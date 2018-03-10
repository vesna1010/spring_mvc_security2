package college.service.impl;

import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import college.dao.extensions.StudentDao;
import college.model.Student;
import college.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Resource
	private StudentDao studentDao;

	@Override
	public Set<Student> findAllStudents() {
		return studentDao.findAll();
	}

	@Override
	public Student findStudentById(String id) {
		return studentDao.findById(id);
	}

	@Override
	public void deleteStudent(Student student) {
		studentDao.delete(student);
	}

	@Override
	public void saveOrUpdateStudent(Student student) {
		studentDao.saveOrUpdate(student);
	}

}

