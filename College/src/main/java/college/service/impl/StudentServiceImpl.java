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
		return studentDao.findOneById(id);
	}

	@Override
	public void deleteStudentById(String id) {
		studentDao.deleteById(id);
	}

	@Override
	public void saveOrUpdateStudent(Student student) {
		studentDao.saveOrUpdate(student);
	}

}

