package college.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import college.model.Subject;
import college.service.SubjectService;

public class SubjectConverter implements Converter<String, Subject> {

	@Autowired
	private SubjectService subjectService;

	@Override
	public Subject convert(String id) {
		return ((id == null || id.isEmpty()) ? null : subjectService.findSubjectById(Long.valueOf(id)));
	}

}
