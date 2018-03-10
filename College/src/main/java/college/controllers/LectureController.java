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
import college.model.StudyProgram;
import college.service.LectureService;
import college.service.ProfessorService;

@Controller
@RequestMapping("/lectures")
public class LectureController {

	@Autowired
	private LectureService lectureService;
	@Autowired
	private ProfessorService professorService;

	@RequestMapping(method = RequestMethod.GET, params = "studyProgram")
	public ModelAndView renderLecturesByStudyProgram(@RequestParam StudyProgram studyProgram) {
		return new ModelAndView("lecturesPage", "studyProgram", studyProgram);
	}

	@RequestMapping("/lectureForm")
	public ModelAndView renderLectureForm(@RequestParam StudyProgram studyProgram) {
		ModelAndView model = new ModelAndView("lectureForm");

		model.addObject("lecture", new Lecture());
		model.addObject("studyProgram", studyProgram);
		model.addObject("professors", professorService.findAllProfessors());

		return model;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveLectureAndRenderForm(@RequestParam StudyProgram studyProgram,
			@Valid @ModelAttribute Lecture lecture, BindingResult result) {
		if (!result.hasErrors()) {
			return saveLectureAndGetModelAndView(lecture, studyProgram);
		}

		return new ModelAndView("lectureForm", getModelMap(studyProgram));
	}

	private ModelAndView saveLectureAndGetModelAndView(Lecture lecture, StudyProgram studyProgram) {
		lectureService.saveOrUpdateLecture(lecture);

		return new ModelAndView("redirect:/lectures/lectureForm?studyProgram=" + studyProgram.getId());
	}

	private Map<String, Object> getModelMap(StudyProgram studyProgram) {
		Map<String, Object> modelMap = new HashMap<>();

		modelMap.put("studyProgram", studyProgram);
		modelMap.put("professors", professorService.findAllProfessors());

		return modelMap;
	}

	@RequestMapping("/edit/{lecture}/{studyProgram}")
	public ModelAndView renderFormWithLecture(@PathVariable StudyProgram studyProgram, @PathVariable Lecture lecture) {
		ModelAndView model = new ModelAndView("lectureForm");

		model.addObject("lecture", lecture);
		model.addObject("studyProgram", studyProgram);
		model.addObject("professors", professorService.findAllProfessors());

		return model;
	}

	@RequestMapping("/delete/{lecture}/{studyProgram}")
	public ModelAndView deleteLectureAndRenderLecturesPage(@PathVariable StudyProgram studyProgram,
			@PathVariable Lecture lecture) {
		lectureService.deleteLecture(lecture);

		return new ModelAndView("redirect:/lectures?studyProgram=" + studyProgram.getId());
	}

}

