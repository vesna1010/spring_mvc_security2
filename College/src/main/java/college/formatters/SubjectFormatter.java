package college.formatters;

import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import college.model.Subject;
import college.service.HibernateService;

public class SubjectFormatter implements Formatter<Subject> {

	@Autowired
	private HibernateService<Subject> service;

	@Override
	public String print(Subject subject, Locale locale) {
		if (subject == null) {
			return null;
		}
		return subject.getTitle();
	}

	@Override
	public Subject parse(String id, Locale locale) throws ParseException {
		if (id == null){
			return null;
		}
		return service.findOne(id);
	}

}
