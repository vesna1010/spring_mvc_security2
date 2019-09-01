package college.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import college.dao.StudentDao;
import college.model.Student;
import college.model.StudyProgram;
import college.service.StudentService;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

	private StudentDao studentDao;

	@Autowired
	public StudentServiceImpl(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	@Override
	public List<Student> findAllStudents() {
		return studentDao.findAll();
	}

	@Override
	public List<Student> findAllStudentsByStudyProgram(StudyProgram studyProgram) {
		return studentDao.findAllByStudyProgram(studyProgram);
	}

	@Override
	public Student findStudentById(Long id) {
		return studentDao.findById(id);
	}

	@Override
	public void saveOrUpdateStudent(Student student) {
		studentDao.saveOrUpdate(student);
	}

	@Override
	public void deleteStudentById(Long id) {
		studentDao.deleteById(id);
	}

}
