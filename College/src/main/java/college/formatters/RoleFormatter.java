package college.formatters;

import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import college.model.Role;
import college.service.UserService;

public class RoleFormatter implements Formatter<Role> {

	@Autowired
	private UserService userService;

	@Override
	public String print(Role role, Locale locale) {
		if (role == null) {
			return null;
		}
		return role.getRole();
	}

	@Override
	public Role parse(String id, Locale locale) throws ParseException {
		if (id == null) {
			return null;
		}
		return userService.findRole(id);
	}
}
