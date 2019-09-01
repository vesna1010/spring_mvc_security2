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
import college.model.StudyProgram;
import college.model.Subject;
import college.service.StudyProgramService;
import college.service.SubjectService;

@Controller
@RequestMapping("/subjects")
public class SubjectsController {

	@Autowired
	private SubjectService subjectService;
	@Autowired
	private StudyProgramService studyProgramService;

	@RequestMapping(method = RequestMethod.GET, params = "!studyProgramId")
	public String renderSubjectsPageWithAllSubjects(Model model) {
		model.addAttribute("subjects", subjectService.findAllSubjects());

		return "subjects/page";
	}

	@RequestMapping(method = RequestMethod.GET, params = "studyProgramId")
	public String renderSubjectsPageWithSubjectsByStudyProgram(
			@RequestParam("studyProgramId") StudyProgram studyProgram, Model model) {
		model.addAttribute("subjects", subjectService.findAllSubjectsByStudyProgram(studyProgram));

		return "subjects/page";
	}

	@RequestMapping(value = "/delete", params = "subjectId", method = RequestMethod.GET)
	public String deleteSubjectAndRenderSubjectsPage(@RequestParam Long subjectId) {
		subjectService.deleteSubjectById(subjectId);

		return "redirect:/subjects";
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public Subject subject() {
		return new Subject();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveSubjectAndRenderSubjectForm(@Valid @ModelAttribute Subject subject, BindingResult result) {
		if (result.hasErrors()) {
			return "subjects/form";
		}

		subjectService.saveOrUpdateSubject(subject);

		return "redirect:/subjects/form";
	}

	@RequestMapping(value = "/edit", params = "subjectId", method = RequestMethod.GET)
	public String renderSubjectFormWithSubject(@RequestParam("subjectId") Subject subject, Model model) {
		model.addAttribute("subject", subject);

		return "subjects/form";
	}

	@ModelAttribute("studyPrograms")
	public List<StudyProgram> studyPrograms() {
		return studyProgramService.findAllStudyPrograms();
	}
}
