package college.validation;

import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import college.enums.Role;
import college.model.User;

@Component
public class UserValidator implements Validator {

	private String userPattern = "^([a-zA-Z0-9]+\\s?){8,15}$";
	private String passwordPattern = "^\\S{8,15}$";
	private String emailPattern = "^[a-zA-Z0-9_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

	@Override
	public boolean supports(Class<?> cls) {
		return User.class.equals(cls);
	}

	@Override
	public void validate(Object object, Errors errors) {
		User user = (User) object;

		if (!isValidUsername(user.getUsername())) {
			errors.rejectValue("username", "user.username");
		}

		if (!isValidPassword(user.getPassword())) {
			errors.rejectValue("password", "user.password");
		}

		if (!isValidEmail(user.getEmail())) {
			errors.rejectValue("email", "user.email");
		}

		if (isValidPassword(user.getPassword()) && !isEqualsPasswords(user.getPassword(), user.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "user.passwordConfirmDifferent");
		}

		if (isEmptyRoles(user.getRoles())) {
			errors.rejectValue("roles", "user.roles");
		}

	}

	private boolean isValidUsername(String username) {
		return username.matches(userPattern);
	}

	private boolean isValidPassword(String password) {
		return password.matches(passwordPattern);
	}

	private boolean isValidEmail(String email) {
		return email.matches(emailPattern);
	}

	private boolean isEqualsPasswords(String password, String confirmPassword) {
		return password.equals(confirmPassword);
	}

	private boolean isEmptyRoles(Set<Role> roles) {
		return (roles == null || roles.size() == 0);
	}

}
