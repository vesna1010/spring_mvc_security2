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

import college.model.Department;
import college.model.StudyProgram;
import college.service.HibernateService;

@Controller
@RequestMapping("/studyPrograms")
public class StudyProgramController {
	
	@Autowired
	private HibernateService<StudyProgram> studyProgramService;
	
	@Autowired
	private HibernateService<Department> departmentService;

	//returns all study programs or by the department
	@RequestMapping(method=RequestMethod.GET)
	public String showCourses(Model model, @RequestParam(required=false) String id){
		if(id!=null){
			Department department=departmentService.findOne(id);
			if(department!=null){
				model.addAttribute("studyPrograms", department.getStudyPrograms());
				model.addAttribute("department", department);
			}else{
				model.addAttribute("studyPrograms", null);
				model.addAttribute("department",null);
			}
		}else{
			model.addAttribute("studyPrograms", studyProgramService.findAll());
			model.addAttribute("department",null);
		}
		return "studyPrograms";
	}
	
	//creates form for new study program
	@RequestMapping("/save")
	public String showForm(Model model){
		model.addAttribute("message", "Add New");
		model.addAttribute("studyProgram", new StudyProgram());
		model.addAttribute("departments", departmentService.findAll());
		return "studyProgramForm";
	}
	
	//save or update study program
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String saveCourse(@RequestParam("button") String button, @Valid @ModelAttribute("studyProgram") StudyProgram studyProgram, BindingResult result, Model model){
		if(!result.hasErrors()){
			if (button.equals("save")) {
			studyProgramService.save(studyProgram);
			return "redirect:/studyPrograms/save";
			}
		}
		if(button.equals("reset")){
			return "redirect:/studyPrograms/save";
		}
		if(studyProgramService.findOne(studyProgram.getId())==null){
			model.addAttribute("message", "Add New");
		}else{
			model.addAttribute("message", "Update");
		}
		model.addAttribute("departments", departmentService.findAll());
		return "studyProgramForm";
	}
	
	//returns study program with specified ID
	@RequestMapping("/find/{id}")
	public String showCourse(Model model, @PathVariable String id){
		model.addAttribute("message", "Update");;
		model.addAttribute("studyProgram", studyProgramService.findOne(id));
		model.addAttribute("departments", departmentService.findAll());
		return "studyProgramForm";
	}
	
	//deletes study program with specified ID
	@RequestMapping("/delete/{id}")
	public String showForm(Model model, @PathVariable String id){
			studyProgramService.delete(id);
		return "redirect:/studyPrograms/";
	}
	
	
}
