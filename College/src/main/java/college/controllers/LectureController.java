package college.controllers;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import college.model.Lecture;
import college.model.Professor;
import college.model.ProfessorSubjectId;
import college.model.StudyProgram;
import college.model.Subject;
import college.service.LectureService;
import college.service.ProfessorService;
import college.service.SubjectService;

@Controller
@RequestMapping("/lectures")
public class LectureController {

	@Autowired
	private LectureService lectureService;
	@Autowired
	private ProfessorService professorService;
	@Autowired
	private SubjectService subjectService;

	@RequestMapping(params = "studyProgramId", method = RequestMethod.GET)
	public String renderLecturesByStudyProgram(@RequestParam("studyProgramId") StudyProgram studyProgram, Model model) {
		model.addAttribute("lectures", lectureService.findAllLecturesByStudyProgram(studyProgram));

		return "lectures/page";
	}

	@RequestMapping(value = "/delete", params = { "studyProgramId", "subjectId",
			"professorId" }, method = RequestMethod.GET)
	public String deleteLectureAndRenderLecturesPage(@RequestParam Map<String, String> params) {
		Long studyProgramId = Long.valueOf(params.get("studyProgramId"));
		Long professorId = Long.valueOf(params.get("professorId"));
		Long subjectId = Long.valueOf(params.get("subjectId"));
		ProfessorSubjectId lectureId = new ProfessorSubjectId(professorId, subjectId);

		lectureService.deleteLectureById(lectureId);

		return "redirect:/lectures?studyProgramId=" + studyProgramId;
	}

	@RequestMapping(value = "/form", params = "studyProgramId", method = RequestMethod.GET)
	public Lecture lecture() {
		return new Lecture();
	}

	@RequestMapping(value = "/save", params = "studyProgramId", method = RequestMethod.POST)
	public String saveLectureAndRenderLectureForm(@Valid @ModelAttribute Lecture lecture, BindingResult result,
			@RequestParam Long studyProgramId) {
		if (result.hasErrors()) {
			return "lectures/form";
		}

		lectureService.saveOrUpdateLecture(lecture);

		return "redirect:/lectures/form?studyProgramId=" + studyProgramId;
	}

	@ModelAttribute("professors")
	public List<Professor> professors() {
		return professorService.findAllProfessors();
	}

	@ModelAttribute("subjects")
	public List<Subject> subjects(@RequestParam("studyProgramId") StudyProgram studyProgram) {
		return subjectService.findAllSubjectsByStudyProgram(studyProgram);
	}

}
