package college.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import college.validation.Title;

@Entity
@Table(name = "DEPARTMENTS")
public class Department implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String title;
	private Date dateOfCreation;
	private Set<StudyProgram> studyPrograms = new HashSet<>();;

	public Department() {
	}

	public Department(Long id, String title) {
		this(id, title, new Date());
	}

	public Department(Long id, String title, Date dateOfCreation) {
		this.id = id;
		this.title = title;
		this.dateOfCreation = dateOfCreation;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	@OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<StudyProgram> getStudyPrograms() {
		return studyPrograms;
	}

	public void setStudyPrograms(Set<StudyProgram> studyPrograms) {
		this.studyPrograms = studyPrograms;
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
