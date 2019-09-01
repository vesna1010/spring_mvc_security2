package college.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import college.dao.ExamDao;
import college.model.Exam;
import college.model.Professor;
import college.model.Student;
import college.model.StudentSubjectId;
import college.model.StudyProgram;
import college.model.Subject;
import college.service.ExamService;

@Service
@Transactional
public class ExamServiceImpl implements ExamService {

	private ExamDao examDao;

	@Autowired
	public ExamServiceImpl(ExamDao examDao) {
		this.examDao = examDao;
	}

	@Override
	public List<Exam> findAllExamsByStudyProgram(StudyProgram studyProgram) {
		return examDao.findAllByStudyProgram(studyProgram);
	}

	@Override
	public List<Exam> findAllExamsByStudent(Student student) {
		return examDao.findAllByStudent(student);
	}

	@Override
	public List<Exam> findAllExamsByProfessorAndSubjectAndDate(Professor professor, Subject subject, Date date) {
		return examDao.findAllByProfessorAndSubjectAndDate(professor, subject, date);
	}

	@Override
	public void saveOrUpdateExam(Exam exam) {
		examDao.saveOrUpdate(exam);
	}

	@Override
	public void deleteExamById(StudentSubjectId id) {
		examDao.deleteById(id);
	}

}
