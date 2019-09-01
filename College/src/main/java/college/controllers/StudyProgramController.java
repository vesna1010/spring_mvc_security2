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

	@RequestMapping(method = RequestMethod.GET, params = "!departmentId")
	public String renderStudyProgramsPageWithAllStudyPrograms(Model model) {
		model.addAttribute("studyPrograms", studyProgramService.findAllStudyPrograms());

		return "studyPrograms/page";
	}

	@RequestMapping(method = RequestMethod.GET, params = "departmentId")
	public String renderStudyProgramsPageWithStudyProgramsByDepartment(
			@RequestParam("departmentId") Department department, Model model) {
		model.addAttribute("studyPrograms", studyProgramService.findAllStudyProgramsByDepartment(department));

		return "studyPrograms/page";
	}

	@RequestMapping(value = "/delete", params = "studyProgramId", method = RequestMethod.GET)
	public String deleteStudyProgramAndRenderStudyProgramsPage(@RequestParam Long studyProgramId) {
		studyProgramService.deleteStudyProgramById(studyProgramId);

		return "redirect:/studyPrograms";
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public StudyProgram studyProgram() {
		return new StudyProgram();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveStudyProgramAndRenderStudyProgramForm(@Valid @ModelAttribute StudyProgram studyProgram,
			BindingResult result) {

		if (result.hasErrors()) {
			return "studyPrograms/form";
		}

		studyProgramService.saveOrUpdateStudyProgram(studyProgram);

		return "redirect:/studyPrograms/form";
	}

	@RequestMapping(value = "/edit", params = "studyProgramId", method = RequestMethod.GET)
	public String renderFormWithStudyProgram(@RequestParam("studyProgramId") StudyProgram studyProgram, Model model) {
		model.addAttribute("studyProgram", studyProgram);

		return "studyPrograms/form";
	}

	@ModelAttribute("departments")
	public List<Department> departments() {
		return departmentService.findAllDepartments();
	}

}
