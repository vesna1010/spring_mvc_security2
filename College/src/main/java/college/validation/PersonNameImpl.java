package college.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PersonNameImpl implements ConstraintValidator<PersonName, String> {

	private String pattern;

	@Override
	public void initialize(PersonName constraintAnnotation) {
		pattern = constraintAnnotation.pattern();
	}

	@Override
	public boolean isValid(String name, ConstraintValidatorContext context) {
		return name.matches(pattern);
	}

}
