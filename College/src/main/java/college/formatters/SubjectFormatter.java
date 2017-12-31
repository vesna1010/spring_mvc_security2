package college.formatters;

import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import college.model.Subject;
import college.service.SubjectService;

@Component
public class SubjectFormatter implements Formatter<Subject> {

	@Autowired
	private SubjectService subjectService;

	@Override
	public String print(Subject subject, Locale locale) {
		return subject.getTitle();
	}

	@Override
	public Subject parse(String subjectId, Locale locale) throws ParseException {
		return subjectService.findSubjectById(subjectId);
	}

}
