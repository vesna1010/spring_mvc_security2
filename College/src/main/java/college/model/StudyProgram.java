package college.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;
import college.validation.MyId;
import college.validation.Title;

@SuppressWarnings({ "serial" })
@Entity
@Table(name = "STUDY_PROGRAMS")
public class StudyProgram implements Serializable {

	private String id;
	private String title;
	private Date dateOfCreation;
	private Integer durationOfStudy;
	private Department department;
	private Set<Student> students = new HashSet<>();
	private Set<Subject> subjects = new HashSet<>();

	public StudyProgram() {
	}

	public StudyProgram(String id, String title, Date dateOfCreation, Integer durationOfStudy, Department department) {
		this.id = id;
		this.title = title;
		this.dateOfCreation = dateOfCreation;
		this.durationOfStudy = durationOfStudy;
		this.department = department;
	}

	@Id
	@MyId
	@Column(name = "ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Title
	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotNull
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_OF_CREATION")
	public Date getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DEPARTMENT_ID")
	@Cascade(value = CascadeType.SAVE_UPDATE)
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@OneToMany(mappedBy = "studyProgram", fetch = FetchType.LAZY)
	@Cascade(value = CascadeType.ALL)
	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public void addStudent(Student student) {
		student.setStudyProgram(this);
		this.students.add(student);
	}

	@OneToMany(mappedBy = "studyProgram", fetch = FetchType.LAZY)
	@Cascade(value = CascadeType.ALL)
	public Set<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}

	public void addSubject(Subject subject) {
		subject.setStudyProgram(this);
		this.subjects.add(subject);
	}

	@NotNull
	@Min(value = 1)
	@Max(value = 5)
	@Column(name = "DURATION_OF_STUDY")
	public Integer getDurationOfStudy() {
		return durationOfStudy;
	}

	public void setDurationOfStudy(Integer durationOfStudy) {
		this.durationOfStudy = durationOfStudy;
	}

	@Transient
	public Integer getEcts() {
		return this.durationOfStudy * 60;
	}

	@Transient
	public Set<Professor> getProfessors() {
		Set<Professor> professors = new HashSet<>();

		for (Subject subject : this.subjects) {
			professors.addAll(subject.getProfessors());
		}

		return professors;
	}

	@Transient
	public Set<Lecture> getLectures() {
		Set<Lecture> lectures = new HashSet<>();

		for (Subject subject : this.subjects) {
			lectures.addAll(subject.getLectures());
		}

		return lectures;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudyProgram other = (StudyProgram) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

