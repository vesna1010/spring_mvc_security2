package college.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import college.model.Lecture;
import college.service.LectureService;

@Component
public class LectureConverter implements Converter<String, Lecture> {

	@Autowired
	private LectureService service;

	@Override
	public Lecture convert(String id) {
		return (id == null ? null : service.findLectureById(Long.valueOf(id)));
	}

}

