package college.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import college.validation.MyId;
import college.validation.Title;

@Entity
@Table(name = "SUBJECTS")
public class Subject implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@MyId
	@Column(name = "ID")
	private String id;

	@Title
	@Column(name = "TITLE")
	private String title;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ST_PROGRAM_ID")
	private StudyProgram studyProgram;

	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SubjectProfessor> subjectsProfessors;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "subject")
	private List<Exam> exams;

	public Subject() {
		exams = new ArrayList<Exam>();
		subjectsProfessors = new ArrayList<SubjectProfessor>();
	}

	public Subject(String id, String title, StudyProgram studyProgram) {
		exams = new ArrayList<Exam>();
		subjectsProfessors = new ArrayList<SubjectProfessor>();
		this.id=id;
		this.title = title;
		this.studyProgram = studyProgram;
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

	public StudyProgram getStudyProgram() {
		return studyProgram;
	}

	public void setStudyProgram(StudyProgram studyProgram) {
		this.studyProgram = studyProgram;
	}

	

	public List<SubjectProfessor> getSubjectsProfessors() {
		return subjectsProfessors;
	}

	public List<Exam> getExams() {
		return exams;
	}

	public void setExams(List<Exam> exams) {
		this.exams = exams;
	}

	//returns list professors for specified subject
	public List<Professor> getProfessors() {
		List<Professor> professors = new ArrayList<Professor>();
		for (SubjectProfessor subProf : this.subjectsProfessors)
			if(!professors.contains(subProf.getProfessor()))
			professors.add(subProf.getProfessor());
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
