package college.formatters;

import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import college.model.Department;
import college.service.HibernateService;

public class DepartmentFormatter implements Formatter<Department> {

	@Autowired
	private HibernateService<Department> departmentService;

	@Override
	public String print(Department department, Locale locale) {
		if (department == null)
			return "";
		else
			return department.getTitle();
	}

	@Override
	public Department parse(String id, Locale locale) throws ParseException {
			return departmentService.findOne(id);
	}

}
