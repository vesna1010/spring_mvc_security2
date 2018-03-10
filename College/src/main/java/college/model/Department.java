package college.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;
import college.validation.MyId;
import college.validation.Title;

@SuppressWarnings("serial")
@Entity
@Table(name = "DEPARTMENTS")
public class Department implements Serializable {

	
	private String id;
	private String title;
	private Date dateOfCreation;
	private Set<StudyProgram> studyPrograms = new HashSet<>();;

	public Department() {
	}

	public Department(String id, String title, Date dateOfCreation) {
		this.id = id;
		this.title = title;
		this.dateOfCreation = dateOfCreation;
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

	@OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
	@Cascade(value = CascadeType.ALL)
	public Set<StudyProgram> getStudyPrograms() {
		return studyPrograms;
	}

	public void setStudyPrograms(Set<StudyProgram> studyPrograms) {
		this.studyPrograms = studyPrograms;
	}

	public void addStudyProgram(StudyProgram studyProgram) {
		studyProgram.setDepartment(this);
		this.studyPrograms.add(studyProgram);
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
		Department other = (Department) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

