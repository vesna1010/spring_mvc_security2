package test.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import college.dao.ProfessorDao;
import college.model.Professor;
import college.model.StudyProgram;

public class ProfessorDaoTest extends BaseDaoTest {

	@Autowired
	private ProfessorDao professorDao;

	@Test
	public void findAllProfessorsTest() {
		List<Professor> professors = professorDao.findAll();

		assertThat(professors, hasSize(3));
	}

	@Test
	public void findAllByStudyProgramTest() {
		StudyProgram studyProgram = new StudyProgram(1L, "Study Program A");
		
		List<Professor> professors = professorDao.findAllByStudyProgram(studyProgram);

		assertThat(professors, hasSize(2));
	}

	@Test
	public void findProfessorById() {
		Professor professor = professorDao.findById(4L);

		assertThat(professor.getFullName(), is("Professor NameA"));
		assertThat(professor.getLectures(), hasSize(2));
		assertThat(professor.getExams(), hasSize(3));
	}

	@Test
	public void saveOrUpdateProfessorTest() {
		Professor professor = professorDao.findById(4L);

		professor.setFullName("New Name");

		professorDao.saveOrUpdate(professor);

		professor = professorDao.findById(4L);

		assertThat(professor.getFullName(), is("New Name"));
		assertThat(professor.getLectures(), hasSize(2));
		assertThat(professor.getExams(), hasSize(3));
	}

	@Test
	public void deleteProfessorByIdTest() {
		professorDao.deleteById(4L);

		assertNull(professorDao.findById(4L));
	}

}
