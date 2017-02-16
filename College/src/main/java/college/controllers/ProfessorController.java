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

import college.model.Professor;
import college.model.StudyProgram;
import college.model.Subject;
import college.service.HibernateService;

@Controller
@RequestMapping("/professors/")
public class ProfessorController {
	
	@Autowired
	private HibernateService<Professor> professorService;
	
	@Autowired
	private HibernateService<Subject> subjectService;
	
	@Autowired
	private HibernateService<StudyProgram> studyProgramService;
	
	//returns all professors or by the study program
	@RequestMapping( method=RequestMethod.GET)
	public String showProfessors(Model model, @RequestParam(required=false) String id){
		if(id!=null){
			StudyProgram studyProgram=studyProgramService.findOne(id);
			if(studyProgram!=null){
				model.addAttribute("professors", studyProgram.getProfessors());
				model.addAttribute("studyProgram", studyProgram);
			}else{
				model.addAttribute("professors", null);
				model.addAttribute("studyProgram", null);
			}
		}else{
			model.addAttribute("professors", professorService.findAll());
			model.addAttribute("studyProgram", null);
		}
		return "professors";
	}
	
	//creates form for new professor
	@RequestMapping("/save")
	public String showForm(Model model){
		model.addAttribute("message", "Add New");
		model.addAttribute("subjects", subjectService.findAll());
		model.addAttribute("professor", new Professor());
		return "professorForm";
	}
	
	//save or update professor
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String saveProfessor(@RequestParam("button") String button, Model model,@Valid @ModelAttribute("professor") Professor professor, BindingResult result){
		if(!result.hasErrors()){
			if (button.equals("save")) {
				professorService.save(professor);
				return "redirect:/professors/save";
			}
		}
		if(button.equals("reset")){
			return "redirect:/professors/save";
		}
		if(professorService.findOne(professor.getId())==null){
			model.addAttribute("message", "Add New");
		}else{
			model.addAttribute("message", "Update");
		}
		model.addAttribute("subjects", subjectService.findAll());
		return "professorForm";
	}
	
	//returns professor with specified ID
	@RequestMapping("/find/{id}")
	public String showProf(Model model, @PathVariable String id ){
		model.addAttribute("message", "Update");
		model.addAttribute("subjects", subjectService.findAll());
		model.addAttribute("professor", professorService.findOne(id));
		return "professorForm";
	}

	//deletes professor with specified ID
	@RequestMapping("/delete/{id}")
	public String deleteProf(Model model, @PathVariable String id ){
		professorService.delete(id);
		return "redirect:/professors/";
	}
	
	
	
}
