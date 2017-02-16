package test;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import college.model.Address;
import college.model.Department;
import college.model.Exam;
import college.model.Professor;
import college.model.Student;
import college.model.StudyProgram;
import college.model.Subject;
import college.model.SubjectProfessor;
import college.service.HibernateService;
import college.service.impl.ExamService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/spring-persistence.xml")
public class Test1 {

	@Autowired
	private HibernateService<Department> departmentService;

	@Autowired
	private HibernateService<StudyProgram> studyProgramService;

	@Autowired
	private HibernateService<Student> studentService;

	@Autowired
	private HibernateService<Subject> subjectService;

	@Autowired
	private HibernateService<Professor> professorService;

	@Autowired
	private ExamService examService;
	
	@Autowired
	private HibernateService<SubjectProfessor> subjectProfessorService;

	private Department department1 = new Department("D1", "Department", new Date());
	private Department department2 = new Department("D2", "Department 2", new Date());

	private StudyProgram studyProgram1 = new StudyProgram("SP1", "Study Program ", new Date(), department1, 3);
	private StudyProgram studyProgram2 = new StudyProgram("SP2", "Study Program 2", new Date(), department2, 3);
	private StudyProgram studyProgram3 = new StudyProgram("SP3", "Study Program 3", new Date(), department2, 3);
	private Address address1 = new Address("City", "Street 1", "State");
	private Address address2 = new Address("City", "Street 2", "State");
	private Address address3 = new Address("City", "Street 3", "State");
	private Address address4 = new Address("City", "Street 4", "State");
	private Address address5 = new Address("City", "Street 5", "State");
	private Address address6 = new Address("City", "Street 6", "State");
	private Address address7 = new Address("City", "Street 7", "State");

	private FileInputStream fileInputStream;
	private Student student1 = new Student("S1", "FirstName A", "LastName A", "Father Name A", new Date(),
			"student_email1@gmail.com", "065111111", "MALE", address1, new Date(), 3, studyProgram1);
	private Student student2 = new Student("S2", "FirstName B", "LastName B", "Father Name B", new Date(),
			"student_email2@gmail.com", "065111111", "MALE", address2, new Date(), 2, studyProgram1);
	private Student student3 = new Student("S3", "FirstName C", "LastName C", "Father Name C", new Date(),
			"student_email3@gmail.com", "065111111", "FEMALE", address3, new Date(), 1, studyProgram2);
	private Student student4 = new Student("S4", "FirstName D", "LastName D", "Father Name D", new Date(),
			"student_email4@gmail.com", "065111111", "FEMALE", address4, new Date(), 2, studyProgram3);

	private Subject subject1 = new Subject("SUB1", "Subject 1", studyProgram1);
	private Subject subject2 = new Subject("SUB2", "Subject 2", studyProgram1);
	private Subject subject3 = new Subject("SUB3", "Subject 3", studyProgram2);
	private Subject subject4 = new Subject("SUB4", "Subject 4", studyProgram3);

	private Professor professor1 = new Professor("P1", "FirstName E", "LastName E", "Father Name E", new Date(),
			"professor_email5@gmail.com", "065111111", "MALE", address5, "Title A", new Date());
	private Professor professor2 = new Professor("P2", "FirstName F", "LastName F", "Father Name F", new Date(),
			"professor_email6@gmail.com", "065111111", "FEMALE", address6, "Title B", new Date());
	private Professor professor3 = new Professor("P3", "FirstName G", "LastName G", "Father Name G", new Date(),
			"professor_email7@gmail.com", "065111111", "FEMALE", address7, "Title C", new Date());

	private Exam exam1 = new Exam(student1, professor1, subject1, new Date(), 10);
	private Exam exam2 = new Exam(student1, professor2, subject2, new Date(), 7);
	private Exam exam3 = new Exam(student2, professor1, subject1, new Date(), 7);
	private Exam exam4 = new Exam(student3, professor3, subject3, new Date(), 8);
	private Exam exam5 = new Exam(student4, professor3, subject4, new Date(), 6);

