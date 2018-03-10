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

	@RequestMapping(method = RequestMethod.GET, params = "!studyProgram")
	public ModelAndView renderStudentsPageWithAllStudents(ModelAndView model) {
		model.setViewName("studentsPage");
		model.addObject("title", "All Students");
		model.addObject("students", studentService.findAllStudents());

		return model;
	}

	@RequestMapping(method = RequestMethod.GET, params = "studyProgram")
	public ModelAndView renderStudentsPageWithStudentsByStudyProgram(@RequestParam StudyProgram studyProgram) {
		ModelAndView model = new ModelAndView("studentsPage");

		model.addObject("title", "Students at " + studyProgram.getTitle());
		model.addObject("students", studyProgram.getStudents());

		return model;
	}

	@RequestMapping("/studentForm")
	public ModelAndView renderEmptyStudentForm(ModelAndView model) {
		model.setViewName("studentForm");
		model.addObject("student", new Student());
		model.addObject("studyPrograms", studyProgramService.findAllStudyPrograms());

		return model;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "multipart/form-data")
	public ModelAndView saveStudentAndRenderStudentForm(@Valid @ModelAttribute Student student, BindingResult result) {

		if (!result.hasErrors()) {
			return saveStudentAndGetModelAndView(student);
		}

		return new ModelAndView("studentForm", "studyPrograms", studyProgramService.findAllStudyPrograms());
	}

	private ModelAndView saveStudentAndGetModelAndView(Student student) {
		studentService.saveOrUpdateStudent(student);

		return new ModelAndView("redirect:/students/studentForm");
	}

	@RequestMapping("/edit/{student}")
	public ModelAndView renderStudentFormWithStudent(@PathVariable Student student) {
		ModelAndView model = new ModelAndView("studentForm");

		model.addObject("student", student);
		model.addObject("studyPrograms", studyProgramService.findAllStudyPrograms());

		return model;
	}

	@RequestMapping("/delete/{student}")
	public ModelAndView deleteStudentAndRenderStudentsPage(@PathVariable Student student) {
		studentService.deleteStudent(student);

		return new ModelAndView("redirect:/students");
	}

	@RequestMapping("/exams/{student}")
	public ModelAndView renderStudentExamsPageWithStudent(@PathVariable Student student) {
		return new ModelAndView("studentExamsPage", "student", student);
	}

}


