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

	
	private String id = "";
	private String title = "";
	private Date dateOfCreation = new Date();
	private Integer durationOfStudy = 3;
	private Department department;
	private Set<Student> students = new HashSet<>();
	private Set<Subject> subjects = new HashSet<>();

	public StudyProgram() {
	}

	public StudyProgram(String id, String title, Department department) {
		this.id = id;
		this.title = title;
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
	@Cascade(CascadeType.SAVE_UPDATE)
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@OneToMany(mappedBy = "studyProgram", fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	public Set<Student> getStudents() {
		return students;
	}
	
	void setStudents(Set<Student> students) {
		this.students = students;
	}
	
	public void addStudent(Student student) {
		student.setStudyProgram(this);
		this.students.add(student);
	}

	@OneToMany(mappedBy = "studyProgram", fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	public Set<Subject> getSubjects() {
		return subjects;
	}
	
	void setSubjects(Set<Subject> subjects) {
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
			for (Professor professor : subject.getProfessors()) {
				professors.add(professor);
			}
		}

		return professors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((department == null) ? 0 : department.hashCode());
		result = prime * result + ((durationOfStudy == null) ? 0 : durationOfStudy.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (durationOfStudy == null) {
			if (other.durationOfStudy != null)
				return false;
		} else if (!durationOfStudy.equals(other.durationOfStudy))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
