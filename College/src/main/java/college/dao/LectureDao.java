package college.dao;

import java.util.List;
import college.dao.HibernateDao;
import college.model.Lecture;
import college.model.ProfessorSubjectId;
import college.model.StudyProgram;

public interface LectureDao extends HibernateDao<Lecture, ProfessorSubjectId> {

	List<Lecture> findAllByStudyProgram(StudyProgram studyProgram);

}
