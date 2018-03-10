package college.controllers;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import college.model.User;
import college.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	@Qualifier("userValidator")
	private Validator validator;
	@Autowired
	private UserService userService;

	@RequestMapping
	public ModelAndView renderUsersPageWithAllUsers() {
		return new ModelAndView("usersPage", "users", userService.findUsers());
	}

	@RequestMapping("/userForm")
	public ModelAndView renderEmptyUserForm(ModelAndView model) {
		return new ModelAndView("userForm", "user", new User());
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveUserAndRenderUserForm(@ModelAttribute User user, BindingResult result) {
		validator.validate(user, result);

		if (!result.hasErrors()) {
			return saveUserAndGetModelAndView(user);
		}

		return new ModelAndView("userForm");
	}

	private ModelAndView saveUserAndGetModelAndView(User user) {
		userService.saveOrUpdateUser(user);

		return new ModelAndView("redirect:/users/userForm");
	}

	@RequestMapping("/delete/{user}")
	public ModelAndView deleteUserAndRenderUsersPage(@PathVariable User user) {
		userService.deleteUser(user);

		return new ModelAndView("redirect:/users");
	}

	@RequestMapping("/disable/{username}")
	public ModelAndView disableUserAndRenderUsersPage(@PathVariable String username) {
		userService.disableUserByUsername(username);

		return new ModelAndView("redirect:/users");
	}

	@RequestMapping("/changePassword")
	public ModelAndView renderUserFormWithUser(Principal principal) {
		return new ModelAndView("userForm", "user", userService.findUserByUsernameWithoutPassword(principal.getName()));
	}

}

