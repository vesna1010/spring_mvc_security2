package college.controllers;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
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
import college.enums.Role;
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
	private List<Role> roles = Arrays.asList(Role.values());
	
	@RequestMapping
	public ModelAndView renderUsersPageWithAllUsers() {
		return new ModelAndView("usersPage", "users", userService.findUsers());
	}

	@RequestMapping("/userForm")
	public ModelAndView renderEmptyUserForm(ModelAndView model) {
		model.setViewName("userForm");
		model.addObject("user", new User());
		model.addObject("roles", roles);
		
		return model;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveUserAndRenderUserForm(@ModelAttribute User user, BindingResult result) {
		validator.validate(user, result);
		
		if(!result.hasErrors()) {
			saveUserAndGetModelAndView(user);
		}
		
		return new ModelAndView("userForm", "roles", roles);
	}

	private ModelAndView saveUserAndGetModelAndView(User user) {
		userService.saveOrUpdateUser(user);

		return new ModelAndView("redirect:/users/userForm");
	}

	@RequestMapping("/delete/{username}")
	public ModelAndView deleteUserAndRenderUsersPage(@PathVariable String username) {
		userService.deleteUserByUsername(username);

		return new ModelAndView("redirect:/users");
	}

	@RequestMapping("/disable/{username}")
	public ModelAndView disableUserAndRenderUsersPage(@PathVariable String username) {
		userService.disableUserByUsername(username);

		return new ModelAndView("redirect:/users");
	}

	@RequestMapping("/changePassword")
	public ModelAndView renderUserFormWithUser(Principal principal) {
		ModelAndView model = new ModelAndView("userForm");

		model.setViewName("userForm");
		model.addObject("user", getUserWithoutPassword(principal.getName()));
		model.addObject("roles", roles);

		return model;
	}
	
	private User getUserWithoutPassword(String username) {
		User user = userService.findUserByUsername(username);
		user.setPassword("");
		
		return user;
	}

}
