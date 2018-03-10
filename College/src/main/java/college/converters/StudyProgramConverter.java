package college.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import college.model.StudyProgram;
import college.service.StudyProgramService;

@Component
public class StudyProgramConverter implements Converter<String, StudyProgram> {

	@Autowired
	private StudyProgramService service;

	@Override
	public StudyProgram convert(String id) {
		return (id == null ? null : service.findStudyProgramById(id));
	}

}

