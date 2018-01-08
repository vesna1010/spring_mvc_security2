package college.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import college.dao.extensions.LectureDao;
import college.model.Lecture;
import college.service.LectureService;

@Service
public class LectureServiceImpl implements LectureService {
	
	@Resource
	private LectureDao lectureDao;

	@Override
	public Lecture findLectureById(String id) {
		return lectureDao.findOneById(id);
	}

	@Override
	public void saveOrUpdateLecture(Lecture lecture) {
		lectureDao.saveOrUpdate(lecture);
	}

	@Override
	public void deleteLectureById(String id) {
		lectureDao.deleteById(id);
	}

}