	@Before
	public void init() throws IOException, URISyntaxException {
		File imageFile1 = new File(this.getClass().getResource("/images/student1.jpg").toURI());
		File imageFile2 = new File(this.getClass().getResource("/images/student2.jpg").toURI());
		File imageFile3 = new File(this.getClass().getResource("/images/student3.jpg").toURI());
		File imageFile4 = new File(this.getClass().getResource("/images/student4.jpg").toURI());
		File imageFile5 = new File(this.getClass().getResource("/images/professor1.jpg").toURI());
		File imageFile6 = new File(this.getClass().getResource("/images/professor2.jpg").toURI());
		File imageFile7 = new File(this.getClass().getResource("/images/professor3.jpg").toURI());

		byte[] bFile1 = new byte[(int) imageFile1.length()];
		byte[] bFile2 = new byte[(int) imageFile2.length()];
		byte[] bFile3 = new byte[(int) imageFile3.length()];
		byte[] bFile4 = new byte[(int) imageFile4.length()];
		byte[] bFile5 = new byte[(int) imageFile5.length()];
		byte[] bFile6 = new byte[(int) imageFile6.length()];
		byte[] bFile7 = new byte[(int) imageFile7.length()];

		fileInputStream = new FileInputStream(imageFile1);
		fileInputStream.read(bFile1);
		fileInputStream = new FileInputStream(imageFile2);
		fileInputStream.read(bFile2);
		fileInputStream = new FileInputStream(imageFile3);
		fileInputStream.read(bFile3);
		fileInputStream = new FileInputStream(imageFile4);
		fileInputStream.read(bFile4);
		fileInputStream = new FileInputStream(imageFile5);
		fileInputStream.read(bFile5);
		fileInputStream = new FileInputStream(imageFile6);
		fileInputStream.read(bFile6);
		fileInputStream = new FileInputStream(imageFile7);
		fileInputStream.read(bFile7);

		student1.setImage(bFile1);
		student2.setImage(bFile2);
		student3.setImage(bFile3);
		student4.setImage(bFile4);

		professor1.setImage(bFile5);
		professor2.setImage(bFile6);
		professor3.setImage(bFile7);

		examService.deleteAll();
		subjectProfessorService.deleteAll();
		professorService.deleteAll();
		studentService.deleteAll();
		subjectService.deleteAll();
		studyProgramService.deleteAll();
		departmentService.deleteAll();
		
		departmentService.save(department1);
		departmentService.save(department2);

		studyProgramService.save(studyProgram1);
		studyProgramService.save(studyProgram2);
		studyProgramService.save(studyProgram3);

		subjectService.save(subject1);
		subjectService.save(subject2);
		subjectService.save(subject3);
		subjectService.save(subject4);

		studentService.save(student1);
		studentService.save(student2);
		studentService.save(student3);
		studentService.save(student4);

		List<Subject> subjects1 = new ArrayList<Subject>();
		subjects1.add(subject1);
		subjects1.add(subject2);

		professor1.setSubjects(subjects1);
		professor2.setSubjects(subjects1);

		List<Subject> subjects2 = new ArrayList<Subject>();
		subjects2.add(subject3);
		subjects2.add(subject4);
		professor3.setSubjects(subjects2);

		professorService.save(professor1);
		professorService.save(professor2);
		professorService.save(professor3);

		examService.save(exam1);
		examService.save(exam2);
		examService.save(exam3);
		examService.save(exam4);
		examService.save(exam5);
	}

	@Test
	public void findAllObjects() {

		assertTrue(examService.findAll().size() == 5);
		assertTrue(subjectService.findAll().size() == 4);
		assertTrue(professorService.findAll().size() == 3);
		assertTrue(studentService.findAll().size() == 4);
		assertTrue(studyProgramService.findAll().size() == 3);
		assertTrue(departmentService.findAll().size() == 2);

	}

	@Test
	public void findObjects() {

		Department department = departmentService.findOne("D1");
		assertNotNull(department);
		assertTrue(department.getStudyPrograms().size() == 1);

		StudyProgram studyProgram = studyProgramService.findOne("SP1");
		assertNotNull(studyProgram);
		assertTrue(studyProgram.getStudents().size() == 2);
		assertTrue(studyProgram.getSubjects().size() == 2);
		assertTrue(studyProgram.getProfessors().size() == 2);

		Student student = studentService.findOne("S1");
		assertNotNull(student);
		assertTrue(student.getExams().size() == 2);
		assertTrue(student.getAverage() == 8.5);

		Professor professor = professorService.findOne("P3");
		assertNotNull(professor);
		assertTrue(professor.getSubjects().size() == 2);
		assertFalse(studyProgram.getProfessors().contains(professor));
		
		Subject subject = subjectService.findOne("SUB3");
		assertNotNull(subject);
		assertTrue(subject.getProfessors().size() == 1);
		assertTrue(professor.getSubjects().contains(subject));

		List<Exam> exams = examService.findByObjects(professor1, subject1, new Date());
		assertTrue(exams.size() == 2);
	}

	@Test
	public void updateDepartment() {

		department1.setTitle("Department 1");
		departmentService.save(department1);

		Department department = departmentService.findOne(department1.getId());
		assertEquals(department.getTitle(), "Department 1");
		assertTrue(department.getStudyPrograms().size() == 1);

	}

