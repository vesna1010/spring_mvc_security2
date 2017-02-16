package college.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import college.model.StudyProgram;
import college.model.Subject;
import college.service.HibernateService;

@Controller
@RequestMapping("/subjects/")
public class SubjectsController {

	@Autowired
	private HibernateService<Subject> subjectService;

	@Autowired
	private HibernateService<StudyProgram> studyProgramService;

	//returns all subjects or by the study program
	@RequestMapping(method = RequestMethod.GET)
	public String showSubjects(Model model, @RequestParam(required = false) String id) {
		if (id != null) {
			StudyProgram studyProgram = studyProgramService.findOne(id);
			if (studyProgram != null) {
				model.addAttribute("subjects", studyProgram.getSubjects());
				model.addAttribute("studyProgram", studyProgram);
			} else {
				model.addAttribute("subjects", null);
				model.addAttribute("studyProgram", null);
			}
		} else {
			model.addAttribute("subjects", subjectService.findAll());
			model.addAttribute("studyProgram", null);
		}
		
		return "subjects";
	}

	//creates form for new subject
	@RequestMapping("/save")
	public String subjectForm(Model model) {
		model.addAttribute("message", "Add New");
		model.addAttribute("studyPrograms", studyProgramService.findAll());
		model.addAttribute("subject", new Subject());
		return "subjectForm";
	}

	//save or update subject
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveSubject(@RequestParam("button") String button, Model model, @Valid @ModelAttribute("subject") Subject subject, BindingResult result) {
		if (!result.hasErrors()) {
			if (button.equals("save")) {
			subjectService.save(subject);
			return "redirect:/subjects/save";
		}
		}
		if(button.equals("reset")){
			return "redirect:/subjects/save";
		}
		if(subjectService.findOne(subject.getId())==null){
			model.addAttribute("message", "Add New");
		}else{
			model.addAttribute("message", "Update");
		}
		model.addAttribute("studyPrograms", studyProgramService.findAll());
		return "subjectForm";
	}
	
	//returns subject with specified ID
	@RequestMapping("/find/{id}")
	public String showSubject(Model model, @PathVariable String id) {
		model.addAttribute("message", "Update");
		model.addAttribute("studyPrograms", studyProgramService.findAll());
		model.addAttribute("subject", subjectService.findOne(id));
		return "subjectForm";
	}

	//deletes subject with specified ID
	@RequestMapping("/delete/{id}")
	public String deleteSubject(Model model, @PathVariable String id) {
		subjectService.delete(id);
		return "redirect:/subjects/";
	}

}
