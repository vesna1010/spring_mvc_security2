package college.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import college.model.Department;
import college.service.DepartmentService;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping
	public ModelAndView renderDepartmentsPageWithAllDepartments() {
		return new ModelAndView("departmentsPage", "departments", departmentService.findAllDepartments());
	}

	@RequestMapping(value = "/departmentForm", method = RequestMethod.GET)
	public ModelAndView renderEmptyDepartmentForm() {
		return new ModelAndView("departmentForm", "department", new Department());
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveDepartmentAndRenderDepartmentForm(@Valid @ModelAttribute Department department,
			BindingResult result) {

		if (!result.hasErrors()) {
			return saveDepartmentAndGetModelAndView(department);
		}

		return new ModelAndView("departmentForm");
	}

	private ModelAndView saveDepartmentAndGetModelAndView(Department department) {
		departmentService.saveOrUpdateDepartment(department);

		return new ModelAndView("redirect:/departments/departmentForm");
	}

	@RequestMapping("/edit/{department}")
	public ModelAndView renderDepartmentFormWithDepartment(@PathVariable Department department) {
		return new ModelAndView("departmentForm", "department", department);
	}

	@RequestMapping("/delete/{department}")
	public ModelAndView deleteDepartmentAndRenderDepartmentsPage(@PathVariable Department department) {
		departmentService.deleteDepartment(department);

		return new ModelAndView("redirect:/departments");
	}

}

