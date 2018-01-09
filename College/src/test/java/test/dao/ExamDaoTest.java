package test.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import college.dao.extensions.ExamDao;
import college.model.Exam;

public class ExamDaoTest extends BaseDaoTest {
	
	@Autowired
	private ExamDao examDao;
	
	@Before
	public void setUp() {
		deleteAll();
		examDao.saveOrUpdate(exam1);
		examDao.saveOrUpdate(exam2);
	}
	
	private void deleteAll() {
		Set<Exam> exams = examDao.findAll();
		
		for(Exam exam : exams) {
			examDao.deleteById(exam.getId());
		}
	}
	
	@Test
	public void findAllExamsTest() {
		Set<Exam> exams = examDao.findAll();
		
		assertThat(exams, hasSize(2));
		assertTrue(exams.contains(exam1));
		assertTrue(exams.contains(exam2));
		assertFalse(exams.contains(exam3));
	}
	
	@Test
	public void findExamByIdTest() {
		Exam exam = examDao.findOneById(exam1.getId());
		
		assertThat(exam.getProfessor(), is(professor1));
		assertThat(exam.getStudent(), is(student1));
		assertThat(exam.getSubject(), is(subject1));
	}

	@Test
	public void saveExamTest() {
        examDao.saveOrUpdate(exam3);
        
        assertNotNull(examDao.findOneById("E3"));
	}
	
	@Test
	public void updateExamTest() {
		exam1.setProfessor(professor2);
		exam1.setStudent(student2);
		exam1.setSubject(subject2);
		
		examDao.saveOrUpdate(exam1);
		
		Exam exam = examDao.findOneById("E1");
		
		assertThat(exam.getProfessor(), is(professor2));
		assertThat(exam.getStudent(), is(student2));
		assertThat(exam.getSubject(), is(subject2));
	}
	
	@Test
	public void deleteExamByIdTest() {	
		examDao.deleteById("E1");
		
		assertNull(examDao.findOneById("E1"));
	}
	
	@Test
	public void findExamsByObjectsTest() {
		Set<Exam> exams = examDao.findExamsByObjects(professor1, subject1, new Date());
		
		assertThat(exams, hasSize(1));
		assertThat(examDao.findAll(), hasSize(2));
	}
	
}
