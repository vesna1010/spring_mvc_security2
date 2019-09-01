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
import college.model.Student;
import college.model.StudyProgram;
import college.service.StudentService;
import college.service.StudyProgramService;

@Controller
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService studentService;
	@Autowired
	private StudyProgramService studyProgramService;

	@RequestMapping(method = RequestMethod.GET, params = "!studyProgramId")
	public String renderStudentsPageWithAllStudents(Model model) {
		model.addAttribute("students", studentService.findAllStudents());

		return "students/page";
	}

	@RequestMapping(method = RequestMethod.GET, params = "studyProgramId")
	public String renderStudentsPageWithStudentsByStudyProgram(
			@RequestParam("studyProgramId") StudyProgram studyProgram, Model model) {
		model.addAttribute("students", studentService.findAllStudentsByStudyProgram(studyProgram));

		return "students/page";
	}

	@RequestMapping(value = "/delete", params = "studentId", method = RequestMethod.GET)
	public String deleteStudentAndRenderStudentsPage(@RequestParam Long studentId) {
		studentService.deleteStudentById(studentId);

		return "redirect:/students";
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public Student student() {
		return new Student();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveStudentAndRenderStudentForm(@Valid @ModelAttribute Student student, BindingResult result) {
		if (result.hasErrors()) {
			return "students/form";
		}

		studentService.saveOrUpdateStudent(student);

		return "redirect:/students/form";
	}

	@RequestMapping(value = "/edit", params = "studentId", method = RequestMethod.GET)
	public String renderStudentFormWithStudent(@RequestParam("studentId") Student student, Model model) {
		model.addAttribute("student", student);

		return "students/form";
	}

	@ModelAttribute("studyPrograms")
	public List<StudyProgram> studyPrograms() {
		return studyProgramService.findAllStudyPrograms();
	}

}
