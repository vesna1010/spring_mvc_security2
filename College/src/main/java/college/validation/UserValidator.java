package college.validation;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import college.enums.Role;
import college.model.User;
import college.service.UserService;

public class UserValidator implements Validator {

	private static final String NAME_PATTERN = "^[a-zA-Z]{3,}\\s[a-zA-Z]{3,}(\\s[a-zA-Z]{3,})?$";
	private static final String PASSWORD_PATTERN = "^\\S{8,15}$";
	private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> cls) {
		return User.class.equals(cls);
	}

	@Override
	public void validate(Object object, Errors errors) {
		User user = (User) object;

		if (isInvalidName(user.getName())) {
			errors.rejectValue("name", "user.name");
		}

		if (isExistEmail(user.getEmail())) {
			errors.rejectValue("email", "user.email.exists");
		}

		if (isInvalidEmail(user.getEmail())) {
			errors.rejectValue("email", "user.email.invalid");
		}

		if (isInvalidPassword(user.getPassword())) {
			errors.rejectValue("password", "user.password");
		} else {
			if (isNotEqualPasswords(user.getPassword(), user.getConfirmPassword())) {
				errors.rejectValue("confirmPassword", "user.confirmPassword");
			}
		}

		if (isInvalidRoles(user.getRoles())) {
			errors.rejectValue("roles", "user.roles");
		}

	}

	private boolean isInvalidName(String name) {
		return (name == null || !name.matches(NAME_PATTERN));
	}

	private boolean isExistEmail(String email) {
		return (userService.findUserByEmail(email) != null);
	}

	private boolean isInvalidEmail(String email) {
		return (email == null || !email.matches(EMAIL_PATTERN));
	}

	private boolean isInvalidPassword(String password) {
		return (password == null || !password.matches(PASSWORD_PATTERN));
	}

	private boolean isNotEqualPasswords(String password, String confirmPassword) {
		return !password.equals(confirmPassword);
	}

	private boolean isInvalidRoles(Set<Role> roles) {
		return (roles == null || roles.size() == 0);
	}

}
