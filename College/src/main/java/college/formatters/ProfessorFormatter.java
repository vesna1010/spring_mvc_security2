package college.formatters;

import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import college.model.Professor;
import college.service.HibernateService;

public class ProfessorFormatter implements Formatter<Professor> {

	@Autowired
	private HibernateService<Professor> professorService;

	@Override
	public String print(Professor professor, Locale locale) {
		if (professor == null)
			return "";
		else
			return professor.getFullName();
	}

	@Override
	public Professor parse(String id, Locale locale) throws ParseException {
		return professorService.findOne(id);
	}

}
