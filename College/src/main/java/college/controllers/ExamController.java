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
import college.model.Exam;
import college.model.StudyProgram;
import college.service.ExamService;
import college.service.ProfessorService;
import college.service.SubjectService;

@Controller
@RequestMapping("/exams")
public class ExamController {

	@Autowired
	private ExamService examService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private ProfessorService professorService;

	@RequestMapping("/examForm")
	public ModelAndView renderExamFormSubject(@RequestParam StudyProgram studyProgram) {
		ModelAndView model = new ModelAndView("examForm");

		model.addObject("exam", new Exam());
		model.addObject("studyProgram", studyProgram);

		return model;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveExamAndRenderExamForm(@RequestParam StudyProgram studyProgram,
			@Valid @ModelAttribute Exam exam, BindingResult result) {
		if (!result.hasErrors()) {
			return saveExamAndGetModelAndView(exam, studyProgram);
		}

		return new ModelAndView("examForm", "studyProgram", studyProgram);
	}

	private ModelAndView saveExamAndGetModelAndView(Exam exam, StudyProgram studyProgram) {
		examService.saveOrUpdateExam(exam);

		return new ModelAndView("redirect:/exams/examForm?studyProgram=" + studyProgram.getId());
	}

	@RequestMapping("/edit/{exam}/{studyProgram}")
	public ModelAndView renderExamFormWithExam(@PathVariable StudyProgram studyProgram, @PathVariable Exam exam) {
		ModelAndView model = new ModelAndView("examForm");

		model.addObject("exam", exam);
		model.addObject("studyProgram", studyProgram);

		return model;
	}

	@RequestMapping("/delete/{exam}")
	public ModelAndView deleteExamById(@PathVariable Exam exam) {
		examService.deleteExam(exam);

		return new ModelAndView("examsPage", "exams",
				examService.findExamsByObjects(exam.getProfessor(), exam.getSubject(), exam.getDate()));
	}

	@RequestMapping("/search")
	public ModelAndView renderSearchExamForm(ModelAndView model) {
		model.setViewName("searchExamsForm");
		model.addObject("exam", new Exam());
		model.addObject("subjects", subjectService.findAllSubjects());
		model.addObject("professors", professorService.findAllProfessors());

		return model;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView renderSearchedExams(@Valid @ModelAttribute Exam exam, BindingResult result) {
		ModelAndView model = new ModelAndView("searchExamsForm");

		if (!result.hasFieldErrors("professor") && !result.hasFieldErrors("subject")
				&& !result.hasFieldErrors("date")) {
			return new ModelAndView("examsPage", "exams",
					examService.findExamsByObjects(exam.getProfessor(), exam.getSubject(), exam.getDate()));
		}

		model.addObject("subjects", subjectService.findAllSubjects());
		model.addObject("professors", professorService.findAllProfessors());

		return model;
	}

}

