package college.formatters;

import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import college.model.StudyProgram;
import college.service.StudyProgramService;

@Component
public class StudyProgramFormatter implements Formatter<StudyProgram> {

	@Autowired
	private StudyProgramService studyProgramService;

	@Override
	public String print(StudyProgram studyProgram, Locale locale) {
		return studyProgram.getTitle();
	}

	@Override
	public StudyProgram parse(String studyProgramId, Locale locale) throws ParseException {
		return studyProgramService.findStudyProgramById(studyProgramId);
	}

}
