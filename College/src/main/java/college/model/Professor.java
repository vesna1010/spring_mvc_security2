package college.model;

import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "PROFESSORS")
public class Professor extends Person {

	private static final long serialVersionUID = 1L;

	@Pattern(regexp = "^([a-zA-Z]+\\s?){5,}$")
	@Column(name = "TITLE")
	private String title;

	@NotNull
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DATE_OF_EMPLOYMENT")
	@Temporal(TemporalType.DATE)
	private Date dateOfEmployment;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DATE_OF_TERMINATION", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dateOfTermination;

	@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SubjectProfessor> subjectsProfessors;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "professor")
	private List<Exam> exams;

	public Professor() {
		exams = new ArrayList<Exam>();
		subjectsProfessors = new ArrayList<SubjectProfessor>();
	}

	public Professor(String id, String firstName, String lastName, String fatherName, Date dateOfBirth, String email,
			String telephone,String gender, Address address,String title, Date dateOfEmployment) {
		super(id,firstName, lastName, fatherName, dateOfBirth, email, telephone, gender, address);
		exams = new ArrayList<Exam>();
		subjectsProfessors = new ArrayList<SubjectProfessor>();
		this.title = title;
		this.dateOfEmployment = dateOfEmployment;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDateOfEmployment() {
		return dateOfEmployment;
	}

	public void setDateOfEmployment(Date dateOfEmployment) {
		this.dateOfEmployment = dateOfEmployment;
	}

	public Date getDateOfTermination() {
		return dateOfTermination;
	}

	public void setDateOfTermination(Date dateOfTermination) {
		this.dateOfTermination = dateOfTermination;
	}

	@NotEmpty
	public List<SubjectProfessor> getSubjectsProfessors() {
		return subjectsProfessors;
	}

	public List<Exam> getExams() {
		return exams;
	}

	public void setExams(List<Exam> exams) {
		this.exams = exams;
	}

	//set up list subjects for specified professor
	public void setSubjects(List<Subject> subjects) {
		this.subjectsProfessors.clear();
		for(Subject subject:subjects)
			this.subjectsProfessors.add(new SubjectProfessor(this, subject));
	}

	//returns list subjects for specified professor
	public List<Subject> getSubjects() {
		List<Subject> subjects = new ArrayList<Subject>();
		for (SubjectProfessor subProf : this.subjectsProfessors)
			if(!subjects.contains(subProf.getSubject()))
				subjects.add(subProf.getSubject());
		return subjects;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dateOfEmployment == null) ? 0 : dateOfEmployment.hashCode());
		result = prime * result + ((dateOfTermination == null) ? 0 : dateOfTermination.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Professor other = (Professor) obj;
		if (dateOfEmployment == null) {
			if (other.dateOfEmployment != null)
				return false;
		} else if (!dateOfEmployment.equals(other.dateOfEmployment))
			return false;
		if (dateOfTermination == null) {
			if (other.dateOfTermination != null)
				return false;
		} else if (!dateOfTermination.equals(other.dateOfTermination))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
