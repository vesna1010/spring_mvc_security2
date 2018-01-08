package college.service;

import college.model.Lecture;

public interface LectureService {
		
	Lecture findLectureById(String id);
	
	void saveOrUpdateLecture(Lecture lecture);
	
	void deleteLectureById(String id);
}

