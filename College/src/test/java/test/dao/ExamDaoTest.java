package test.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import college.dao.ExamDao;
import college.model.Exam;
import college.model.Professor;
import college.model.Student;
import college.model.StudentSubjectId;
import college.model.StudyProgram;
import college.model.Subject;

public class ExamDaoTest extends BaseDaoTest {

	@Autowired
	private ExamDao examDao;

	@Test
	public void findAllTest() {
		List<Exam> exams = examDao.findAll();

		assertThat(exams, hasSize(4));
	}

	@Test
	public void findAllByStudyProgramTest() {
		StudyProgram studyProgram = new StudyProgram(1L, "Study Program A");
		
		List<Exam> exams = examDao.findAllByStudyProgram(studyProgram);

		assertThat(exams, hasSize(3));
	}

	@Test
	public void findAllByStudentTest() {
		Student student = new Student(1L, "Student A");
		
		List<Exam> exams = examDao.findAllByStudent(student);

		assertThat(exams, hasSize(2));
	}

	@Test
	public void findAllByProfessorAndSubjectAndDateTest() {
		Professor professor = new Professor(4L, "Professor A");
		Subject subject = new Subject(1L, "Subject A");
		Date date = new GregorianCalendar(2018, Calendar.FEBRUARY, 1).getTime();

		List<Exam> exams = examDao.findAllByProfessorAndSubjectAndDate(professor, subject, date);

		assertThat(exams, hasSize(1));
	}

	@Test
	public void findExamByIdTest() {
		StudentSubjectId examId = new StudentSubjectId(1L, 1L);
		
		Exam exam = examDao.findById(examId);

		assertThat(exam.getScore(), is(9));
	}

	@Test
	public void saveOrUpdateExamTest() {
		StudentSubjectId examId = new StudentSubjectId(1L, 1L);
		Exam exam = examDao.findById(examId);

		exam.setScore(10);

		examDao.saveOrUpdate(exam);

		exam = examDao.findById(examId);

		assertThat(exam.getScore(), is(10));
	}

	@Test
	public void deleteExamByIdTest() {
		StudentSubjectId examId = new StudentSubjectId(1L, 1L);

		examDao.deleteById(examId);

		assertNull(examDao.findById(examId));
	}

}
