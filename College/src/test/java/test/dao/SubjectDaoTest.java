package test.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import college.dao.SubjectDao;
import college.model.StudyProgram;
import college.model.Subject;

public class SubjectDaoTest extends BaseDaoTest {

	@Autowired
	private SubjectDao subjectDao;

	@Test
	public void findAllSubjectsTest() {
		List<Subject> subjects = subjectDao.findAll();

		assertThat(subjects, hasSize(3));
	}

	@Test
	public void findAllByStudyProgramfTest() {
		StudyProgram studyProgram = new StudyProgram(1L, "Study Program A");
		
		List<Subject> subjects = subjectDao.findAllByStudyProgram(studyProgram);

		assertThat(subjects, hasSize(2));
	}

	@Test
	public void findSubjectByIdTest() {
		Subject subject = subjectDao.findById(1L);

		assertThat(subject.getTitle(), is("Subject A"));
		assertThat(subject.getLectures(), hasSize(2));
		assertThat(subject.getExams(), hasSize(2));
		assertThat(subject.getProfessors(), hasSize(2));
	}

	@Test
	public void updateSubjectTest() {
		Subject subject = subjectDao.findById(1L);

		subject.setTitle("Subject");

		subjectDao.saveOrUpdate(subject);

		subject = subjectDao.findById(1L);

		assertThat(subject.getTitle(), is("Subject"));
		assertThat(subject.getLectures(), hasSize(2));
		assertThat(subject.getExams(), hasSize(2));
		assertThat(subject.getProfessors(), hasSize(2));
	}

	@Test
	public void deleteSubjectByIdTest() {
		subjectDao.deleteById(1L);

		assertNull(subjectDao.findById(1L));
	}

}
