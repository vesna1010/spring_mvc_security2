package college.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import college.model.Department;
import college.service.DepartmentService;

public class DepartmentConverter implements Converter<String, Department> {

	@Autowired
	private DepartmentService departmentService;

	@Override
	public Department convert(String id) {
		return ((id == null || id.isEmpty()) ? null : departmentService.findDepartmentById(Long.valueOf(id)));
	}

}

