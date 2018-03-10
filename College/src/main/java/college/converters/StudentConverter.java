package college.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import college.model.Student;
import college.service.StudentService;

@Component
public class StudentConverter implements Converter<String, Student> {

	@Autowired
	private StudentService service;

	@Override
	public Student convert(String id) {
		return (id == null ? null : service.findStudentById(id));
	}

}

