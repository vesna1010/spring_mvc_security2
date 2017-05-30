package college.formatters;

import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import college.model.StudyProgram;
import college.service.HibernateService;

public class StudyProgramFormatter implements Formatter<StudyProgram> {

	@Autowired
	private HibernateService<StudyProgram> studyProgramService;

	@Override
	public String print(StudyProgram studyProgram, Locale locale) {
		if (studyProgram == null){
			return null;
		};
			return studyProgram.getTitle();
	}

	@Override
	public StudyProgram parse(String id, Locale locale) throws ParseException {
		if (id == null){
			return null;
		}
		return studyProgramService.findOne(id);
	}

}
