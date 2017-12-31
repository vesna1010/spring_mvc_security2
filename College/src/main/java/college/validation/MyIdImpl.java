package college.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MyIdImpl implements ConstraintValidator<MyId, String> {

	private String pattern;

	@Override
	public void initialize(MyId constraintAnnotation) {
		pattern = constraintAnnotation.pattern();
	}

	@Override
	public boolean isValid(String id, ConstraintValidatorContext context) {
		return id.matches(pattern);
	}

}
