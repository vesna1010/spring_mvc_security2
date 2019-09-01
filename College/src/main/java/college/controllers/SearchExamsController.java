package college.controllers;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import college.model.Exam;
import college.model.Professor;
import college.model.Student;
import college.model.Subject;
import college.service.ExamService;
import college.service.ProfessorService;
import college.service.SubjectService;

@Controller
@RequestMapping("/exams")
public class SearchExamsController {

	@Autowired
	private ExamService examService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private ProfessorService professorService;

	@RequestMapping(value = "/student", method = RequestMethod.GET)
	public String renderStudentExamsPageWithStudent(@RequestParam("studentId") Student student, Model model) {
		model.addAttribute("exams", examService.findAllExamsByStudent(student));

		return "student/exams/page";
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public Exam renderSearchExamForm() {
		return new Exam();
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String renderSearchedExams(@Valid @ModelAttribute Exam exam, BindingResult result, Model model) {
		if (result.hasFieldErrors("professor") || result.hasFieldErrors("subject") || result.hasFieldErrors("date")) {
			return "exams/search";
		}

		model.addAttribute("exams", examService.findAllExamsByProfessorAndSubjectAndDate(exam.getProfessor(),
				exam.getSubject(), exam.getDate()));

		return "searched/exams/page";
	}

	@ModelAttribute("subjects")
	public List<Subject> subjects() {
		return subjectService.findAllSubjects();
	}

	@ModelAttribute("professors")
	public List<Professor> professors() {
		return professorService.findAllProfessors();
	}

}
