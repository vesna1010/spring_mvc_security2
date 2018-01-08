package college.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import college.validation.MyId;

@SuppressWarnings("serial")
@Entity
@Table(name = "SUBJECTS_PROFESSORS")
public class Lecture implements Serializable {

	private String id;
	private Professor professor;
	private Subject subject;
	private Integer hours;
    
	public Lecture(){
	}

	public Lecture(String id, Professor professor, Subject subject, Integer hours) {
		this.id = id;
		this.professor = professor;
		this.subject = subject;
		this.hours = hours;
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

	@NotNull
	@ManyToOne
	@JoinColumn(name = "PROFESSOR_ID")
	@Cascade(CascadeType.MERGE)
	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name = "SUBJECT_ID")
	@Cascade(CascadeType.MERGE)
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@NotNull
	@Min(value = 1)
	@Max(value = 5)
	@Column(name = "HOURS")
	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((professor == null) ? 0 : professor.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
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
		Lecture other = (Lecture) obj;
		if (professor == null) {
			if (other.professor != null)
				return false;
		} else if (!professor.equals(other.professor))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}

}

