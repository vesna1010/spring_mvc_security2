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
import college.dao.extensions.LectureDao;
import college.model.Lecture;

public class LectureDaoTest extends BaseDaoTest {

	@Autowired
	private LectureDao lectureDao;

	@Before
	public void setUp() {
		lectureDao.deleteAll();
		lectureDao.saveOrUpdate(lecture1);
		lectureDao.saveOrUpdate(lecture2);
	}

	@Test
	public void findAllLectures() {
		Set<Lecture> lectures = lectureDao.findAll();

		assertThat(lectures, hasSize(2));
		assertTrue(lectures.contains(lecture1));
		assertTrue(lectures.contains(lecture2));
		assertFalse(lectures.contains(lecture3));
	}

	@Test
	public void findLectureByIdTest() {
		Lecture lecture = lectureDao.findById(lecture1.getId());

		assertThat(lecture.getProfessor(), is(professor1));
		assertThat(lecture.getSubject(), is(subject1));
	}

	@Test
	public void saveLectureTest() {
		lectureDao.saveOrUpdate(lecture3);

		assertNotNull(lecture3.getId());
	}

	@Test
	public void updateLectureTest() {
		lecture1.setProfessor(professor2);
		lecture1.setSubject(subject2);

		lectureDao.saveOrUpdate(lecture1);

		Lecture lecture = lectureDao.findById(lecture1.getId());

		assertThat(lecture.getProfessor(), is(professor2));
		assertThat(lecture.getSubject(), is(subject2));
	}

	@Test
	public void deleteLectureByIdTest() {
		lectureDao.deleteById(lecture1.getId());

		assertNull(lectureDao.findById(lecture1.getId()));
	}
	
	@Test
	public void deleteLectureTest() {
		lectureDao.delete(lecture1);

		assertNull(lectureDao.findById(lecture1.getId()));
	}

}
