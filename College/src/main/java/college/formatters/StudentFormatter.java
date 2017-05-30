package college.formatters;

import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import college.model.Student;
import college.service.HibernateService;

public class StudentFormatter implements Formatter<Student> {

	@Autowired
	private HibernateService<Student> studentService;

	@Override
	public String print(Student student, Locale locale) {
		if (student == null) {
			return null;
		}
		return student.getFullName();
	}

	@Override
	public Student parse(String id, Locale locale) throws ParseException {
		if (id == null) {
			return null;
		}
		return studentService.findOne(id);
	}

}
