package college.converters;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import college.model.Exam;
import college.service.ExamService;

@Component
public class ExamConverter implements Converter<String, Exam> {

	@Autowired
	private ExamService service;

	@Override
	public Exam convert(String id) {
		return (id == null ? null : service.findExamById(id));
	}

}
