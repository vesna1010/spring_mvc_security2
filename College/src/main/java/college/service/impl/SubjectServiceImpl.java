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
		return subjectDao.findOneById(id);
	}

	@Override
	public void deleteSubjectById(String id) {
		subjectDao.deleteById(id);
	}

	@Override
	public void saveOrUpdateSubject(Subject subject) {
		subjectDao.saveOrUpdate(subject);
	}

}
