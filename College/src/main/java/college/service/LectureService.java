package college.service;

import java.util.List;
import college.model.Lecture;
import college.model.ProfessorSubjectId;
import college.model.StudyProgram;

public interface LectureService {

	List<Lecture> findAllLecturesByStudyProgram(StudyProgram studyProgram);

	void saveOrUpdateLecture(Lecture lecture);

	void deleteLectureById(ProfessorSubjectId id);

}

