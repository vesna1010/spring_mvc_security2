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
	public Lecture findLectureById(Long id) {
		return lectureDao.findById(id);
	}

	@Override
	public void saveOrUpdateLecture(Lecture lecture) {
		lectureDao.saveOrUpdate(lecture);
	}

	@Override
	public void deleteLecture(Lecture lecture) {
		lectureDao.delete(lecture);
	}

}

