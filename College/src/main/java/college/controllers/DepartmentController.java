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
import college.service.HibernateService;

@Controller
@RequestMapping("/departments/")
public class DepartmentController {

	@Autowired
	private HibernateService<Department> departmentService;

	//returns departments
	@RequestMapping(method = RequestMethod.GET)
	public String showAllDepartements(Model model) {
		model.addAttribute("departments", departmentService.findAll());
		return "departments";
	}

	//creates form for new department
	@RequestMapping("/save")
	public String showDepartmentForm(Model model) {
		model.addAttribute("message", "Add New");
		model.addAttribute("department", new Department());
		return "departmentForm";
	}

	//save or update department
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveDepartment(@RequestParam("button") String button, Model model,
			@Valid @ModelAttribute("department") Department department, BindingResult result) {
		if (!result.hasErrors()) {
			if (button.equals("save")) {
				departmentService.save(department);
				return "redirect:/departments/save";
			}
		}
		if(button.equals("reset")){
			return "redirect:/departments/save";
		}
		if(departmentService.findOne(department.getId())==null){
			model.addAttribute("message", "Add New");
		}else{
			model.addAttribute("message", "Update");
		}
		return "departmentForm";
	}

	//returns department with specified ID
	@RequestMapping("/find/{id}")
	public String showDep(Model model, @PathVariable String id) {
		model.addAttribute("message", "Update");
		model.addAttribute("department", departmentService.findOne(id));
		return "departmentForm";
	}

	//deletes department with specified ID
	@RequestMapping("/delete/{id}")
	public String deleteDep(Model model, @PathVariable String id) {
		departmentService.delete(id);
		return "redirect:/departments/";
	}

}
