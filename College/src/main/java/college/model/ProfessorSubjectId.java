package college.model;

import java.io.Serializable;

public class ProfessorSubjectId implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long professor;
	private Long subject;

	public ProfessorSubjectId() {
	}

	public ProfessorSubjectId(Long professor, Long subject) {
		this.professor = professor;
		this.subject = subject;
	}

	public Long getProfessor() {
		return professor;
	}

	public void setProfessor(Long professor) {
		this.professor = professor;
	}

	public Long getSubject() {
		return subject;
	}

	public void setSubject(Long subject) {
		this.subject = subject;
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
		ProfessorSubjectId other = (ProfessorSubjectId) obj;
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
