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
import college.model.Subject;
import college.service.ExamService;
import college.service.ProfessorService;
import college.service.StudyProgramService;
import college.service.SubjectService;

@Controller("/exams")
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
	public ModelAndView renderExamForm(@RequestParam String studyProgramId) {
		ModelAndView model = new ModelAndView("examForm");
		
		model.addObject("exam", new Exam());
		model.addObject("studyProgram", studyProgramService.findStudyProgramById(studyProgramId));
		
		return model;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveExamAndRenderExamForm(@Valid @ModelAttribute Exam exam, BindingResult result) {
		if (!result.hasErrors()) {
			saveExamAndGetModel(exam);
		}
		
		return new ModelAndView("examForm");
	}
	
	private ModelAndView saveExamAndGetModel(Exam exam) {
		examService.saveOrUpdateExam(exam);

		return new ModelAndView(
				"redirect:/exams/examForm?studyProgramId=" + getStudyProgramByExam(exam).getId());
	}
	
	@RequestMapping("/edit/{examId}")
	public ModelAndView renderExamFormWithExam(@PathVariable Long examId) {
		return getModelAndViewWithExam(examService.findExamById(examId));
	}
	
	private ModelAndView getModelAndViewWithExam(Exam exam) {
		ModelAndView model = new ModelAndView("examForm");

		model.addObject("exam", exam);
		model.addObject("studyProgram", getStudyProgramByExam(exam));

		return model;
	}

	private StudyProgram getStudyProgramByExam(Exam exam) {	
		return getStudyProgramBySubject(exam.getSubject());
	}
	
	private StudyProgram getStudyProgramBySubject(Subject subject) {
		return subject.getStudyProgram();
	}
	
	@RequestMapping("/delete/{examId}")
	public ModelAndView deleteExamById(@PathVariable Long examId) {
		Exam exam = examService.findExamById(examId);
		ModelAndView model = new ModelAndView("examsPage");
		
		examService.deleteExamById(examId);
		model.addObject("exams", examService.findExamsByObjects(
				exam.getProfessor(), exam.getSubject(), exam.getDate()));
		
		
		return model;
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
		ModelAndView model = new ModelAndView("listExamsPage");
		
		if (!result.hasFieldErrors("professor") 
				&& !result.hasFieldErrors("subject")
				&& !result.hasFieldErrors("date")) {
			model.addObject("exams", examService.findExamsByObjects(
					exam.getProfessor(), exam.getSubject(), exam.getDate()));
			
			return model;
		}
		
		model.addObject("subjects", subjectService.findAllSubjects());
		model.addObject("professors", professorService.findAllProfessors());
		model.setViewName("searchExamsForm");
		
		return model;
	}

}
