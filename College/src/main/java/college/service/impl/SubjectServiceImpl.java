package college.service.impl;

import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import college.dao.extensions.SubjectDao;
import college.model.Subject;
import college.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Resource
	private SubjectDao subjectDao;

	@Override
	public Set<Subject> findAllSubjects() {
		return subjectDao.findAll();
	}

	@Override
	public Subject findSubjectById(String id) {
		return subjectDao.findById(id);
	}

	@Override
	public void deleteSubject(Subject subject) {
		subjectDao.delete(subject);
	}

	@Override
	public void saveOrUpdateSubject(Subject subject) {
		subjectDao.saveOrUpdate(subject);
	}

}

