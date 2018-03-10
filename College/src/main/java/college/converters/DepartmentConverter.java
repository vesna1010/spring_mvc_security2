package college.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import college.model.Department;
import college.service.DepartmentService;

@Component
public class DepartmentConverter implements Converter<String, Department> {

	@Autowired
	private DepartmentService service;

	@Override
	public Department convert(String id) {
		return (id == null ? null : service.findDepartmentById(id));
	}

}
