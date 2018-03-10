package college.service;

import college.model.Lecture;

public interface LectureService {

	Lecture findLectureById(Long id);

	void saveOrUpdateLecture(Lecture lecture);

	void deleteLecture(Lecture lecture);
	
}

