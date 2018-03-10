package college.dao.impl.extensions;

import org.springframework.stereotype.Component;
import college.dao.extensions.LectureDao;
import college.dao.impl.HibernateDaoImpl;
import college.model.Lecture;

@Component("lectureDao")
public class LectureDaoImpl extends HibernateDaoImpl<Long, Lecture> implements LectureDao {

	public LectureDaoImpl() {
		setEntityClass(Lecture.class);
	}
	
}

