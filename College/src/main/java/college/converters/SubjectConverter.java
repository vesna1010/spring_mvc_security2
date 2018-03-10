package college.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import college.model.Subject;
import college.service.SubjectService;

@Component
public class SubjectConverter implements Converter<String, Subject> {

	@Autowired
	private SubjectService service;

	@Override
	public Subject convert(String id) {
		return (id == null ? null : service.findSubjectById(id));
	}

}
