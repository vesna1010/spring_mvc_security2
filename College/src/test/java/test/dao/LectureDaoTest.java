package test.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import college.dao.LectureDao;
import college.model.Lecture;
import college.model.ProfessorSubjectId;
import college.model.StudyProgram;

public class LectureDaoTest extends BaseDaoTest {

	@Autowired
	private LectureDao lectureDao;

	@Test
	public void findAllTest() {
		List<Lecture> lectures = lectureDao.findAll();

		assertThat(lectures, hasSize(4));
	}

	@Test
	public void findAllByStudyProgramIdTest() {
		StudyProgram studyProgram = new StudyProgram(1L, "Study Program A");
		
		List<Lecture> lectures = lectureDao.findAllByStudyProgram(studyProgram);

		assertThat(lectures, hasSize(3));
	}

	@Test
	public void findLectureByIdTest() {
		ProfessorSubjectId lectureId = new ProfessorSubjectId(4L, 1L);
		
		Lecture lecture = lectureDao.findById(lectureId);

		assertThat(lecture.getHours(), is(2));
	}

	@Test
	public void saveOrUpdateLectureTest() {
		ProfessorSubjectId lectureId = new ProfessorSubjectId(4L, 1L);
		Lecture lecture = lectureDao.findById(lectureId);

		lecture.setHours(4);

		lectureDao.saveOrUpdate(lecture);

		lecture = lectureDao.findById(lectureId);

		assertThat(lecture.getHours(), is(4));
	}

	@Test
	public void deleteLectureByIdTest() {
		ProfessorSubjectId lectureId = new ProfessorSubjectId(4L, 1L);

		lectureDao.deleteById(lectureId);

		assertNull(lectureDao.findById(lectureId));
	}

}
