package college.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import college.model.Department;
import college.service.DepartmentService;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(method = RequestMethod.GET)
	public String renderDepartmentsPageWithAllDepartments(Model model) {
		model.addAttribute("departments", departmentService.findAllDepartments());

		return "departments/page";
	}

	@RequestMapping(value = "/delete", params = "departmentId", method = RequestMethod.GET)
	public String deleteDepartmentAndRenderDepartmentsPage(@RequestParam Long departmentId) {
		departmentService.deleteDepartmentById(departmentId);

		return "redirect:/departments";
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public Department department() {
		return new Department();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveDepartmentAndRenderDepartmentForm(@Valid @ModelAttribute Department department,
			BindingResult result) {
		if (result.hasErrors()) {
			return "departments/form";
		}

		departmentService.saveOrUpdateDepartment(department);

		return "redirect:/departments/form";
	}

	@RequestMapping(value = "/edit", params = "departmentId", method = RequestMethod.GET)
	public String renderDepartmentFormWithDepartment(@RequestParam("departmentId") Department department, Model model) {
		model.addAttribute("department", department);

		return "departments/form";
	}

}
