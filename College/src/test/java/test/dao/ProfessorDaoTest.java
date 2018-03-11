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
import college.dao.extensions.ProfessorDao;
import college.enums.Gender;
import college.model.Professor;

public class ProfessorDaoTest extends BaseDaoTest {

	@Autowired
	private ProfessorDao professorDao;

	@Before
	public void setUp() {
		professorDao.deleteAll();
		professor1.addLecture(lecture1);
		professor1.addLecture(lecture3);
		professor1.addExam(exam1);
		professor1.addExam(exam3);
		professorDao.saveOrUpdate(professor1);
	}

	@Test
	public void findAllProfessorsTest() {
		Set<Professor> professors = professorDao.findAll();

		assertThat(professors, hasSize(1));
		assertTrue(professors.contains(professor1));
		assertFalse(professors.contains(professor2));
	}

	@Test
	public void findProfessorById() {
		Professor professor = professorDao.findById("P1");

		assertThat(professor.getLectures(), hasSize(2));
		assertThat(professor.getExams(), hasSize(2));
		assertThat(professor.getSubjects(), hasSize(2));
	}

	@Test
	public void saveProfessorTest() {
		professorDao.saveOrUpdate(professor2);

		assertNotNull(professorDao.findById("P2"));
	}

	@Test
	public void updateProfessorTest() {
		professor1.setGender(Gender.FEMALE);

		professorDao.saveOrUpdate(professor1);

		Professor professor = professorDao.findById("P1");

		assertThat(professor.getGender(), is(Gender.FEMALE));
		assertThat(professor.getLectures(), hasSize(2));
		assertThat(professor.getExams(), hasSize(2));
		assertThat(professor.getSubjects(), hasSize(2));
	}

	@Test
	public void deleteProfessorByIdTest() {
		professorDao.deleteById("P1");

		assertNull(professorDao.findById("P1"));
	}
	
	@Test
	public void deleteProfessorest() {
		professorDao.delete(professor1);

		assertNull(professorDao.findById("P1"));
	}

}

