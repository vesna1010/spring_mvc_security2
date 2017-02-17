package college.controllers;

import java.io.UnsupportedEncodingException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import college.model.Student;
import college.model.StudyProgram;
import college.service.HibernateService;

@Controller
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private HibernateService<Student> studentService;

	@Autowired
	private HibernateService<StudyProgram> studyProgramService;

	//returns all students or by the study program
	@RequestMapping(method = RequestMethod.GET)
	public String showStudents(Model model, @RequestParam(required = false) String id) {
		if (id != null) {
			StudyProgram studyProgram = studyProgramService.findOne(id);
			if (studyProgram != null) {
				model.addAttribute("students", studyProgram.getStudents());
				model.addAttribute("studyProgram", studyProgram);
			} else {
				model.addAttribute("students", null);
				model.addAttribute("studyProgram", null);
			}
		} else {
			model.addAttribute("students", studentService.findAll());
			model.addAttribute("studyProgram", null);
		}
		return "students";
	}

	//creates form for new student
	@RequestMapping("/save")
	public String showForm(Model model) {
		model.addAttribute("message", "Add New");
		model.addAttribute("studyPrograms", studyProgramService.findAll());
		model.addAttribute("student", new Student());
		return "studentForm";
	}

	//save or update student
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "multipart/form-data")
	public String saveStudent(@RequestParam("button") String button, @Valid @ModelAttribute("student") Student student, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			if (button.equals("save")) {
				studentService.save(student);
				return "redirect:/students/save";
			}
		}
		if (button.equals("reset")) {
			return "redirect:/students/save";
		}
		if (studentService.findOne(student.getId()) == null) {
			model.addAttribute("message", "Add New");
		} else {
			model.addAttribute("message", "Update");
		}
		model.addAttribute("studyPrograms", studyProgramService.findAll());
		return "studentForm";
	}
	
	//returns student with specified ID
	@RequestMapping("/find/{id}")
	public String showStudent(Model model, @PathVariable String id) throws UnsupportedEncodingException {
		model.addAttribute("message", "Update");
		model.addAttribute("studyPrograms", studyProgramService.findAll());
		model.addAttribute("student", studentService.findOne(id));
		return "studentForm";
	}

	//returns all exams by student
	@RequestMapping("/delete/{id}")
	public String deleteStudent(Model model, @PathVariable String id) {
		studentService.delete(id);
		return "redirect:/students/";
	}

	//returns exams with specified student
	@RequestMapping("/exams/{id}")
	public String studentExams(Model model, @PathVariable String id) {
		model.addAttribute("student", studentService.findOne(id));
		return "studentExams";
	}

}
