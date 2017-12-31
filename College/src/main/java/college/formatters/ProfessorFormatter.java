package college.formatters;

import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import college.model.Professor;
import college.service.ProfessorService;

@Component
public class ProfessorFormatter implements Formatter<Professor> {

	@Autowired
	private ProfessorService professorService;

	@Override
	public String print(Professor professor, Locale locale) {
		return professor.getFullName();
	}

	@Override
	public Professor parse(String professorId, Locale locale) throws ParseException {
		return professorService.findProfessorById(professorId);
	}

}
