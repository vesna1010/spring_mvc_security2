package college.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import college.model.Professor;
import college.model.StudyProgram;
import college.service.ProfessorService;

@Controller
@RequestMapping("/professors")
public class ProfessorController {

	@Autowired
	private ProfessorService professorService;

	@RequestMapping(method = RequestMethod.GET, params = "!studyProgramId")
	private String renderProfessorsPageWithAllProfessors(Model model) {
		model.addAttribute("professors", professorService.findAllProfessors());

		return "professors/page";
	}

	@RequestMapping(method = RequestMethod.GET, params = "studyProgramId")
	public String renderProfessorsPageWithProfessorsByStudyProgram(
			@RequestParam("studyProgramId") StudyProgram studyProgram, Model model) {
		model.addAttribute("professors", professorService.findAllProfessorsByStudyProgram(studyProgram));

		return "professors/page";
	}

	@RequestMapping(value = "/delete", params = "professorId", method = RequestMethod.GET)
	public String deleteProfessorAndRenderProfessorsPage(@RequestParam Long professorId) {
		professorService.deleteProfessorById(professorId);

		return "redirect:/professors";
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public Professor professor() {
		return new Professor();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProfessorAndRenderProfessorForm(@Valid @ModelAttribute Professor professor,
			BindingResult result) {
		if (result.hasErrors()) {
			return "professors/form";
		}

		professorService.saveOrUpdateProfessor(professor);

		return "redirect:/professors/form";
	}

	@RequestMapping(value = "/edit", params = "professorId", method = RequestMethod.GET)
	public String renderFormWithProfessor(@RequestParam("professorId") Professor professor, Model model) {
		model.addAttribute("professor", professor);

		return "professors/form";
	}

}
