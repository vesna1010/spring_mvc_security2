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
import college.model.Professor;
import college.model.StudyProgram;
import college.service.ProfessorService;

@Controller
@RequestMapping("/professors")
public class ProfessorController {

	@Autowired
	private ProfessorService professorService;

	@RequestMapping(method = RequestMethod.GET, params = "!studyProgram")
	private ModelAndView renderProfessorsPageWithAllProfessors(ModelAndView model) {
		model.setViewName("professorsPage");
		model.addObject("title", "All Professors");
		model.addObject("professors", professorService.findAllProfessors());

		return model;
	}

	@RequestMapping(method = RequestMethod.GET, params = "studyProgram")
	public ModelAndView renderProfessorsPageWithProfessorsByStudyProgram(@RequestParam StudyProgram studyProgram) {
		ModelAndView model = new ModelAndView("professorsPage");

		model.addObject("title", "Professors at " + studyProgram.getTitle());
		model.addObject("professors", studyProgram.getProfessors());

		return model;
	}

	@RequestMapping(value = "/professorForm", method = RequestMethod.GET)
	public ModelAndView renderEmptyProfessorForm() {
		return new ModelAndView("professorForm", "professor", new Professor());
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveProfessorAndRenderProfessorForm(@Valid @ModelAttribute Professor professor,
			BindingResult result) {

		if (!result.hasErrors()) {
			return saveProfessorAndGetModelAndView(professor);
		}

		return new ModelAndView("professorForm");
	}

	private ModelAndView saveProfessorAndGetModelAndView(Professor professor) {
		professorService.saveOrUpdateProfessor(professor);

		return new ModelAndView("redirect:/professors/professorForm");
	}

	@RequestMapping("/edit/{professor}")
	public ModelAndView renderFormWithProfessor(@PathVariable Professor professor) {
		return new ModelAndView("professorForm", "professor", professor);
	}

	@RequestMapping("/delete/{professor}")
	public ModelAndView deleteProfessorAndRenderProfessorsPage(@PathVariable Professor professor) {
		professorService.deleteProfessor(professor);

		return new ModelAndView("redirect:/professors");
	}

}

