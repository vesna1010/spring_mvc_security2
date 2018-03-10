package college.model;

import java.util.Set;
import java.util.Date;
import java.util.HashSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;
import college.enums.Gender;

@SuppressWarnings("serial")
@Entity
@Table(name = "PROFESSORS")
public class Professor extends Person {

	private Date dateOfEmployment;
	private String titleOfProfessor;
	private Set<Lecture> lectures = new HashSet<>();
	private Set<Exam> exams = new HashSet<>();

	public Professor() {
	}

	public Professor(String id, String fullName, String fatherName, Date dateOfBirth, String email, String telephone,
			Gender gender, Address address, Date dateOfEmployment, String titleOfProfessor) {
		super(id, fullName, fatherName, dateOfBirth, email, telephone, gender, address);
		this.dateOfEmployment = dateOfEmployment;
		this.titleOfProfessor = titleOfProfessor;
	}

	@Pattern(regexp = "^([a-zA-Z]+\\s?){5,}$")
	@Column(name = "TITLE")
	public String getTitleOfProfessor() {
		return titleOfProfessor;
	}

	public void setTitleOfProfessor(String titleOfProfessor) {
		this.titleOfProfessor = titleOfProfessor;
	}

	@NotNull
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_OF_EMPLOYMENT")
	public Date getDateOfEmployment() {
		return dateOfEmployment;
	}

	public void setDateOfEmployment(Date dateOfEmployment) {
		this.dateOfEmployment = dateOfEmployment;
	}

	@OneToMany(mappedBy = "professor", fetch = FetchType.LAZY)
	@Cascade(value = CascadeType.ALL)
	public Set<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(Set<Lecture> lectures) {
		this.lectures = lectures;
	}

	public void addLecture(Lecture lecture) {
		lecture.setProfessor(this);
		this.lectures.add(lecture);
	}

	@OneToMany(mappedBy = "professor", fetch = FetchType.LAZY)
	@Cascade(value = CascadeType.ALL)
	public Set<Exam> getExams() {
		return exams;
	}

	public void setExams(Set<Exam> exams) {
		this.exams = exams;
	}

	public void addExam(Exam exam) {
		exam.setProfessor(this);
		exams.add(exam);
	}

	@Transient
	public Set<Subject> getSubjects() {
		Set<Subject> subjects = new HashSet<>();

		for (Lecture subjectProfessor : this.lectures) {
			subjects.add(subjectProfessor.getSubject());
		}

		return subjects;
	}

}

