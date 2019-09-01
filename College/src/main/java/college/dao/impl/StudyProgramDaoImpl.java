package college.dao.impl;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import college.dao.StudyProgramDao;
import college.dao.impl.HibernateDaoImpl;
import college.model.Department;
import college.model.StudyProgram;

@SuppressWarnings("unchecked")
@Repository
public class StudyProgramDaoImpl extends HibernateDaoImpl<StudyProgram, Long> implements StudyProgramDao {

	public StudyProgramDaoImpl() {
		setEntityClass(StudyProgram.class);
	}

	@Override
	public List<StudyProgram> findAllByDepartment(Department department) {
		Query query = getSession().createQuery("select s from StudyProgram s where s.department=:department");

		query.setEntity("department", department);

		return query.list();
	}

}

