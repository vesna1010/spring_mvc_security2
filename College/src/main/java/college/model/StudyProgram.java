package college.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;
import college.validation.MyId;
import college.validation.Title;

@Entity
@Table(name="STUDY_PROGRAMS")
public class StudyProgram implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@MyId
	@Column(name="ID")
	private String id;

	@Title
	@Column(name="TITLE")
	private String title;

	@NotNull
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name="DATE_OF_CREATION")
	@Temporal(TemporalType.DATE)
	private Date dateOfCreation;
	
	@NotNull
	@Min(value = 1)
	@Max(value = 5)
	@Column(name="DURATION_OF_STUDY")
	private Integer durationOfStudy;
	
	@Formula("DURATION_OF_STUDY*60")
	@Column(name="ECTS")
	private Integer ects;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="DEPARTMENT_ID")
	private Department department;

	@OrderBy("yearOfStudy ASC")
	@OneToMany(mappedBy="studyProgram", cascade=CascadeType.ALL)
	private List<Student> students;
	
	@OneToMany(mappedBy="studyProgram", cascade=CascadeType.ALL)
	private List<Subject> subjects;

	public StudyProgram(){
		students=new ArrayList<Student>();
		subjects=new ArrayList<Subject>();
	}

	public StudyProgram(String id, String title, Date dateOfCreation, Department department, Integer durationOfStudy) {
		students=new ArrayList<Student>();
		subjects=new ArrayList<Subject>();
		this.id = id;
		this.title = title;
		this.dateOfCreation = dateOfCreation;
		this.department = department;
		this.durationOfStudy=durationOfStudy;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<Student> getStudents() {
		List<Student> activeStudents=new ArrayList<Student>();
		for(Student student:this.students)
			if(student.getDateOfCompletion()==null)
				activeStudents.add(student);
		return activeStudents;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}
	
	public Integer getDurationOfStudy() {
		return durationOfStudy;
	}

	public void setDurationOfStudy(Integer durationOfStudy) {
		this.durationOfStudy = durationOfStudy;
	}

	public Integer getEcts() {
		return ects;
	}

	public void setEcts(Integer ects) {
		this.ects = ects;
	}

	public List<Professor> getProfessors(){
		List<Professor> professors=new ArrayList<Professor>();
		for(Subject subject: getSubjects()){
			for(Professor professor: subject.getProfessors()){
				if(!professors.contains(professor) && professor.getDateOfTermination()==null)
					professors.add(professor);
			}
		}
		return professors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateOfCreation == null) ? 0 : dateOfCreation.hashCode());
		result = prime * result + ((department == null) ? 0 : department.hashCode());
		result = prime * result + ((durationOfStudy == null) ? 0 : durationOfStudy.hashCode());
		result = prime * result + ((ects == null) ? 0 : ects.hashCode());
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
		if (dateOfCreation == null) {
			if (other.dateOfCreation != null)
				return false;
		} else if (!dateOfCreation.equals(other.dateOfCreation))
			return false;
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
		if (ects == null) {
			if (other.ects != null)
				return false;
		} else if (!ects.equals(other.ects))
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
