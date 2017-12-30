package college.controllers;

import java.util.Arrays;
import java.util.HashMap;
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
import college.enums.Gender;
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
	public ModelAndView renderStudentsPageWithAllStudents(ModelAndView model) {
		model.setViewName("studentsPage");
		model.addObject("title", "All Students");
		model.addObject("students", studentService.findAllStudents());
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.GET, params = "studyProgramId")
	public ModelAndView renderStudentsPageWithStudentsByStudyProgram(@RequestParam String studyProgramId) {
		return getModelAndViewByStudyProgram(studyProgramService.findStudyProgramById(studyProgramId));
	}

	private ModelAndView getModelAndViewByStudyProgram(StudyProgram studyProgram) {
		ModelAndView model = new ModelAndView("studentsPage");

		model.addObject("title", "Students at " + studyProgram.getTitle());
		model.addObject("students", studyProgram.getStudents());

		return model;

	}
	@RequestMapping("/studentForm")
	public ModelAndView renderEmptyStudentForm(ModelAndView model) {
		model.setViewName("studentForm");
		model.addObject("student", new Student());
		model.addObject("genders", Arrays.asList(Gender.values()));
		model.addObject("studyPrograms", studyProgramService.findAllStudyPrograms());
		
		return model;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "multipart/form-data")
	public ModelAndView saveStudentAndRenderStudentForm(@Valid @ModelAttribute Student student, 
			BindingResult result) {
		
		if (!result.hasErrors()) {
			return saveStudentAndGetModelAndView(student);
		}
	
		return new ModelAndView("studentForm", getModelMap());
	}
	
	private ModelAndView saveStudentAndGetModelAndView(Student student) {
		studentService.saveOrUpdateStudent(student);
		
		return new ModelAndView("redirect:/students/studentForm");
	}
	
	private Map<String, Object> getModelMap() {
		Map<String, Object> modelMap = new HashMap<>();

		modelMap.put("genders", Arrays.asList(Gender.values()));
		modelMap.put("studyPrograms", studyProgramService.findAllStudyPrograms());

		return modelMap;
	}
	
	@RequestMapping("/edit/{studentId}")
	public ModelAndView renderStudentFormWithStudent(@PathVariable String studentId) {
		ModelAndView model = new ModelAndView("studentForm");
		
		model.addObject("student", studentService.findStudentById(studentId));
		model.addObject("genders", Arrays.asList(Gender.values()));
		model.addObject("studyPrograms", studyProgramService.findAllStudyPrograms());
		
		return model;
	}

	@RequestMapping("/delete/{studentId}")
	public ModelAndView deleteStudentAndRenderStudentsPage(@PathVariable String studentId) {
		studentService.deleteStudentById(studentId);
		
		return new ModelAndView("redirect:/students");
	}

	@RequestMapping("/exams/{studentId}")
	public ModelAndView renderStudentExamsPageWithStudent(@PathVariable String studentId) {	
		return new ModelAndView("studentExamsPage", "student", studentService.findStudentById(studentId));
	}

}
