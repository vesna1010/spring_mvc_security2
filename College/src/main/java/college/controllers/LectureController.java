package college.controllers;

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
import college.model.Lecture;
import college.service.LectureService;
import college.service.ProfessorService;
import college.service.StudyProgramService;

@Controller
@RequestMapping("/lectures")
public class LectureController {

	@Autowired
	private LectureService lectureService;
	@Autowired
	private ProfessorService professorService;
	@Autowired
	private StudyProgramService studyProgramService;

	@RequestMapping(method = RequestMethod.GET, params = "studyProgramId")
	public ModelAndView renderLecturesByStudyProgram(@RequestParam String studyProgramId) {
		return new ModelAndView("lectures", "studyProgram", studyProgramService.findStudyProgramById(studyProgramId));
	}

	@RequestMapping("/lectureForm")
	public ModelAndView renderLectureForm(@RequestParam String studyProgramId) {
		ModelAndView model = new ModelAndView("lectureForm");

		model.addObject("lecture", new Lecture());
		model.addObject("studyProgram", studyProgramService.findStudyProgramById(studyProgramId));
		model.addObject("professors", professorService.findAllProfessors());

		return model;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveLectureAndRenderForm(@RequestParam String studyProgramId,
			@Valid @ModelAttribute Lecture lecture, BindingResult result) {
		if (!result.hasErrors()) {
			return saveLectureAndGetModelAndView(lecture, studyProgramId);
		}

		return new ModelAndView("lectureForm", getModelMap(studyProgramId));
	}

	private ModelAndView saveLectureAndGetModelAndView(Lecture lecture, String studyProgramId) {
		lectureService.saveOrUpdateLecture(lecture);

		return new ModelAndView("redirect:/lectures/lectureForm?studyProgramId=" + studyProgramId);
	}

	private Map<String, Object> getModelMap(String studyProgramId) {
		Map<String, Object> modelMap = new HashMap<>();

		modelMap.put("studyProgram", studyProgramService.findStudyProgramById(studyProgramId));
		modelMap.put("professors", professorService.findAllProfessors());

		return modelMap;
	}

	@RequestMapping("/edit/{lectureId}/{studyProgramId}")
	public ModelAndView renderFormWithLecture(@PathVariable Map<String, String> variables) {
		ModelAndView model = new ModelAndView("lectureForm");

		model.addObject("lecture", lectureService.findLectureById(variables.get("lectureId")));
		model.addObject("studyProgram", studyProgramService.findStudyProgramById(variables.get("studyProgramId")));
		model.addObject("professors", professorService.findAllProfessors());

		return model;
	}

	@RequestMapping("/delete/{lectureId}/{studyProgramId}")
	public ModelAndView deleteLectureAndRenderLecturesPage(@PathVariable Map<String, String> variables) {
		lectureService.deleteLectureById(variables.get("lectureId"));

		return new ModelAndView("redirect:/lectures?studyProgramId=" + variables.get("studyProgramId"));
	}

}

