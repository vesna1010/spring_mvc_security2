package college.controllers;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import college.model.User;
import college.service.UserService;
import college.validation.UserValidator;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserValidator userValidator;
	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String renderUsersPageWithAllUsers(Model model) {
		model.addAttribute("users", userService.findAllUsers());

		return "users/page";
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public User user() {
		return new User();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveUserAndRenderUserForm(@ModelAttribute User user, BindingResult result) {
		userValidator.validate(user, result);

		if (result.hasErrors()) {
			return "users/form";
		}

		userService.saveOrUpdateUser(user);

		return "redirect:/users/form";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String renderUserFormWithUser(Principal principal, Model model) {
		model.addAttribute("user", userService.findUserByEmail(principal.getName()));

		return "users/update/form";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateUserPasswordAndRenderPasswordForm(@ModelAttribute User user, BindingResult result) {
		userValidator.validate(user, result);

		if (!result.hasFieldErrors("password") && !result.hasFieldErrors("confirmPassword")) {
			userService.saveOrUpdateUser(user);
		}

		return "users/update/form";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteUserAndRenderUsersPage(@RequestParam Long userId) {
		userService.deleteUserById(userId);

		return "redirect:/users";
	}

	@RequestMapping(value = "/disable", method = RequestMethod.GET)
	public String disableUserAndRenderUsersPage(@RequestParam Long userId) {
		userService.disableUserById(userId);

		return "redirect:/users";
	}

}
