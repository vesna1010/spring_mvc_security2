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
import college.model.Professor;
import college.model.StudyProgram;
import college.service.ProfessorService;
import college.service.StudyProgramService;
import college.service.SubjectService;

@Controller
@RequestMapping("/professors")
public class ProfessorController {

	@Autowired
	private ProfessorService professorService;
	@Autowired
	private StudyProgramService studyProgramService;
	@Autowired
	private SubjectService subjectService;

	@RequestMapping(method = RequestMethod.GET, params = "!studyProgramId")
	private ModelAndView renderProfessorsPageWithAllProfessors(ModelAndView model) {
		model.setViewName("professorsPage");
		model.addObject("title", "All Professors");
		model.addObject("professors", professorService.findAllProfessors());
		
		return model;
	}

	@RequestMapping(method = RequestMethod.GET, params = "studyProgramId")
	public ModelAndView renderProfessorsPageWithProfessorsByStudyProgram(
			@RequestParam String studyProgramId) {
		return setModelAndViewByStudyProgram(studyProgramService.findStudyProgramById(studyProgramId));
	}
	
	private ModelAndView setModelAndViewByStudyProgram(StudyProgram studyProgram) {
		ModelAndView model = new ModelAndView("professorsPage");

		model.addObject("title", "Professors at " + studyProgram.getTitle());
		model.addObject("professors", studyProgram.getProfessors());

		return model;
	}

	@RequestMapping(value = "/professorForm", method = RequestMethod.GET)
	public ModelAndView renderEmptyProfessorForm(ModelAndView model) {
		model.setViewName("professorForm");
		model.addObject("professor", new Professor());
		model.addObject("genders", Arrays.asList(Gender.values()));
		model.addObject("subjects", subjectService.findAllSubjects());
		
		return model;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "multipart/form-data")
	public ModelAndView saveProfessorAndRenderProfessorForm(@Valid @ModelAttribute Professor professor, 
		BindingResult result) {
	
		if(!result.hasErrors()) {
			return saveProfessorAndGetModelAndView(professor);
		}
			
		return new ModelAndView("professorForm", getModelMap());
	}
	
	private ModelAndView saveProfessorAndGetModelAndView(Professor professor) {
		professorService.saveOrUpdateProfessor(professor);

		return new ModelAndView("redirect:/professors/save");
	}
	
	private Map<String, Object> getModelMap() {
		Map<String, Object> modelMap = new HashMap<>();

		modelMap.put("genders", Arrays.asList(Gender.values()));
		modelMap.put("subjects", subjectService.findAllSubjects());

		return modelMap;
	}

	@RequestMapping("/edit/{professorId}")
	public ModelAndView renderFormWithProfessor(@PathVariable String professorId) {
		ModelAndView model = new ModelAndView("professorForm");
		
		model.addObject("professor", professorService.findProfessorById(professorId));
		model.addObject("genders", Arrays.asList(Gender.values()));
		model.addObject("subjects", subjectService.findAllSubjects());
		
		return model;
	}

	@RequestMapping("/delete/{professorId}")
	public ModelAndView deleteProfessorAndRenderProfessorsPage(@PathVariable String professorId) {
		professorService.deleteProfessorById(professorId);

		return new ModelAndView("redirect:/professors");
	}

}
