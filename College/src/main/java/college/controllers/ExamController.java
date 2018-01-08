package college.controllers;

import java.util.Map;
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
import college.service.ExamService;
import college.service.ProfessorService;
import college.service.StudyProgramService;
import college.service.SubjectService;

@Controller
@RequestMapping("/exams")
public class ExamController {

	@Autowired
	private ExamService examService;
	@Autowired
	private StudyProgramService studyProgramService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private ProfessorService professorService;

	@RequestMapping("/examForm")
	public ModelAndView renderExamFormSubject(@RequestParam String studyProgramId) {
		ModelAndView model = new ModelAndView("examForm");

		model.addObject("exam", new Exam());
		model.addObject("studyProgram", studyProgramService.findStudyProgramById(studyProgramId));

		return model;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveExamAndRenderExamForm(@RequestParam String studyProgramId, @Valid @ModelAttribute Exam exam,
			BindingResult result) {
		if (!result.hasErrors()) {
			return saveExamAndGetModelAndView(exam, studyProgramId);
		}

		return new ModelAndView("examForm", "studyProgram", studyProgramService.findStudyProgramById(studyProgramId));
	}

	private ModelAndView saveExamAndGetModelAndView(Exam exam, String studyProgramId) {
		examService.saveOrUpdateExam(exam);

		return new ModelAndView("redirect:/exams/examForm?studyProgramId=" + studyProgramId);
	}

	@RequestMapping("/edit/{examId}/{studyProgramId}")
	public ModelAndView renderExamFormWithExam(@PathVariable Map<String, String> variables) {
		ModelAndView model = new ModelAndView("examForm");

		model.addObject("exam", examService.findExamById(variables.get("examId")));
		model.addObject("studyProgram", studyProgramService.findStudyProgramById(variables.get("studyProgramId")));

		return model;
	}

	@RequestMapping("/delete/{examId}")
	public ModelAndView deleteExamById(@PathVariable String examId) {
		Exam exam = examService.findExamById(examId);

		examService.deleteExamById(examId);

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

