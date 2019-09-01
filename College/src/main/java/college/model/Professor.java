package college.model;

import java.util.Set;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import college.enums.Gender;
import college.validation.Title;

@Entity
@Table(name = "PROFESSORS")
public class Professor extends Person implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date dateOfEmployment;
	private String titleOfProfessor;
	private Set<Lecture> lectures = new HashSet<>();
	private Set<Exam> exams = new HashSet<>();

	public Professor() {
	}

	public Professor(Long id, String fullName) {
		super(id, fullName);
	}

	public Professor(Long id, String fullName, String fatherName, Date dateOfBirth, String email, String telephone,
			Gender gender, Address address, Date dateOfEmployment, String titleOfProfessor) {
		super(id, fullName, fatherName, dateOfBirth, email, telephone, gender, address);
		this.dateOfEmployment = dateOfEmployment;
		this.titleOfProfessor = titleOfProfessor;
	}

	@Title
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

	@OneToMany(mappedBy = "professor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(Set<Lecture> lectures) {
		this.lectures = lectures;
	}

	@OneToMany(mappedBy = "professor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<Exam> getExams() {
		return exams;
	}

	public void setExams(Set<Exam> exams) {
		this.exams = exams;
	}

}
