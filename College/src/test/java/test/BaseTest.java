package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashSet;

import org.junit.Before;

import college.enums.Gender;
import college.enums.Role;
import college.model.Address;
import college.model.Department;
import college.model.Exam;
import college.model.Lecture;
import college.model.Professor;
import college.model.Student;
import college.model.StudyProgram;
import college.model.Subject;
import college.model.User;

public abstract class BaseTest {

	protected final User user1 = new User("Username 1", "username1@gmail.com", "Password1",
			new HashSet<>(Arrays.asList(Role.ADMIN, Role.USER)));
	protected final User user2 = new User("Username 2", "username2@gmail.com", "Password2",
			new HashSet<>(Arrays.asList(Role.ADMIN, Role.USER)));
	protected final User user3 = new User("Username 3", "username3@gmail.com", "Password3",
			new HashSet<>(Arrays.asList(Role.USER, Role.PROFESSOR)));

	protected final Department department1 = new Department("D1", "Department 1");
	protected final Department department2 = new Department("D2", "Department 2");

	protected final StudyProgram studyProgram1 = new StudyProgram("SP1", "Study Program 1", department1);
	protected final StudyProgram studyProgram2 = new StudyProgram("SP2", "Study Program 2", department1);

	protected final Subject subject1 = new Subject("SUB1", "Subject 1", studyProgram1);
	protected final Subject subject2 = new Subject("SUB2", "Subject 2", studyProgram1);

	protected final Student student1 = new Student("S1", "First Student", "First Father Name",
			new GregorianCalendar(1995, 5, 5).getTime(), "email1@gmail.com", "065-123-456", Gender.MALE,
			new Address("City", "Street", "State"), studyProgram1);
	protected final Student student2 = new Student("S2", "Second Student", "Second Father Name",
			new GregorianCalendar(1995, 4, 4).getTime(), "email2@gmail.com", "065-124-156", Gender.MALE,
			new Address("City", "Street", "State"), studyProgram1);

	protected final Professor professor1 = new Professor("P1", "First Professor", "First Father Name",
			new GregorianCalendar(1985, 3, 2).getTime(), "email3@gmail.com", "065-333-212", Gender.MALE,
			new Address("City", "Street", "State"), "Title");
	protected final Professor professor2 = new Professor("P2", "Second Professor", "Second Father Name",
			new GregorianCalendar(1985, 1, 5).getTime(), "email4@gmail.com", "065-423-515", Gender.MALE,
			new Address("City", "Street", "State"), "Title");

	protected final Lecture lecture1 = new Lecture("L1", professor1, subject1, 2);
	protected final Lecture lecture2 = new Lecture("L2", professor2, subject1, 2);
	protected final Lecture lecture3 = new Lecture("L3", professor1, subject2, 2);

	protected final Exam exam1 = new Exam("E1", student1, professor1, subject1, 8);
	protected final Exam exam2 = new Exam("E2", student1, professor2, subject2, 9);
	protected final Exam exam3 = new Exam("E3", student2, professor1, subject2, 9);

	public byte[] getImage() throws IOException, URISyntaxException {
		File imageFile = new File(this.getClass().getResource("/images/image.jpg").toURI());
		FileInputStream fileInputStream = new FileInputStream(imageFile);
		byte[] image = new byte[(int) imageFile.length()];

		fileInputStream.read(image);
		fileInputStream.close();

		return image;
	}

	@Before
	public void setImages() throws IOException, URISyntaxException {
		professor1.setImage(getImage());
		professor2.setImage(getImage());
		student1.setImage(getImage());
		student2.setImage(getImage());
	}

}

