package test.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import college.dao.extensions.StudentDao;
import college.model.Student;

public class StudentDaoTest extends BaseDaoTest {

	@Autowired
	private StudentDao studentDao;

	@Before
	public void setUp() {
		studentDao.deleteAll();
		student1.addExam(exam1);
		student1.addExam(exam2);
		studentDao.saveOrUpdate(student1);
	}

	@Test
	public void findAllStudentsTest() {
		Set<Student> students = studentDao.findAll();
		
		assertThat(students, hasSize(1));
		assertTrue(students.contains(student1));
		assertFalse(students.contains(student2));
	}
	
	@Test
	public void findStudentByIdTest() {
		Student student = studentDao.findById("S1");
		
		assertThat(student.getStudyProgram(), is(studyProgram1));
		assertThat(student.getExams(), hasSize(2));
		assertThat(student.getAverage(), is(8.5));
	}
	
	@Test
	public void saveStudentTest() {
		studentDao.saveOrUpdate(student2);
		
		assertNotNull(studentDao.findById("S2"));
	}
	
	@Test
	public void updateStudentTest() {
		student1.setStudyProgram(studyProgram2);

		studentDao.saveOrUpdate(student1);

		Student student = studentDao.findById("S1");

		assertThat(student.getStudyProgram(), is(studyProgram2));
		assertThat(student.getExams(), hasSize(2));
		assertThat(student.getAverage(), is(8.5));
	}
	
	@Test
	public void deleteStudentByIdTest() {
		studentDao.deleteById("S1");
		
		assertNull(studentDao.findById("S1"));
	}
	
	@Test
	public void deleteStudentTest() {
		studentDao.delete(student1);
		
		assertNull(studentDao.findById("S1"));
	}
}

