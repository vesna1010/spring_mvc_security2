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
import college.model.Department;
import college.model.StudyProgram;
import college.service.DepartmentService;
import college.service.StudyProgramService;

@Controller
@RequestMapping("/studyPrograms")
public class StudyProgramController {

	@Autowired
	private StudyProgramService studyProgramService;
	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(method = RequestMethod.GET, params = "!department")
	public ModelAndView renderStudyProgramsPageWithAllStudyPrograms(ModelAndView model) {
		model.setViewName("studyProgramsPage");
		model.addObject("title", "All Study Programs");
		model.addObject("studyPrograms", studyProgramService.findAllStudyPrograms());

		return model;
	}

	@RequestMapping(method = RequestMethod.GET, params = "department")
	public ModelAndView renderStudyProgramsPageWithStudyProgramsByDepartment(@RequestParam Department department) {
		ModelAndView model = new ModelAndView("studyProgramsPage");

		model.addObject("title", "Study Programs at " + department.getTitle());
		model.addObject("studyPrograms", department.getStudyPrograms());

		return model;
	}

	@RequestMapping(value = "/studyProgramForm", method = RequestMethod.GET)
	public ModelAndView renderEmptyStudyProgramForm(ModelAndView model) {
		model.setViewName("studyProgramForm");
		model.addObject("studyProgram", new StudyProgram());
		model.addObject("departments", departmentService.findAllDepartments());

		return model;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveStudyProgramAndRenderStudyProgramForm(@Valid @ModelAttribute StudyProgram studyProgram,
			BindingResult result) {

		if (!result.hasErrors()) {
			return saveStudyProgramAndGetModelAndView(studyProgram);
		}

		return new ModelAndView("studyProgramForm", "departments", departmentService.findAllDepartments());
	}

	private ModelAndView saveStudyProgramAndGetModelAndView(StudyProgram studyProgram) {
		studyProgramService.saveOrUpdateStudyProgram(studyProgram);

		return new ModelAndView("redirect:/studyPrograms/studyProgramForm");
	}

	@RequestMapping("/edit/{studyProgram}")
	public ModelAndView renderFormWithStudyProgram(@PathVariable StudyProgram studyProgram) {
		ModelAndView model = new ModelAndView("studyProgramForm");

		model.addObject("studyProgram", studyProgram);
		model.addObject("departments", departmentService.findAllDepartments());

		return model;
	}

	@RequestMapping("/delete/{studyProgram}")
	public ModelAndView deleteStudyProgramAndRenderStudyProgramsPage(@PathVariable StudyProgram studyProgram) {
		studyProgramService.deleteStudyProgram(studyProgram);

		return new ModelAndView("redirect:/studyPrograms");
	}

}