	@Test
	public void updateStudyProgram() {

		studyProgram1.setDepartment(department2);
		studyProgramService.save(studyProgram1);

		Department department1 = departmentService.findOne("D1");
		assertTrue(department1.getStudyPrograms().size() == 0);

		Department department2 = departmentService.findOne("D2");
		assertTrue(department2.getStudyPrograms().size() == 3);

		StudyProgram studyProgram = studyProgramService.findOne("SP1");
		assertEquals(studyProgram.getDepartment(), department2);
		assertTrue(studyProgram.getStudents().size() == 2);
		assertTrue(studyProgram.getSubjects().size() == 2);
		assertTrue(studyProgram.getProfessors().size() == 2);

	}

	@Test
	public void updateStudent() {

		student1.setStudyProgram(studyProgram2);
		studentService.save(student1);

		StudyProgram studyProgram1 = studyProgramService.findOne("SP1");
		assertTrue(studyProgram1.getStudents().size() == 1);

		StudyProgram studyProgram2 = studyProgramService.findOne("SP2");
		assertTrue(studyProgram2.getStudents().size() == 2);

		Student student = studentService.findOne("S1");
		assertEquals(student.getStudyProgram(), studyProgram2);
		assertTrue(student.getExams().size() == 2);

	}

	@Test
	public void updateSubject() {

		subject1.setStudyProgram(studyProgram2);
		subjectService.save(subject1);

		StudyProgram studyProgram1 = studyProgramService.findOne("SP1");
		assertTrue(studyProgram1.getSubjects().size() == 1);

		StudyProgram studyProgram2 = studyProgramService.findOne("SP2");
		assertTrue(studyProgram2.getSubjects().size() == 2);

		Subject subject = subjectService.findOne("SUB1");
		assertEquals(subject.getStudyProgram(), studyProgram2);
		assertTrue(subject.getProfessors().size() == 2);

	}

	@Test
	public void updateProfessor() {

		professor1.setFirstName("My Name");
		professorService.save(professor1);

		Professor professor = professorService.findOne("P1");
		assertEquals(professor.getFirstName(), "My Name");
		assertTrue(professor.getSubjects().size() == 2);

		List<Subject> subjects = new ArrayList<Subject>();
		subjects.add(subject3);
		professor1.setSubjects(subjects);
		professorService.save(professor1);
		professor = professorService.findOne("P1");
		assertTrue(professor.getSubjects().size() == 1);

		Subject subject = subjectService.findOne("SUB1");
		assertTrue(subject.getProfessors().size() == 1);
	}

	@Test
	public void updateExam() {

		exam1.setStudent(student2);
		examService.save(exam1);

		Student student1 = studentService.findOne("S1");
		assertTrue(student1.getExams().size() == 1);

		Student student2 = studentService.findOne("S2");
		assertTrue(student2.getExams().size() == 2);
	}

	@Test
	public void deleteDepartment() {

		Department department = departmentService.findOne("D2");
		assertNotNull(department);

		departmentService.delete("D2");

		department = departmentService.findOne("D2");
		assertNull(department);
	}

	@Test
	public void deleteStudyProgram() {

		StudyProgram studyProgram = studyProgramService.findOne("SP1");
		assertNotNull(studyProgram);

		studyProgramService.delete("SP1");
		

		studyProgram = studyProgramService.findOne("SP1");
		assertNull(studyProgram);

		Department department = departmentService.findOne("D1");
		assertTrue(department.getStudyPrograms().size() == 0);
	}

	@Test
	public void deleteProfessor() {

		Professor professor = professorService.findOne("P1");
		assertNotNull(professor);

		professorService.delete("P1");

		professor = professorService.findOne("P1");
		assertNull(professor);

		Subject subject = subjectService.findOne("SUB1");
		assertTrue(subject.getProfessors().size() == 1);

	}

	@Test
	public void deleteStudent() {

		Student student = studentService.findOne("S1");
		assertNotNull(student);

		studentService.delete("S1");

		student = studentService.findOne("S1");
		assertNull(student);

		StudyProgram studyProgram = studyProgramService.findOne("SP1");
		assertTrue(studyProgram.getStudents().size() == 1);
	}

	@Test
	public void deleteSubject() {

		Subject subject = subjectService.findOne("SUB1");
		assertNotNull(subject);

		subjectService.delete("SUB1");

		subject = subjectService.findOne("SUB1");
		assertNull(subject);

		Professor professor = professorService.findOne("P1");
		assertTrue(professor.getSubjects().size() == 1);
	}

	@Test
	public void deleteExam() {

		Exam exam = examService.findOne(exam1.getId());
		assertNotNull(exam);

		examService.delete(exam1.getId());

		exam = examService.findOne(exam1.getId());
		assertNull(exam);

		Student student = studentService.findOne("S1");
		assertTrue(student.getExams().size() == 1);
	}

}
