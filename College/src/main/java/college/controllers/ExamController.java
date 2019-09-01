package college.controllers;

import java.util.List;
import java.util.Map;
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
import college.model.StudentSubjectId;
import college.model.StudyProgram;
import college.model.Subject;
import college.service.ExamService;
import college.service.ProfessorService;
import college.service.StudentService;
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
	@Autowired
	private StudentService studentService;

	@RequestMapping(params = "studyProgramId", method = RequestMethod.GET)
	public String renderPageWithExamsByStudyProgram(@RequestParam("studyProgramId") StudyProgram studyProgram,
			Model model) {
		model.addAttribute("exams", examService.findAllExamsByStudyProgram(studyProgram));

		return "exams/page";
	}

	@RequestMapping(value = "/delete", params = { "studentId", "subjectId",
			"studyProgramId" }, method = RequestMethod.GET)
	public String deleteExamAndRenderExamsPage(@RequestParam Map<String, String> params) {
		Long studyProgramId = Long.valueOf(params.get("studyProgramId"));
		Long studentId = Long.valueOf(params.get("studentId"));
		Long subjectId = Long.valueOf(params.get("subjectId"));
		StudentSubjectId examId = new StudentSubjectId(studentId, subjectId);

		examService.deleteExamById(examId);

		return "redirect:/exams?studyProgramId=" + studyProgramId;
	}

	@RequestMapping(value = "/form", params = "studyProgramId", method = RequestMethod.GET)
	public Exam exam() {
		return new Exam();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveExamAndRenderExamForm(@ModelAttribute @Valid Exam exam, BindingResult result,
			@RequestParam Long studyProgramId) {
		if (result.hasErrors()) {
			return "exams/form";
		}

		examService.saveOrUpdateExam(exam);

		return "redirect:/exams/form?studyProgramId=" + studyProgramId;
	}

	@ModelAttribute("subjects")
	public List<Subject> subjects(@RequestParam("studyProgramId") StudyProgram studyProgram) {
		return subjectService.findAllSubjectsByStudyProgram(studyProgram);
	}

	@ModelAttribute("professors")
	public List<Professor> professors(@RequestParam("studyProgramId") StudyProgram studyProgram) {
		return professorService.findAllProfessorsByStudyProgram(studyProgram);
	}

	@ModelAttribute("students")
	public List<Student> students(@RequestParam("studyProgramId") StudyProgram studyProgram) {
		return studentService.findAllStudentsByStudyProgram(studyProgram);
	}

}
