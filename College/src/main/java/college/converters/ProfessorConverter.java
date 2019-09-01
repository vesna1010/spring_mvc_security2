package college.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import college.model.Professor;
import college.service.ProfessorService;

public class ProfessorConverter implements Converter<String, Professor> {

	@Autowired
	private ProfessorService professorService;

	@Override
	public Professor convert(String id) {
		return ((id == null || id.isEmpty()) ? null : professorService.findProfessorById(Long.valueOf(id)));
	}

}

