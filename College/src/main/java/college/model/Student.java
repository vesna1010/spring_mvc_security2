package college.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import college.enums.Gender;

@Entity
@Table(name = "STUDENTS")
public class Student extends Person {

	private static final long serialVersionUID = 1L;
	private Date dateOfEntry;
	private Integer yearOfStudy;
	private Double average;
	private StudyProgram studyProgram;
	private Set<Exam> exams = new HashSet<>();

	public Student() {
	}

	public Student(Long id, String fullName) {
		super(id, fullName);
	}

	public Student(Long id, String fullName, String fatherName, Date dateOfBirth, String email, String telephone,
			Gender gender, Address address, Date dateOfEntry, Integer yearOfStudy, StudyProgram studyProgram) {
		super(id, fullName, fatherName, dateOfBirth, email, telephone, gender, address);
		this.yearOfStudy = yearOfStudy;
		this.dateOfEntry = dateOfEntry;
		this.studyProgram = studyProgram;
	}

	@NotNull
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_OF_ENTRY")
	public Date getDateOfEntry() {
		return dateOfEntry;
	}

	public void setDateOfEntry(Date dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
	}

	@NotNull
	@Min(value = 1)
	@Max(value = 5)
	@Column(name = "YEAR_OF_STUDY")
	public Integer getYearOfStudy() {
		return yearOfStudy;
	}

	public void setYearOfStudy(Integer yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name = "STUDY_PROGRAM_ID")
	public StudyProgram getStudyProgram() {
		return studyProgram;
	}

	public void setStudyProgram(StudyProgram studyProgram) {
		this.studyProgram = studyProgram;
	}

	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<Exam> getExams() {
		return exams;
	}

	public void setExams(Set<Exam> exams) {
		this.exams = exams;
	}

	@Column(name = "AVERAGE")
	@Formula("(SELECT AVG(EXAMS.SCORE) FROM EXAMS WHERE EXAMS.STUDENT_ID=ID)")
	public Double getAverage() {
		return average;
	}

	public void setAverage(Double average) {
		this.average = average;
	}

}
