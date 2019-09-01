package college.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import college.model.StudyProgram;
import college.service.StudyProgramService;

public class StudyProgramConverter implements Converter<String, StudyProgram> {

	@Autowired
	private StudyProgramService studyProgramService;

	@Override
	public StudyProgram convert(String id) {
		return ((id == null || id.isEmpty()) ? null : studyProgramService.findStudyProgramById(Long.valueOf(id)));
	}

}

