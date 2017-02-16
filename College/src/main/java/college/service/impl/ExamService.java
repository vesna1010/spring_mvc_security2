package college.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import college.dao.impl.ExamDao;
import college.model.Exam;
import college.model.Professor;
import college.model.Subject;
import college.service.HibernateService;

@Service
public class ExamService extends HibernateService<Exam> {
	
	
	private ExamDao dao;
	
	
	@Autowired
	public void setDao(ExamDao dao) {
		this.dao = dao;
	}



	public List<Exam> findByObjects(Professor professor, Subject subject, Date date) {
		return dao.findByObjects(professor, subject, date);
	}
		

}
