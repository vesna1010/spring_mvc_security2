package test.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import college.dao.StudentDao;
import college.model.Student;
import college.model.StudyProgram;

public class StudentDaoTest extends BaseDaoTest {

	@Autowired
	private StudentDao studentDao;

	@Test
	public void findAllStudentsTest() {
		List<Student> students = studentDao.findAll();

		assertThat(students, hasSize(3));
	}

	@Test
	public void findAllByStudyProgramTest() {
		StudyProgram studyProgram = new StudyProgram(1L, "Study Program A");
		
		List<Student> students = studentDao.findAllByStudyProgram(studyProgram);

		assertThat(students, hasSize(2));
	}

	@Test
	public void findStudentByIdTest() {
		Student student = studentDao.findById(1L);

		assertThat(student.getFullName(), is("Student NameA"));
		assertThat(student.getExams(), hasSize(2));
		assertThat(student.getAverage(), is(8.5));
	}

	@Test
	public void saveOrUpdateStudentTest() {
		Student student = studentDao.findById(1L);

		student.setFullName("New Name");

		studentDao.saveOrUpdate(student);

		student = studentDao.findById(1L);

		assertThat(student.getFullName(), is("New Name"));
		assertThat(student.getExams(), hasSize(2));
	}

	@Test
	public void deleteStudentByIdTest() {
		studentDao.deleteById(1L);

		assertNull(studentDao.findById(1L));
	}

}
