package college.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import college.model.StudyProgram;
import college.model.Subject;
import college.service.StudyProgramService;
import college.service.SubjectService;

@Controller
@RequestMapping("/subjects")
public class SubjectsController {

	@Autowired
	private SubjectService subjectService;
	@Autowired
	private StudyProgramService studyProgramService;

	@RequestMapping(method = RequestMethod.GET, params = "!studyProgram")
	public ModelAndView renderSubjectsPageWithAllSubjects(ModelAndView model) {
		model.setViewName("subjectsPage");
		model.addObject("title", "All Subjects");
		model.addObject("subjects", subjectService.findAllSubjects());

		return model;
	}

	@RequestMapping(method = RequestMethod.GET, params = "studyProgram")
	public ModelAndView renderSubjectsPageWithSubjectsByStudyProgram(@RequestParam StudyProgram studyProgram) {
		ModelAndView model = new ModelAndView("subjectsPage");

		model.addObject("title", "Subjects at " + studyProgram.getTitle());
		model.addObject("subjects", studyProgram.getSubjects());

		return model;
	}

	@RequestMapping(value = "/subjectForm", method = RequestMethod.GET)
	public ModelAndView renderEmptySubjectForm(ModelAndView model) {
		model.setViewName("subjectForm");
		model.addObject("subject", new Subject());
		model.addObject("studyPrograms", studyProgramService.findAllStudyPrograms());

		return model;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveSubjectAndRenderSubjectForm(@Valid @ModelAttribute Subject subject, BindingResult result) {

		if (!result.hasErrors()) {
			return saveSubjectAndGetModelAndView(subject);
		}

		return new ModelAndView("subjectForm", "studyPrograms", studyProgramService.findAllStudyPrograms());
	}

	private ModelAndView saveSubjectAndGetModelAndView(Subject subject) {
		subjectService.saveOrUpdateSubject(subject);

		return new ModelAndView("redirect:/subjects/subjectForm");
	}

	@RequestMapping("/edit/{subject}")
	public ModelAndView renderSubjectFormWithSubject(@PathVariable Subject subject) {
		ModelAndView model = new ModelAndView("subjectForm");

		model.addObject("subject", subject);
		model.addObject("studyPrograms", studyProgramService.findAllStudyPrograms());

		return model;
	}

	@RequestMapping("/delete/{subject}")
	public ModelAndView deleteSubjectAndRenderSubjectsPage(@PathVariable Subject subject) {
		subjectService.deleteSubject(subject);

		return new ModelAndView("redirect:/subjects");
	}

}

