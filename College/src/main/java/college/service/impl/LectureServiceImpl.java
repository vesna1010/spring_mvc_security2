package college.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import college.dao.LectureDao;
import college.model.Lecture;
import college.model.ProfessorSubjectId;
import college.model.StudyProgram;
import college.service.LectureService;

@Service
@Transactional
public class LectureServiceImpl implements LectureService {

	private LectureDao lectureDao;

	@Autowired
	public LectureServiceImpl(LectureDao lectureDao) {
		this.lectureDao = lectureDao;
	}

	@Override
	public List<Lecture> findAllLecturesByStudyProgram(StudyProgram studyProgram) {
		return lectureDao.findAllByStudyProgram(studyProgram);
	}

	@Override
	public void saveOrUpdateLecture(Lecture lecture) {
		lectureDao.saveOrUpdate(lecture);
	}

	@Override
	public void deleteLectureById(ProfessorSubjectId id) {
		lectureDao.deleteById(id);
	}

}
