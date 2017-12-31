package college.formatters;

import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import college.model.Student;
import college.service.StudentService;

@Component
public class StudentFormatter implements Formatter<Student> {

	@Autowired
	private StudentService studentService;

	@Override
	public String print(Student student, Locale locale) {
		return student.getFullName();
	}

	@Override
	public Student parse(String studentId, Locale locale) throws ParseException {
		return studentService.findStudentById(studentId);
	}

}
