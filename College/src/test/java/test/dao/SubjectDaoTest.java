package test.dao;

import static org.hamcrest.CoreMatchers.equalTo;
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
import college.dao.extensions.SubjectDao;
import college.model.Subject;

public class SubjectDaoTest extends BaseDaoTest {

	@Autowired
	private SubjectDao subjectDao;

	@Before
	public void setUp() {
		subjectDao.deleteAll();
		subject1.addLecture(lecture1);
		subject1.addLecture(lecture2);
		subject1.addExam(exam1);
		subjectDao.saveOrUpdate(subject1);
	}

	@Test
	public void findAllSubjectsTest() {
		Set<Subject> subjects = subjectDao.findAll();

		assertThat(subjects, hasSize(1));
		assertTrue(subjects.contains(subject1));
		assertFalse(subjects.contains(subject2));
	}

	@Test
	public void findOneSubjectTest() {
		Subject subject = subjectDao.findById("SUB1");

		assertThat(subject.getTitle(), is("Subject 1"));
		assertThat(subject.getStudyProgram(), equalTo(studyProgram1));
		assertThat(subject.getLectures(), hasSize(2));
		assertThat(subject.getExams(), hasSize(1));
		assertThat(subject.getProfessors(), hasSize(2));
	}

	@Test
	public void saveSubjectTest() {	
		subjectDao.saveOrUpdate(subject2);

		assertNotNull(subjectDao.findById("SUB2"));
	}

	@Test
	public void updateSubjectTest() {
		subject1.setStudyProgram(studyProgram2);

		subjectDao.saveOrUpdate(subject1);

		Subject subject = subjectDao.findById("SUB1");

		assertThat(subject.getTitle(), is("Subject 1"));
		assertThat(subject.getStudyProgram(), equalTo(studyProgram2));
		assertThat(subject.getLectures(), hasSize(2));
		assertThat(subject.getExams(), hasSize(1));
		assertThat(subject.getProfessors(), hasSize(2));
	}

	@Test
	public void deleteSubjectByIdTest() {
		subjectDao.deleteById("SUB1");

		assertNull(subjectDao.findById("SUB1"));
	}
	
	@Test
	public void deleteSubjectest() {
		subjectDao.delete(subject1);

		assertNull(subjectDao.findById("SUB1"));
	}

}

