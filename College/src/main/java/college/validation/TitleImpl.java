package college.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TitleImpl implements ConstraintValidator<Title, String> {

	private String pattern;

	@Override
	public void initialize(Title constraintAnnotation) {
		pattern = constraintAnnotation.pattern();
	}

	@Override
	public boolean isValid(String title, ConstraintValidatorContext context) {
		return title.matches(pattern);
	}

}
