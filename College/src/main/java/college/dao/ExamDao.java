package college.dao;

import java.util.Date;
import java.util.List;
import college.dao.HibernateDao;
import college.model.Exam;
import college.model.Professor;
import college.model.Student;
import college.model.StudentSubjectId;
import college.model.StudyProgram;
import college.model.Subject;

public interface ExamDao extends HibernateDao<Exam, StudentSubjectId> {

	List<Exam> findAllByStudyProgram(StudyProgram studyProgram);

	List<Exam> findAllByStudent(Student student);

	List<Exam> findAllByProfessorAndSubjectAndDate(Professor professor, Subject subject, Date date);

}
