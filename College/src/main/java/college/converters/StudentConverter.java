package college.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import college.model.Student;
import college.service.StudentService;

public class StudentConverter implements Converter<String, Student> {

	@Autowired
	private StudentService studentService;

	@Override
	public Student convert(String id) {
		return ((id == null || id.isEmpty()) ? null : studentService.findStudentById(Long.valueOf(id)));
	}

}

