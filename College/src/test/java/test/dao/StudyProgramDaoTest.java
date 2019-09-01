package test.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import college.dao.StudyProgramDao;
import college.model.Department;
import college.model.StudyProgram;

public class StudyProgramDaoTest extends BaseDaoTest {

	@Autowired
	private StudyProgramDao studyProgramDao;

	@Test
	public void findAllStudyProgramsTest() {
		List<StudyProgram> studyPrograms = studyProgramDao.findAll();

		assertThat(studyPrograms, hasSize(3));
	}

	@Test
	public void findAllByDepartmentTest() {
		Department department = new Department(1L, "Department A");
		
		List<StudyProgram> studyPrograms = studyProgramDao.findAllByDepartment(department);

		assertThat(studyPrograms, hasSize(2));
	}

	@Test
	public void findStudyProgramByIdTest() {
		StudyProgram studyProgram = studyProgramDao.findById(1L);

		assertThat(studyProgram.getTitle(), is("Study Program A"));
		assertThat(studyProgram.getStudents(), hasSize(2));
		assertThat(studyProgram.getSubjects(), hasSize(2));
	}

	@Test
	public void saveOrUpdateStudyProgramTest() {
		StudyProgram studyProgram = studyProgramDao.findById(1L);

		studyProgram.setTitle("Study Program");

		studyProgramDao.saveOrUpdate(studyProgram);

		studyProgram = studyProgramDao.findById(1L);

		assertThat(studyProgram.getTitle(), is("Study Program"));
		assertThat(studyProgram.getStudents(), hasSize(2));
		assertThat(studyProgram.getSubjects(), hasSize(2));
	}

	@Test
	public void deleteStudyProgramByIdTest() {
		studyProgramDao.deleteById(1L);

		assertNull(studyProgramDao.findById(1L));
	}

}
