package college.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="STUDENTS")
public class Student extends Person{
	
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name="DATE_OF_ENTRY")
	@Temporal(TemporalType.DATE)
	private Date dateOfEntry;
	
	@NotNull
	@Min(value = 1)
	@Max(value = 5)
	@Column(name="YEAR_OF_STUDY")
	private Integer yearOfStudy;
	
	@Column(name="AVERAGE")
	@Formula("(SELECT AVG(EXAMS.SCORE) FROM EXAMS WHERE EXAMS.STUDENT_ID=ID)")
	private Double average=(double) 0;
	
	//the student can take only one study program
	@NotNull
	@ManyToOne
	@JoinColumn(name="ST_PROGRAM_ID")
	private StudyProgram studyProgram;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="student")
	private List<Exam> exams;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name="DATE_OF_COMPLETION", nullable=true)
	@Temporal(TemporalType.DATE)
	private Date dateOfCompletion;
	
	public Student(){
		exams=new ArrayList<Exam>();
	}
	
	public Student(String id, String firstName, String lastName, String fatherName, Date dateOfBirth, String email,
			String telephone,String gender, Address address, Date dateOfEntry, Integer yearOfStudy, StudyProgram studyProgram) {
		super(id,firstName, lastName, fatherName, dateOfBirth, email,telephone,gender, address);
		exams=new ArrayList<Exam>();
		this.dateOfEntry = dateOfEntry;
		this.yearOfStudy = yearOfStudy;
		this.studyProgram=studyProgram;
	}

	public Date getDateOfEntry() {
		return dateOfEntry;
	}

	public void setDateOfEntry(Date dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
	}

	public Integer getYearOfStudy() {
		return yearOfStudy;
	}

	public void setYearOfStudy(Integer yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}

	public StudyProgram getStudyProgram() {
		return studyProgram;
	}

	public void setStudyProgram(StudyProgram studyProgram) {
		this.studyProgram = studyProgram;
	}

	public Date getDateOfCompletion() {
		return dateOfCompletion;
	}

	public void setDateOfCompletion(Date dateOfCompletion) {
		this.dateOfCompletion = dateOfCompletion;
	}

	public List<Exam> getExams() {
		return exams;
	}

	public void setExams(List<Exam> exams) {
		this.exams = exams;
	}

	public Double getAverage() {
		return average;
	}

	public void setAverage(Double average) {
		this.average = average;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((average == null) ? 0 : average.hashCode());
		result = prime * result + ((dateOfCompletion == null) ? 0 : dateOfCompletion.hashCode());
		result = prime * result + ((dateOfEntry == null) ? 0 : dateOfEntry.hashCode());
		result = prime * result + ((studyProgram == null) ? 0 : studyProgram.hashCode());
		result = prime * result + ((yearOfStudy == null) ? 0 : yearOfStudy.hashCode());
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
		Student other = (Student) obj;
		if (average == null) {
			if (other.average != null)
				return false;
		} else if (!average.equals(other.average))
			return false;
		if (dateOfCompletion == null) {
			if (other.dateOfCompletion != null)
				return false;
		} else if (!dateOfCompletion.equals(other.dateOfCompletion))
			return false;
		if (dateOfEntry == null) {
			if (other.dateOfEntry != null)
				return false;
		} else if (!dateOfEntry.equals(other.dateOfEntry))
			return false;
		if (studyProgram == null) {
			if (other.studyProgram != null)
				return false;
		} else if (!studyProgram.equals(other.studyProgram))
			return false;
		if (yearOfStudy == null) {
			if (other.yearOfStudy != null)
				return false;
		} else if (!yearOfStudy.equals(other.yearOfStudy))
			return false;
		return true;
	}

	

}
