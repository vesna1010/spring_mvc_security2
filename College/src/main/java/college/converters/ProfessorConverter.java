package college.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import college.model.Professor;
import college.service.ProfessorService;

@Component
public class ProfessorConverter implements Converter<String, Professor> {

	@Autowired
	private ProfessorService service;

	@Override
	public Professor convert(String id) {
		return (id == null ? null : service.findProfessorById(id));
	}

}

