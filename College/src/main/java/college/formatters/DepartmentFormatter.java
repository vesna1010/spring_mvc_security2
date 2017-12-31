package college.formatters;

import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import college.model.Department;
import college.service.DepartmentService;

@Component
public class DepartmentFormatter implements Formatter<Department> {

	@Autowired
	private DepartmentService departmentService;

	@Override
	public String print(Department department, Locale locale) {
		return department.getTitle();
	}

	@Override
	public Department parse(String departmentId, Locale locale) throws ParseException {
		return departmentService.findDepartmentById(departmentId);
	}

}

