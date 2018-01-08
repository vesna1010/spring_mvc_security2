package college.service.impl;

import java.util.Date;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import college.dao.extensions.ExamDao;
import college.model.Exam;
import college.model.Professor;
import college.model.Subject;
import college.service.ExamService;

@Service
public class ExamServiceImpl implements ExamService {

	@Resource
	private ExamDao examDao;

	@Override
	public void saveOrUpdateExam(Exam exam) {
		examDao.saveOrUpdate(exam);
	}

	@Override
	public Exam findExamById(String id) {
		return examDao.findOneById(id);
	}

	@Override
	public void deleteExamById(String id) {
		examDao.deleteById(id);
	}

	@Override
	public Set<Exam> findExamsByObjects(Professor professor, Subject subject, Date date) {
		return examDao.findExamsByObjects(professor, subject, date);
	}

}

