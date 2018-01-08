package college.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import college.validation.MyId;
import college.validation.Title;

@SuppressWarnings("serial")
@Entity
@Table(name = "SUBJECTS")
public class Subject implements Serializable {

	private String id = "";
	private String title = "";
	private StudyProgram studyProgram;
	private Set<Lecture> lectures = new HashSet<>();
	private Set<Exam> exams = new HashSet<>();

	public Subject() {
	}
	
	public Subject(String id, String title, StudyProgram studyProgram) {
		this.id = id;
		this.title = title;
		this.studyProgram = studyProgram;
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
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STUDY_PROGRAM_ID")
	@Cascade(CascadeType.MERGE)
	public StudyProgram getStudyProgram() {
		return studyProgram;
	}

	public void setStudyProgram(StudyProgram studyProgram) {
		this.studyProgram = studyProgram;
	}

	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	public Set<Exam> getExams() {
		return exams;
	}
	
	void setExams(Set<Exam> exams) {
		this.exams = exams;
	}
	
	public void addExam(Exam exam) {
		exam.setSubject(this);
		exams.add(exam);
	}

	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	public Set<Lecture> getLectures() {
		return lectures;
	}

	void setLectures(Set<Lecture> lectures) {
		this.lectures = lectures;
	}
	
	public void addLecture(Lecture lecture) {
		lecture.setSubject(this);
		this.lectures.add(lecture);
	}

	@Transient
	public Set<Professor> getProfessors() {
		Set<Professor> professors = new HashSet<>();

		for (Lecture subjectProfessor : this.lectures) {
			professors.add(subjectProfessor.getProfessor());
		}

		return professors;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((studyProgram == null) ? 0 : studyProgram.hashCode());
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
		Subject other = (Subject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (studyProgram == null) {
			if (other.studyProgram != null)
				return false;
		} else if (!studyProgram.equals(other.studyProgram))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}

