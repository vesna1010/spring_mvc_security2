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
import college.dao.extensions.StudyProgramDao;
import college.model.StudyProgram;

public class StudyProgramDaoTest extends BaseDaoTest{
	
	@Autowired
	private StudyProgramDao studyProgramDao;

	@Before
	public void setUp() {
		studyProgramDao.deleteAll();
		subject1.addLecture(lecture1);
		subject1.addLecture(lecture2);
		studyProgram1.addStudent(student1);
		studyProgram1.addStudent(student2);
		studyProgram1.addSubject(subject1);
		studyProgram1.addSubject(subject2);
		studyProgramDao.saveOrUpdate(studyProgram1);
	}
	
	@Test
	public void findAllStudyProgramsTest() {
		Set<StudyProgram> studyPrograms = studyProgramDao.findAll();

		assertThat(studyPrograms, hasSize(1));
		assertTrue(studyPrograms.contains(studyProgram1));
		assertFalse(studyPrograms.contains(studyProgram2));
	}
	
	@Test
	public void findStudyProgramByIdTest() {
		StudyProgram studyProgram = studyProgramDao.findById("SP1");

		assertThat(studyProgram.getTitle(), is("Study Program 1"));
		assertThat(studyProgram.getDepartment(), equalTo(department1));
		assertThat(studyProgram.getStudents(), hasSize(2));
		assertThat(studyProgram.getSubjects(), hasSize(2));
		assertThat(studyProgram.getLectures(), hasSize(2));
		assertThat(studyProgram.getProfessors(), hasSize(2));
	}

	@Test
	public void saveStudyProgramTest() {
		studyProgramDao.saveOrUpdate(studyProgram2);
		
		assertNotNull(studyProgramDao.findById("SP2"));
	}
	
	@Test
	public void updateStudyProgramTest() {
		studyProgram1.setDepartment(department2);
		
		studyProgramDao.saveOrUpdate(studyProgram1);
		
		StudyProgram studyProgram = studyProgramDao.findById("SP1");

		assertThat(studyProgram.getTitle(), is("Study Program 1"));
		assertThat(studyProgram.getDepartment(), equalTo(department2));
		assertThat(studyProgram.getStudents(), hasSize(2));
		assertThat(studyProgram.getSubjects(), hasSize(2));
	}
	
	@Test
	public void deleteStudyProgramByIdTest() {
		studyProgramDao.deleteById("SP1");
		
		assertNull(studyProgramDao.findById("SP1"));
	}
	
	@Test
	public void deleteStudyProgramTest() {
		studyProgramDao.delete(studyProgram1);
		
		assertNull(studyProgramDao.findById("SP1"));
	}
}

